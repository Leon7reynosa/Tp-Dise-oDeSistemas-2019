package models;

import Domain.entidades.MoldeAtuendo;
import java.util.List;

public class MoldeAtuendoModel extends Model{

    private static MoldeAtuendoModel instance;

    public static MoldeAtuendoModel getInstance() {
        if(instance == null){
            instance = new MoldeAtuendoModel();
        }
        return instance;
    }

    @Override
    public List<MoldeAtuendo> buscarTodos(){
        return entityManager().createQuery("from MoldeAtuendo").getResultList();
    }

    @Override
    public MoldeAtuendo buscar(int id){
        return entityManager().find(MoldeAtuendo.class, id);
    }

}