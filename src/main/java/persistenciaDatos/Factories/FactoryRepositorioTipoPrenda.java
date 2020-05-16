package persistenciaDatos.Factories;

import models.TipoPrendaModel;
import persistenciaDatos.Repositiorios.RepositorioGeneral;
import persistenciaDatos.daos.DAOMySQL;

public class FactoryRepositorioTipoPrenda {

    private static RepositorioGeneral repo;

    public static RepositorioGeneral get(){

        if(repo == null){

            repo = new RepositorioGeneral(new DAOMySQL(TipoPrendaModel.getInstance()));

        }

        return repo;
    }

}
