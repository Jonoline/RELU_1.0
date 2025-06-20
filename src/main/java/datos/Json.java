package datos;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
// deben manejarse excepciones, FilenotFound en esoecial creo:V
public class Json {
    //crea gson con saltos de lineas, sangrias y adaptadores par ael localdatetime
    private final Gson gson = JavaTimeUtils.obtenerGsonConTiempo();


    public ArrayList<Usuario> cargarUsuarioJson() {
        try (FileReader lector = new FileReader("usuarios.json")) {
            Type tipoLista = new TypeToken<ArrayList<Usuario>>() {}.getType();
            return gson.fromJson(lector, tipoLista);
        } catch (IOException e){
            return new ArrayList<Usuario>();
        }
    }

    public void IngresarReservas(ArrayList<Reserva> reservas){
        try (FileWriter escritor = new FileWriter("reservas.json")){
            gson.toJson(reservas, escritor);
            System.out.println("Reservas guardadas");
        } catch (IOException e) {
            System.out.println("Error al guardar las reservas en el archivo JSON");
        }
    }
    public ArrayList<Reserva> CargarReservas(){
        try(FileReader lector = new FileReader("reservas.json")){
            Type listType = new TypeToken<ArrayList<Reserva>>(){}.getType();
            return gson.fromJson(lector, listType);
        }catch (IOException e){
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
            Type listType = new TypeToken<ArrayList<Logia>>(){}.getType();
            return gson.fromJson(lector, listType);
        } catch (IOException e) {
            System.out.println("Error al leer el archivo JSON");
            return new ArrayList<Logia>();
        }
    }

}

