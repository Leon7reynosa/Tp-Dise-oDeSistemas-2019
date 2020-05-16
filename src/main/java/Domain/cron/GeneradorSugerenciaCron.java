package Domain.cron;

import java.util.List;

import Domain.Notificacion.AdministradorNotificaciones;
import Domain.Notificacion.DataNotificador;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import Domain.entidades.Evento;
import Domain.entidades.Usuario;
import persistenciaDatos.Factories.FactoryRepositorioEvento;
import persistenciaDatos.Factories.FactoryRepositorioUsuario;
import persistenciaDatos.Repositiorios.RepositorioGeneral;

public class GeneradorSugerenciaCron implements Job {

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {

	RepositorioGeneral repoUsuario = FactoryRepositorioUsuario.get();

		List<Usuario> listaUsuarios = repoUsuario.buscarTodos();

		this.atenderUsuarios(listaUsuarios);

	}

	private void atenderUsuarios(List<Usuario> listaUsuarios) {

		for (int i = 0; i < listaUsuarios.size(); i++) {

			Usuario usuario = listaUsuarios.get(i);

			this.atenderEventoDe(usuario);

		}

	}

	private void atenderEventoDe(Usuario usuario) {

		for (int i = 0; i < usuario.cantidadDeEventos(); i++) {

			Evento evento = usuario.getEventos().get(i);

			if (evento.estasProximo() && !evento.tenesSugerencia()) {

				evento.generaSugerencia(usuario);

				FactoryRepositorioEvento.get().modificar(evento);
//				AdministradorNotificaciones admin = new AdministradorNotificaciones();
//				DataNotificador dataUsuario = this.generameDataDelUsuario(usuario);
//				admin.notificar(dataUsuario);

			}

		}

	}

	private DataNotificador generameDataDelUsuario(Usuario usuario){
		return null;
	}

}
