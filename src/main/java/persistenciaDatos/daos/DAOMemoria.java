package persistenciaDatos.daos;

import Domain.entidades.EntidadPersistente;

import java.util.List;
import java.util.stream.Collectors;

public class DAOMemoria implements DAO{

    private List<EntidadPersistente> entidades;

    public DAOMemoria(List<EntidadPersistente> entidades){
        this.entidades = entidades;
    }


    @Override
    public <T> List<T> buscarTodos() {
        return (List<T>)this.entidades;
    }

    @Override
    public <T> T buscar(int id) {
        return (T) this.entidades.stream()
                .filter(entidad -> entidad.getId() == id)
                .collect(Collectors.toList()).get(0);
    }

    @Override
    public void agregar(Object unObjeto) {
        this.entidades.add((EntidadPersistente) unObjeto);
    }

    @Override
    public void modificar(Object unObjeto) {

        //simplemente se modifica el en el universo de objetos

    }

    @Override
    public void eliminar(Object unObjeto) {

        this.entidades.remove(unObjeto);

    }

    @Override
    public <T> T buscar(String unString){
        return null;
    }
}
