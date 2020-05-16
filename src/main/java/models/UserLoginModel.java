package models;

import Domain.entidades.UserLogin.UserLogin;

import javax.persistence.NoResultException;
import java.util.List;

public class UserLoginModel extends Model{

    private static UserLoginModel instance;

    public static UserLoginModel getInstance() {
        if(instance == null){
            instance = new UserLoginModel();
        }
        return instance;
    }

    @Override
    public List<UserLogin> buscarTodos(){
        return entityManager().createQuery("from UserLogin").getResultList();
    }

    @Override
    public UserLogin buscar(int id){
        return entityManager().find(UserLogin.class, id);
    }

    @Override
    public UserLogin buscar(String unUsuario){

        try{
            UserLogin busqueda = (UserLogin) entityManager().createQuery("from UserLogin log where log.user=:user")
                .setParameter("user",unUsuario).getSingleResult();

            return busqueda;
        }
        catch(NoResultException ex){
            return null;
        }


    }

}
