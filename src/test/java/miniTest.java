import Domain.entidades.MoldeAtuendo;
import Domain.entidades.Prenda;
import Domain.entidades.TipoDePrenda;
import Domain.entidades.UserLogin.UserLogin;
import org.junit.Test;
import persistenciaDatos.Factories.FactoryRepositorioMoldeAtuendo;
import persistenciaDatos.Factories.FactoryRepositorioPrenda;
import persistenciaDatos.Factories.FactoryRepositorioTipoPrenda;
import persistenciaDatos.Factories.FactoryRepositorioUserLogin;
import persistenciaDatos.Repositiorios.RepositorioGeneral;
import persistenciaDatos.Repositiorios.RepositorioLoginUser;

import java.util.List;

public class miniTest {

    @Test
    public void miniTest(){

        RepositorioLoginUser repo = FactoryRepositorioUserLogin.get();

        List<UserLogin> unLogin =  repo.buscarTodos();

        if(unLogin.size() <= 0){
            System.out.println("NO SE ENCONTRO");
        }
        else{
            System.out.println("Se encontro");

        }
    }

    @Test
    public void creoUnMolde(){

        RepositorioGeneral repo = FactoryRepositorioTipoPrenda.get();
        RepositorioGeneral repoMolde = FactoryRepositorioMoldeAtuendo.get();

        MoldeAtuendo molde = new MoldeAtuendo();

        TipoDePrenda remera = repo.buscar(1);
        TipoDePrenda jean = repo.buscar(2);
        TipoDePrenda zapatillas = repo.buscar(3);
        TipoDePrenda camperita = repo.buscar(7);

        molde.agregarNuevaPrenda(remera);
        molde.agregarNuevaPrenda(jean);
        molde.agregarNuevaPrenda(zapatillas);
        molde.agregarNuevaPrenda(camperita);

        repoMolde.agregar(molde);

    }

    @Test
    public void testSystemProperty(){

        System.out.println(System.getProperty("remera_verde.jpg"));

    }

}
