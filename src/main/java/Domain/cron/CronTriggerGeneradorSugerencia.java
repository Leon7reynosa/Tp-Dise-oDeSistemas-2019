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

public class CronTriggerGeneradorSugerencia {

	public void inicializarCron() throws SchedulerException {

		JobDetail job = JobBuilder.newJob(GeneradorSugerenciaCron.class).withIdentity("NoSeQueVaAca", "Grupo2").build();

		String sugerenciaSchedule = ObtenerConfiguracion.getInstance().getSugerenciaSchedule();

		Trigger trigger = TriggerBuilder.newTrigger().withIdentity("dummyTriggerName", "Group1")
				.withSchedule(CronScheduleBuilder.cronSchedule(sugerenciaSchedule)).build();

		Scheduler scheduler = new StdSchedulerFactory().getScheduler();
		scheduler.start();
		scheduler.scheduleJob(job, trigger);

	
	}
/*
	public static void main(String[] args) throws SchedulerException {

		JobDetail job = JobBuilder.newJob(GeneradorSugerenciaCron.class).withIdentity("NoSeQueVaAca", "Grupo2").build();

		// JobDetail job2 =
		// JobBuilder.newJob(GestionarEventosCron.class).withIdentity("NoSeQueVaAca",
		// "Grupo2").build();

		Trigger trigger = TriggerBuilder.newTrigger().withIdentity("dummyTriggerName", "Group1")
				.withSchedule(CronScheduleBuilder.cronSchedule("0/5 * * * * ?")).build();

		// Trigger trigger2 =
		// TriggerBuilder.newTrigger().withIdentity("dummyTriggerName2", "Group1")
		// .withSchedule(CronScheduleBuilder.cronSchedule("0/5 * * * * ?")).build();

		Scheduler scheduler = new StdSchedulerFactory().getScheduler();
		scheduler.start();
		scheduler.scheduleJob(job, trigger);
		// scheduler.scheduleJob(job2, trigger2);
	}
*/
}
