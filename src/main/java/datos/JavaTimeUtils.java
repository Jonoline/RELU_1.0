package datos;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class JavaTimeUtils {

    // Formato solo para la fecha si necesitamos separarlo
    public static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd/MM/yy");
    public static final DateTimeFormatter FORMATO_HORA = DateTimeFormatter.ofPattern("HH:mm");

    // Formato completo HH:mm - HH:mm | dd/MM/yy
    public static String formatearConHoraFinal(LocalDateTime inicio) {
        LocalDateTime fin = inicio.plusMinutes(90);
        return inicio.format(FORMATO_HORA) + " - " + fin.format(FORMATO_HORA) + " | " + inicio.format(FORMATO_FECHA);
    }

    // Adaptador para LocalDateTime en el json
    public static class LocalDateTimeAdapter implements JsonSerializer<LocalDateTime>, JsonDeserializer<LocalDateTime> {

        @Override
        public JsonElement serialize(LocalDateTime fecha, Type type, JsonSerializationContext context) {
            return new JsonPrimitive(formatearConHoraFinal(fecha));
        }

        @Override
        public LocalDateTime deserialize(JsonElement json, Type type, JsonDeserializationContext context)
                throws JsonParseException {
            // Extraer solo la hora de inicio y la fecha desde el string completo: "08:30 - 09:30 | 05/06/25"
            try {
                String texto = json.getAsString(); // "08:30 - 09:30 | 05/06/25"
                String[] partes = texto.split(" \\| ");
                String horaInicio = partes[0].split(" - ")[0]; // "08:30"
                String fechaTexto = partes[1];                 // "05/06/25"
                String textoFinal = horaInicio + " - " + fechaTexto; // "08:30 - 05/06/25"

                // Formato de entrada simplificado: HH:mm - dd/MM/yy
                DateTimeFormatter formatoEntrada = DateTimeFormatter.ofPattern("HH:mm - dd/MM/yy");
                return LocalDateTime.parse(textoFinal, formatoEntrada);
            } catch (Exception e) {
                throw new JsonParseException("Formato inválido para fechaHora: " + json.getAsString(), e);
            }
        }
    }

    // Metodo para obtener instancia Gson
    public static Gson obtenerGsonConTiempo() {
        return new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                .setPrettyPrinting()
                .create();
    }


    public Integer[] formarHorasYminutos(String horaInicio){
        Integer[] partes = new Integer[2];
        String[] partesHora = horaInicio.split(":");
        partes[0] = Integer.parseInt(partesHora[0]);
        partes[1] = Integer.parseInt(partesHora[1]);
        return partes;
    }


    public LocalDateTime FormarFecha(int dia, String mes, Integer[] horasYminutos){
        LocalDateTime fecha = LocalDateTime.of(LocalDateTime.now().getYear(), Integer.parseInt(mes), dia, horasYminutos[0],horasYminutos[1]);
        if (fecha.isBefore(LocalDateTime.now())){
            throw new IllegalArgumentException("La fecha seleccionada no puede ser anterior a la fecha actual");
        }
        return fecha;
    }
}
