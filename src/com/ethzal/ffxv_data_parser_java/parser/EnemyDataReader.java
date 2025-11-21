/*
 * File: EnemyDataReader.java
 * Funcion: Clase principal para orquestar la lectura, cacheo y parsing de datos de enemigos.
 *
 * Autor: Ethzal
 * Version: 1.0
 */

package com.ethzal.ffxv_data_parser_java.parser;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Scanner;

import com.ethzal.ffxv_data_parser_java.models.Enemy;
import com.ethzal.ffxv_data_parser_java.util.FileUtils;

public class EnemyDataReader {

    // --- Constantes de Configuración de Archivo Binario ---
    private static final int ENEMY_COUNT = 935;
    private static final long ENEMY_BASE_OFFSET = 0xA2AB; // Offset absoluto de la primera entrada de enemigo
    private static final int ENEMY_ENTRY_SIZE = 0x448;    // Tamaño de cada entrada de enemigo en bytes

    // --- OFFSETS RELATIVOS FIJOS: Mapeo de Estructura Binaria (Descubierto mediante Ingeniería Inversa) ---
    // Estos valores representan el desplazamiento exacto en bytes desde el inicio de cada entrada (0x00).
    // Han sido validados mediante la creación de una plantilla en 010 Editor y pruebas de lectura/escritura
    // en el archivo binario. Son constantes porque reflejan la estructura inmutable del formato.
    private static final int REL_SPIRIT_OFFSET = 0x08;
    private static final int REL_HP_OFFSET = 0x0C;
    private static final int REL_MAGIC_OFFSET = 0x10;
    private static final int REL_ATTACK_OFFSET = 0x1C;
    private static final int REL_EXP_OFFSET = 0x20;
    private static final int REL_DEF_OFFSET = 0x1F4;
    private static final int REL_SPEED1_OFFSET = 0x178;
    private static final int REL_SPEED2_OFFSET = 0x17C;
    private static final int REL_SPEED3_OFFSET = 0x180;
    private static final int REL_POISE_OFFSET = 0x3F4;

    // --- Configuración de Nombres de Enemigos ---
    private static final String NAMES_FILE_PATH = "data/enemy_names.bin";
    private static final long NAMES_START_OFFSET = 0x124; // Offset absoluto donde empiezan los registros de nombres
    private static final int NAME_RECORD_SIZE = 8;        // Tamaño de cada registro de nombre (ID + OffsetString)
    private static final int STRING_OFFSET_ADDITION = 0x120; // Ajuste para el offset real del texto del nombre

    // --- Configuración Externa para IDs Desconocidos ---
    private static final String UNKNOWN_IDS_FILE_PATH = "data/unknown_enemy_ids.txt";

    // --- Estructuras de Datos en Memoria ---
    private final Map<Long, String> enemyNamesCache = new HashMap<>(); // Cache para nombres de enemigos
    private final Set<Long> unknownEnemyIdsSet = new HashSet<>();     // Set para IDs desconocidos cargados externamente
    private final String enemiesFilePath;                             // Ruta al archivo binario principal de enemigos

    /**
     * Constructor que inicializa el lector y carga los datos de configuración.
     * @param enemiesFilePath Ruta al archivo binario principal de enemigos.
     * @throws IOException Si ocurre un error al cargar los nombres o los IDs desconocidos.
     */
    public EnemyDataReader(String enemiesFilePath) throws IOException {
        this.enemiesFilePath = enemiesFilePath;
        loadEnemyNamesCache();     // Carga todos los nombres al inicio (optimización)
        loadUnknownEnemyIds();     // Carga los IDs desconocidos desde el archivo externo
    }

    /**
     * Carga todos los nombres de enemigos en un cache en memoria para evitar reabrir el archivo NAMES_FILE_PATH
     * y realizar búsquedas repetitivas, mejorando drásticamente el rendimiento.
     * @throws IOException Si ocurre un error de lectura durante la carga.
     */
    private void loadEnemyNamesCache() throws IOException {
        try (RandomAccessFile raf = new RandomAccessFile(NAMES_FILE_PATH, "r")) {
            long fileLength = raf.length();
            long offset = NAMES_START_OFFSET;

            while (offset + NAME_RECORD_SIZE <= fileLength) { // Asegurar que hay espacio para leer un registro completo
                raf.seek(offset);
                long id = FileUtils.readUInt32LE(raf);
                long stringBinOffset = FileUtils.readUInt32LE(raf);

                long textPos = stringBinOffset + STRING_OFFSET_ADDITION;
                // Validación para evitar leer fuera de los límites del archivo de nombres
                if (textPos < fileLength && textPos >= 0) { // textPos debe ser válido y positivo
                    raf.seek(textPos);
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    int b;
                    while ((b = raf.read()) != -1 && b != 0) { // Lee hasta encontrar un byte nulo (terminador de cadena)
                        baos.write(b);
                    }
                    String name = new String(baos.toByteArray(), StandardCharsets.UTF_8);
                    enemyNamesCache.put(id, FileUtils.sanitizeName(name));
                }
                offset += NAME_RECORD_SIZE; // Siguiente registro de nombre
            }
        }
    }

