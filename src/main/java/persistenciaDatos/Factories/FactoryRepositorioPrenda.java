package persistenciaDatos.Factories;

import models.PrendaModel;
import persistenciaDatos.Repositiorios.RepositorioGeneral;
import persistenciaDatos.daos.DAOMySQL;

public class FactoryRepositorioPrenda {

    private static RepositorioGeneral repo;

    public static RepositorioGeneral get(){

        if(repo == null){

            repo = new RepositorioGeneral(new DAOMySQL(PrendaModel.getInstance()));

        }

        return repo;
    }

}
