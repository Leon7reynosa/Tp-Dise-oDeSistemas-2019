package models;

import Domain.entidades.TipoDePrenda;

import java.util.List;

public class TipoPrendaModel extends Model{

    private static TipoPrendaModel instance;

    public static TipoPrendaModel getInstance() {
        if(instance == null){
            instance = new TipoPrendaModel();
        }
        return instance;
    }

    @Override
    public List<TipoDePrenda> buscarTodos(){
        return entityManager().createQuery("from TipoDePrenda").getResultList();
    }

    @Override
    public TipoDePrenda buscar(int id){
        return entityManager().find(TipoDePrenda.class, id);
    }

}
