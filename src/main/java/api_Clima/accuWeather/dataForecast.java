package api_Clima.accuWeather;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class dataForecast {
	public String Date;
	// epoch
	public Temperature Temperature;
	// day
	// night
	// sources
	// mobileLink
	// link

	public Temperature getTemperature() {
		return this.Temperature;
	}

	public LocalDate getDate() {

		DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssz");

		LocalDateTime fecha = LocalDateTime.parse(this.Date, formatoFecha);



		return fecha.toLocalDate();
	}


}
