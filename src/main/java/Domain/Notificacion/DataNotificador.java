package Domain.Notificacion;

public class DataNotificador {
	private String mailDestinatario;
	private String numeroDestinatario;
	private String mensaje;
	private String topico;

	public DataNotificador(String mailUsuario, String mensajeAEnviar, String numero, String _topico) {
		this.mailDestinatario = mailUsuario;
		this.numeroDestinatario = numero;
		this.mensaje = mensajeAEnviar;
		this.topico = _topico;

	}

	public String getNumeroDestinatario() {
		return numeroDestinatario;

	}

	public String getMailDestinatario() {
		return mailDestinatario;
	}

	public String getMensajeAEnviar() {
		return mensaje;
	}
	
	public String getTopico() {
		return topico;
	}
}
