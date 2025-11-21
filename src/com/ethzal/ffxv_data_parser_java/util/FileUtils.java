/*
 * File: FileUtils.java
 * Funcion: Herramienta para leer y transformar datos binarios.
 *
 * Autor: Ethzal
 * Version: 1.0
 */

package com.ethzal.ffxv_data_parser_java.util;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class FileUtils {

    /**
     * Lee un entero sin signo de 4 bytes (UInt32) de forma Little-Endian.
     * @param raf Objeto RandomAccessFile.
     * @return El valor leído como long (para evitar desbordamiento de int).
     * @throws IOException Si ocurre un error de lectura.
     */
    public static long readUInt32LE(RandomAccessFile raf) throws IOException {
        int b0 = raf.readUnsignedByte();
        int b1 = raf.readUnsignedByte();
        int b2 = raf.readUnsignedByte();
        int b3 = raf.readUnsignedByte();
        return ((long)b3 << 24) | ((long)b2 << 16) | ((long)b1 << 8) | (long)b0;
    }

    /**
     * Lee un entero de 4 bytes (int) de forma Little-Endian desde una posición relativa.
     * @param raf Objeto RandomAccessFile.
     * @param entryStart Offset de inicio de la entrada.
     * @param relativeOffset Offset relativo dentro de la entrada.
     * @return El valor entero leído.
     * @throws IOException Si ocurre un error de lectura.
     */
    public static int readIntAtRelativeOffset(RandomAccessFile raf, long entryStart, int relativeOffset) throws IOException {
        raf.seek(entryStart + relativeOffset);
        // ByteBuffer para asegurar la lectura Little-Endian de un int
        byte[] bytes = new byte[4];
        raf.readFully(bytes);
        return ByteBuffer.wrap(bytes).order(ByteOrder.LITTLE_ENDIAN).getInt();
    }

    /**
     * Lee un flotante de 4 bytes (float) de forma Little-Endian desde una posición relativa.
     * @param raf Objeto RandomAccessFile.
     * @param entryStart Offset de inicio de la entrada.
     * @param relativeOffset Offset relativo dentro de la entrada.
     * @return El valor flotante leído.
     * @throws IOException Si ocurre un error de lectura.
     */
    public static float readFloatAtRelativeOffset(RandomAccessFile raf, long entryStart, int relativeOffset) throws IOException {
        raf.seek(entryStart + relativeOffset);
        // ByteBuffer para asegurar la lectura Little-Endian de un float
        byte[] bytes = new byte[4];
        raf.readFully(bytes);
        return ByteBuffer.wrap(bytes).order(ByteOrder.LITTLE_ENDIAN).getFloat();
    }

    /**
     * Limpia el nombre de un enemigo para que sea un identificador válido y corrige inconsistencias.
     * Reemplaza espacios y caracteres no alfanuméricos por guiones bajos.
     * @param name Nombre original.
     * @return Nombre limpio.
     */
    public static String sanitizeName(String name) {
        name = name.replace(" ", "_");         // Reemplazar espacios por _
        name = name.replaceAll("[^A-Za-z0-9_]", ""); // Eliminar cualquier carácter que no sea letra, número o '_'
        if (name.isEmpty()) return "_unknown"; // Asegurar que el nombre no sea vacío

        // Si empieza por un dígito, añadir '_' al principio para asegurar un identificador válido
        if (Character.isDigit(name.charAt(0))) {
            name = "_" + name;
        }
        return name;
    }

    /**
     *
     * @param filePath Ubicacion del archivo de ids unknown.
     * @param content Long del nuevo id unknown.
     * @throws IOException Error al escribir el archivo.
     */
    public static void appendToFile(String filePath, long content) throws IOException {
        try (FileWriter fw = new FileWriter(filePath, true);
             BufferedWriter bw = new BufferedWriter(fw)) {
            bw.newLine();
            bw.write(String.valueOf(content));
        }
    }
}