package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

public class BinaryToImageConverter extends JFrame {

    private JTextArea binaryTextArea;

    public BinaryToImageConverter() {
        setTitle("Convertidor de Binario a Imagen");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Área de texto para ingresar el binario
        binaryTextArea = new JTextArea();
        binaryTextArea.setLineWrap(true);
        binaryTextArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(binaryTextArea);
        scrollPane.setPreferredSize(new Dimension(380, 200));
        add(scrollPane, BorderLayout.CENTER);

        // Botón para descargar imagen
        JButton downloadButton = new JButton("Descargar Imagen");
        add(downloadButton, BorderLayout.SOUTH);
        downloadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                downloadImageFromBinary();
            }
        });

        setVisible(true);
    }

    private void downloadImageFromBinary() {
        String binaryString = binaryTextArea.getText();
        if (binaryString.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, ingrese los datos binarios para descargar una imagen.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            byte[] decodedBytes = Base64.getDecoder().decode(binaryString);
            Path desktopPath = Paths.get(System.getProperty("user.home"), "Desktop");
            Path outputPath = desktopPath.resolve("imagen_descargada.png");
            try (FileOutputStream fos = new FileOutputStream(outputPath.toFile())) {
                fos.write(decodedBytes);
                fos.flush();
            }
            JOptionPane.showMessageDialog(this, "Imagen descargada con éxito en: " + outputPath.toString(), "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al decodificar los datos binarios o al guardar la imagen.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new BinaryToImageConverter());
    }
}
