package models;


import Domain.entidades.Color;

import javax.persistence.NoResultException;
import java.util.List;

public class ColorModel extends Model{

    private static ColorModel instance;

    public static ColorModel getInstance() {
        if(instance == null){
            instance = new ColorModel();
        }
        return instance;
    }

    @Override
    public List<Color> buscarTodos(){
        return entityManager().createQuery("from color").getResultList();
    }

    @Override
    public Color buscar(int id){
        return entityManager().find(Color.class, id);
    }

    @Override
    public Color buscar(String unColorEnHexa){
        try{
            Color busqueda = (Color) entityManager().createQuery("from Color color where color.colorHexa=:unColor")
                    .setParameter("unColor",unColorEnHexa).getSingleResult();

            return busqueda;
        }
        catch(NoResultException ex){
            return null;
        }
    }

}
