package models;

import Domain.entidades.Material;
import Domain.entidades.Usuario;

import java.util.List;

public class MaterialModel extends Model {

    private static MaterialModel instance;

    public static MaterialModel getInstance() {
        if(instance == null){
            instance = new MaterialModel();
        }
        return instance;
    }

    private MaterialModel(){
        super();
    }

    @Override
    public List<Material> buscarTodos(){
        return entityManager().createQuery("from Material").getResultList();
    }

    @Override
    public Material buscar(int id){
        return entityManager().find(Material.class, id);
    }
}
