package Domain.entidades;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import Domain.Clima;
import Domain.ConsultorClima;
import Json_Configuracion.ObtenerConfiguracion;

import javax.persistence.*;

@Entity
@Table(name = "evento")
public class Evento extends EntidadPersistente{

	@Column(name = "descripcion")
	private String descripcionEvento;

	@ManyToOne
	@JoinColumn(name = "usuario_id")
	private Usuario usuario;

	@Column(name = "instantaneo")
	private boolean instantaneo;

	@OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
	private List<Sugerencia> sugerencias;

	@OneToOne(cascade = {CascadeType.ALL})
	@JoinColumn(name = "sugerencia_actual_id")
	private Sugerencia sugerenciaActual;

	public EstiloVestimenta getEstiloVestimenta() {
		return estiloVestimenta;
	}

	@Enumerated(EnumType.STRING)
	@Column(name = "estilo_vestimenta")
	private EstiloVestimenta estiloVestimenta;

	@OneToOne(cascade = {CascadeType.ALL})
	@JoinColumn(name = "frecuencia_id")
	private TipoDeFrecuencia frecuencia;

	@ManyToOne
	@JoinColumn(name = "guardarropa_id")
	private Guardarropa guardarropa;

	//////////////////////////////////////////////Constuctor, setters y getters/////////////////////////////////////////
	public Evento(){
		sugerencias = new ArrayList<>();
	}

	public Evento(String unaDescripcion, TipoDeFrecuencia frecuencia, EstiloVestimenta estiloVestimenta,
				  Usuario unUsuario, Guardarropa unGuardarropa ) {

		this.descripcionEvento = unaDescripcion;
		this.frecuencia = frecuencia;
		this.estiloVestimenta = estiloVestimenta;
		this.sugerencias = new ArrayList<>();
		this.frecuencia.setEvento(this);
		this.usuario = unUsuario;
		this.guardarropa = unGuardarropa;

		if(this.estasProximo()){
			this.generaSugerencia(this.usuario);
		}


	}

	public boolean isInstantaneo() {
		return instantaneo;
	}

	public void setInstantaneo(boolean instantaneo) {
		this.instantaneo = instantaneo;
	}

	public Guardarropa getGuardarropa() {
		return guardarropa;
	}

	public void setGuardarropa(Guardarropa guardarropa) {
		this.guardarropa = guardarropa;
	}

	public void setFrecuencia(TipoDeFrecuencia _frecuencia) {

		frecuencia = _frecuencia;

	}

	public void setDescripcionEvento(String unaDescripcion){
		this.descripcionEvento = unaDescripcion;
	}

	public void setUsuario(Usuario unUsuario){
		this.usuario = unUsuario;
	}

	public void setEstiloVestimenta(EstiloVestimenta unEstilo){
		this.estiloVestimenta = unEstilo;
	}

	public LocalDate getDate() {

		LocalDate fecha;

		try{
			fecha = this.frecuencia.getFechaDelEvento();
		}catch (IOException ex){
			fecha = null;

		}

		return fecha;
	}

	public List<Sugerencia> getSugerencia() {
		return this.sugerencias;
	}

	public void setSugerenciaActual(Sugerencia sugerencia){
		this.sugerenciaActual = sugerencia;
	}

	public Sugerencia getSugerenciaActual(){
		return sugerenciaActual;
	}

	public String getDescripcionEvento() {
		return this.descripcionEvento;
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public long diasRestantes() {

		LocalDateTime timeActual = LocalDateTime.now();

		Duration duracion = Duration.between(timeActual, this.getDate().atTime(timeActual.toLocalTime()));

		return duracion.toDays();
	}

	public boolean estasProximo() {

		ObtenerConfiguracion configuracion = ObtenerConfiguracion.getInstance();

		int proximidad = configuracion.getProximidadEnDias();

		long days = this.diasRestantes();

		return days <= proximidad;
	}

	public boolean tenesSugerencia() {

		return this.sugerenciaActual != null;

	}

	public boolean tenesEsteUsuario(Usuario usuarioDuenio){
		return this.usuario == usuarioDuenio;
	}

	public void agregaSugerencia(Sugerencia unaSugerencia){

		this.sugerencias.add(unaSugerencia);

	}

	public void generaSugerencia(Usuario usuario) {

		List<Guardarropa> listaGuardarropas = usuario.getGuardarropas();

		Clima climaDeEvento = new ConsultorClima().getClimaParaFecha(this.getDate());

		Sugerencia sugerenciaCreada = this.getGuardarropa().generaSugerencia(usuario, climaDeEvento);

		this.agregaSugerencia(sugerenciaCreada);

		this.setSugerenciaActual(sugerenciaCreada);

		if(sugerenciaCreada != null){
			sugerenciaActual.agregarEvento(this);
			sugerenciaActual.setFechaDeUso(this.getDate());
		}

	}

	public void finalizate(Usuario usuario, Evento evento) {
		this.frecuencia.finalizate(usuario, evento);
	}

	public void rechazarSugerenciaActual(){
		this.getSugerenciaActual().setRechazado(true);

		generaSugerencia(this.usuario);
		/* despues lo miro, estaba hecho para otra cosa esto
		List<Sugerencia> sugerencias_no_rechazadas = this.sugerencias.stream()
				.filter(sugerencia -> sugerencia.getFechaDeUso().isEqual(this.getDate()) && !sugerencia.isRechazado())
				.collect(Collectors.toList());

		if(!sugerencias_no_rechazadas.isEmpty()){
			this.setSugerenciaActual(sugerencias_no_rechazadas.get(0));
		}
		else{
			this.setSugerenciaActual(null);
		}
		*/



	}

}
