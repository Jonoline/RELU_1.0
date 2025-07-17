
package ui;

import datos.Json;
import datos.Usuario;
import logica.GestorUsuarios;

import javax.swing.*;
import java.awt.*;


public class ViewInicioSesion extends JFrame {
    private final Json json = new Json();
    private final GestorUsuarios gestorUsuarios = new GestorUsuarios();
    //se establecen atributos necesarios para el Jframe
    private JLabel labelTitulo;
    private JLabel labelInicioSesionUsuario;
    private JLabel labelInicioSesionClave;
    private JTextField textMatricula;
    private JPasswordField textClave;
    private JButton botonConfirmacion;
    private JButton botonSalir;

    private JPanel panelPrincipal;

    public ViewInicioSesion() {
        configurarVentana();
        inicializarComponentes();
        configurarEventos();
    }

    private void configurarVentana() {
        setTitle("Sistema RELU");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setIconImage(new ImageIcon("icons/relu.png").getImage());
    }

    private void inicializarComponentes() {
        // Inicialización de componentes
        labelTitulo = new JLabel("LOGIN RELU", SwingConstants.CENTER);
        labelTitulo.setFont(new Font("Arial", Font.BOLD, 24));

        labelInicioSesionUsuario = new JLabel("Matrícula: ", SwingConstants.RIGHT);
        labelInicioSesionClave = new JLabel("Contraseña: ", SwingConstants.RIGHT);

        textMatricula = new JTextField(15);
        textClave = new JPasswordField(15);

        botonConfirmacion = new JButton("Iniciar Sesión");
        botonSalir = new JButton("Salir");

        // Panel principal con BorderLayout
        panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Panel para el título y logo
        JPanel panelTitulo = new JPanel(new FlowLayout(FlowLayout.CENTER));
        ImageIcon logoIcon = new ImageIcon("icons/relu.png");
        // Redimensionar el logo si es necesario
        Image img = logoIcon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        logoIcon = new ImageIcon(img);
        panelTitulo.add(new JLabel(logoIcon));
        panelTitulo.add(labelTitulo);

        // Panel para los campos de entrada
        JPanel panelCampos = new JPanel(new GridLayout(3, 2, 10, 10));
        panelCampos.add(labelInicioSesionUsuario);
        panelCampos.add(textMatricula);
        panelCampos.add(labelInicioSesionClave);
        panelCampos.add(textClave);
        // Panel para los botones
        JPanel panelBotones = new JPanel(new GridLayout(1, 2, 10, 0));
        panelBotones.add(botonConfirmacion);
        panelBotones.add(botonSalir);

        // Agregar los paneles al panel principal
        panelPrincipal.add(panelTitulo, BorderLayout.NORTH);
        panelPrincipal.add(panelCampos, BorderLayout.CENTER);
        panelPrincipal.add(panelBotones, BorderLayout.SOUTH);

        // Agregar panel principal al frame
        add(panelPrincipal);
    }

    private void configurarEventos() {

        // uso de clase anonima que implementa la interfaz ActionListener y sobreescribe su unico metodo
        botonConfirmacion.addActionListener(e -> iniciarSesion());

        botonSalir.addActionListener(e -> {
            json.crearBackups();
            System.exit(0);
        });
    }

    private void iniciarSesion() {
        String matricula = textMatricula.getText();
        String contrasena = new String(textClave.getPassword());
    //mensaje de error posicionado sobre el jframe
        if (matricula.isEmpty() || contrasena.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Por favor, complete todos los campos",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        Usuario intento = new Usuario(matricula, contrasena);
        Usuario usuarioValidado = gestorUsuarios.iniciarSesion(intento);

        limpiarCampos();

        if (usuarioValidado == null) {
            JOptionPane.showMessageDialog(this,
                    "No se ha encontrado el usuario en el sistema",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        this.setVisible(false);

        if (gestorUsuarios.usuarioEsAdmin(usuarioValidado)) {
            new ViewMenuAdmin(usuarioValidado,gestorUsuarios).setVisible(true);
        } else {
            new ViewMenu( usuarioValidado).setVisible(true);
        }

        this.dispose();
    }

    private void limpiarCampos() {
        textMatricula.setText("");
        textClave.setText("");
        textMatricula.requestFocus(); // Opcional: Devuelve el foco al campo de matrícula
    }

}