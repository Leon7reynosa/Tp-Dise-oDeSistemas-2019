package Domain.entidades;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "sugerencia")
public class Sugerencia extends EntidadPersistente{

	@OneToOne(cascade={CascadeType.ALL})
	@JoinColumn(name = "atuendo_id")
	private Atuendo atuendo;

	@ManyToOne
	@JoinColumn(name = "evento_id", referencedColumnName = "id")
	private Evento evento;

	@Column(name = "fecha_creacion")
	private LocalDate fechaDeCreacion;

	@Column(name = "fecha_de_uso")
	private LocalDate fechaDeUso;

	@ManyToOne
	@JoinColumn(name = "usuario_id", referencedColumnName = "id")
	private Usuario usuario;

	@OneToOne(cascade = {CascadeType.ALL})
	@JoinColumn(name = "calificacion_id",referencedColumnName = "id")
	private CalificacionSugerencia calificacion;

	@Column(name = "rechazado")
	private boolean rechazado;

	public Sugerencia(){
		super();
		this.fechaDeCreacion = LocalDate.now();
		this.evento = null;
		this.atuendo = null;
	}

	public Sugerencia(Atuendo atuendoSugerencia) {

		this.atuendo = atuendoSugerencia;
		this.evento = null;
		this.fechaDeCreacion = LocalDate.now();

	}

	public boolean isRechazado() {
		return rechazado;
	}

	public void setRechazado(boolean rechazado) {
		this.rechazado = rechazado;
	}

	public LocalDate getFechaDeUso() {
		return fechaDeUso;
	}

	public void setFechaDeUso(LocalDate fechaDeUso) {
		this.fechaDeUso = fechaDeUso;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Atuendo getAtuendo() {
		return atuendo;
	}

	public void setEvento(Evento evento) {
		this.evento = evento;
	}

	public LocalDate getFechaDeCreacion() {
		return fechaDeCreacion;
	}

	public void setFechaDeCreacion(LocalDate fechaDeCreacion) {
		this.fechaDeCreacion = fechaDeCreacion;
	}

	public void setAtuendo(Atuendo unAtuendo){
		this.atuendo = unAtuendo;
	}

	public Evento getEvento() {
		return this.evento;

	}

	public void setCalificacion(CalificacionSugerencia _calificacion){
		this.calificacion = _calificacion;
	}

	public CalificacionSugerencia getCalificacion(){
		return this.calificacion;
	}

	public void agregarEvento(Evento eventoASetear) {

		this.evento = eventoASetear;

	}

	public Atuendo atuendo() {
		return this.atuendo;
	}

	public boolean tenesMismoAtuendo(Atuendo atuendo) {

		return this.atuendo.sosIgualA(atuendo);

	}

	public float abrigacionTotal() {

		return this.atuendo.getPrendas().stream().mapToInt(prenda -> prenda.getTipo().getPuntosDeAbrigo()).sum();

	}

	public boolean cumplisEsteClima(double abrigacion) {

		return abrigacion <= (this.abrigacionTotal() + 3) || abrigacion >= (this.abrigacionTotal() - 3);

	}

	public boolean podesCalifacarte(){
		//tenes que haber sido aceptada, no haber sido calificada, y , haber sido instantanea o cumplir
		return this.fuisteAceptada() && !this.fuisteCalificada() && ( this.terminoTuEvento() );
	}

	private boolean fuisteAceptada(){
		return this.getUsuario().aceptasteSugerencia(this);
	}

	private boolean fuisteCalificada(){
		return this.calificacion != null;
	}

	public boolean tenesEvento(){
		return this.evento != null;
	}

	/**/
	private boolean  terminoTuEvento() {

		LocalDate fechaDeHoy = LocalDate.now();

		return this.fechaDeUso.isBefore(fechaDeHoy);
	}

}
