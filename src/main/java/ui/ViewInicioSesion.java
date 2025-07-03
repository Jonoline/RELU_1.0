
package ui;

import datos.Json;
import datos.Usuario;
import logica.GestorUsuarios;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ViewInicioSesion extends JFrame {
    private final Json json = new Json();
    private final GestorUsuarios gestorUsuarios = new GestorUsuarios();

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

        // Configuración del panel principal con GridLayout
        panelPrincipal = new JPanel(new GridLayout(6, 2, 10, 10));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Agregando componentes al panel en orden
        panelPrincipal.add(new JLabel("")); // Espaciador
        panelPrincipal.add(labelTitulo);

        panelPrincipal.add(new JLabel("")); // Espaciador
        panelPrincipal.add(new JLabel("")); // Espaciador

        panelPrincipal.add(labelInicioSesionUsuario);
        panelPrincipal.add(textMatricula);

        panelPrincipal.add(labelInicioSesionClave);
        panelPrincipal.add(textClave);

        panelPrincipal.add(new JLabel("")); // Espaciador
        panelPrincipal.add(new JLabel("")); // Espaciador

        panelPrincipal.add(botonConfirmacion);
        panelPrincipal.add(botonSalir);

        // Agregar panel principal al frame
        add(panelPrincipal);
    }

    private void configurarEventos() {
        botonConfirmacion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                iniciarSesion();
            }
        });

        botonSalir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                json.crearBackups();
                System.exit(0);
            }
        });
    }

    private void iniciarSesion() {
        String matricula = textMatricula.getText();
        String contrasena = new String(textClave.getPassword());

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
            new MenuAdmin(usuarioValidado, gestorUsuarios).iniciar();
        } else {
            new Menu(usuarioValidado).iniciar();
        }

        this.dispose();
    }

    private void limpiarCampos() {
        textMatricula.setText("");
        textClave.setText("");
        textMatricula.requestFocus(); // Opcional: Devuelve el foco al campo de matrícula
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ViewInicioSesion view = new ViewInicioSesion();
            view.setVisible(true);
        });
    }
}