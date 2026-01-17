package com.clientView;

import com.clientController.*;

import javax.swing.*;
import java.awt.*;

public class MainView extends JFrame {

    private ClientController controller;

    private JPanel cardPanel;
    private CardLayout cardLayout;

    // Panel Crear Contenedor
    private JTextField txtContenedorId;
    private JTextField txtContenedorUbicacion;
    private JTextField txtContenedorCapacidad;
    private JTextField txtContenedorCodigoPostal;

    // Panel Consultar Estado Contenedor
    private JTextField txtEstadoId;
    private JTextField txtEstadoFechaInicio;
    private JTextField txtEstadoFechaFin;
    private JTextArea txtEstadoResultado;

    // Panel Consultar Capacidad Planta
    private JTextField txtPlantaId;
    private JTextField txtPlantaFecha;
    private JTextArea txtPlantaResultado;

    // Panel Asignar Contenedores
    private JTextField txtAsignarPlanta;
    private JTextField txtAsignarContenedores;
    private JTextArea txtAsignarResultado;

    public MainView(ClientController controller) {
        this.controller = controller;
        initComponents();
    }

    private void initComponents() {
        setTitle("Ecoembes - Sistema de Gestión (" + controller.getEmailUsuario() + ")");
        setSize(750, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setLayout(new BorderLayout());

        // Menu lateral
        JPanel menuPanel = crearMenuLateral();
        add(menuPanel, BorderLayout.WEST);

        // Panel de contenido con CardLayout
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        cardPanel.add(crearPanelBienvenida(), "BIENVENIDA");
        cardPanel.add(crearPanelCrearContenedor(), "CREAR_CONTENEDOR");
        cardPanel.add(crearPanelConsultarEstado(), "CONSULTAR_ESTADO");
        cardPanel.add(crearPanelConsultarPlanta(), "CONSULTAR_PLANTA");
        cardPanel.add(crearPanelAsignarContenedores(), "ASIGNAR");

        add(cardPanel, BorderLayout.CENTER);
    }

    private JPanel crearMenuLateral() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.setPreferredSize(new Dimension(220, 0));
        panel.setBackground(new Color(240, 240, 240));

        JLabel titulo = new JLabel("Menú Principal");
        titulo.setFont(new Font("Arial", Font.BOLD, 16));
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(titulo);
        panel.add(Box.createVerticalStrut(20));

        String[] opciones = {
                "Inicio",
                "Crear Contenedor",
                "Consultar Estado Contenedor",
                "Consultar Capacidad Planta",
                "Asignar Contenedores",
                "Cerrar Sesión"
        };

        String[] cards = {
                "BIENVENIDA",
                "CREAR_CONTENEDOR",
                "CONSULTAR_ESTADO",
                "CONSULTAR_PLANTA",
                "ASIGNAR",
                "LOGOUT"
        };

        for (int i = 0; i < opciones.length; i++) {
            JButton btn = new JButton(opciones[i]);
            btn.setAlignmentX(Component.CENTER_ALIGNMENT);
            btn.setMaximumSize(new Dimension(200, 35));
            final String card = cards[i];

            btn.addActionListener(e -> {
                if (card.equals("LOGOUT")) {
                    realizarLogout();
                } else {
                    cardLayout.show(cardPanel, card);
                }
            });

            panel.add(btn);
            panel.add(Box.createVerticalStrut(10));
        }

        return panel;
    }

    private JPanel crearPanelBienvenida() {
        JPanel panel = new JPanel(new BorderLayout());
        
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        
        JLabel label = new JLabel("Bienvenido al Sistema de Gestión Ecoembes");
        label.setFont(new Font("Arial", Font.BOLD, 20));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel sublabel = new JLabel("Seleccione una opción del menú lateral");
        sublabel.setFont(new Font("Arial", Font.PLAIN, 14));
        sublabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        centerPanel.add(Box.createVerticalGlue());
        centerPanel.add(label);
        centerPanel.add(Box.createVerticalStrut(10));
        centerPanel.add(sublabel);
        centerPanel.add(Box.createVerticalGlue());
        
        panel.add(centerPanel, BorderLayout.CENTER);
        return panel;
    }

