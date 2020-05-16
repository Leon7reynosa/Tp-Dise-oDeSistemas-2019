package api_Clima.openWeather;

public class ClimaOW {

    public float temp;
    //pressure
    //humidity
    public float temp_min;
    public float temp_max;

    public String getInfo(){

        return "INFO:\n " + "TEMPERATURA: " +  this.temp + "\n" + "TEMPERATURA MINIMA: " +
                this.temp_min + "\n" + "TEMPERATURA MAXIMA: " + this.temp_max + "\n";
    }

    public float getTemp_min(){

        return temp_min - 273;

    }

    public float getTemp_max(){
        return temp_max - 273;
    }

}
