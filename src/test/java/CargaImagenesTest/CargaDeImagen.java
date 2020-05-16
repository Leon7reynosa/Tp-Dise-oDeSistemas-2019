package CargaImagenesTest;

import Domain.entidades.Usuario;
import org.junit.Test;
import persistenciaDatos.Factories.FactoryRepositorioUsuario;
import persistenciaDatos.Repositiorios.RepositorioGeneral;

import java.io.IOException;

public class CargaDeImagen {

    @Test
    public void cargoUnaImagen() throws IOException{
        RepositorioGeneral repo = FactoryRepositorioUsuario.get();
        Usuario usuario = repo.buscar(1);

        String direccionDeImagen = "C:/Users/Leon/Desktop/Oca.jpg";

        try{
            System.out.println("EMPEZAMOS");
            System.out.println("USUARIO = " + usuario.getNombre());
            System.out.println("Direccion de imagen = " + direccionDeImagen);
            usuario.cargaImagen(direccionDeImagen);
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

}
