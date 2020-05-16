package api_Clima.openWeather;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Retrofit_OpenWeather_Service {

    @GET("/data/2.5/weather")
    Call<ResponseClimaOW> getClimaActual(@Query("id") int idCiudad,@Query("apikey") String apiKey );

    @GET("/data/2.5/forecast")
    Call<ResponseListaClimaOW> getClimaPor5(@Query("id") int idCiudad, @Query("apikey") String apiKey);
}