    /**
     * Carga los IDs de enemigos desconocidos desde un archivo de texto externo.
     * Esto externaliza la configuración y evita el hardcoding directo en el código.
     * @throws IOException Si el archivo UNKNOWN_IDS_FILE_PATH no se puede leer.
     */
    private void loadUnknownEnemyIds() throws IOException {
        File unknownIdsFile = new File(UNKNOWN_IDS_FILE_PATH);
        if (!unknownIdsFile.exists()) {
            throw new FileNotFoundException("Archivo de IDs desconocidos no encontrado: " + UNKNOWN_IDS_FILE_PATH);
        }
        try (Scanner scanner = new Scanner(unknownIdsFile)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (!line.isEmpty() && !line.startsWith("#")) { // Ignorar líneas vacías y comentarios
                    try {
                        unknownEnemyIdsSet.add(Long.parseLong(line));
                    } catch (NumberFormatException e) {
                        System.err.println("Advertencia: Línea no válida en " + UNKNOWN_IDS_FILE_PATH + ", se esperaba un número: " + line);
                    }
                }
            }
        }
    }

    /**
     * Lee los datos de un enemigo específico del archivo binario principal.
     * @param enemyIndex El índice del enemigo a leer (0 a ENEMY_COUNT-1).
     * @return Un objeto com.ethzal.ffxv_tooling.models.Enemy con los datos leídos y parseados.
     * @throws IOException Si ocurre un error de lectura.
     * @throws IllegalArgumentException Si el índice del enemigo está fuera de rango.
     */
    public Enemy readEnemy(int enemyIndex) throws IOException {
        if (enemyIndex < 0 || enemyIndex >= ENEMY_COUNT) {
            throw new IllegalArgumentException("Índice de enemigo fuera de rango: " + enemyIndex);
        }

        try (RandomAccessFile enemies = new RandomAccessFile(enemiesFilePath, "r")) {
            long entryStartOffset = ENEMY_BASE_OFFSET + (long) enemyIndex * ENEMY_ENTRY_SIZE;

            // Validación de límites: Asegurar que la entrada completa cabe en el archivo
            if (entryStartOffset + ENEMY_ENTRY_SIZE > enemies.length() || entryStartOffset < 0) {
                throw new IOException("Intento de leer fuera de los límites del archivo para el enemigo " + enemyIndex +
                        ". Offset: " + entryStartOffset + ", Tamaño entrada: " + ENEMY_ENTRY_SIZE + ", Tamaño archivo: " + enemies.length());
            }

            enemies.seek(entryStartOffset);

            // Leer el ID del enemigo (asumiendo que está al inicio de la entrada o en un offset fijo)
            long enemyId = FileUtils.readUInt32LE(enemies); // Leer ID desde el inicio de la entrada para este ejemplo

            // Volver al inicio de la entrada para leer los demás datos con offsets relativos
            enemies.seek(entryStartOffset);

            // Leer todos los valores utilizando los offsets relativos
            int spirit = FileUtils.readIntAtRelativeOffset(enemies, entryStartOffset, REL_SPIRIT_OFFSET);
            int hp = FileUtils.readIntAtRelativeOffset(enemies, entryStartOffset, REL_HP_OFFSET);
            int magic = FileUtils.readIntAtRelativeOffset(enemies, entryStartOffset, REL_MAGIC_OFFSET);
            int attack = FileUtils.readIntAtRelativeOffset(enemies, entryStartOffset, REL_ATTACK_OFFSET);
            int defense = FileUtils.readIntAtRelativeOffset(enemies, entryStartOffset, REL_DEF_OFFSET);
            int exp = FileUtils.readIntAtRelativeOffset(enemies, entryStartOffset, REL_EXP_OFFSET);
            float speed1 = FileUtils.readFloatAtRelativeOffset(enemies, entryStartOffset, REL_SPEED1_OFFSET);
            float speed2 = FileUtils.readFloatAtRelativeOffset(enemies, entryStartOffset, REL_SPEED2_OFFSET);
            float speed3 = FileUtils.readFloatAtRelativeOffset(enemies, entryStartOffset, REL_SPEED3_OFFSET);
            float poise = FileUtils.readFloatAtRelativeOffset(enemies, entryStartOffset, REL_POISE_OFFSET);

            String name = getEnemyName(enemyId);

            return new Enemy(enemyId, name, spirit, hp, magic, attack, defense, exp, speed1, speed2, speed3, poise);
        }
    }

    /**
     * Obtiene el nombre del enemigo. Prioriza el caché, luego comprueba IDs desconocidos,
     * y si es un ID nuevo, lo busca en el binario principal y lo añade al caché y a la lista de desconocidos si falla.
     * @param id ID del enemigo.
     * @return El nombre del enemigo o "_unknown" si es un ID conocido como desconocido.
     */
    private String getEnemyName(long id) {
        // 1. Busqueda en el cache
        String cachedName = enemyNamesCache.get(id);
        if (cachedName != null) {
            return cachedName;
        }

        // 2. Busqueda en lista de unknown ids
        if (unknownEnemyIdsSet.contains(id)) {
            return "_unknown";
        }

        // 3. Es nuevo id
        unknownEnemyIdsSet.add(id);

        // Llama a FileUtils para registrarlo en el TXT externo
        try {
            FileUtils.appendToFile(UNKNOWN_IDS_FILE_PATH, id);
        } catch (IOException e) {
            System.err.println("ADVERTENCIA: No se pudo guardar el nuevo ID desconocido: " + id);
        }

        return "_unknown";
    }

    /**
     * Procesa todos los datos de los enemigos y escribe el resultado en el archivo de salida especificado.
     * @param outputFile Ruta del archivo de salida (ej: "output/010_elements.txt").
     * @throws IOException Si ocurre un error de escritura.
     */
    public void generateOutputFile(String outputFile) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
            for (int i = 0; i < ENEMY_COUNT; i++) {
                Enemy enemy = readEnemy(i);

                // Escribir línea tipo ParamTable_Element usando el nombre limpio
                writer.write("ParamTable_Element " + enemy.getName() + ";");
                writer.newLine();

                // Imprimir en consola para ver el progreso
                System.out.printf("Processed Enemy[%d] -> ID: %d -> Name: %s%n", i, enemy.getId(), enemy.getName());

            }
        }
    }
}