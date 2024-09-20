
package examen2;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.*;
import javax.swing.border.TitledBorder;

public class Main {
    
    private PSNUsers usuarios;
    private JFrame frame;
    private JTextField usuarioTxt, juegoTxt, usernameTxt;
    private JComboBox<Trophy> trofeosBox;
    private JTextArea usuarioInfo;
    
    public Main() throws IOException {
        usuarios = new PSNUsers();
        frame = new JFrame("Play Station Network");
        frame.setSize(650, 600);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(170, 200, 250));
        frame.add(panel);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        Font fuente = new Font("Consolas", Font.BOLD, 16);
        Font fuenteBtn = new Font("Consolas", Font.PLAIN, 16);
        Color colorBtn = new Color(150, 150, 150);
        
        JLabel usuarioLabel = new JLabel("Usuario:");
        usuarioLabel.setFont(fuente);
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(usuarioLabel, gbc);
        
        usuarioTxt = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 0;
        panel.add(usuarioTxt, gbc);
        
        JLabel trofeosLabel = new JLabel("Juego:");
        trofeosLabel.setFont(fuente);
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(trofeosLabel, gbc);
        
        juegoTxt = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(juegoTxt, gbc);
        
        JLabel nombreTrofeo = new JLabel("Nombre del trofeo:");
        nombreTrofeo.setFont(fuente);
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(nombreTrofeo, gbc);
        
        usernameTxt = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 2;
        panel.add(usernameTxt, gbc);
        
        JLabel tipoTrofeo = new JLabel("Tipo de trofeo:");
        tipoTrofeo.setFont(fuente);
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(tipoTrofeo, gbc);
        
        trofeosBox = new JComboBox<>(Trophy.values());
        gbc.gridx = 1;
        gbc.gridy = 3;
        panel.add(trofeosBox, gbc);
        
        JButton addBtn = new JButton("Agregar usuario");
        addBtn.setFont(fuenteBtn);
        addBtn.setBackground(colorBtn);
        addBtn.setForeground(Color.WHITE);
        addBtn.setFocusPainted(false);
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        addBtn.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(colorBtn, 1),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        addBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = usuarioTxt.getText();
                try {
                    if (usuarios.buscarUsername(username)) {
                        JOptionPane.showMessageDialog(null, "El usuario ya existe", 
                                "Error", JOptionPane.ERROR_MESSAGE);
                    } else {
                        usuarios.addUser(username);
                        JOptionPane.showMessageDialog(null, "Usuario creado!", "Creacion", 1);
                        limpiar();
                        actualizar();
                    }
                } catch (IOException ex) {
                    mostrarError(ex, "Error al agregar usuario");
                }
            }
        });
        panel.add(addBtn, gbc);
        
        JButton deactivateBtn = new JButton("Desactivar usuario");
        deactivateBtn.setFont(fuenteBtn);
        deactivateBtn.setBackground(colorBtn);
        deactivateBtn.setForeground(Color.WHITE);
        deactivateBtn.setFocusPainted(false);
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        deactivateBtn.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(colorBtn, 1),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        deactivateBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = usuarioTxt.getText();
                try {
                    if (!usuarios.buscarUsername(username)) {
                        JOptionPane.showMessageDialog(null, "El usuario no fue encontrado", 
                                "Error", JOptionPane.ERROR_MESSAGE);
                    } else {
                        usuarios.deactivateUser(username);
                        JOptionPane.showMessageDialog(null, "El usuario ha sido desactivado", 
                                "Desactivacion", 1);
                        limpiar();
                        actualizar();
                    }
                } catch (IOException ex) {
                    mostrarError(ex, "Error al desactivar usuario");
                }
            }
        });
        panel.add(deactivateBtn, gbc);
        
        JButton searchBtn = new JButton("Buscar usuario");
        searchBtn.setFont(fuenteBtn);
        searchBtn.setBackground(colorBtn);
        searchBtn.setForeground(Color.WHITE);
        searchBtn.setFocusPainted(false);
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        searchBtn.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(colorBtn, 1),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        searchBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = usuarioTxt.getText();
                try {
                    if (!usuarios.buscarUsername(username)) {
                        JOptionPane.showMessageDialog(null, "El usuario no fue encontrado", 
                                "Error", JOptionPane.ERROR_MESSAGE);
                    } else {
                        usuarioInfo.setText(usuarios.playerInfo(username));
                    }
                } catch (IOException ex) {
                    mostrarError(ex, "Error al buscar usuario");
                }
            }
        });
        panel.add(searchBtn, gbc);
        
        JButton addTrofeoBtn = new JButton("Agregar trofeo");
        addTrofeoBtn.setFont(fuenteBtn);
        addTrofeoBtn.setBackground(colorBtn);
        addTrofeoBtn.setForeground(Color.WHITE);
        addTrofeoBtn.setFocusPainted(false);
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 2;
        addTrofeoBtn.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(colorBtn, 1),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        addTrofeoBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = usuarioTxt.getText();
                String trophyGame = juegoTxt.getText();
                String trophyName = usernameTxt.getText();
                Trophy type = (Trophy) trofeosBox.getSelectedItem();
                try {
                    if (!usuarios.buscarUsername(username)) {
                        JOptionPane.showMessageDialog(null, "El usuario no fue encontrado", 
                                "Error", JOptionPane.ERROR_MESSAGE);
                    } else if (!usuarios.playerInfo(username).contains("Activo: Si")) {
                        JOptionPane.showMessageDialog(null, "El usuario no esta activo", 
                                "Error", JOptionPane.ERROR_MESSAGE);
                    } else {
                        usuarios.addTrophieTo(username, trophyGame, trophyName, type);
                        JOptionPane.showMessageDialog(null, "Trofeo agregado!", "Creacion", 1);
                        limpiar();
                        actualizar();
                    }
                } catch (IOException ex) {
                    mostrarError(ex, "Error al agregar trofeo");
                }
            }
        });
        panel.add(addTrofeoBtn, gbc);
        
        usuarioInfo = new JTextArea();
        usuarioInfo.setEditable(false);
        usuarioInfo.setFont(new Font("Consolas", Font.PLAIN, 14));
        usuarioInfo.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0), 1));
        
        JScrollPane scrollPane = new JScrollPane(usuarioInfo);
        TitledBorder titulo = BorderFactory.createTitledBorder("Informacion del usuario");
        titulo.setTitleFont(new Font("Consolas", Font.PLAIN, 12));
        scrollPane.setBorder(titulo);
        scrollPane.setBackground(colorBtn);
        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        panel.add(scrollPane, gbc);
        
        frame.setVisible(true);
    }
    
    private void limpiar() {
        usuarioTxt.setText("");
        juegoTxt.setText("");
        usernameTxt.setText("");
        trofeosBox.setSelectedIndex(0);
    }
    
    private void actualizar() {
        usuarioInfo.setText("");
    }
    
    private void mostrarError(Exception ex, String mensaje) {
        JOptionPane.showMessageDialog(null, mensaje + "\nDetalles: " 
                + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        ex.printStackTrace();
    }
    
    public static void main(String[] args) throws IOException {
        new Main();
    }
}
