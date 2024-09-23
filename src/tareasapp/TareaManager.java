/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tareasapp;


import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class TareaManager {
    private static final String FILE_NAME = "tareas.txt";
    List<Tarea> tareas;

    public TareaManager() {
        tareas = new ArrayList<>();
        cargarTareas();
    }

    public void guardarTarea(String nombre, String descripcion) {
        Tarea tarea = new Tarea(nombre, descripcion);
        tareas.add(tarea);
        guardarTareasEnArchivo();
    }

    public void eliminarTarea(int index) {
        if (index >= 0 && index < tareas.size()) {
            tareas.remove(index);
            guardarTareasEnArchivo();
        }
    }

    public Tarea obtenerTarea(int index) {
        return tareas.get(index);
    }

    public void cargarTareas() {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length == 2) {
                    tareas.add(new Tarea(parts[0], parts[1]));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void guardarTareasEnArchivo() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Tarea tarea : tareas) {
                bw.write(tarea.getNombre() + ";" + tarea.getDescripcion());
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}






