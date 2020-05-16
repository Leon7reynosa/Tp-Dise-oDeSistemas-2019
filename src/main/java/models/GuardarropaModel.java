package models;

import Domain.entidades.Guardarropa;
import java.util.List;

public class GuardarropaModel extends Model{

    private static GuardarropaModel instance;

    public static GuardarropaModel getInstance() {
        if(instance == null){
            instance = new GuardarropaModel();
        }
        return instance;
    }

    @Override
    public List<Guardarropa> buscarTodos(){
        return entityManager().createQuery("from Guardarropa").getResultList();
    }

/*    public void agregar(Guardarropa guardarropa){
        EntityManagerHelper.persist(guardarropa);
    }
*/
    @Override
    public Guardarropa buscar(int id){
        return entityManager().find(Guardarropa.class, id);
    }

}