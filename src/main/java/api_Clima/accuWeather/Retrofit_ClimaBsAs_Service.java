package api_Clima.accuWeather;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Retrofit_ClimaBsAs_Service {
	//@GET("/forecasts/v1/daily/1day/7894?apikey=PQaZVn6oWg45RAzmE5dkMsAAmIgdus2d") // 7894 es BSAS
	@GET("/forecasts/v1/daily/1day/{id}")
	Call<ResponseClimaBsAs> getClimaBsAs(@Path("id") int id, @Query("apikey") String apiKey);

	@GET("/forecasts/v1/daily/5day/{id}")
	Call<ResponseClimaBsAs> getClimaBsAs5(@Path("id") int id, @Query("apikey") String apiKey);
}
