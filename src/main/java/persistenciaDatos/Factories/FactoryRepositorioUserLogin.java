package persistenciaDatos.Factories;

import models.UserLoginModel;
import persistenciaDatos.Repositiorios.RepositorioLoginUser;
import persistenciaDatos.daos.DAOMySQL;

public class FactoryRepositorioUserLogin {

    private static RepositorioLoginUser repo;

    public static RepositorioLoginUser get(){

        if(repo == null){

            repo = new RepositorioLoginUser(new DAOMySQL(UserLoginModel.getInstance()));

        }

        return repo;
    }

}
