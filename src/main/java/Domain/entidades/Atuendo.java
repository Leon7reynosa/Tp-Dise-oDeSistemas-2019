package Domain.entidades;

import Domain.Clima;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Entity
@Table(name = "atuendo")
public class Atuendo extends EntidadPersistente {

	@ManyToMany(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
	@JoinTable(name = "atuendo_prenda",
			joinColumns = @JoinColumn(name = "atuendo_id"),
			inverseJoinColumns = @JoinColumn(name = "prenda_id")
	)
	private List<Prenda> conjuntoPrendas;

	public Atuendo() {
		super();
		this.conjuntoPrendas = new ArrayList<Prenda>();

	}

	public Atuendo(List<Prenda> prendas) {
		this.conjuntoPrendas = new ArrayList<Prenda>();
		this.conjuntoPrendas.addAll(prendas);
	}

	public List<Prenda> getPrendas() {
		return this.conjuntoPrendas;
	}

	public void agregaPrenda(Prenda prenda) {
		this.conjuntoPrendas.add(prenda);
	}

	public void ponerPrendasEnDesuso() {
		this.conjuntoPrendas.forEach(prenda -> prenda.setearPrendaUsada(false));
	}

	public List<Prenda> getConjuntoPrendas() {

		return conjuntoPrendas;

	}

	public boolean cubrisTemperatura(Clima climaActual) {

		return true;
	}

	public void agregarPrendaDeLista(Categoria categoria, List<Prenda> prendasParaAtuendo) {

		Random numero = new Random();

		List<Prenda> prendasFiltradas = prendasParaAtuendo.stream().filter(prenda -> prenda.sosDeCategoria(categoria))
				.collect(Collectors.toList());

		int index = numero.nextInt(prendasFiltradas.size());

		this.agregaPrenda(prendasFiltradas.get(index));
	}

	public void mostrarAtuendo() {
		System.out.println(">>>Atuendo<<<");
		for (int i = 0; i < this.getConjuntoPrendas().size(); i++) {
			System.out.print(this.getConjuntoPrendas().get(i).getNombrePrenda());
		}
		System.out.println("============");
		System.out.println();
	}

	public boolean sosIgualA(Atuendo atuendo) {

		return this.conjuntoPrendas.containsAll(atuendo.conjuntoPrendas);

	}
}
