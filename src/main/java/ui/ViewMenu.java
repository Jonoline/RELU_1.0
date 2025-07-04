package ui;

import com.toedter.calendar.JCalendar;
import datos.*;
import logica.GestorLogias;
import logica.GestorReservas;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ViewMenu extends JFrame {
    private final Usuario usuario;
    private final GestorReservas gestorReservas;
    private final GestorLogias gestorLogias;
    private final Json json;

    // Paneles principales
    private JPanel panelPrincipal;
    private JPanel panelReservaActual;
    private JPanel panelBotones;

    // Área de texto para mostrar la reserva
    private JTextArea areaReserva;

    // Botones principales
    private JButton botonAgendarLogia;
    private JButton botonEliminarReserva;
    private JButton botonCerrarSesion;

    // Constructor
    public ViewMenu(Usuario usuario) {
        this.usuario = usuario;
        this.gestorReservas = new GestorReservas(usuario);
        this.gestorLogias = new GestorLogias();
        this.json = new Json();

        configurarVentana();
        inicializarComponentes();
        configurarEventos();
        actualizarReservaActual();
    }

    private void configurarVentana() {
        setTitle("RELU - Sistema de Reservas");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
    }

    private void inicializarComponentes() {
        // Panel principal con GridLayout
        panelPrincipal = new JPanel(new GridLayout(1, 2, 15, 15));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Panel izquierdo - Reserva actual
        panelReservaActual = new JPanel(new BorderLayout(10, 10));
        panelReservaActual.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(),
                "Reserva Actual",
                javax.swing.border.TitledBorder.CENTER,
                javax.swing.border.TitledBorder.TOP,
                new Font("Arial", Font.BOLD, 14)
        ));

        // Configuración del área de texto
        areaReserva = new JTextArea(15, 35);
        areaReserva.setEditable(false);
        areaReserva.setFont(new Font("Monospaced", Font.PLAIN, 14));
        areaReserva.setMargin(new Insets(10, 10, 10, 10));
        JScrollPane scrollReserva = new JScrollPane(areaReserva);
        scrollReserva.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelReservaActual.add(scrollReserva, BorderLayout.CENTER);

        // Panel derecho - Botones
        panelBotones = new JPanel(new GridLayout(4, 1, 15, 15));
        panelBotones.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Etiqueta de opciones
        JLabel labelOpciones = new JLabel("Opciones", SwingConstants.CENTER);
        labelOpciones.setFont(new Font("Arial", Font.BOLD, 16));

        // Inicialización de botones
        botonAgendarLogia = new JButton("Agendar Nueva Logia");
        botonEliminarReserva = new JButton("Eliminar Reserva Actual");
        botonCerrarSesion = new JButton("Cerrar Sesión");

        // Configuración visual de botones
        configurarBoton(botonAgendarLogia);
        configurarBoton(botonEliminarReserva);
        configurarBoton(botonCerrarSesion);

        // Agregar componentes al panel de botones
        panelBotones.add(labelOpciones);
        panelBotones.add(botonAgendarLogia);
        panelBotones.add(botonEliminarReserva);
        panelBotones.add(botonCerrarSesion);

        // Agregar paneles al panel principal
        panelPrincipal.add(panelReservaActual);
        panelPrincipal.add(panelBotones);

        // Agregar panel principal al frame
        add(panelPrincipal);
    }

    private void configurarBoton(JButton boton) {
        boton.setFocusPainted(false);
        boton.setFont(new Font("Arial", Font.BOLD, 14));
        boton.setBackground(new Color(70, 130, 180));
        boton.setForeground(Color.BLACK);
        boton.setBorder(BorderFactory.createRaisedBevelBorder());

        // Efectos hover
        boton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                boton.setBackground(new Color(100, 149, 237));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                boton.setBackground(new Color(70, 130, 180));
            }
        });
    }

    private void configurarEventos() {
        botonAgendarLogia.addActionListener(e -> manejarAgendarLogia());
        botonEliminarReserva.addActionListener(e -> cancelarReserva());
        botonCerrarSesion.addActionListener(e -> cerrarSesion());
    }

    private void actualizarReservaActual() {
        StringBuilder texto = new StringBuilder();
        texto.append("=== Información de Reserva ===\n\n");

        Reserva reservaActual = gestorReservas.getReservaUsuario();
        if (reservaActual != null) {
            texto.append("Estado: ACTIVA\n");
            texto.append("---------------------------\n");
            texto.append(reservaActual.toString());
        } else {
            texto.append("Estado: SIN RESERVAS\n");
            texto.append("---------------------------\n");
            texto.append("No tiene reservas activas en este momento.\n");
            texto.append("Puede realizar una nueva reserva usando\n");
            texto.append("el botón 'Agendar Nueva Logia'.");
        }

        areaReserva.setText(texto.toString());
    }

    private void manejarAgendarLogia() {
        if (gestorReservas.getReservaUsuario() != null) {
            JOptionPane.showMessageDialog(this,
                    "Ya tiene una reserva activa. Debe cancelar la actual para realizar una nueva.",
                    "Reserva Existente",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        mostrarDialogoReserva();
    }

    private void mostrarDialogoReserva() {
        JDialog dialogoReserva = new JDialog(this, "Nueva Reserva de Logia", true);
        dialogoReserva.setSize(700, 500);
        dialogoReserva.setLocationRelativeTo(this);
        dialogoReserva.setLayout(new BorderLayout(10, 10));

        // Panel principal del diálogo
        JPanel panelReserva = new JPanel(new GridBagLayout());
        panelReserva.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        GridBagConstraints gbc = new GridBagConstraints();

        // Configuración del calendario
        JCalendar calendario = configurarCalendario();

        // Configuración de selectores
        JComboBox<Logia> comboLogias = new JComboBox<>(
                gestorLogias.obtenerTodasLasLogias().stream()
                        .filter(Logia::getHabilitada)
                        .toArray(Logia[]::new)
        );
        comboLogias.setPreferredSize(new Dimension(200, 30));

        JComboBox<Horario> comboHorarios = new JComboBox<>();
        comboHorarios.setPreferredSize(new Dimension(200, 30));
        comboHorarios.setEnabled(false);

        // Listeners para actualización de horarios
        calendario.addPropertyChangeListener("calendar", evt ->
                actualizarHorarios(calendario.getDate(), (Logia) comboLogias.getSelectedItem(), comboHorarios));

        comboLogias.addActionListener(e ->
                actualizarHorarios(calendario.getDate(), (Logia) comboLogias.getSelectedItem(), comboHorarios));

        // Agregar componentes usando GridBagLayout
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(5, 5, 15, 5);
        panelReserva.add(calendario, gbc);

        // Selector de Logia
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.EAST;
        panelReserva.add(new JLabel("Seleccione Logia: "), gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        panelReserva.add(comboLogias, gbc);

        // Selector de Horario
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST;
        panelReserva.add(new JLabel("Seleccione Horario: "), gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        panelReserva.add(comboHorarios, gbc);

        // Panel de botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnConfirmar = new JButton("Confirmar Reserva");
        JButton btnCancelar = new JButton("Cancelar");

        configurarBoton(btnConfirmar);
        configurarBoton(btnCancelar);

        btnConfirmar.addActionListener(e -> {
            try {
                realizarReserva(calendario, comboLogias, comboHorarios);
                dialogoReserva.dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialogoReserva,
                        "Error: " + ex.getMessage(),
                        "Error en Reserva",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        btnCancelar.addActionListener(e -> dialogoReserva.dispose());

        panelBotones.add(btnConfirmar);
        panelBotones.add(btnCancelar);

        dialogoReserva.add(panelReserva, BorderLayout.CENTER);
        dialogoReserva.add(panelBotones, BorderLayout.SOUTH);
        dialogoReserva.setVisible(true);
    }

    private JCalendar configurarCalendario() {
        JCalendar calendario = new JCalendar();
        calendario.setMinSelectableDate(new Date());

        // Deshabilitar sábados y domingos
        calendario.getDayChooser().addDateEvaluator(new com.toedter.calendar.IDateEvaluator() {
            @Override
            public boolean isSpecial(Date date) {
                return false;
            }

            @Override
            public Color getSpecialForegroundColor() {
                return null;
            }

            @Override
            public Color getSpecialBackroundColor() {
                return null;
            }

            @Override
            public String getSpecialTooltip() {
                return null;
            }

            @Override
            public boolean isInvalid(Date date) {
                Calendar cal = Calendar.getInstance();
                cal.setTime(date);
                int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
                return dayOfWeek == Calendar.SATURDAY || dayOfWeek == Calendar.SUNDAY;
            }

            @Override
            public Color getInvalidForegroundColor() {
                return null;
            }

            @Override
            public Color getInvalidBackroundColor() {
                return null;
            }

            @Override
            public String getInvalidTooltip() {
                return "";
            }
        });

        return calendario;
    }

    private void actualizarHorarios(Date fecha, Logia logia, JComboBox<Horario> comboHorarios) {
        comboHorarios.removeAllItems();

        if (fecha == null || logia == null) {
            comboHorarios.setEnabled(false);
            return;
        }

        try {
            LocalDateTime fechaLocal = fecha.toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDateTime();

            String mes = String.valueOf(fechaLocal.getMonthValue());
            int dia = fechaLocal.getDayOfMonth();

            ArrayList<Horario> horarios = gestorReservas.obtenerBloquesDisponibles(logia, dia, mes);

            if (!horarios.isEmpty()) {
                horarios.forEach(comboHorarios::addItem);
                comboHorarios.setEnabled(true);
            } else {
                comboHorarios.setEnabled(false);
            }
        } catch (Exception e) {
            comboHorarios.setEnabled(false);
            JOptionPane.showMessageDialog(this,
                    "Error al cargar horarios: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void realizarReserva(JCalendar calendario, JComboBox<Logia> comboLogias,
                                 JComboBox<Horario> comboHorarios) {
        Logia logiaSeleccionada = (Logia) comboLogias.getSelectedItem();
        Horario horarioSeleccionado = (Horario) comboHorarios.getSelectedItem();

        if (logiaSeleccionada == null || horarioSeleccionado == null) {
            throw new IllegalArgumentException("Debe seleccionar todos los campos requeridos");
        }

        LocalDateTime fechaHora = calendario.getDate().toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate()
                .atTime(
                        Integer.parseInt(horarioSeleccionado.getHoraInicio().split(":")[0]),
                        Integer.parseInt(horarioSeleccionado.getHoraInicio().split(":")[1])
                );

        gestorReservas.agregarReserva(usuario, logiaSeleccionada, fechaHora);
        actualizarReservaActual();

        JOptionPane.showMessageDialog(this,
                "Reserva realizada con éxito",
                "Reserva Confirmada",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void cancelarReserva() {
        if (gestorReservas.getReservaUsuario() == null) {
            JOptionPane.showMessageDialog(this,
                    "No tiene una reserva activa para cancelar",
                    "Sin Reserva",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        int confirmacion = JOptionPane.showConfirmDialog(this,
                "¿Está seguro que desea cancelar su reserva actual?",
                "Confirmar Cancelación",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);

        if (confirmacion == JOptionPane.YES_OPTION) {
            if (gestorReservas.CancelarReserva(usuario)) {
                actualizarReservaActual();
                JOptionPane.showMessageDialog(this,
                        "Reserva cancelada correctamente",
                        "Cancelación Exitosa",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    private void cerrarSesion() {
        int confirmacion = JOptionPane.showConfirmDialog(this,
                "¿Está seguro que desea cerrar sesión?\nSe guardarán todos los cambios.",
                "Confirmar Cierre de Sesión",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);

        if (confirmacion == JOptionPane.YES_OPTION) {
            json.crearBackups();
            this.dispose();
            new ViewInicioSesion().setVisible(true);
        }
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            Usuario usuarioPrueba = new Usuario("21797495k23", "123");
            ViewMenu view = new ViewMenu(usuarioPrueba);
            view.setVisible(true);
        });
    }
}