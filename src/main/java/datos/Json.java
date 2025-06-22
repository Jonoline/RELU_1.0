package datos;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
// deben manejarse excepciones, FilenotFound en esoecial creo:V
public class Json {
    //crea gson con saltos de lineas, sangrias y adaptadores par ael localdatetime
    private final Gson gson = JavaTimeUtils.obtenerGsonConTiempo();
    private final String ARCHIVO_USUARIOS = "data/usuarios.json";
    private final String ARCHIVO_RESERVAS = "data/reservas.json";
    private final String ARCHIVO_LOGIAS = "data/logias.json";
    private final String BACKUP_USUARIOS = "backup/usuariosBackup.json";
    private final String BACKUP_LOGIAS = "backup/logiasBackup.json";
    private final String BACKUP_RESERVAS = "backup/reservasBackup.json";


    private <T> ArrayList<T> cargarArchivoGenerico(String archivoOriginal, String archivoBackup, TypeToken<ArrayList<T>> typeToken) {
        File archivo = new File(archivoOriginal);
        File backup = new File(archivoBackup);
        ArrayList<T> datos;

        try {
            if (archivo.exists()) {
                datos = leerArchivo(archivoOriginal, typeToken);
            } else if (backup.exists()) {
                // Si existe el backup pero no el original, cargamos del backup
                datos = leerArchivo(archivoBackup, typeToken);
                // Creamos el archivo original a partir del backup
                try (FileWriter escritor = new FileWriter(archivoOriginal)) {
                    gson.toJson(datos, escritor);
                    System.out.println("Se ha restaurado " + archivoOriginal + " desde el backup");
                }
            } else {
                throw new RuntimeException("No se encontró el archivo " + archivoOriginal + " ni su respaldo");
            }
            return datos;
        } catch (IOException e) {
            throw new RuntimeException("Error al leer/escribir el archivo: " + e.getMessage());
        }
    }


    private <T> ArrayList<T> leerArchivo(String rutaArchivo, TypeToken<ArrayList<T>> typeToken) throws IOException {
        try (FileReader lector = new FileReader(rutaArchivo)) {
            return gson.fromJson(lector, typeToken.getType());
        }
    }

    public ArrayList<Usuario> cargarUsuarioJson() {
        return cargarArchivoGenerico(
                ARCHIVO_USUARIOS,
                BACKUP_USUARIOS,
                new TypeToken<ArrayList<Usuario>>() {}
        );
    }

    public ArrayList<Logia> cargarLogias() {
        return cargarArchivoGenerico(
                ARCHIVO_LOGIAS,
                BACKUP_LOGIAS,
                new TypeToken<ArrayList<Logia>>() {}
        );
    }

    public ArrayList<Reserva> cargarReservas() {
        return cargarArchivoGenerico(
                ARCHIVO_RESERVAS,
                BACKUP_RESERVAS,
                new TypeToken<ArrayList<Reserva>>() {}
        );
    }



    public void IngresarReservas(ArrayList<Reserva> reservas) {
        try (FileWriter escritor = new FileWriter(ARCHIVO_RESERVAS)) {
            gson.toJson(reservas, escritor);
        } catch (IOException e) {
            System.out.println("Error al guardar las reservas en el archivo JSON");
        }
    }

    //public void ingresarLogias(ArrayList<Logia> logias) {
       // try (FileWriter escritor = new FileWriter(ARCHIVO_LOGIAS)) {
          //  gson.toJson(logias, escritor);
        //    System.out.println("Logias guardadas");
       // } catch (IOException e) {
        //    System.out.println("Error al guardar las logias en el archivo JSON");
      //  }
    //}

    private <T> void crearBackupGenerico(String archivoBackup, ArrayList<T> datos) { //<T> es para usar cualquier tipo de dato entrante, locura
        File directorioBackup = new File("backup");
        if (!directorioBackup.exists()) {
            directorioBackup.mkdir();
        }

        try (FileWriter escritor = new FileWriter(archivoBackup)) {
            gson.toJson(datos, escritor);
            //System.out.println("Archivo de respaldo creado: " + archivoBackup); no creo que sea necesario pero ahi esta para debugging
        } catch (IOException e) {
            throw new RuntimeException("Error al crear el archivo de respaldo: " + e.getMessage());
        }
    }


    public void crearBackupUsuarios() {
        try {
            ArrayList<Usuario> usuarios = cargarUsuarioJson();
            crearBackupGenerico(BACKUP_USUARIOS, usuarios);
        } catch (RuntimeException e) {
            throw new RuntimeException("Error al crear backup de usuarios: " + e.getMessage());
        }
    }

    public void crearBackupLogias() {
        try {
            ArrayList<Logia> logias = cargarLogias();
            crearBackupGenerico(BACKUP_LOGIAS, logias);
        } catch (RuntimeException e) {
            throw new RuntimeException("Error al crear backup de logias: " + e.getMessage());
        }
    }

    public void crearBackupReservas() {
        try {
            ArrayList<Reserva> reservas = cargarReservas();
            crearBackupGenerico(BACKUP_RESERVAS, reservas);
        } catch (RuntimeException e) {
            throw new RuntimeException("Error al crear backup de reservas: " + e.getMessage());
        }
    }

    public void crearBackups() {
        crearBackupUsuarios();
        crearBackupLogias();
        crearBackupReservas();
    }




}
