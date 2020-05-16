package persistenciaDatos.Factories;

import persistenciaDatos.Repositiorios.RepositorioGeneral;
import persistenciaDatos.daos.DAOMySQL;
import models.UsuarioModel;


public class FactoryRepositorioUsuario {

    private static RepositorioGeneral repo;

        public static RepositorioGeneral get(){

            if(repo == null){

                repo = new RepositorioGeneral(new DAOMySQL(UsuarioModel.getInstance()));

            }

            return repo;
        }
}


