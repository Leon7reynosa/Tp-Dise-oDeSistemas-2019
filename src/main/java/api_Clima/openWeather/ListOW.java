package api_Clima.openWeather;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ListOW {

    public String dt_txt;

    public ClimaOW main;

    public LocalDate getDate(){

        DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        LocalDateTime fecha = LocalDateTime.parse(this.dt_txt, formatoFecha);

        return fecha.toLocalDate();
    }

}
