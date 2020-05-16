package Domain.entidades;

import persistenciaDatos.Factories.FactoryRepositorioMoldeAtuendo;
import persistenciaDatos.Repositiorios.RepositorioGeneral;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class MoldeAtuendoClonador{

	private RepositorioGeneral repo;

	public MoldeAtuendoClonador() {
		repo = FactoryRepositorioMoldeAtuendo.get();
	}

	public List<MoldeAtuendo> getListaMoldes() {
		return this.repo.buscarTodos();
	}

	public Set<MoldeAtuendo> moldesConTipo(TipoDePrenda prendaBuscada) {

		Set<MoldeAtuendo> moldesFiltradosPorTipo = this.getListaMoldes().stream()
				.filter(molde -> molde.tenesEstaPrenda(prendaBuscada)).collect(Collectors.toSet());

		return moldesFiltradosPorTipo;

	}

	private List<MoldeAtuendo> moldesAClonarSegunTipo(TipoDePrenda tipoPrenda, Set<MoldeAtuendo> moldesFiltrados) {

		List<MoldeAtuendo> moldesFiltradosAClonar = moldesFiltrados.stream()
				.filter(molde -> molde.dameUltimaPrenda(tipoPrenda.getCategoria()).equals(tipoPrenda))
				.collect(Collectors.toList());

		return moldesFiltradosAClonar;
	}

	private List<MoldeAtuendo> clonarAtuendos(List<MoldeAtuendo> moldesAClonar) throws CloneNotSupportedException {

		List<MoldeAtuendo> ClonesDeMoldes = new ArrayList<MoldeAtuendo>();

		for (int i = 0; i < moldesAClonar.size(); i++) {

			MoldeAtuendo moldeAClonar = moldesAClonar.get(i);

			Object moldeClonado = moldeAClonar.clone();

			ClonesDeMoldes.add((MoldeAtuendo) moldeClonado);

		}

		return ClonesDeMoldes;

	}

	// Esta funcion devuelve una lista de los moldes clonados (de los moldes que se
	// pueden clonar)
	public List<MoldeAtuendo> generarNuevosMoldesSegun(TipoDePrenda prendaBuscada) {

		Set<MoldeAtuendo> moldesFiltradosPorTipo = this.moldesConTipo(prendaBuscada);

		List<MoldeAtuendo> moldesFiltradosAClonar = this.moldesAClonarSegunTipo(prendaBuscada, moldesFiltradosPorTipo);

		try {

			List<MoldeAtuendo> moldesClonados = this.clonarAtuendos(moldesFiltradosAClonar);

			return moldesClonados;

		} catch (CloneNotSupportedException ex) {
			// esto devuelve una lista vacia si tira excepcion
			return (new ArrayList<MoldeAtuendo>());
		}

	}

}
