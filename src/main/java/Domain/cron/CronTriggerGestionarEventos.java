package Domain.cron;

import Json_Configuracion.ObtenerConfiguracion;
import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

public class CronTriggerGestionarEventos {

    public void inicializarCron() throws SchedulerException {

        JobDetail job = JobBuilder.newJob(GestionarEventosCron.class).withIdentity("NoSeQueVaAca", "Grupo2").build();

        String eventoSchedule = ObtenerConfiguracion.getInstance().getEventoSchedule();

        Trigger trigger = TriggerBuilder.newTrigger().withIdentity("dummyTriggerName", "Group1")
                .withSchedule(CronScheduleBuilder.cronSchedule(eventoSchedule)).build();

        Scheduler scheduler = new StdSchedulerFactory().getScheduler();
        scheduler.start();
        scheduler.scheduleJob(job, trigger);

    }

}