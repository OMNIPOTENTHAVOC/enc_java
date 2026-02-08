package com.encryption;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class FileEncryptionTool extends JFrame {
    private JTextField passwordField;
    private JTextField inputFileField;
    private JTextField outputFileField;
    private String originalHash;
    
    public FileEncryptionTool() {
        setTitle("File Encryption Tool - AES-128 (Poor Impl Demo)");
        setSize(500, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(6, 2, 10, 10));
        
        add(new JLabel("Password (16 chars ideal):"));
        passwordField = new JTextField("weakpassword123456"); // Poor: Default weak pw [web:3]
        add(passwordField);
        
        add(new JLabel("Input File:"));
        inputFileField = new JTextField();
        inputFileField.setEditable(false);
        add(inputFileField);
        JButton selectInput = new JButton("Browse");
        selectInput.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                File file = chooser.getSelectedFile();
                inputFileField.setText(file.getAbsolutePath());
                try {
                    originalHash = CryptoUtils.computeSHA256(file);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Hash error: " + ex.getMessage());
                }
            }
        });
        add(selectInput);
        
        add(new JLabel("Output File:"));
        outputFileField = new JTextField();
        add(outputFileField);
        
        JButton encryptBtn = new JButton("Encrypt");
        encryptBtn.addActionListener(this::handleEncrypt);
        add(encryptBtn);
        
        JButton decryptBtn = new JButton("Decrypt");
        decryptBtn.addActionListener(this::handleDecrypt);
        add(decryptBtn);
        
        JButton validateBtn = new JButton("Validate Integrity");
        validateBtn.addActionListener(this::handleValidate);
        add(validateBtn);
        
        setVisible(true);
    }
    
    private void handleEncrypt(ActionEvent e) {
        processFile(true);
    }
    
    private void handleDecrypt(ActionEvent e) {
        processFile(false);
    }
    
    private void processFile(boolean encrypt) {
        try {
            File in = new File(inputFileField.getText());
            if (!in.exists()) {
                JOptionPane.showMessageDialog(this, "Input file not found!");
                return;
            }
            String suffix = encrypt ? ".enc" : ".dec";
            File out = new File(inputFileField.getText() + suffix);
            outputFileField.setText(out.getAbsolutePath());
            
            CryptoUtils.getKey(passwordField.getText());
            if (encrypt) {
                CryptoUtils.encrypt(in, out, CryptoUtils.getKey(passwordField.getText()));
            } else {
                CryptoUtils.decrypt(in, out, CryptoUtils.getKey(passwordField.getText()));
            }
            JOptionPane.showMessageDialog(this, "Operation complete! Output: " + out.getAbsolutePath());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage() + "\n(Poor impl demo: Fixed IV/weak key fails here)");
        }
    }
    
    private void handleValidate(ActionEvent e) {
        try {
            File dec = new File(outputFileField.getText());
            if (!dec.exists()) {
                JOptionPane.showMessageDialog(this, "No decrypted file!");
                return;
            }
            String newHash = CryptoUtils.computeSHA256(dec);
            boolean match = originalHash != null && originalHash.equals(newHash);
            JOptionPane.showMessageDialog(this, "Integrity: " + (match ? "PASS" : "FAIL") + "\nOriginal: " + originalHash + "\nNew: " + newHash);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Validate error: " + ex.getMessage());
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(FileEncryptionTool::new);
    }
}
