package persistenciaDatos.Repositiorios;

import persistenciaDatos.daos.DAO;

import java.util.List;

public class RepositorioColor extends Repositorio {

        public RepositorioColor(DAO unDAO){
            this.dao = unDAO;
        }

        public <T> List<T> buscarTodos(){
            return this.dao.buscarTodos();
        }

        public <T> T buscar(int id){
            return this.dao.buscar(id);
        }

        public <T> T buscar(String usuario){
            return this.dao.buscar(usuario);
        }

}
