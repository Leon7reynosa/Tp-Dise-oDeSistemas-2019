package RenderizacionImagen;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.DataOutput;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import Json_Configuracion.ObtenerConfiguracion;

public class RenderizadorImagen {

	private static String pathImagenFinal;

	private static int width = ObtenerConfiguracion.getInstance().getWidth();
	private static int height = ObtenerConfiguracion.getInstance().getHeight();
	private static File file;
	private static String pathDestino = ObtenerConfiguracion.getInstance().getPathImagenes();
	

	public String renderizarImagen(String path, String nombre) throws IOException {

		try {
			file = new File(path);
			BufferedImage imagenOriginal = ImageIO.read(file);
			System.out.println("imagenOriginal = " + imagenOriginal);
			
			BufferedImage imagenTerminada = new BufferedImage(width, height, imagenOriginal.getType());
			System.out.println("imagenTerminada = " + imagenTerminada);

	        Graphics2D g2d = imagenTerminada.createGraphics();
	        g2d.drawImage(imagenOriginal, 0, 0, width, height, null);
	        g2d.dispose();
			System.out.println("me  mori aca ?");


	        pathImagenFinal = this.crearNombrePath(nombre);
			System.out.println("pathImagenFinal = " + pathImagenFinal);
	        
	        ImageIO.write(imagenTerminada, "JPG" , new File(pathImagenFinal));

	        String[] path_final = pathImagenFinal.split("/public");


	        return path_final[1];
	        
		} catch (IOException exception) {
			System.out.println("Error: " + exception);
			return null;
		}


	}

	private String crearNombrePath(String nombreUsuario){

		int numeroAUsar = 1;
		int indice = 0;
		int auxiliar;
		String pathCarpetaUsuario = pathDestino + "/" + nombreUsuario;
		String pathFinal = null;
		File folder = new File(pathCarpetaUsuario);
		folder.mkdir();
		File[] listadoDeImagenes = folder.listFiles();
		int longitud = listadoDeImagenes.length;

		System.out.println("cantidad de imagenes en el directorio = " + longitud);

	//	System.out.println("cantidad de imagenes en el directorio = " + listadoDeImagenes.length);
		if(listadoDeImagenes.length > 0){

			do {

				auxiliar = this.buscarNumeroEnNombreDeImagen(listadoDeImagenes[indice].getName(), nombreUsuario);
				if(auxiliar >= numeroAUsar){
					numeroAUsar = auxiliar + 1;
				}
				indice++;
				System.out.println("numero a usar = " + numeroAUsar);
			}while(auxiliar == numeroAUsar - 1  && indice < listadoDeImagenes.length);

			System.out.println("numero a usar FINAL = " + numeroAUsar);
			pathFinal = pathCarpetaUsuario + "/" + nombreUsuario + (numeroAUsar) + ".jpg";

			System.out.println("pathFinal = " + pathFinal);

		}
		else{
			pathFinal = pathCarpetaUsuario + "/" + nombreUsuario + numeroAUsar + ".jpg";
		}

		return pathFinal;
	}

	private int buscarNumeroEnNombreDeImagen(String path, String nombreUsuario){
		String primerCorte = path.substring(nombreUsuario.length());
		String corteFinal = primerCorte.substring(0, primerCorte.length() - 4);
		return Integer.valueOf(corteFinal);


	}

}
