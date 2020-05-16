package ClimaTest;

import Domain.Clima;
import Domain.ConsultorClima;
import api_Clima.AccuWeatherAPI;
import api_Clima.OpenWeatherAPI;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ClimaTest {

	ConsultorClima consultorClima;
	AccuWeatherAPI accuWeatherAPI;
	OpenWeatherAPI openWeatherAPI;

	List<Clima> climasDe5dias;

	@Before
	public void creaciones(){

		climasDe5dias = new ArrayList<>();

		accuWeatherAPI = mock(AccuWeatherAPI.class);
		openWeatherAPI = mock(OpenWeatherAPI.class);

		consultorClima = new ConsultorClima();

		consultorClima.limpiarApis(); //esto para poder meter los mocks
		consultorClima.agregarApi(accuWeatherAPI);
		consultorClima.agregarApi(openWeatherAPI);
		consultorClima.setApiDelClima(accuWeatherAPI);

		climasDe5dias.add(new Clima(LocalDate.parse("2019-01-09"),10,15));
		climasDe5dias.add(new Clima(LocalDate.parse("2019-02-09"),15,21));
		climasDe5dias.add(new Clima(LocalDate.parse("2019-03-09"),12,18));
		climasDe5dias.add(new Clima(LocalDate.parse("2019-04-09"),13,17));
		climasDe5dias.add(new Clima(LocalDate.parse("2019-05-09"),15,20));


		when(accuWeatherAPI.getClimaActual()).thenReturn(
				new Clima(LocalDate.parse("2019-02-09"),15,21)
		);

		when(openWeatherAPI.getClimaActual()).thenReturn(
				new Clima(LocalDate.parse("2019-02-09"),15,19)
		);

		when(accuWeatherAPI.getClimaDe5Dias()).thenReturn(climasDe5dias);

	}

	@Test
	public void obtenerClimaActual(){

		Clima climaObtenido = consultorClima.getClimaActual();

		Assert.assertEquals(18.0,climaObtenido.getClimaPromedio(), 0);

	}

	@Test
	public void cambioDeApi(){

		consultorClima.setApiDelClima(openWeatherAPI);

		Clima clima = consultorClima.getClimaActual();

		Assert.assertEquals(17.0,clima.getClimaPromedio(),0);
	}

	@Test
	public void climaParaDeterminadaFecha(){

		LocalDate fechaDeterminada = LocalDate.parse("2019-03-09");

		Clima clima = consultorClima.getClimaParaFecha(fechaDeterminada);

		Assert.assertEquals(15.0,clima.getClimaPromedio(),0);
	}
}
