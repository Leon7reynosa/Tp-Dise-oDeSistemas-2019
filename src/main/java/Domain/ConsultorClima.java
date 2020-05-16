package Domain;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import api_Clima.AccuWeatherAPI;
import api_Clima.ClimaAPI;
import api_Clima.ExcepcionDeApi;
import api_Clima.OpenWeatherAPI;

public class ConsultorClima {

    private List<ClimaAPI> apisDelClima;
    private ClimaAPI apiEnUso;

    public ConsultorClima(){

        apisDelClima = new ArrayList<ClimaAPI>();

        apisDelClima.add(new AccuWeatherAPI());
        apisDelClima.add(new OpenWeatherAPI());

        apiEnUso = apisDelClima.get(0);

    }

    private int cantidadDeApis(){
    	return this.apisDelClima.size();
	}

	public void agregarApi(ClimaAPI api){
    	this.apisDelClima.add(api);
	}

	private void cambiarDeApi(){

		ClimaAPI apiQueNoFunciona = this.apisDelClima.remove(0);

		this.setApiDelClima(this.apisDelClima.get(0));

		this.agregarApi(apiQueNoFunciona);

	}

	//puede devolver null, significa que ninguna api anda
    public Clima getClimaActual(){

    	Clima climaObtenido = this.apiEnUso.getClimaActual() ;
    	int apisUsadas = 1;

    	while(climaObtenido == null && apisUsadas < this.cantidadDeApis()){

    		this.cambiarDeApi();

    		climaObtenido = this.apiEnUso.getClimaActual();

			apisUsadas ++;
		}

    	return climaObtenido;

    }


    
    //llamar a esta funcion sila fecha es proxima, sino devuelve null (deberia)
    //se asume que se le pasa una fecha que esta en el rango de [hoy - 4 dias mas]
	//puede devolver null, osea, ningun api anda
    public Clima getClimaParaFecha(LocalDate fecha) {

		Clima climaDeFecha = null;
		List<Clima> climasDevueltoPorApi = this.apiEnUso.getClimaDe5Dias();
		int apisUsadas = 1;

		while(climasDevueltoPorApi == null && apisUsadas <= this.cantidadDeApis()){

			this.cambiarDeApi();

			climasDevueltoPorApi = this.apiEnUso.getClimaDe5Dias();

			apisUsadas++;

		}

		if(climasDevueltoPorApi != null) {
			climaDeFecha = climasDevueltoPorApi.stream().filter(
					clima -> clima.getFecha().equals(fecha)
			).collect(Collectors.toList()).get(0);
		}

    	return climaDeFecha;
    		


    }
    
    public void setApiDelClima(ClimaAPI nuevaApi) {
    	
    	this.apiEnUso = nuevaApi;
    	
    }

    public void limpiarApis(){

    	this.apisDelClima.clear();
    	this.setApiDelClima(null);
	}
    
}
