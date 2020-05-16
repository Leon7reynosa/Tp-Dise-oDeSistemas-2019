package Json_Configuracion;

import java.io.FileNotFoundException;
import java.io.FileReader;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class ObtenerConfiguracion {

	private static ObtenerConfiguracion instance = null;

	private static int proximidadEnDias;
	private static String pathImagenes;
	private static int prendasMaximasGratuitas;
	private static int width;
	private static int height;
	private static String stringPuntoDeMontaje;
	private static String mailContacto;
	private static String eventoSchedule;
	private static String sugerenciaSchedule;
	private static String externalStaticFiles;

	private ObtenerConfiguracion() {
		super();
		this.inicializar();
	}

	public static ObtenerConfiguracion getInstance() {
		if (instance == null) {
			instance = new ObtenerConfiguracion();
			instance.inicializar();
		}

		return instance;
	}

	public String getExternalStaticFiles(){return externalStaticFiles;}

	public String getEventoSchedule(){return eventoSchedule;}

	public String getSugerenciaSchedule(){return sugerenciaSchedule;}

	public int getProximidadEnDias() {
		return proximidadEnDias;
	}

	public String getPathImagenes() {
		return pathImagenes;
	}
	
	public String getMailContacto() {
		return mailContacto;
	}

	public int getPrendasMaximasGratuitas() {
		return prendasMaximasGratuitas;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public String getPuntoDeMontaje() {
		return stringPuntoDeMontaje;
	}

	public void inicializar(){
		JsonParser jsonParser = new JsonParser();

		try {
			FileReader reader = new FileReader("configuracion.json");

			Object object = jsonParser.parse(reader);

			JsonObject configuracion = (JsonObject) object;

			JsonObject configuracionEvento = (JsonObject) configuracion.get("configuracionEvento");
			JsonObject normalizacionImagen = (JsonObject) configuracion.get("normalizacionImagen");
			JsonObject configuracionGuardarropas = (JsonObject) configuracion.get("configuracionGuardarropas");
			JsonObject puntoDeMontaje = (JsonObject) configuracion.get("puntoDeMontaje");
			JsonObject informacion = (JsonObject) configuracion.get("informacion");
			JsonObject triggerConfig = (JsonObject) configuracion.get("triggerConfig");
			JsonObject serverConfig = (JsonObject) configuracion.get("serverConfig");

			proximidadEnDias = configuracionEvento.get("proximidadEnDias").getAsInt();

			pathImagenes = normalizacionImagen.get("pathCarpetaImagenes").getAsString();
			width = normalizacionImagen.get("width").getAsInt();
			height = normalizacionImagen.get("height").getAsInt();

			prendasMaximasGratuitas = configuracionGuardarropas.get("prendasMaximasGratuitas").getAsInt();

			stringPuntoDeMontaje = puntoDeMontaje.get("puntoDeMontaje").getAsString();

			mailContacto = informacion.get("mailContacto").getAsString();

			eventoSchedule = triggerConfig.get("eventoSchedule").getAsString();

			sugerenciaSchedule = triggerConfig.get("sugerenciaSchedule").getAsString();

			externalStaticFiles = serverConfig.get("externalStaticFiles").getAsString();
		}
		catch(FileNotFoundException ex){
			System.out.println("Excepcion de archivoConfig jaja salu3.\n");
		}
	}

	public static void main(String[] args) throws FileNotFoundException {
		ObtenerConfiguracion obtenerConfiguracion = ObtenerConfiguracion.getInstance();

		obtenerConfiguracion.inicializar();
		System.out.println(obtenerConfiguracion.getPathImagenes());
		System.out.println(obtenerConfiguracion.getProximidadEnDias());
		System.out.println(obtenerConfiguracion.getPrendasMaximasGratuitas());
		System.out.println(obtenerConfiguracion.getMailContacto());
		System.out.println(obtenerConfiguracion.getHeight());
		System.out.println(obtenerConfiguracion.getWidth());
		System.out.println(obtenerConfiguracion.getPuntoDeMontaje());
		System.out.println(obtenerConfiguracion.getEventoSchedule());
		System.out.println(obtenerConfiguracion.getSugerenciaSchedule());
		System.out.println(obtenerConfiguracion.getExternalStaticFiles());

	}

}
