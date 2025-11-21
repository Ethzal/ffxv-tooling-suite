/*
 * File: MainApp.java
 * Funcion: Main que inicia el proyecto.
 *
 * Autor: Ethzal
 * Version: 1.0
 */

package com.ethzal.ffxv_data_parser_java;

import com.ethzal.ffxv_data_parser_java.parser.EnemyDataReader;

import java.io.FileNotFoundException;
import java.io.IOException;

public class MainApp {

    public static void main(String[] args) {
        // --- CONFIGURACIÓN DE RUTAS ---
        String enemiesFile = "data/enemies.bin";
        String outputFile = "output/010_elements_refactored.txt";

        System.out.println("INICIANDO PROCESO DE EXTRACCIÓN DE DATOS BINARIOS...");

        try {
            // 1. INICIALIZACIÓN: Se crea el lector, que automáticamente carga el cache y los IDs.
            EnemyDataReader reader = new EnemyDataReader(enemiesFile);

            // 2. EJECUCIÓN: El orquestador llama al método que procesa todo.
            reader.generateOutputFile(outputFile);

            System.out.println("\nPROCESO COMPLETADO CON ÉXITO.");
            System.out.println("Resultado guardado en: " + outputFile);

        } catch (FileNotFoundException e) {
            System.err.println("ERROR CRÍTICO: Faltan archivos de entrada. Verifica la carpeta /data/.");
            System.err.println("Detalle: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("ERROR DE LECTURA/ESCRITURA: Hubo un problema con los archivos binarios.");
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("ERROR INESPERADO DURANTE LA EJECUCIÓN.");
            e.printStackTrace();
        }
    }
}