package com.clientView;

import com.clientController.*;

import javax.swing.*;
import java.awt.*;

public class LoginView extends JFrame {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField emailField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private ClientController controller;

    public LoginView() {
        controller = new ClientController();
        initComponents();
    }

    private void initComponents() {
        setTitle("Ecoembes - Login");
        setSize(350, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Panel principal
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Email
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Email:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        emailField = new JTextField(20);
        panel.add(emailField, gbc);

        // Password
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0;
        panel.add(new JLabel("Contraseña:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        passwordField = new JPasswordField(20);
        panel.add(passwordField, gbc);

        // Boton Login
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.weightx = 0;
        loginButton = new JButton("Iniciar Sesión");
        panel.add(loginButton, gbc);

        add(panel);

        // Accion del boton
        loginButton.addActionListener(e -> realizarLogin());

        // Enter para hacer login
        passwordField.addActionListener(e -> realizarLogin());
    }

    private void realizarLogin() {
        String email = emailField.getText().trim();
        String password = new String(passwordField.getPassword());

        if (email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Por favor, introduce email y contraseña",
                    "Campos vacíos",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (controller.login(email, password)) {
            JOptionPane.showMessageDialog(this,
                    "¡Bienvenido/a!",
                    "Login exitoso",
                    JOptionPane.INFORMATION_MESSAGE);

            // Abrir ventana principal
            new MainView(controller).setVisible(true);
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this,
                    "Email o contraseña incorrectos",
                    "Error de login",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}