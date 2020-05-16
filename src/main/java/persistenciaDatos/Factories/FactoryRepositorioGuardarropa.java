package persistenciaDatos.Factories;

import models.GuardarropaModel;
import persistenciaDatos.Repositiorios.RepositorioGeneral;
import persistenciaDatos.daos.DAOMySQL;


public class FactoryRepositorioGuardarropa {

    private static RepositorioGeneral repo;

    public static RepositorioGeneral get(){

        if(repo == null){

            repo = new RepositorioGeneral(new DAOMySQL(GuardarropaModel.getInstance()));

        }

        return repo;
    }
}