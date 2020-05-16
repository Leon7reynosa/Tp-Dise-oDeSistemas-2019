package api_Clima;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import Domain.Clima;
import api_Clima.openWeather.ClimaOW;
import api_Clima.openWeather.ListOW;
import api_Clima.openWeather.ResponseClimaOW;
import api_Clima.openWeather.ResponseListaClimaOW;
import api_Clima.openWeather.Retrofit_OpenWeather_Service;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class OpenWeatherAPI implements ClimaAPI {

	public Clima getClimaActual(){
		Retrofit retrofit = new Retrofit.Builder().baseUrl("http://api.openweathermap.org")
				.addConverterFactory(GsonConverterFactory.create()).build();

		Retrofit_OpenWeather_Service service = retrofit.create(Retrofit_OpenWeather_Service.class);
		// harcodeado el id y la apiKey, hay que sacarlo de un config o algo asi;
		Call<ResponseClimaOW> call = service.getClimaActual(3435910, "f31f2f142a96e7666b110ee8a5e7a0cc");

		try {

			Response<ResponseClimaOW> response = call.execute();
			ClimaOW clima = response.body().main;

			return new Clima(LocalDateTime.now().toLocalDate(), clima.getTemp_min(), clima.getTemp_max());

		} catch (Exception ex) {

			return null;

		}
	}

	public List<Clima> getClimaDe5Dias(){

		Retrofit retrofit = new Retrofit.Builder().baseUrl("http://api.openweathermap.org")
				.addConverterFactory(GsonConverterFactory.create()).build();

		Retrofit_OpenWeather_Service service = retrofit.create(Retrofit_OpenWeather_Service.class);
		// harcodeado el id y la apiKey, hay que sacarlo de un config o algo asi;
		Call<ResponseListaClimaOW> call = service.getClimaPor5(3435910, "f31f2f142a96e7666b110ee8a5e7a0cc");

		try {

			Response<ResponseListaClimaOW> response = call.execute();

			List<Clima> climasPor5 = this.sumarClimasConMismaFecha(response.body().list, 5);
			
			System.out.println("Cantidad de climas: "+ climasPor5.size());
			
			return climasPor5;

		} catch (Exception ex) {

			return null;

		}

	}
	
	private List<Clima> sumarClimasConMismaFecha(List<ListOW> climasConMismaFecha, int cantidadDeFechas){
		
		List<Clima> climasFinales = new ArrayList<Clima>();
		
		int cantidadClimasParaFecha = 0;
		int posicionEnLista = 0;
		
		for(int i = 0; i < (cantidadDeFechas); i++) {
			
			ListOW primerClimaConFechaDeLista = climasConMismaFecha.get(posicionEnLista);
			
			List<ClimaOW> climasOWMismaFecha = 
					climasConMismaFecha.stream().filter(listaOW -> listaOW.getDate().equals(primerClimaConFechaDeLista.getDate())).map(
								listOW -> listOW.main
							).collect(Collectors.toList());
					
			cantidadClimasParaFecha = climasOWMismaFecha.size();
			
			posicionEnLista += cantidadClimasParaFecha;
			
			Clima nuevoClima = this.sumarClimas(climasOWMismaFecha, primerClimaConFechaDeLista.getDate());
			
			climasFinales.add(nuevoClima);
			
		}
		
		
		return climasFinales;
	}
	
	private Clima sumarClimas(List<ClimaOW> climas, LocalDate fecha) {
		
		float temp_max = 0;
		float temp_min = 0;	
		
		int cantidadDeClimas = climas.size();
		
		for(int i = 0; i < cantidadDeClimas; i++) {
			
			temp_max += climas.get(i).getTemp_max();
			temp_min += climas.get(i).getTemp_min();
			
		}
		
		//promedio
		temp_max = temp_max / cantidadDeClimas;
		temp_min = temp_min / cantidadDeClimas;
		
		return new Clima(fecha, temp_min, temp_max);
		
	}

}
