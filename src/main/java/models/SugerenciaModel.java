package models;


import Domain.entidades.Sugerencia;

import java.util.List;

public class SugerenciaModel extends Model {

    private static SugerenciaModel instance;

    public static SugerenciaModel getInstance() {
        if(instance == null){
            instance = new SugerenciaModel();
        }
        return instance;
    }

    @Override
    public List<Sugerencia> buscarTodos(){
        return entityManager().createQuery("from sugerencia").getResultList();
    }

    @Override
    public Sugerencia buscar(int id){
        return entityManager().find(Sugerencia.class, id);
    }

}
