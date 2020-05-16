package persistenciaDatos.Factories;

import models.EventoModel;
import persistenciaDatos.Repositiorios.RepositorioGeneral;
import persistenciaDatos.daos.DAOMySQL;


public class FactoryRepositorioEvento {

    private static RepositorioGeneral repo;

        public static RepositorioGeneral get(){

        if(repo == null){

            repo = new RepositorioGeneral(new DAOMySQL(EventoModel.getInstance()));

        }

        return repo;
    }
}