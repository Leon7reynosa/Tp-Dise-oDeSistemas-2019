package NotificacionTests;

import Domain.Notificacion.AdministradorNotificaciones;
import Domain.Notificacion.DataNotificador;
import Domain.entidades.AdministradorSugerencia;
import Domain.entidades.Usuario;
import Json_Configuracion.ObtenerConfiguracion;
import org.junit.Test;
import persistenciaDatos.Factories.FactoryRepositorioUsuario;
import persistenciaDatos.Repositiorios.RepositorioGeneral;

import static org.junit.Assert.assertEquals;

public class NotificacionTest {


    @Test
    public void verificamosQueAgarraElUsuarioCorrecto() {

        RepositorioGeneral repo = FactoryRepositorioUsuario.get();

        Usuario peronista = repo.buscar(1);

        assertEquals(peronista.getNombre(), "Peron"); //wii, agarra bien :D
    }

    @Test
    public void probamosNotificacionesParaSugerenciaLista(){ //andaa, faltaría anidarlo con el cron
        RepositorioGeneral repo = FactoryRepositorioUsuario.get();
        Usuario peronista = repo.buscar(1);

        AdministradorNotificaciones adminNoti = new AdministradorNotificaciones();
        DataNotificador dataDelPeronista =
                new DataNotificador(peronista.getEmail(), "Tu sugerencia esta lista wn ctm !", peronista.getNumero(), "QueMePongo!");

        adminNoti.notificar(dataDelPeronista);
    }

    @Test
    public void obtenemosElMailDeLaEmpresa(){
        String mailDeLaEmpresa = ObtenerConfiguracion.getInstance().getMailContacto();

        assertEquals("olivera4lautaro@gmail.com", mailDeLaEmpresa);
    }
}