    private JPanel crearPanelCrearContenedor() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Titulo
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        JLabel titulo = new JLabel("Crear Nuevo Contenedor");
        titulo.setFont(new Font("Arial", Font.BOLD, 16));
        panel.add(titulo, gbc);

        gbc.gridwidth = 1;

        // ID
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("ID:"), gbc);
        gbc.gridx = 1;
        txtContenedorId = new JTextField(20);
        panel.add(txtContenedorId, gbc);

        // Ubicacion
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(new JLabel("Ubicación:"), gbc);
        gbc.gridx = 1;
        txtContenedorUbicacion = new JTextField(20);
        panel.add(txtContenedorUbicacion, gbc);

        // Capacidad
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(new JLabel("Capacidad:"), gbc);
        gbc.gridx = 1;
        txtContenedorCapacidad = new JTextField(20);
        panel.add(txtContenedorCapacidad, gbc);

        // Codigo Postal
        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(new JLabel("Código Postal:"), gbc);
        gbc.gridx = 1;
        txtContenedorCodigoPostal = new JTextField(20);
        panel.add(txtContenedorCodigoPostal, gbc);

        // Boton
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        JButton btnCrear = new JButton("Crear Contenedor");
        btnCrear.addActionListener(e -> crearContenedor());
        panel.add(btnCrear, gbc);

        return panel;
    }

    private JPanel crearPanelConsultarEstado() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Titulo
        JLabel titulo = new JLabel("Consultar Estado de Contenedor");
        titulo.setFont(new Font("Arial", Font.BOLD, 16));
        panel.add(titulo, BorderLayout.NORTH);

        // Panel central con campos
        JPanel centerPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        centerPanel.add(new JLabel("ID Contenedor:"), gbc);
        gbc.gridx = 1;
        txtEstadoId = new JTextField(15);
        centerPanel.add(txtEstadoId, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        centerPanel.add(new JLabel("Fecha Inicio (YYYY-MM-DD):"), gbc);
        gbc.gridx = 1;
        txtEstadoFechaInicio = new JTextField(15);
        centerPanel.add(txtEstadoFechaInicio, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        centerPanel.add(new JLabel("Fecha Fin (YYYY-MM-DD):"), gbc);
        gbc.gridx = 1;
        txtEstadoFechaFin = new JTextField(15);
        centerPanel.add(txtEstadoFechaFin, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        JButton btnConsultar = new JButton("Consultar");
        btnConsultar.addActionListener(e -> consultarEstadoContenedor());
        centerPanel.add(btnConsultar, gbc);

        gbc.gridy = 4;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        txtEstadoResultado = new JTextArea(10, 30);
        txtEstadoResultado.setEditable(false);
        centerPanel.add(new JScrollPane(txtEstadoResultado), gbc);

        panel.add(centerPanel, BorderLayout.CENTER);

        return panel;
    }

    private JPanel crearPanelConsultarPlanta() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Titulo
        JLabel titulo = new JLabel("Consultar Capacidad de Planta de Reciclaje");
        titulo.setFont(new Font("Arial", Font.BOLD, 16));
        panel.add(titulo, BorderLayout.NORTH);

        // Panel central
        JPanel centerPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        centerPanel.add(new JLabel("ID Planta (1=PlasSB, 2=ContSocket):"), gbc);
        gbc.gridx = 1;
        txtPlantaId = new JTextField(10);
        centerPanel.add(txtPlantaId, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        centerPanel.add(new JLabel("Fecha (YYYY-MM-DD):"), gbc);
        gbc.gridx = 1;
        txtPlantaFecha = new JTextField(10);
        centerPanel.add(txtPlantaFecha, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        JButton btnConsultar = new JButton("Consultar Capacidad");
        btnConsultar.addActionListener(e -> consultarCapacidadPlanta());
        centerPanel.add(btnConsultar, gbc);

        gbc.gridy = 3;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        txtPlantaResultado = new JTextArea(10, 30);
        txtPlantaResultado.setEditable(false);
        centerPanel.add(new JScrollPane(txtPlantaResultado), gbc);

        panel.add(centerPanel, BorderLayout.CENTER);

        return panel;
    }

    private JPanel crearPanelAsignarContenedores() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Titulo
        JLabel titulo = new JLabel("Asignar Contenedores a Planta de Reciclaje");
        titulo.setFont(new Font("Arial", Font.BOLD, 16));
        panel.add(titulo, BorderLayout.NORTH);

        // Panel central
        JPanel centerPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        centerPanel.add(new JLabel("ID Planta:"), gbc);
        gbc.gridx = 1;
        txtAsignarPlanta = new JTextField(10);
        centerPanel.add(txtAsignarPlanta, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        centerPanel.add(new JLabel("IDs Contenedores (separados por coma):"), gbc);
        gbc.gridx = 1;
        txtAsignarContenedores = new JTextField(20);
        centerPanel.add(txtAsignarContenedores, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        JButton btnAsignar = new JButton("Asignar Contenedores");
        btnAsignar.addActionListener(e -> asignarContenedores());
        centerPanel.add(btnAsignar, gbc);

        gbc.gridy = 3;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        txtAsignarResultado = new JTextArea(10, 30);
        txtAsignarResultado.setEditable(false);
        centerPanel.add(new JScrollPane(txtAsignarResultado), gbc);

        panel.add(centerPanel, BorderLayout.CENTER);

        return panel;
    }

    // ==================== ACCIONES ====================

    private void crearContenedor() {
        try {
            String id = txtContenedorId.getText().trim();
            String ubicacion = txtContenedorUbicacion.getText().trim();
            int capacidad = Integer.parseInt(txtContenedorCapacidad.getText().trim());
            String codigoPostal = txtContenedorCodigoPostal.getText().trim();

            if (id.isEmpty() || ubicacion.isEmpty() || codigoPostal.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String resultado = controller.crearContenedor(id, ubicacion, capacidad, codigoPostal);

            if (resultado.contains("error")) {
                JOptionPane.showMessageDialog(this, "Error: " + resultado, "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Contenedor creado correctamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                txtContenedorId.setText("");
                txtContenedorUbicacion.setText("");
                txtContenedorCapacidad.setText("");
                txtContenedorCodigoPostal.setText("");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "La capacidad debe ser un número", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void consultarEstadoContenedor() {
        String id = txtEstadoId.getText().trim();
        String fechaInicio = txtEstadoFechaInicio.getText().trim();
        String fechaFin = txtEstadoFechaFin.getText().trim();

        if (id.isEmpty() || fechaInicio.isEmpty() || fechaFin.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String resultado = controller.consultarEstadoContenedor(id, fechaInicio, fechaFin);
        txtEstadoResultado.setText(formatearJson(resultado));
    }

    private void consultarCapacidadPlanta() {
        String idPlanta = txtPlantaId.getText().trim();
        String fecha = txtPlantaFecha.getText().trim();

        if (idPlanta.isEmpty() || fecha.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String resultado = controller.consultarCapacidadPlanta(idPlanta, fecha);
        txtPlantaResultado.setText(formatearJson(resultado));
    }

    private void asignarContenedores() {
        String idPlanta = txtAsignarPlanta.getText().trim();
        String contenedoresStr = txtAsignarContenedores.getText().trim();

        if (idPlanta.isEmpty() || contenedoresStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String[] contenedorIds = contenedoresStr.split(",");
        for (int i = 0; i < contenedorIds.length; i++) {
            contenedorIds[i] = contenedorIds[i].trim();
        }

        String resultado = controller.asignarContenedores(idPlanta, contenedorIds);
        txtAsignarResultado.setText(formatearJson(resultado));
    }

    private void realizarLogout() {
        int opcion = JOptionPane.showConfirmDialog(this,
                "¿Estás seguro de que quieres cerrar sesión?",
                "Cerrar Sesión",
                JOptionPane.YES_NO_OPTION);

        if (opcion == JOptionPane.YES_OPTION) {
            controller.logout();
            new LoginView().setVisible(true);
            this.dispose();
        }
    }

    private String formatearJson(String json) {
        if (json == null) return "Sin respuesta";
        return json.replace(",", ",\n  ").replace("{", "{\n  ").replace("}", "\n}");
    }
}