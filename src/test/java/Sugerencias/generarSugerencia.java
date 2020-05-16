package Sugerencias;

import Domain.Clima;
import Domain.ConsultorClima;
import Domain.entidades.*;
import Json_Configuracion.ObtenerConfiguracion;
import org.junit.Test;
import persistenciaDatos.Factories.*;
import persistenciaDatos.Repositiorios.RepositorioGeneral;

import java.util.ArrayList;
import java.util.List;

public class generarSugerencia {

    //antes de hacerlo, ejecutar los scripts de base de datos
    @Test
    public void testDeSugerencia() {

        RepositorioGeneral repoGuardarropa = FactoryRepositorioGuardarropa.get();
        RepositorioGeneral repoUsuario = FactoryRepositorioUsuario.get();

        Guardarropa guardarropaTesteado = repoGuardarropa.buscar(1); //guardarropa de aroco

        if(guardarropaTesteado.getAlgoritmo() != null){

            ConsultorClima consultor = new ConsultorClima();

            Clima climaActual = consultor.getClimaActual();

            List<MoldeAtuendo> moldes = FactoryRepositorioMoldeAtuendo.get().buscarTodos();

            Usuario alejandro = repoUsuario.buscar(1);

            System.out.println("Cantidad de moldes: " + moldes.size());
            System.out.println("Clima: " + climaActual.getInfo());
            System.out.println("puntos de abrigo que necesita Alejandro: " +
                alejandro.cuantoAbrigoNecesitas(climaActual)
            );

            if(climaActual != null) {
                Sugerencia sugerencia = guardarropaTesteado.generaSugerencia(alejandro, climaActual);

                if(sugerencia != null){
                    System.out.println("Sugerencia: ");
                    sugerencia.atuendo().mostrarAtuendo();
                }else{
                    System.out.println("No se genero ninguna sugerencia");
                }

            }else{
                System.out.println("Fallo la api");
            }

        }else{
            System.out.println("No tiene algoritmo el guardarropa");
        }

        System.out.println("Fin de test!");

    }
}
