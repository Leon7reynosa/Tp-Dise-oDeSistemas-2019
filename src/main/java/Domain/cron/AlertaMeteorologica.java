package Domain.cron;

import java.util.List;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import Domain.Clima;
import Domain.ConsultorClima;

import Domain.entidades.Evento;
import Domain.entidades.Usuario;
import Domain.Notificacion.AdministradorNotificaciones;
import Domain.Notificacion.DataNotificador;

public class AlertaMeteorologica implements Job {
	
	private AdministradorNotificaciones notificador = new AdministradorNotificaciones();
	String notificacionTopico = "�ALERTA METEOROLOGICA PROXIMA!";
	String notificacionMensaje = "Alerta Meteorologica Proxima, Deberias buscar una nueva sugerencia para el evento ";

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		// TODO Auto-generated method stub
		/*
		Repositorio repo = Repositorio.getInstance();
		
		List<Usuario> listaUsuarios = repo.getUsuarios();
		
		this.atenderUsuarios(listaUsuarios);
		*/
	}
	
	private void atenderUsuarios(List<Usuario> listaUsuarios) {
		
		for(int i = 0 ; i < listaUsuarios.size() ; i++	) {
			
			Usuario usuario = listaUsuarios.get(i);
			
			this.atenderEventoDe(usuario); //agrego usuario para fijarlo en la b�squeda
			
		}
	
	}
	
	private void atenderEventoDe(Usuario usuario) { 
		
		for (int  i = 0 ; i < usuario.cantidadDeEventos() ; i++) {
				
			Evento evento = usuario.getEventos().get(i);
			
			if(evento.tenesSugerencia()  && this.hayAlertaMeteorologica(evento, usuario)) {
				
				DataNotificador notificacionData = 
						new DataNotificador(usuario.getEmail() , this.notificacionMensaje +  evento.getDescripcionEvento(), usuario.getNumero(), this.notificacionTopico);
			
				this.notificador.notificar(notificacionData);
				
				
			}
			
		}
		
	}
	
	public boolean hayAlertaMeteorologica(Evento evento, Usuario usuario) {
		
		if(evento.estasProximo()) {
			
			Clima climaEvento = new ConsultorClima().getClimaParaFecha(evento.getDate());
				
			double abrigacionNecesaria = usuario.cuantoAbrigoNecesitas(climaEvento);
			
			return evento.getSugerenciaActual().cumplisEsteClima(abrigacionNecesaria);
			
		}
		
		return false;
		
	}
	
	
	
}
