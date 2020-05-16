package Domain.Notificacion;

import Json_Configuracion.ObtenerConfiguracion;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.Twilio;
import com.twilio.type.PhoneNumber;

public class NotificadorSMS implements Notificador {

    //numero de contacto ? SI -> Agregar a JSON
    private static String From = ObtenerConfiguracion.getInstance().getMailContacto();


    public void notificar(DataNotificador dtoNotificacion){
        final String ACCOUNT_SID = "ACf629630474633e9eb490cd7d89b786da";
        final String AUTH_TOKEN = "1c9f5c9213b2b90e87efa0d48b3ff9ea";

        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

        Message message = Message
                .creator(new PhoneNumber(dtoNotificacion.getNumeroDestinatario()), new PhoneNumber("+1 256-367-3925"),"Tu sugerencia esta lista !" )
                .create();

        System.out.println(message.getSid());
    }

}
