package Domain.entidades;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import Domain.Clima;

import javax.persistence.*;

@Entity
@Table(name = "molde")
public class MoldeAtuendo extends EntidadPersistente implements Cloneable{

	@ManyToMany(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
	@JoinTable(name = "molde_tipoPrenda",
				joinColumns = @JoinColumn(name = "molde_id"),
				inverseJoinColumns = @JoinColumn(name = "tipoPrenda_id")
	)
	private List<TipoDePrenda> listaTipoPrendas;

	public MoldeAtuendo(){
		super();
		this.listaTipoPrendas = new ArrayList<>();
	}

	public MoldeAtuendo(List<TipoDePrenda> listaPrendas) {

		this.listaTipoPrendas = new ArrayList<TipoDePrenda>();

		// verificar si son compatibles las prendas?

		this.listaTipoPrendas.addAll(listaPrendas);
		
		this.guardarMolde();

	}
	
	public void guardarMolde() {
		
	}
	

	public Object clone() throws CloneNotSupportedException {

		return super.clone();

	}

	public int puntosDeAbrigo() {

		return this.listaTipoPrendas.stream().mapToInt(tipoPrenda -> tipoPrenda.getPuntosDeAbrigo()).sum();

	}

	public int puntosDeAbrigo(Categoria categoria){

		List<TipoDePrenda> tiposPorCategoria = this.tiposFiltradosPorCategoria(categoria);

		return tiposPorCategoria.stream().mapToInt(tipo -> tipo.getPuntosDeAbrigo()).sum();
	}

	private List<TipoDePrenda> tiposFiltradosPorCategoria(Categoria categoria){

		return this.listaTipoPrendas.stream().filter(tipo -> tipo.sosDeCategoria(categoria)).collect(Collectors.toList());

	}

	public List<TipoDePrenda> getListaPrendas() {

		return this.listaTipoPrendas;

	}

	public void agregarNuevaPrenda(TipoDePrenda prendaNueva) {

		this.listaTipoPrendas.add(prendaNueva); // deberia ser set?

	}

	public boolean tenesEstaPrenda(TipoDePrenda prendaBuscada) {

		return this.listaTipoPrendas.contains(prendaBuscada);

	}
	
	public boolean soportasEstaAbrigacion(double abrigacionNecesaria) {

		boolean cumple = this.puntosDeAbrigo() < (abrigacionNecesaria + 3) && this.puntosDeAbrigo() > (abrigacionNecesaria - 3);

		return cumple;
		
	}
	
	
	public boolean sosAptoParaClima(Clima clima) {
		int nivelDeAbrigo;

		return true;

	}

	public boolean podesCreartePorEstaLista(Set<Prenda> listaDePrendas) {

		boolean cumple = this.listaTipoPrendas.stream().allMatch(
				tipoDePrenda -> listaDePrendas.stream().anyMatch(prenda -> prenda.getTipo().equals(tipoDePrenda)));

		return cumple;

	}

	public TipoDePrenda dameUltimaPrenda(Categoria categoria) {

		// Deberia de devolvernos SIEMPRE 1 lista con 1 solo elemento (el de arriba)
		TipoDePrenda ultimaPrenda = this.prendasNoSuperponibles(categoria).get(0);

		return ultimaPrenda;
	}

	private List<TipoDePrenda> prendasDeCategoria(Categoria categoria){

		return  this.listaTipoPrendas.stream()
				.filter(tipoPrenda -> tipoPrenda.sosDeCategoria(categoria)).collect(Collectors.toList());

	}

	private List<TipoDePrenda> prendasNoSuperponibles(Categoria categoria){

		List<TipoDePrenda> prendasFiltradas = this.prendasDeCategoria(categoria);

		return prendasFiltradas.stream()
				.filter(tipoDePrenda -> !tipoDePrenda.algunoEsSuperponible(this.tiposSinLaPrenda(tipoDePrenda)))
				.collect(Collectors.toList());

	}

	private List<TipoDePrenda> prendasNoCombinables(Categoria categoria) {

		// best funcion ever, por que no encontrabamos un FIND

		List<TipoDePrenda> prendasFiltradas = this.listaTipoPrendas.stream()
				.filter(tipoPrenda -> tipoPrenda.sosDeCategoria(categoria)).collect(Collectors.toList());

		return prendasFiltradas.stream()
				.filter(tipoDePrenda -> tipoDePrenda.noSosCombinable(this.tiposSinLaPrenda(tipoDePrenda)))
				.collect(Collectors.toList());

	}

	private List<TipoDePrenda> tiposSinLaPrenda(TipoDePrenda tipoARemover) {

		// best funcion ever x 2, por que no encontrabamos un FIND

		List<TipoDePrenda> listaSinPrendaARemover = this.listaTipoPrendas.stream()
				.filter(tipoDePrenda -> !tipoDePrenda.equals(tipoARemover)).collect(Collectors.toList());

		return listaSinPrendaARemover;

	}

}
