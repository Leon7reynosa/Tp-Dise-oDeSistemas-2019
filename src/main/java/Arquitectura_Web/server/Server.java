package Arquitectura_Web.server;

import Domain.cron.CronTriggerGeneradorSugerencia;
import spark.Spark;
import spark.debug.DebugScreen;

public class Server {
    public static void main(String[] args) {
        Spark.port(9002);
        //hay que levantar el cron aca?
        CronTriggerGeneradorSugerencia cron = new CronTriggerGeneradorSugerencia();
        try{
            cron.inicializarCron();
        }catch (Exception ex){
        }

        Router.init();
//        DebugScreen.enableDebugScreen(); // esto ha que sacarlo
    }
}