package models;

import Domain.entidades.Tela;

import java.util.List;

public class TelaModel extends Model {

    private static TelaModel instance;

    public static TelaModel getInstance() {
        if(instance == null){
            instance = new TelaModel();
        }
        return instance;
    }

    private TelaModel(){
        super();
    }

    @Override
    public List<Tela> buscarTodos(){
        return entityManager().createQuery("from Tela").getResultList();
    }

    @Override
    public Tela buscar(int id){
        return entityManager().find(Tela.class, id);
    }
}
