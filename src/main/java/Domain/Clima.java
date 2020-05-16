package Domain;

import java.time.LocalDate;

public class Clima {

	private LocalDate fecha;
	private float tempMin;
	private float tempMax;

	public Clima(LocalDate _fecha, float min, float max) {
		super();
		fecha = _fecha;
		tempMin = min;
		tempMax = max;
	}
	
	public LocalDate getFecha() {
		return fecha;
	}

	public float getMaxima() {
		return tempMax;
	}

	public float getMinimo() {
		return tempMin;
	}

	public String getInfo() {

		return "Fecha: " + this.fecha + "\n" + "Temperatura Minima: " + this.tempMin + "\n" + "Temperatura Maxima: "
				+ this.tempMax + "\n";

	}

	public float getClimaPromedio() {

		return (this.getMinimo() + this.getMaxima()) / 2;

	}

}
