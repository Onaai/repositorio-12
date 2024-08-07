package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

public class SimpleImageToBinaryConverter extends JFrame {

    public SimpleImageToBinaryConverter() {
        setTitle("Convertidor de Imagen a Binario");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Botón para elegir imagen
        JButton chooseImageButton = new JButton("Elegir Imagen");
        add(chooseImageButton, BorderLayout.CENTER);
        chooseImageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                chooseImage();
            }
        });

        setVisible(true);
    }

    private void chooseImage() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new javax.swing.filechooser.FileFilter() {
            @Override
            public boolean accept(File f) {
                return f.isDirectory() || f.getName().toLowerCase().endsWith(".png");
            }

            @Override
            public String getDescription() {
                return "Imágenes PNG (*.png)";
            }
        });
        int returnValue = fileChooser.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            convertImageToBinary(selectedFile);
        }
    }

    private void convertImageToBinary(File file) {
        try {
            byte[] fileContent = Files.readAllBytes(file.toPath());
            String encodedString = Base64.getEncoder().encodeToString(fileContent);
            System.out.println("Representación binaria: " + encodedString);
            saveBinaryToFile(encodedString, "imagen_binaria.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveBinaryToFile(String binaryData, String fileName) {
        try {
            Path desktopPath = Paths.get(System.getProperty("user.home"), "Desktop");
            Path filePath = desktopPath.resolve(fileName);
            Files.write(filePath, binaryData.getBytes());
            JOptionPane.showMessageDialog(this, "Archivo binario guardado en: " + filePath.toString(), "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al guardar el archivo binario.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new SimpleImageToBinaryConverter());
    }
}
