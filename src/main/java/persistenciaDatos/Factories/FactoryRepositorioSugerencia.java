package persistenciaDatos.Factories;

import models.SugerenciaModel;
import persistenciaDatos.Repositiorios.RepositorioGeneral;
import persistenciaDatos.daos.DAOMySQL;

public class FactoryRepositorioSugerencia {

    private static RepositorioGeneral repo;

    public static RepositorioGeneral get(){

        if(repo == null){

            repo = new RepositorioGeneral(new DAOMySQL(SugerenciaModel.getInstance()));

        }

        return repo;
    }
}
