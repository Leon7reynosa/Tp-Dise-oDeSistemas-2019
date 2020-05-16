package Domain.Notificacion;

import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import Json_Configuracion.ObtenerConfiguracion;

public class NotificadorEmail implements Notificador {

	private static String From = ObtenerConfiguracion.getInstance().getMailContacto();
	// ver despues si hay que cambiar los parametros, un mensaje a varios usuarios o
	// un usuario a varios mensajes

	public NotificadorEmail() {

	}

	public void notificar(DataNotificador dtoNotificacion) {
		final String APIKey = "ce2b21796485fd37e6fdba98367eb1f7";
		final String SecretKey = "f474af2bec26fc579234a60cefd0bd9b";

		Properties props = new Properties();

		props.put("mail.smtp.host", "in.mailjet.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");

		javax.mail.Session session = javax.mail.Session.getDefaultInstance(props, new javax.mail.Authenticator() {
			protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
				return new javax.mail.PasswordAuthentication(APIKey, SecretKey);
			}
		});

		try {

			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(From));
			message.setRecipients(MimeMessage.RecipientType.TO,
					InternetAddress.parse(dtoNotificacion.getMailDestinatario()));
			message.setSubject(dtoNotificacion.getTopico());
			message.setText(dtoNotificacion.getMensajeAEnviar());

			Transport.send(message);

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
}
