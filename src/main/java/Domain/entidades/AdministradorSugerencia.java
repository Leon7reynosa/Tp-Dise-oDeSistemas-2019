package Domain.entidades;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import Domain.Clima;
import Domain.ConsultorClima;

import javax.persistence.*;


/*@Entity
@Table(name = "administrador")*/
@Embeddable
public class AdministradorSugerencia /*extends EntidadPersistente */{

	@OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
	@JoinColumn(name = "admin_id")
	private List<Atuendo> atuendosRechazados;

	@OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
	@JoinColumn(name = "admin_id")
	private List<Sugerencia> sugerenciasAceptadas;


	@OneToOne(cascade = {CascadeType.ALL})
	@JoinColumn(name = "commando_id")
	private UndoableCommand ultimaEleccion;
	
	public AdministradorSugerencia() {

		atuendosRechazados = new ArrayList<Atuendo>();
		ultimaEleccion = null;
		
	}

	public List<Atuendo> getAtuendosRechazados() {
		return atuendosRechazados;
	}

	public void setAtuendosRechazados(List<Atuendo> atuendosRechazados) {
		this.atuendosRechazados = atuendosRechazados;
	}

	public List<Sugerencia> getSugerenciasAceptadas() {
		return sugerenciasAceptadas;
	}

	public void setSugerenciasAceptadas(List<Sugerencia> sugerenciasAceptadas) {
		this.sugerenciasAceptadas = sugerenciasAceptadas;
	}

	public UndoableCommand getUltimaEleccion() {
		return ultimaEleccion;
	}

	public void setUltimaEleccion(UndoableCommand ultimaEleccion) {
		this.ultimaEleccion = ultimaEleccion;
	}

	public List<Atuendo> atuendosRechazados(){
		
		return this.atuendosRechazados;
		
	}
	
	public UndoableCommand ultimaEleccion() {
		
		return this.ultimaEleccion;
		
	}
	
	public void agregarAtuendoRechazado(Atuendo atuendo) {
		this.atuendosRechazados.add(atuendo);
	}

	public void eliminarAtuendoRechazado(Atuendo atuendo) {

		this.atuendosRechazados.remove(atuendo);

	}

	public void agregarSugerenciaAceptada(Sugerencia unaSugerencia) {
		this.sugerenciasAceptadas.add(unaSugerencia);
	}

	public void eliminarSugerenciaAceptada(Sugerencia unaSugerencia) {
		this.sugerenciasAceptadas.remove(unaSugerencia);
	}


	public Sugerencia generarSugerencia(Usuario usuario,Guardarropa guardarropa){
		
		Clima clima = new ConsultorClima().getClimaActual();

		Sugerencia sugerenciaRecibida = guardarropa.generaSugerencia(usuario, clima);

		if(sugerenciaRecibida != null){
			sugerenciaRecibida.setFechaDeUso(LocalDate.now());
		}

		return sugerenciaRecibida;

	}

	public boolean yaFueRechazada(Sugerencia sugerencia){

		return this.atuendosRechazados.stream().anyMatch(atuendo -> sugerencia.tenesMismoAtuendo(atuendo));

	}

	public boolean yaFueAceptada(Sugerencia sugerencia){
		return this.sugerenciasAceptadas.contains(sugerencia);
	}



}
