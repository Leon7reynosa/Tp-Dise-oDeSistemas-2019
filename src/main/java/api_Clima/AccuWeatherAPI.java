package api_Clima;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import Domain.Clima;
import api_Clima.accuWeather.*;

import java.util.ArrayList;
import java.util.List;


public class AccuWeatherAPI implements ClimaAPI {



	public Clima getClimaActual(){
		Retrofit retrofit = new Retrofit.Builder().baseUrl("http://dataservice.accuweather.com/")
				.addConverterFactory(GsonConverterFactory.create()).build();

		Retrofit_ClimaBsAs_Service service = retrofit.create(Retrofit_ClimaBsAs_Service.class);

		//harcodeado el id y la apiKey, hay que sacarlo de un config o algo asi;
		Call<ResponseClimaBsAs> call = service.getClimaBsAs(7894, "PQaZVn6oWg45RAzmE5dkMsAAmIgdus2d");
		try {
			
			Response<ResponseClimaBsAs> response = call.execute();
			Clima_BsAs climaMaximo = response.body().DailyForecasts.get(0).getTemperature().Maximum;

			Clima_BsAs climaMinimo = response.body().DailyForecasts.get(0).getTemperature().Minimum;

			return new Clima(response.body().DailyForecasts.get(0).getDate(),
					climaMinimo.getValue(), climaMaximo.getValue() );

		} catch (Exception ex) {

			return null;

		}
	}

	public List<Clima> getClimaDe5Dias(){

		List<Clima> climaDe5Dias = new ArrayList<Clima>();

		Retrofit retrofit = new Retrofit.Builder().baseUrl("http://dataservice.accuweather.com/")
				.addConverterFactory(GsonConverterFactory.create()).build();

		Retrofit_ClimaBsAs_Service service = retrofit.create(Retrofit_ClimaBsAs_Service.class);

		//harcodeado el id y la apiKey, hay que sacarlo de un config o algo asi;
		Call<ResponseClimaBsAs> call = service.getClimaBsAs5(7894, "PQaZVn6oWg45RAzmE5dkMsAAmIgdus2d");

		try {

			Response<ResponseClimaBsAs> response = call.execute();

			for(int i = 0; i < response.body().DailyForecasts.size(); i++){

				//aprovecho que estan publicos y le agarro sus atributos, nada de encapsulamiento
				//por que son DTO
				Clima_BsAs climaAccuweatherMax = response.body().DailyForecasts.get(i).Temperature.Maximum;
				Clima_BsAs climaAccuweatherMin = response.body().DailyForecasts.get(i).Temperature.Minimum;

				Clima climaDeDiaX = new Clima(response.body().DailyForecasts.get(i).getDate(),
									 climaAccuweatherMin.getValue(), climaAccuweatherMax.getValue());

				climaDe5Dias.add(climaDeDiaX);

			}



			return climaDe5Dias;

		} catch (Exception ex) {

			return null;

		}

	}


}
