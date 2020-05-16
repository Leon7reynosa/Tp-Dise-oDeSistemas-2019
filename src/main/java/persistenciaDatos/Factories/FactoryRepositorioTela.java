package persistenciaDatos.Factories;

import models.TelaModel;
import persistenciaDatos.Repositiorios.RepositorioGeneral;
import persistenciaDatos.daos.DAOMySQL;

public class FactoryRepositorioTela {

    private static RepositorioGeneral repo;

    public static RepositorioGeneral get(){

        if(repo == null){

            repo = new RepositorioGeneral(new DAOMySQL(TelaModel.getInstance()));

        }

        return repo;
    }

}
