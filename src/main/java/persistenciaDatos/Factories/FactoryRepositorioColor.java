package persistenciaDatos.Factories;

import models.ColorModel;
import persistenciaDatos.Repositiorios.RepositorioColor;
import persistenciaDatos.Repositiorios.RepositorioGeneral;
import persistenciaDatos.daos.DAOMySQL;

public class FactoryRepositorioColor{

    private static RepositorioColor repo;

    public static RepositorioColor get(){

        if(repo == null){

            repo = new RepositorioColor(new DAOMySQL(ColorModel.getInstance()));

        }

        return repo;
    }

}
