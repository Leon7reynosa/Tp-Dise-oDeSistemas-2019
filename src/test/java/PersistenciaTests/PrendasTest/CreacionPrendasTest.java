package PersistenciaTests.PrendasTest;

import Domain.entidades.TipoDePrenda;
import Domain.entidades.*;
import org.junit.Test;
import persistenciaDatos.Factories.*;
import persistenciaDatos.Repositiorios.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class CreacionPrendasTest {

    RepositorioGeneral repoTipoPrenda = FactoryRepositorioTipoPrenda.get();
    RepositorioColor repoColor = FactoryRepositorioColor.get();
    RepositorioGeneral repoTela = FactoryRepositorioTela.get();

    TipoDePrenda remera;
    TipoDePrenda pantalon;
    TipoDePrenda zapatillas;
    TipoDePrenda camperita;

    @Test
    public void crearTipoDePrenda(){

        remera = new TipoDePrenda();
        remera.setNombrePrenda("remera");
        remera.setCategoria(Categoria.SUPERIOR);
        remera.setPuntosDeAbrigo(2);

        camperita = new TipoDePrenda();
        camperita.setNombrePrenda("camperita");
        camperita.sosDeCategoria(Categoria.SUPERIOR);
        camperita.setPuntosDeAbrigo(6);


        pantalon = new TipoDePrenda();
        pantalon.setNombrePrenda("jean");
        pantalon.setCategoria(Categoria.INFERIOR);
        pantalon.setPuntosDeAbrigo(4);


        zapatillas = new TipoDePrenda();
        zapatillas.setNombrePrenda("zapatillas");
        zapatillas.setCategoria(Categoria.CALZADO);
        zapatillas.setPuntosDeAbrigo(1);

        repoTipoPrenda.agregar(remera);
        repoTipoPrenda.agregar(pantalon);
        repoTipoPrenda.agregar(zapatillas);
        repoTipoPrenda.agregar(camperita);
    }

    @Test
    public void modificarTipos(){

        remera = repoTipoPrenda.buscar(1);
        pantalon = repoTipoPrenda.buscar(2);
        zapatillas = repoTipoPrenda.buscar(3);
        camperita = repoTipoPrenda.buscar(7);

        remera.agregarPrendaCombinable(pantalon);
        remera.agregarPrendaCombinable(pantalon);

        remera.agregarPrendaCombinable(pantalon);
        remera.agregarPrendaCombinable(pantalon);

        remera.agregarPrendaCombinable(pantalon);
        remera.agregarPrendaCombinable(pantalon);

        Tela algodon = repoTela.buscar(2);
        Tela seda = repoTela.buscar(4);
        Tela jean = repoTela.buscar(1);
        Tela cuero = repoTela.buscar(3);

        remera.agregarTela(algodon);
        remera.agregarTela(seda);

        camperita.agregarTela(algodon);

        pantalon.agregarTela(jean);

        zapatillas.agregarTela(cuero);

        repoTipoPrenda.modificar(remera);
        repoTipoPrenda.modificar(camperita);
        repoTipoPrenda.modificar(pantalon);
        repoTipoPrenda.modificar(zapatillas);
    }

    @Test
    public void creacionDeColores(){

        Color rojo = new Color();
        rojo.setColorHexa("#FE2D00");

        Color azul = new Color();
        azul.setColorHexa("#0017FF");

        Color verde = new Color();
        verde.setColorHexa("#00FF00");

        Color negro = new Color();
        negro.setColorHexa("#000000");

        Color blanco = new Color();
        blanco.setColorHexa("#FFFFFF");

        repoColor.agregar(rojo);
        repoColor.agregar(azul);
        repoColor.agregar(verde);
        repoColor.agregar(negro);
        repoColor.agregar(blanco);

    }


    @Test
    public void crearBuzito(){

        Prenda buzo = new Prenda();
        buzo.setTipo(repoTipoPrenda.buscar(4));
        buzo.setearPrendaUsada(false);

        FactoryRepositorioPrenda.get().agregar(buzo);
    }

    @Test
    public void crearPrendas(){

        Prenda remera = new Prenda();
        Prenda jean = new Prenda();
        Prenda zapatillas = new Prenda();

        remera.setTipo(repoTipoPrenda.buscar(1)); //tipo remera
        remera.setTela(repoTela.buscar(2)); //algodon
        jean.setTipo(repoTipoPrenda.buscar(2)); //tipo pantalon
        jean.setTela(repoTela.buscar(1)); //jean
        zapatillas.setTipo(repoTipoPrenda.buscar(3)); //zapatillas
        zapatillas.setTela(repoTela.buscar(3));  //cuero


        List<Color> colorRemera = new ArrayList<>();
        colorRemera.add(repoColor.buscar(6)); //rojo
        remera.setColores(colorRemera);

        List<Color> colorPantalon = new ArrayList<>();
        colorPantalon.add(repoColor.buscar(7)); //azul
        jean.setColores(colorPantalon);

        List<Color> colorZapatillas = new ArrayList<>();
        colorZapatillas.add(repoColor.buscar(9)); //negro
        zapatillas.setColores(colorZapatillas);

        remera.setearPrendaUsada(false);
        jean.setearPrendaUsada(false);
        zapatillas.setearPrendaUsada(false);

        RepositorioGeneral repoPrenda = FactoryRepositorioPrenda.get();

        repoPrenda.agregar(remera);
        repoPrenda.agregar(jean);
        repoPrenda.agregar(zapatillas);

    }

}
