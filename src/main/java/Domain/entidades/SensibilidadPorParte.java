package Domain.entidades;

import Domain.Clima;

import javax.persistence.*;

@Entity
@Table(name = "sensibilidad_especifica")
public class SensibilidadPorParte extends EntidadPersistente{

	@ManyToOne
	@JoinColumn(name = "sensibilidad_usuario_id")
	private SensibilidadUsuario sensibilidadGeneral;

	@Enumerated(EnumType.STRING)
	@Column(name = "parte_cuerpo")
	private Categoria parteQueCubre;
	@Column(name = "pendiente")
	private double pendiente;
	@Column(name = "ordenada")
	private double ordenadaAlOrigen;

	public SensibilidadPorParte(){
		super();
	}

	public SensibilidadPorParte(SensibilidadUsuario sensibilidad,Categoria categoria, double _pendiente, double _ordenadaAlOrigen){

		parteQueCubre = categoria;
		pendiente = _pendiente;
		ordenadaAlOrigen = _ordenadaAlOrigen;
		this.sensibilidadGeneral = sensibilidad;

	}

	public double puntosNecesariosParaClima(Clima clima) {

		double chanchada = this.pendiente * clima.getClimaPromedio() + this.ordenadaAlOrigen;

		return chanchada;

	}

	//si hay que disminuir, unidad va a ser negativo
	public void modificarSoporteTemperatura(double unidad){

		this.setOrdenadaAlOrigen(this.ordenadaAlOrigen + unidad);
		// this.setPendiente(); falta esto
	}

	public void setOrdenadaAlOrigen(double ordenada) {
		this.ordenadaAlOrigen = ordenada;
	}

	public void setPendiente(double pendiente) {
		this.pendiente = pendiente;
	}
	
	public double getPendiente() {
		return pendiente;
	}

	public double getOrdenadaAlOrigen() {
		return ordenadaAlOrigen;
	}

	public boolean sosCubiertaPor(MoldeAtuendo molde, Clima clima){

		return this.estaEnRangoDeAbrigacion(molde.puntosDeAbrigo(this.parteQueCubre), clima);
	}

	private boolean estaEnRangoDeAbrigacion(int puntosDeAbrigo, Clima clima){

		return puntosDeAbrigo >= (this.puntosNecesariosParaClima(clima) - 4)
				&& puntosDeAbrigo <= (this.puntosNecesariosParaClima(clima) + 4);

	}

	public boolean sosDeCategoria(Categoria unaCategoria){

		return this.parteQueCubre.equals(unaCategoria);

	}

}
