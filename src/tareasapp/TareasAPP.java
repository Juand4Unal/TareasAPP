/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package tareasapp;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TareasAPP extends JFrame {
    private TareaManager tareaManager;
    private JTextField nombreField;
    private JTextArea descripcionArea;
    private JList<Tarea> listaTareas;
    private DefaultListModel<Tarea> listModel;

    public TareasAPP() {
        tareaManager = new TareaManager();
        listModel = new DefaultListModel<>();
        listaTareas = new JList<>(listModel);
        setTitle("Gestor de Tareas");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 0, 10);
        add(new JLabel("Nombre de la tarea:"), gbc);

        nombreField = new JTextField(20);
        gbc.gridx = 1;
        add(nombreField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        add(new JLabel("Descripción:"), gbc);

        descripcionArea = new JTextArea(5, 20);
        JScrollPane descripcionScroll = new JScrollPane(descripcionArea);
        gbc.gridx = 1;
        add(descripcionScroll, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        JButton guardarButton = new JButton("Guardar Tarea");
        guardarButton.addActionListener((ActionEvent e) -> {
            String nombre = nombreField.getText();
            String descripcion = descripcionArea.getText();
            tareaManager.guardarTarea(nombre, descripcion);
            listModel.addElement(new Tarea(nombre, descripcion));
            nombreField.setText("");
            descripcionArea.setText("");
        });
        add(guardarButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH;
        listaTareas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        add(new JScrollPane(listaTareas), gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        JButton abrirButton = new JButton("Abrir Tarea");
        abrirButton.addActionListener((ActionEvent e) -> {
            int selectedIndex = listaTareas.getSelectedIndex();
            if (selectedIndex != -1) {
                Tarea tarea = tareaManager.obtenerTarea(selectedIndex);
                abrirVentanaTarea(tarea, selectedIndex);
            } else {
                JOptionPane.showMessageDialog(null, "Selecciona una tarea para abrir.");
            }
        });
        add(abrirButton, gbc);

        // Cargar tareas desde el archivo al inicio
        cargarTareas();
    }

    private void cargarTareas() {
        for (Tarea tarea : tareaManager.tareas) {
            listModel.addElement(tarea);
        }
    }

    private void abrirVentanaTarea(Tarea tarea, int index) {
        JDialog dialog = new JDialog(this, "Detalles de Tarea", true);
        dialog.setSize(400, 300);
        dialog.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 0;
        dialog.add(new JLabel("Nombre de la tarea:"), gbc);
        JTextField nombreFieldDialog = new JTextField(tarea.getNombre(), 20);
        gbc.gridx = 1;
        dialog.add(nombreFieldDialog, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        dialog.add(new JLabel("Descripción de la tarea:"), gbc);
        JTextArea descripcionAreaDialog = new JTextArea(tarea.getDescripcion(), 5, 20);
        JScrollPane descripcionScroll = new JScrollPane(descripcionAreaDialog);
        gbc.gridx = 1;
        dialog.add(descripcionScroll, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        JPanel buttonPanel = new JPanel();

        JButton guardarButton = new JButton("Guardar Cambios");
        guardarButton.addActionListener((ActionEvent e) -> {
            tareaManager.eliminarTarea(index);
            tareaManager.guardarTarea(nombreFieldDialog.getText(), descripcionAreaDialog.getText());
            listModel.set(index, new Tarea(nombreFieldDialog.getText(), descripcionAreaDialog.getText()));
            dialog.dispose();
        });
        buttonPanel.add(guardarButton);

        JButton eliminarButton = new JButton("Eliminar Tarea");
        eliminarButton.addActionListener((ActionEvent e) -> {
            tareaManager.eliminarTarea(index);
            listModel.remove(index);
            dialog.dispose();
        });
        buttonPanel.add(eliminarButton);

        dialog.add(buttonPanel, gbc);
        dialog.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TareasAPP app = new TareasAPP();
            app.setVisible(true);
        });
    }
}














