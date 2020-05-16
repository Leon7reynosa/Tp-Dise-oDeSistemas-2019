package Domain.Notificacion;

import java.util.ArrayList;
import java.util.List;

public class AdministradorNotificaciones {

	private List<Notificador> listaNotificadores;

	public AdministradorNotificaciones() {

		listaNotificadores = new ArrayList<Notificador>();

		NotificadorEmail notificadorMail = new NotificadorEmail();
		NotificadorSMS notificadorSMS = new NotificadorSMS();
		this.agregarNotificador(notificadorMail);
		this.agregarNotificador(notificadorSMS);
	}

	public List<Notificador> getListaNotificadores() {
		return this.listaNotificadores;
	}

	public void agregarNotificador(Notificador notificadorAgregar) {

		this.listaNotificadores.add(notificadorAgregar);

	}

	public void notificar(DataNotificador usuarioNotificar) {

		this.listaNotificadores.forEach(notificador -> notificador.notificar(usuarioNotificar));

	}

}
