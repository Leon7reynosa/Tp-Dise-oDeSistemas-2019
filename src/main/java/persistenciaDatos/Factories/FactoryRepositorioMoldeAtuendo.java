package persistenciaDatos.Factories;

import models.MoldeAtuendoModel;
import persistenciaDatos.Repositiorios.RepositorioGeneral;
import persistenciaDatos.daos.DAOMySQL;


public class FactoryRepositorioMoldeAtuendo {

    private static RepositorioGeneral repo;

    public static RepositorioGeneral get(){

        if(repo == null){
            repo = new RepositorioGeneral(new DAOMySQL(MoldeAtuendoModel.getInstance()));
        }

        return repo;
    }
}