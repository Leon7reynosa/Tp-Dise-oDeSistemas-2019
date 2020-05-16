package PersistenciaTests.PrendasTest;

import Domain.entidades.Material;
import Domain.entidades.Tela;
import org.junit.Test;
import persistenciaDatos.Factories.FactoryRepositorioMaterial;
import persistenciaDatos.Factories.FactoryRepositorioTela;
import persistenciaDatos.Repositiorios.RepositorioGeneral;

import static junit.framework.TestCase.assertEquals;

public class MaterialesYTelas{

    @Test
    public void creacionTela(){

        Tela nuevaTela = new Tela();
        nuevaTela.setNombre("Jean");

        Tela nuevaTela2 = new Tela();
        nuevaTela2.setNombre("Algodon");

        Tela nuevaTela3 = new Tela();
        nuevaTela3.setNombre("Cuero");

        Tela nuevaTela4 = new Tela();
        nuevaTela4.setNombre("Seda");

       RepositorioGeneral repo = FactoryRepositorioTela.get();

       repo.agregar(nuevaTela);
       repo.agregar(nuevaTela2);
       repo.agregar(nuevaTela3);
       repo.agregar(nuevaTela4);

    }

    @Test
    public void creacionMaterial(){

        Material nuevoMaterial = new Material();
        nuevoMaterial.setNombre("Oro");

        Material nuevoMaterial2 = new Material();
        nuevoMaterial2.setNombre("Madera");

        Material nuevoMaterial3 = new Material();
        nuevoMaterial3.setNombre("Cobre");

        Material nuevoMaterial4 = new Material();
        nuevoMaterial4.setNombre("Acero");

        RepositorioGeneral repo = FactoryRepositorioMaterial.get();

        repo.agregar(nuevoMaterial);
        repo.agregar(nuevoMaterial2);
        repo.agregar(nuevoMaterial3);
        repo.agregar(nuevoMaterial4);
    }

    @Test
    public void recuperandoTela(){

        Tela jean = FactoryRepositorioTela.get().buscar(1);

        assertEquals("Jean", jean.getNombre());
    }

    @Test
    public void recuperandoMaterial(){

        Material material = FactoryRepositorioMaterial.get().buscar(1);

        assertEquals("Oro", material.getNombre());
    }
}
