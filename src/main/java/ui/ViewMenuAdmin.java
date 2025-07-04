package ui;

import datos.*;
import logica.*;
import com.toedter.calendar.JCalendar;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.beans.PropertyChangeListener;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;

public class ViewMenuAdmin extends JFrame {
    private final GestorLogias gestorLogias;
    private final GestorAdmin gestorAdmin;
    private final GestorUsuarios gestorUsuarios;
    private final Json json;
    private final Usuario usuario;

    // Componentes principales
    private JTabbedPane tabbedPane;
    private JPanel panelUsuarios;
    private JPanel panelReservas;
    private JPanel panelLogias;

    // Tablas
    private JTable tablaUsuarios;
    private JTable tablaReservas;
    private JTable tablaLogias;

    // Modelos de tablas
    private DefaultTableModel modeloUsuarios;
    private DefaultTableModel modeloReservas;
    private DefaultTableModel modeloLogias;

    public ViewMenuAdmin(Usuario usuario, GestorUsuarios gestorUsuarios) {
        this.usuario = usuario;
        this.gestorUsuarios = gestorUsuarios;
        this.gestorLogias = new GestorLogias();
        this.gestorAdmin = new GestorAdmin(usuario, gestorUsuarios);
        this.json = new Json();

        configurarVentana();
        inicializarComponentes();
        cargarDatos();
    }

    private void configurarVentana() {
        setTitle("RELU - Panel de Administración - Usuario: " + usuario.getMatricula());
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(true);
    }

    private void inicializarComponentes() {
        tabbedPane = new JTabbedPane();

        // Inicializar modelos de tablas
        inicializarModelosTablas();

        // Inicializar paneles
        panelUsuarios = crearPanelUsuarios();
        panelReservas = crearPanelReservas();
        panelLogias = crearPanelLogias();

        // Agregar paneles al TabbedPane
        tabbedPane.addTab("Usuarios", new ImageIcon("icons/users.png"), panelUsuarios);
        tabbedPane.addTab("Reservas", new ImageIcon("icons/calendar.png"), panelReservas);
        tabbedPane.addTab("Logias", new ImageIcon("icons/building.png"), panelLogias);

        // Menú superior
        JMenuBar menuBar = crearMenuBar();
        setJMenuBar(menuBar);

        // Agregar TabbedPane al frame
        add(tabbedPane);
    }

