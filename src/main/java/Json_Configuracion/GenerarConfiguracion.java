package Json_Configuracion;

import java.io.FileWriter;
import java.io.IOException;

import com.google.gson.JsonObject;

public class GenerarConfiguracion {

	public static void main(String[] args) {

		JsonObject configuracionEvento = new JsonObject();
		configuracionEvento.addProperty("proximidadEnDias", 1);

		// =================================================================================

		JsonObject normalizacionImagen = new JsonObject();
		normalizacionImagen.addProperty("pathCarpetaImagenes", "src/main/resources/public/Imagenes");
		normalizacionImagen.addProperty("width", 500);
		normalizacionImagen.addProperty("height", 500);

		// =================================================================================

		JsonObject configuracionGuardarropas = new JsonObject();
		configuracionGuardarropas.addProperty("prendasMaximasGratuitas", 25);

		// ===================================================================================

		JsonObject puntoDeMontaje = new JsonObject();
		puntoDeMontaje.addProperty("puntoDeMontaje", "D:/Dise�o de Sistemas/QueMePongo/2019-ma-ma-group-02/");
		
		//====================================================================================
		
		JsonObject informacion = new JsonObject();
		informacion.addProperty("mailContacto", "olivera4lautaro@gmail.com");
		
		//====================================================================================
		JsonObject configuracionTrigger = new JsonObject();
		configuracionTrigger.addProperty("eventoSchedule","0 0 0 * * ?");
		configuracionTrigger.addProperty("sugerenciaSchedule","0 25 1 * * ?");
		//====================================================================================
		JsonObject configuracionServer = new JsonObject();
		configuracionServer.addProperty("externalStaticFiles","/Users/Familia/Desktop/Lalo/TP-DDS/2019-ma-ma-group-02/src/main/resources/public");
		//====================================================================================
		JsonObject configuracion = new JsonObject();
		configuracion.add("configuracionEvento", configuracionEvento);
		configuracion.add("normalizacionImagen", normalizacionImagen);
		configuracion.add("configuracionGuardarropas", configuracionGuardarropas);
		configuracion.add("puntoDeMontaje", puntoDeMontaje);
		configuracion.add("informacion", informacion);
		configuracion.add("triggerConfig",configuracionTrigger);
		configuracion.add("serverConfig",configuracionServer);
		
		try (FileWriter file = new FileWriter("configuracion.json")) {
			file.write(configuracion.toString());
			file.flush();
		}

		catch (IOException excepcion) {
			excepcion.printStackTrace();
		}

	}
}
