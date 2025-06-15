package javaapplication3;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.nio.file.*;
import java.util.*;

public class EncryptorGUI extends JFrame {

    public EncryptorGUI() {
        initComponents();
    }

    // initalize component & layout
    private void initComponents() {
        setTitle("Ghaida System - One Time Pad Encryptor 🔐");
        setSize(400, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JButton encryptButton = new JButton("Encrypt File");
        encryptButton.addActionListener(e -> encryptFile());
        panel.add(encryptButton);

        JButton decryptButton = new JButton("Decrypt File");
        decryptButton.addActionListener(e -> decryptFile());
        panel.add(decryptButton);

        JButton encryptFolderButton = new JButton("Encrypt Folder");
        encryptFolderButton.addActionListener(e -> encryptFolder());
        panel.add(encryptFolderButton);

        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(e -> System.exit(0));
        panel.add(exitButton);

        add(panel);
    }

    // Opens a file chooser to select and encrypt a single file
    private void encryptFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Select File to Encrypt");
        int option = fileChooser.showOpenDialog(this);

        if (option == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            if (!selectedFile.isFile()) {
                JOptionPane.showMessageDialog(this, "Please select a valid file",
                        "Invalid Selection", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                OneTimePadEncryptor.encrypt(selectedFile.getAbsolutePath());
                // Delete the original file after successful encryption
                Files.delete(selectedFile.toPath());
                JOptionPane.showMessageDialog(this,
                        "File encrypted successfully and original file deleted!\nOutput: " + selectedFile.getName() + ".enc",
                        "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(),
                        "Encryption Error", JOptionPane.ERROR_MESSAGE);
            } catch (IOException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Failed to encrypt or delete file: " + ex.getMessage(),
                        "Encryption Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Opens file choosers to select encrypted file and key file for decryption
    private void decryptFile() {
        JFileChooser encChooser = new JFileChooser();
        encChooser.setDialogTitle("Select Encrypted File (*.enc)");
        encChooser.setFileFilter(new javax.swing.filechooser.FileFilter() {
            @Override
            public boolean accept(File f) {
                return f.getName().endsWith(".enc");
            }

            @Override
            public String getDescription() {
                return "Encrypted Files (*.enc)";
            }
        });
        int encOption = encChooser.showOpenDialog(this);

        if (encOption == JFileChooser.APPROVE_OPTION) {
            File encFile = encChooser.getSelectedFile();
            if (!encFile.getName().endsWith(".enc")) {
                JOptionPane.showMessageDialog(this, "Please select a .enc file",
                        "Invalid File", JOptionPane.ERROR_MESSAGE);
                return;
            }
            // Level 2
            JFileChooser keyChooser = new JFileChooser();
            keyChooser.setDialogTitle("Select Key File (*.key)");
            keyChooser.setFileFilter(new javax.swing.filechooser.FileFilter() {
                @Override
                public boolean accept(File f) {
                    return f.getName().endsWith(".key");
                }

                @Override
                public String getDescription() {
                    return "Key Files (*.key)";
                }
            });
            int keyOption = keyChooser.showOpenDialog(this);

            if (keyOption == JFileChooser.APPROVE_OPTION) {
                File keyFile = keyChooser.getSelectedFile();
                if (!keyFile.getName().endsWith(".key")) {
                    JOptionPane.showMessageDialog(this, "Please select a .key file",
                            "Invalid File", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                try {
                    OneTimePadEncryptor.decrypt(encFile.getAbsolutePath(), keyFile.getAbsolutePath());
                    JOptionPane.showMessageDialog(this,
                            "File decrypted successfully!\nOutput: "
                            + encFile.getName().replace(".enc", ".decrypted"),
                            "Success", JOptionPane.INFORMATION_MESSAGE);
                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(),
                            "Decryption Error", JOptionPane.ERROR_MESSAGE);
                } catch (IOException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(this, "Failed to decrypt file: " + ex.getMessage(),
                            "Decryption Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    // Open a folder chooser to select and encrypt all files in a folder
    private void encryptFolder() {
        JFileChooser folderChooser = new JFileChooser();
        folderChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        folderChooser.setDialogTitle("Select Folder to Encrypt");
        int option = folderChooser.showOpenDialog(this);

        if (option == JFileChooser.APPROVE_OPTION) {
            File folder = folderChooser.getSelectedFile();
            if (!folder.isDirectory()) {
                JOptionPane.showMessageDialog(this, "Please select a valid folder",
                        "Invalid Selection", JOptionPane.ERROR_MESSAGE);
                return;
            }

            File[] files = folder.listFiles((dir, name) -> !name.endsWith(".enc") && !name.endsWith(".key"));
            if (files == null || files.length == 0) {
                JOptionPane.showMessageDialog(this, "Folder is empty or contains no valid files",
                        "Empty Folder", JOptionPane.WARNING_MESSAGE);
                return;
            }

            int successCount = 0;
            int failureCount = 0;
            for (File file : files) {
                if (file.isFile()) {
                    try {
                        OneTimePadEncryptor.encrypt(file.getAbsolutePath());
                        // Delete the original file after successful encryption
                        Files.delete(file.toPath());
                        successCount++;
                    } catch (IOException | IllegalArgumentException ex) {
                        System.err.println("Error encrypting or deleting file: " + file.getName() + " - " + ex.getMessage());
                        failureCount++;
                    }
                }
            }
            String message = String.format("Encrypted %d file(s) successfully and originals deleted.\nFailed to encrypt %d file(s)",
                    successCount, failureCount);
            JOptionPane.showMessageDialog(this, message, "Folder Encryption Result",
                    successCount > 0 ? JOptionPane.INFORMATION_MESSAGE : JOptionPane.WARNING_MESSAGE);
        }
    }

    // Main
    public static void main(String[] args) throws Exception {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        SwingUtilities.invokeLater(() -> new EncryptorGUI().setVisible(true));
    }
}