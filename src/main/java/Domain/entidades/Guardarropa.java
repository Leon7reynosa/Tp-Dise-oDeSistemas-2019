package Domain.entidades;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import Domain.Clima;
import Domain.sugerencia.AlgoritmoDeSugerencia;

import javax.persistence.*;

@Entity
@Table(name = "guardarropa")
public class Guardarropa extends EntidadPersistente {

	@OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
	@JoinColumn(name = "guardarropa_id")
	private Set<Prenda> prendas;

	@Column(name = "nombre")
	private String nombre;

	@Transient
	private AlgoritmoDeSugerencia algoritmo = new AlgoritmoDeSugerencia();

	@Enumerated(EnumType.STRING)
	@Column(name = "estilo_vestimenta")
	private EstiloVestimenta estiloVestimenta;

	@Column(name = "fecha_ingreso")
	private LocalDate fecha_ingreso;

	@Column(name = "descripcion")
	private String descripcion;

	public String estiloVestimentaToString(){

			switch(this.estiloVestimenta){

				case CASUAL:
					return "CASUAL";
				case FORMAL:
					return "FORMAL";
				case DEPORTIVO:
					return "DEPORTIVO";
				default:
					return "xD";
			}


	}
//////////////////////////////////////////CONSTRUCTORES////////////////////////////////////////////////////////////////

	public Guardarropa(){
		this.prendas = new HashSet<>();
		this.algoritmo = new AlgoritmoDeSugerencia();
	}

	public Guardarropa(EstiloVestimenta estilo) {
		super();
		this.prendas = new HashSet<Prenda>();
		this.algoritmo = new AlgoritmoDeSugerencia();
		
		this.estiloVestimenta = estilo;
	}

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/////////////////////////////////////////////////GETTER AND SETTER//////////////////////////////////////////////////////

	public LocalDate getFecha_ingreso() {
		return fecha_ingreso;
	}

	public void setFecha_ingreso(LocalDate fecha_ingreso) {
		this.fecha_ingreso = fecha_ingreso;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public EstiloVestimenta getEstiloVestimenta() {
		return estiloVestimenta;
	}

	public void setEstiloVestimenta(EstiloVestimenta estiloVestimenta) {
		this.estiloVestimenta = estiloVestimenta;
	}

	public Set<Prenda> getPrendas() {
		return prendas;
	}

	public Set<Prenda> getNPrendas(int n) {

		return this.getPrendas().stream().limit(n).collect(Collectors.toSet());

	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public int cantidadDePrendas() {
		return this.prendas.size();
	}

	public void guardarPrenda(Prenda prenda) {
		this.prendas.add(prenda);
	}

	public void sacarPrenda(Prenda prenda) {
		this.prendas.remove(prenda);
	}

	public boolean tenesPrenda(TipoDePrenda prenda) {

		return this.prendas.contains(prenda);

	}

	public boolean sosEstiloVestimenta(EstiloVestimenta estilo) {

		return this.estiloVestimenta == estilo;

	}

	public Set<Prenda> filtrarPrendaPor(Categoria categoria) {
		return this.prendas.stream().filter(prenda -> prenda.sosDeCategoria(categoria)).collect(Collectors.toSet());
	}

	public boolean podesCompletarEsteMolde(MoldeAtuendo molde) {

		List<TipoDePrenda> listaDelMolde = molde.getListaPrendas();

		return listaDelMolde.stream().allMatch(prenda -> this.tenesPrenda(prenda));

	}

	public AlgoritmoDeSugerencia getAlgoritmo() {
		return algoritmo;
	}

	public Sugerencia generaSugerencia(Usuario usuario, Clima clima) {

		Set<Prenda> prendasDelGuardarropa = usuario.prendasAUsar(this);

		Set<Prenda> prendasNoUsadas = prendasDelGuardarropa.stream().filter(prenda -> prenda.noEstasUsada())
				.collect(Collectors.toSet());

		return this.getAlgoritmo().generameSugerencia(usuario, clima, prendasNoUsadas);
	}

}