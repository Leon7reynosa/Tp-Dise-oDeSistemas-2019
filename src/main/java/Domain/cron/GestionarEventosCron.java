package Domain.cron;

import java.util.List;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import Domain.entidades.Usuario;
import Domain.Notificacion.AdministradorNotificaciones;
import persistenciaDatos.Factories.FactoryRepositorioUsuario;
import persistenciaDatos.Repositiorios.RepositorioGeneral;

public class GestionarEventosCron implements Job {

	private AdministradorNotificaciones notificador = new AdministradorNotificaciones();

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {

		RepositorioGeneral repoUsuario = FactoryRepositorioUsuario.get();

		List<Usuario> listaUsuarios = repoUsuario.buscarTodos();

		this.atenderUsuarios(listaUsuarios);
	}

	private void atenderUsuarios(List<Usuario> listaUsuarios) {
		listaUsuarios.stream().forEach(usuario -> this.atenderEventoDe(usuario));
	}

	private void atenderEventoDe(Usuario usuario) {
		usuario.getEventos().stream().forEach(evento -> evento.finalizate(usuario, evento));
	}

}
