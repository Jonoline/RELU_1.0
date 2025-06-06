package logica;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import datos.Reserva;
import datos.Usuario;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Json {
    //crea gson con saltos de lineas y sangrias
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static void guardarUsuarioJson(Usuario nuevoUsuario, String rutaArchivo) {
        //crea lista vacia de usuarios
        List<Usuario> listaUsuarios = new ArrayList<>();

        // Leer usuarios anteriores si el archivo existe
        try (FileReader reader = new FileReader(rutaArchivo)) {
            Usuario[] usuariosExistentes;
            usuariosExistentes = gson.fromJson(reader, Usuario[].class);
            if (usuariosExistentes != null) {
                listaUsuarios = new ArrayList<>(Arrays.asList(usuariosExistentes));
            }
        } catch (IOException e) {
            // Si el archivo no existe, lo ignoramos
        }

        // Agregar el nuevo usuario
        listaUsuarios.add(nuevoUsuario);

        // Guardar lista completa en formato JSON
        try (FileWriter writer = new FileWriter(rutaArchivo)) {
            gson.toJson(listaUsuarios, writer);
            System.out.println("Usuario guardado en archivo JSON válido.");
        } catch (IOException e) {
            throw new RuntimeException("Error al leer el archivo JSON: " + e.getMessage(), e);
        }

    }

    public static void buscarPorMatricula(String matriculaBuscada, String rutaArchivo) {
        Gson gson = new Gson();

        try (FileReader reader = new FileReader(rutaArchivo)) {
            Type tipoLista = new TypeToken<List<Usuario>>() {}.getType();
            List<Usuario> listaUsuarios = gson.fromJson(reader, tipoLista);

            boolean encontrado = false;
            for (Usuario u : listaUsuarios) {
                if (u.getMatricula().equals(matriculaBuscada)) {
                    System.out.println("Usuario encontrado:");
                    System.out.println("UfroMail: " + u.getUfromail());
                    System.out.println("Contraseña: " + u.getContrasena());
                    encontrado = true;
                    break;
                }
            }

            if (!encontrado) {
                System.out.println("No se encontró un usuario con matrícula: " + matriculaBuscada);
            }

        } catch (IOException e) {
            System.out.println("Error al leer el archivo JSON.");
        }
    }
    public static void IngresarReservas(Reserva reservas){
        try (FileWriter escritor = new FileWriter("reservas.json")){
            gson.toJson(reservas, escritor);
            System.out.println("Reservas guardadas");
        } catch (IOException e) {
            System.out.println("Error al guardar las reservas en el archivo JSON");
        }
    }
    public static ArrayList<Reserva> CargarReservas(){
        try(FileReader lector = new FileReader("reservas.json")){
            Type listType = new TypeToken<ArrayList<Reserva>>(){}.getType();
            return gson.fromJson(lector, listType);
        }catch (IOException e){
            System.out.println("Error al leer el archivo JSON");
            return new ArrayList<>();
        }
    }
}