    private void inicializarModelosTablas() {
        // Modelo para tabla de usuarios
        String[] columnasUsuarios = {"Matrícula", "Correo", "Rol"};
        modeloUsuarios = new DefaultTableModel(columnasUsuarios, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        // Modelo para tabla de reservas
        String[] columnasReservas = {"Matrícula", "Logia", "Fecha", "Hora"};
        modeloReservas = new DefaultTableModel(columnasReservas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        // Modelo para tabla de logias
        String[] columnasLogias = {"ID", "Capacidad", "Piso", "Estado"};
        modeloLogias = new DefaultTableModel(columnasLogias, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
    }

    private JMenuBar crearMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        JMenu menuArchivo = new JMenu("Archivo");
        JMenuItem itemRefrescar = new JMenuItem("Refrescar Datos");
        JMenuItem itemSalir = new JMenuItem("Cerrar Sesión");

        itemRefrescar.addActionListener(e -> cargarDatos());
        itemSalir.addActionListener(e -> cerrarSesion());

        menuArchivo.add(itemRefrescar);
        menuArchivo.addSeparator();
        menuArchivo.add(itemSalir);

        menuBar.add(menuArchivo);
        return menuBar;
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
    private JPanel crearPanelUsuarios() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Panel de botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        JButton btnAgregar = crearBoton("Agregar Usuario", "icons/add_user.png");
        JButton btnEliminar = crearBoton("Eliminar Usuario", "icons/delete_user.png");

        btnAgregar.addActionListener(e -> mostrarDialogoAgregarUsuario());
        btnEliminar.addActionListener(e -> eliminarUsuarioSeleccionado());

        panelBotones.add(btnAgregar);
        panelBotones.add(btnEliminar);

        // Tabla de usuarios
        tablaUsuarios = new JTable(modeloUsuarios);
        configurarTabla(tablaUsuarios);
        JScrollPane scrollPane = new JScrollPane(tablaUsuarios);

        panel.add(panelBotones, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private JPanel crearPanelReservas() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Panel de botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        JButton btnAgregar = crearBoton("Agregar Reserva", "icons/add_calendar.png");
        JButton btnEliminar = crearBoton("Eliminar Reserva", "icons/delete_calendar.png");
        JButton btnBuscar = crearBoton("Buscar Reserva", "icons/search.png");

        btnAgregar.addActionListener(e -> mostrarDialogoAgregarReserva());
        btnEliminar.addActionListener(e -> eliminarReservaSeleccionada());
        btnBuscar.addActionListener(e -> buscarReserva());

        panelBotones.add(btnAgregar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnBuscar);

        // Tabla de reservas
        tablaReservas = new JTable(modeloReservas);
        configurarTabla(tablaReservas);
        JScrollPane scrollPane = new JScrollPane(tablaReservas);

        panel.add(panelBotones, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private JPanel crearPanelLogias() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Panel de botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        JButton btnAgregar = crearBoton("Agregar Logia", "icons/add_building.png");
        JButton btnHabilitar = crearBoton("Habilitar/Deshabilitar", "icons/toggle.png");

        btnAgregar.addActionListener(e -> mostrarDialogoAgregarLogia());
        btnHabilitar.addActionListener(e -> mostrarDialogoHabilitarLogia());

        panelBotones.add(btnAgregar);
        panelBotones.add(btnHabilitar);

        // Tabla de logias
        tablaLogias = new JTable(modeloLogias);
        configurarTabla(tablaLogias);
        JScrollPane scrollPane = new JScrollPane(tablaLogias);

        panel.add(panelBotones, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private JButton crearBoton(String texto, String rutaIcono) {
        JButton boton = new JButton(texto);
        try {
            boton.setIcon(new ImageIcon(rutaIcono));
        } catch (Exception e) {
            // Si no se encuentra el ícono, solo muestra el texto
        }
        boton.setFocusPainted(false);
        return boton;
    }

    private void configurarTabla(JTable tabla) {
        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabla.getTableHeader().setReorderingAllowed(false);
        tabla.setRowHeight(25);
        tabla.setAutoCreateRowSorter(true);
    }

    // Implementación de los diálogos
    private void mostrarDialogoAgregarUsuario() {
        JDialog dialogo = new JDialog(this, "Agregar Nuevo Usuario", true);
        dialogo.setLayout(new BorderLayout(10, 10));

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Campos de entrada
        JTextField txtMatricula = new JTextField(20);
        JTextField txtCorreo = new JTextField(20);
        JPasswordField txtContrasena = new JPasswordField(20);
        JComboBox<Rol> comboRol = new JComboBox<>(Rol.values());

        // Agregar componentes
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("Matrícula:"), gbc);
        gbc.gridx = 1;
        panel.add(txtMatricula, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("Correo:"), gbc);
        gbc.gridx = 1;
        panel.add(txtCorreo, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(new JLabel("Contraseña:"), gbc);
        gbc.gridx = 1;
        panel.add(txtContrasena, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        panel.add(new JLabel("Rol:"), gbc);
        gbc.gridx = 1;
        panel.add(comboRol, gbc);

        // Panel de botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnGuardar = new JButton("Guardar");
        JButton btnCancelar = new JButton("Cancelar");

        btnGuardar.addActionListener(e -> {
            try {
                String matricula = txtMatricula.getText().trim();
                String correo = txtCorreo.getText().trim();
                String contrasena = new String(txtContrasena.getPassword());
                Rol rol = (Rol) comboRol.getSelectedItem();

                gestorAdmin.agregarUsuario(matricula, correo, contrasena, rol);
                cargarDatos();
                dialogo.dispose();
                JOptionPane.showMessageDialog(this,
                        "Usuario agregado correctamente",
                        "Éxito",
                        JOptionPane.INFORMATION_MESSAGE);
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(dialogo,
                        "Error: " + ex.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        btnCancelar.addActionListener(e -> dialogo.dispose());

        panelBotones.add(btnGuardar);
        panelBotones.add(btnCancelar);

        dialogo.add(panel, BorderLayout.CENTER);
        dialogo.add(panelBotones, BorderLayout.SOUTH);

        dialogo.pack();
        dialogo.setLocationRelativeTo(this);
        dialogo.setVisible(true);
    }
    private void eliminarUsuarioSeleccionado() {
        int filaSeleccionada = tablaUsuarios.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this,
                    "Por favor, seleccione un usuario para eliminar",
                    "Error",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        String matricula = (String) tablaUsuarios.getValueAt(filaSeleccionada, 0);
        int confirmacion = JOptionPane.showConfirmDialog(this,
                "¿Está seguro que desea eliminar el usuario con matrícula " + matricula + "?\n" +
                        "Esta acción también eliminará todas sus reservas asociadas.",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);

        if (confirmacion == JOptionPane.YES_OPTION) {
            try {
                gestorAdmin.eliminarUsuario(matricula);
                cargarDatos();
                JOptionPane.showMessageDialog(this,
                        "Usuario eliminado correctamente",
                        "Éxito",
                        JOptionPane.INFORMATION_MESSAGE);
            } catch (IllegalArgumentException e) {
                JOptionPane.showMessageDialog(this,
                        "Error: " + e.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void mostrarDialogoAgregarReserva() {
        JDialog dialogo = new JDialog(this, "Agregar Nueva Reserva", true);
        dialogo.setLayout(new BorderLayout(10, 10));

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Campos de entrada
        JTextField txtMatricula = new JTextField(20);
        JComboBox<String> comboLogias = new JComboBox<>();
        JCalendar calendario = new JCalendar();
        JComboBox<Horario> comboHorarios = new JComboBox<>();

        // Cargar logias habilitadas
        for (Logia logia : gestorLogias.obtenerTodasLasLogias()) {
            if (logia.getHabilitada()) {
                comboLogias.addItem(logia.getID());
            }
        }

        // Listener para actualizar horarios disponibles cuando cambie la fecha o la logia
        PropertyChangeListener actualizarHorarios = evt -> {
            if (comboLogias.getSelectedItem() != null && calendario.getDate() != null) {
                actualizarHorariosDisponibles(comboHorarios,
                        (String)comboLogias.getSelectedItem(),
                        calendario.getDate());
            }
        };

        calendario.addPropertyChangeListener("calendar", actualizarHorarios);
        comboLogias.addActionListener(e -> {
            if (calendario.getDate() != null) {
                actualizarHorariosDisponibles(comboHorarios,
                        (String)comboLogias.getSelectedItem(),
                        calendario.getDate());
            }
        });

        // Agregar componentes
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("Matrícula:"), gbc);
        gbc.gridx = 1;
        panel.add(txtMatricula, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("Logia:"), gbc);
        gbc.gridx = 1;
        panel.add(comboLogias, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        gbc.gridwidth = 2;
        panel.add(calendario, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        gbc.gridwidth = 1;
        panel.add(new JLabel("Horario:"), gbc);
        gbc.gridx = 1;
        panel.add(comboHorarios, gbc);

        // Panel de botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnGuardar = new JButton("Guardar");
        JButton btnCancelar = new JButton("Cancelar");

        btnGuardar.addActionListener(e -> {
            try {
                String matricula = txtMatricula.getText().trim();
                String idLogia = (String) comboLogias.getSelectedItem();
                Date fechaSeleccionada = calendario.getDate();
                Horario horario = (Horario) comboHorarios.getSelectedItem();

                if (matricula.isEmpty() || idLogia == null || fechaSeleccionada == null || horario == null) {
                    throw new IllegalArgumentException("Todos los campos son obligatorios");
                }

                Usuario usuario = gestorUsuarios.buscarUsuario(matricula);
                Logia logia = gestorLogias.obtenerLogia(idLogia);

                LocalDateTime fechaHora = fechaSeleccionada.toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDateTime()
                        .withHour(Integer.parseInt(horario.getHoraInicio().split(":")[0]))
                        .withMinute(Integer.parseInt(horario.getHoraInicio().split(":")[1]));

                gestorAdmin.agregarReservaParaUsuario(usuario, logia, fechaHora);
                cargarDatos();
                dialogo.dispose();
                JOptionPane.showMessageDialog(this,
                        "Reserva agregada correctamente",
                        "Éxito",
                        JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialogo,
                        "Error: " + ex.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        btnCancelar.addActionListener(e -> dialogo.dispose());

        panelBotones.add(btnGuardar);
        panelBotones.add(btnCancelar);

        dialogo.add(panel, BorderLayout.CENTER);
        dialogo.add(panelBotones, BorderLayout.SOUTH);

        dialogo.pack();
        dialogo.setLocationRelativeTo(this);
        dialogo.setVisible(true);
    }

    private void actualizarHorariosDisponibles(JComboBox<Horario> comboHorarios, String idLogia, Date fecha) {
        comboHorarios.removeAllItems();
        try {
            Logia logia = gestorLogias.obtenerLogia(idLogia);
            if (logia != null) {
                LocalDateTime fechaLocal = fecha.toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDateTime();

                ArrayList<Horario> horariosDisponibles = gestorAdmin.obtenerBloquesDisponibles(
                        logia,
                        fechaLocal.getDayOfMonth(),
                        String.format("%02d", fechaLocal.getMonthValue())
                );

                for (Horario horario : horariosDisponibles) {
                    comboHorarios.addItem(horario);
                }
            }
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this,
                    "Error: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void eliminarReservaSeleccionada() {
        int filaSeleccionada = tablaReservas.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this,
                    "Por favor, seleccione una reserva para eliminar",
                    "Error",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        String matricula = (String) tablaReservas.getValueAt(filaSeleccionada, 0);
        try {
            Usuario usuario = gestorUsuarios.buscarUsuario(matricula);
            if (gestorAdmin.CancelarReserva(usuario)) {
                cargarDatos();
                JOptionPane.showMessageDialog(this,
                        "Reserva cancelada correctamente",
                        "Éxito",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this,
                        "No se pudo cancelar la reserva",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this,
                    "Error: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void buscarReserva() {
        String matricula = JOptionPane.showInputDialog(this,
                "Ingrese la matrícula del usuario:",
                "Buscar Reserva",
                JOptionPane.QUESTION_MESSAGE);

        if (matricula != null && !matricula.trim().isEmpty()) {
            try {
                Reserva reserva = gestorAdmin.buscarReserva(matricula.trim());
                if (reserva != null) {
                    JOptionPane.showMessageDialog(this,
                            "Reserva encontrada:\n" + reserva.toString(),
                            "Información de Reserva",
                            JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this,
                            "El usuario no tiene una reserva activa",
                            "Sin Reservas",
                            JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (IllegalArgumentException e) {
                JOptionPane.showMessageDialog(this,
                        "Error: " + e.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    private void mostrarDialogoAgregarLogia() {
        JDialog dialogo = new JDialog(this, "Agregar Nueva Logia", true);
        dialogo.setLayout(new BorderLayout(10, 10));

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Campos de entrada
        JComboBox<Integer> comboCapacidad = new JComboBox<>(new Integer[]{4, 6});
        JComboBox<String> comboPiso = new JComboBox<>(new String[]{"P2", "P3"});

        // Agregar componentes
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("Capacidad:"), gbc);
        gbc.gridx = 1;
        panel.add(comboCapacidad, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("Piso:"), gbc);
        gbc.gridx = 1;
        panel.add(comboPiso, gbc);

        // Panel de botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnGuardar = new JButton("Guardar");
        JButton btnCancelar = new JButton("Cancelar");

        btnGuardar.addActionListener(e -> {
            try {
                int capacidad = (Integer) comboCapacidad.getSelectedItem();
                String piso = (String) comboPiso.getSelectedItem();

                gestorLogias.agregarLogiaADMIN(capacidad, piso);
                cargarDatos();
                dialogo.dispose();
                JOptionPane.showMessageDialog(this,
                        "Logia agregada correctamente",
                        "Éxito",
                        JOptionPane.INFORMATION_MESSAGE);
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(dialogo,
                        "Error: " + ex.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        btnCancelar.addActionListener(e -> dialogo.dispose());

        panelBotones.add(btnGuardar);
        panelBotones.add(btnCancelar);

        dialogo.add(panel, BorderLayout.CENTER);
        dialogo.add(panelBotones, BorderLayout.SOUTH);

        dialogo.pack();
        dialogo.setLocationRelativeTo(this);
        dialogo.setVisible(true);
    }

    private void mostrarDialogoHabilitarLogia() {
        JDialog dialogo = new JDialog(this, "Gestionar Estado de Logia", true);
        dialogo.setLayout(new BorderLayout(10, 10));

        // Crear tabla de logias con sus estados
        String[] columnas = {"ID", "Capacidad", "Piso", "Estado"};
        DefaultTableModel modeloTablaLogias = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 3; // Solo permite editar la columna de estado
            }

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                return columnIndex == 3 ? Boolean.class : String.class;
            }
        };

        JTable tablaLogias = new JTable(modeloTablaLogias);
        tablaLogias.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Cargar datos de logias
        for (Logia logia : gestorLogias.obtenerTodasLasLogias()) {
            modeloTablaLogias.addRow(new Object[]{
                    logia.getID(),
                    logia.getCapacidad(),
                    logia.getPiso(),
                    logia.getHabilitada()
            });
        }

        // Panel de botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnGuardar = new JButton("Guardar Cambios");
        JButton btnCancelar = new JButton("Cancelar");

        btnGuardar.addActionListener(e -> {
            try {
                for (int i = 0; i < modeloTablaLogias.getRowCount(); i++) {
                    String id = (String) modeloTablaLogias.getValueAt(i, 0);
                    boolean nuevoEstado = (Boolean) modeloTablaLogias.getValueAt(i, 3);
                    Logia logia = gestorLogias.obtenerLogia(id);

                    if (logia != null && logia.getHabilitada() != nuevoEstado) {
                        if (nuevoEstado) {
                            gestorLogias.habilitarLogiaADMIN(id);
                        } else {
                            gestorLogias.deshabilitarLogiaADMIN(id);
                        }
                    }
                }
                cargarDatos();
                dialogo.dispose();
                JOptionPane.showMessageDialog(this,
                        "Estados de logias actualizados correctamente",
                        "Éxito",
                        JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialogo,
                        "Error al actualizar estados: " + ex.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        btnCancelar.addActionListener(e -> dialogo.dispose());

        panelBotones.add(btnGuardar);
        panelBotones.add(btnCancelar);

        dialogo.add(new JScrollPane(tablaLogias), BorderLayout.CENTER);
        dialogo.add(panelBotones, BorderLayout.SOUTH);

        dialogo.setSize(500, 300);
        dialogo.setLocationRelativeTo(this);
        dialogo.setVisible(true);
    }

    private void cargarDatos() {
        cargarTablaUsuarios();
        cargarTablaReservas();
        cargarTablaLogias();
    }

    private void cargarTablaUsuarios() {
        modeloUsuarios.setRowCount(0);
        ArrayList<Usuario> usuarios = gestorUsuarios.getListaUsuarios();
        for (Usuario usuario : usuarios) {
            modeloUsuarios.addRow(new Object[]{
                    usuario.getMatricula(),
                    usuario.getUfromail(),
                    usuario.getRol()
            });
        }
    }

    private void cargarTablaReservas() {
        modeloReservas.setRowCount(0);
        // Aquí deberías obtener todas las reservas activas
        ArrayList<Usuario> usuarios = gestorUsuarios.getListaUsuarios();
        for (Usuario usuario : usuarios) {
            Reserva reserva = gestorAdmin.buscarReserva(usuario.getMatricula());
            if (reserva != null) {
                modeloReservas.addRow(new Object[]{
                        usuario.getMatricula(),
                        reserva.getLogia().getID(),
                        reserva.getFechaHora().toLocalDate(),
                        reserva.getFechaHora().toLocalTime()
                });
            }
        }
    }

    private void cargarTablaLogias() {
        modeloLogias.setRowCount(0);
        ArrayList<Logia> logias = gestorLogias.obtenerTodasLasLogias();
        for (Logia logia : logias) {
            modeloLogias.addRow(new Object[]{
                    logia.getID(),
                    logia.getCapacidad(),
                    logia.getPiso(),
                    logia.getHabilitada() ? "Habilitada" : "Deshabilitada"
            });
        }
    }

    // Metodo main para pruebas
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                // Configurar el Look and Feel
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

                // Crear un usuario administrador de prueba
                Usuario adminPrueba = new Usuario("soyadmin@ufrontera.cl", "12345678k25", "123", Rol.ADMINISTRADOR);
                GestorUsuarios gestorUsuarios = new GestorUsuarios();

                ViewMenuAdmin ventana = new ViewMenuAdmin(adminPrueba, gestorUsuarios);
                ventana.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}