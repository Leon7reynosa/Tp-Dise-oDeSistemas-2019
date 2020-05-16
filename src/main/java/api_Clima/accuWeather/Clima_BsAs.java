package api_Clima.accuWeather;

public class Clima_BsAs {
	public float Value;
	public String Unit;
	public int UnitType;

	public String allClima() {
		return "Value: " + this.getValue() + "\n" + "Unit: " + this.getUnit() + "\n" /*+ "UnitType: " + this.UnitType + "\n"*/;
	}
	
	public float getValue() {
		return (Value - 32) * 5/9;
	}
	
	public String getUnit() {
		return "Celsius :)";
	}



}
