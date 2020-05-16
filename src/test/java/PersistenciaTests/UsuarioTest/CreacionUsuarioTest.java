package PersistenciaTests.UsuarioTest;
import Domain.entidades.*;
import org.junit.Test;
import persistenciaDatos.Factories.FactoryRepositorioGuardarropa;
import persistenciaDatos.Factories.FactoryRepositorioUsuario;
import persistenciaDatos.Repositiorios.RepositorioGeneral;

public class CreacionUsuarioTest {

	RepositorioGeneral repoUsuario = FactoryRepositorioUsuario.get();

	@Test
	public void crearNuevoUsuario() {

		Usuario usuario = new Usuario("Peron", "lamanodedios@hotmail.com", "1889");

		repoUsuario.agregar(usuario);

	}

	@Test
	public void cambiarDatosDelUsuario(){
		RepositorioGeneral repo = FactoryRepositorioUsuario.get();
		Usuario usuario =  repo.buscar(1);

		usuario.setEmail("brianhockey98@gmail.com");
		usuario.setNumero("+54 11 4036-0374");

		repo.modificar(usuario); //anda !
	}

	@Test
	public void agregarGuardarropa(){
		RepositorioGeneral repoUsuario = FactoryRepositorioUsuario.get();
		RepositorioGeneral repoGuardarropa = FactoryRepositorioGuardarropa.get();

		Usuario unUsuario = repoUsuario.buscar(1);
		Guardarropa unGuardarropa = repoGuardarropa.buscar(1);

		unUsuario.agregarGuardarropa(unGuardarropa);

		repoUsuario.modificar(unUsuario);

	}

}
