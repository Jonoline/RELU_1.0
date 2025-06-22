package datos;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
// deben manejarse excepciones, FilenotFound en esoecial creo:V
public class Json {
    //crea gson con saltos de lineas, sangrias y adaptadores par ael localdatetime
    private final Gson gson = JavaTimeUtils.obtenerGsonConTiempo();
    private final String ARCHIVO_USUARIOS = "usuarios.json";
    private final String ARCHIVO_BACKUP = "backup/usuariosBackup.json";


    public ArrayList<Usuario> cargarUsuarioJson() { //se necesita cambiar el orden en como se lee el archivo json, en este momento el check si existe el archivo solo se hace despues de intentar iniciar sesion
        File archivoUsuarios = new File(ARCHIVO_USUARIOS);
        File archivoBackup = new File(ARCHIVO_BACKUP);

        try {
            if (archivoUsuarios.exists()) {
                return cargarArchivo(ARCHIVO_USUARIOS);
            } else if (archivoBackup.exists()) {
                return cargarArchivo(ARCHIVO_BACKUP);
            } else {
                throw new RuntimeException("El archivo de respaldo no existe, no es posible iniciar el programa");
            }
        } catch (IOException e) {
            throw new RuntimeException("Error al leer el archivo: " + e.getMessage());
        }

    }

    public void IngresarReservas(ArrayList<Reserva> reservas) {
        try (FileWriter escritor = new FileWriter("reservas.json")) {
            gson.toJson(reservas, escritor);
        } catch (IOException e) {
            System.out.println("Error al guardar las reservas en el archivo JSON");
        }
    }

    public ArrayList<Reserva> CargarReservas() {
        try (FileReader lector = new FileReader("reservas.json")) {
            Type listType = new TypeToken<ArrayList<Reserva>>() {
            }.getType();
            return gson.fromJson(lector, listType);
        } catch (IOException e) {
            System.out.println("Error al leer el archivo JSON");
            return new ArrayList<Reserva>();
        }
    }

    public void ingresarLogias(ArrayList<Logia> logias) {
        try (FileWriter escritor = new FileWriter("logias.json")) {
            gson.toJson(logias, escritor);
            System.out.println("Logias guardadas");
        } catch (IOException e) {
            System.out.println("Error al guardar las logias en el archivo JSON");
        }
    }

    public ArrayList<Logia> cargarLogias() {
        try (FileReader lector = new FileReader("logias.json")) {
            Type listType = new TypeToken<ArrayList<Logia>>() {
            }.getType();
            return gson.fromJson(lector, listType);
        } catch (IOException e) {
            System.out.println("Error al leer el archivo JSON");
            return new ArrayList<Logia>();
        }
    }

    private ArrayList<Usuario> cargarArchivo(String rutaArchivo) throws IOException {
        try (FileReader lector = new FileReader(rutaArchivo)) {
            Type tipoLista = new TypeToken<ArrayList<Usuario>>() {
            }.getType();
            return gson.fromJson(lector, tipoLista);
        }
    }

    public void crearBackupUsuarios() {
        File directorioBackup = new File("backup");
        if (!directorioBackup.exists()) {
            directorioBackup.mkdir();
        }

        try {
            // se leen los usuarios actuales
            ArrayList<Usuario> usuarios = cargarUsuarioJson();

            // se escribe el archivo de backup con los usuarios actuales iniciados
            try (FileWriter escritor = new FileWriter(ARCHIVO_BACKUP)) {
                gson.toJson(usuarios, escritor);
                System.out.println("Archivo de respaldo creado");
            }
        } catch (IOException e) {
            throw new RuntimeException("Error al crear el archivo de respaldo: " + e.getMessage());
        }
    }

}
