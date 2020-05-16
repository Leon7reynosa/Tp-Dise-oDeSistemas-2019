package models;

import Domain.entidades.Prenda;

import java.util.List;

public class PrendaModel extends Model{

    private static PrendaModel instance;

    public static PrendaModel getInstance() {
        if(instance == null){
            instance = new PrendaModel();
        }
        return instance;
    }

    @Override
    public List<Prenda> buscarTodos(){
        return entityManager().createQuery("from prendas").getResultList();
    }

    @Override
    public Prenda buscar(int id){
        return entityManager().find(Prenda.class, id);
    }
}
