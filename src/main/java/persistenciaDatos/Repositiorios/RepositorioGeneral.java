package persistenciaDatos.Repositiorios;

import persistenciaDatos.daos.DAO;

import java.util.List;

public class RepositorioGeneral extends Repositorio{

    public RepositorioGeneral(DAO unDAO){

        super();
        this.setDao(unDAO);

    }

    public <T> List<T> buscarTodos(){
        return this.dao.buscarTodos();
    }

    public <T> T buscar(int id){
        return this.dao.buscar(id);
    }
}
