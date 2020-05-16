package Domain.sugerencia;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import Domain.*;
import Domain.entidades.*;
import Domain.entidades.MoldeAtuendo;
import persistenciaDatos.Factories.FactoryRepositorioMoldeAtuendo;
import persistenciaDatos.Repositiorios.RepositorioGeneral;

public class AlgoritmoDeSugerencia {

	public Sugerencia generameSugerencia(Usuario usuario, Clima clima, Set<Prenda> listaPrendas) {

		double abrigacionNecesaria = usuario.cuantoAbrigoNecesitas(clima);

		List<MoldeAtuendo> moldesQueAbrigan = this.moldesAptoParaAbrigar(abrigacionNecesaria, listaPrendas);

		System.out.println("Moldes que abrigan: " + moldesQueAbrigan.size());

		List<MoldeAtuendo> moldesAptos = this.moldesAptosParaSensibilidad(usuario,clima,moldesQueAbrigan);

		System.out.println("Moldes aptos: " + moldesAptos.size());

		MoldeAtuendo moldeElegido = this.dameUnMolde(moldesAptos); // posible loop para cambiar de molde si no te da ningun atuendo

		if(moldeElegido == null){
			return null;
		}

		Sugerencia unaSugerencia = this.dameUnaSugerencia(usuario, moldeElegido, listaPrendas);

		while(unaSugerencia == null && (moldesAptos.size() > 1)){
			moldesAptos.remove(moldeElegido);
			moldeElegido = this.dameUnMolde(moldesAptos);
			unaSugerencia = this.dameUnaSugerencia(usuario, moldeElegido, listaPrendas);
		}

		return unaSugerencia;

	}

	private List<MoldeAtuendo> moldesAptoParaAbrigar(double nivelDeAbrigo, Set<Prenda> listaPrendas) {

		RepositorioGeneral prototipoMoldes = FactoryRepositorioMoldeAtuendo.get();

		List<MoldeAtuendo> moldes = prototipoMoldes.buscarTodos();

		List<MoldeAtuendo> listaMoldesAptosClima = moldes.stream().filter(
				molde -> molde.soportasEstaAbrigacion(nivelDeAbrigo) && molde.podesCreartePorEstaLista(listaPrendas))
				.collect(Collectors.toList());

		return listaMoldesAptosClima;

	}

	private List<MoldeAtuendo> moldesAptosParaSensibilidad(Usuario usuario,Clima clima ,List<MoldeAtuendo> moldes){

		List<MoldeAtuendo> moldesAptos = moldes.stream().filter(
										molde -> usuario.satisfaceSensibilidad(molde, clima)
										).collect(Collectors.toList());

		return moldesAptos;
	}



	private Sugerencia dameUnaSugerencia(Usuario unUsuario, MoldeAtuendo molde, Set<Prenda> listaPrendas) {

		int numeroCombinaciones = this.combinaciones(molde, listaPrendas);

		System.out.println("Numero de combinaciones: " + numeroCombinaciones);

		Set<Sugerencia> sugerenciasPosibles = new HashSet<>();

		Sugerencia unaSugerencia = new Sugerencia();
		Atuendo atuendoGenerado = new Atuendo();
		unaSugerencia.setAtuendo(atuendoGenerado);

		boolean seguir_en_while = true;
		while(seguir_en_while && (sugerenciasPosibles.size() < numeroCombinaciones)){

			atuendoGenerado = new Atuendo();

			for (int i = 0; i < molde.getListaPrendas().size(); i++) {

				TipoDePrenda tipoPrenda = molde.getListaPrendas().get(i);

				List<Prenda> prendasPorTipo = listaPrendas.stream().filter(prenda -> prenda.getTipo().equals(tipoPrenda))
						.collect(Collectors.toList());

				int random = this.devolvemeNumeroRandom(prendasPorTipo.size());

				Prenda prendaParaAtuendo = prendasPorTipo.get(random);

				atuendoGenerado.agregaPrenda(prendaParaAtuendo);

			}

			unaSugerencia.setAtuendo(atuendoGenerado);
			sugerenciasPosibles.add(unaSugerencia);

			if(!unUsuario.rechazasteAtuendo(unaSugerencia)){
				seguir_en_while = false;
			}else{
				atuendoGenerado = null;
			}

		}

		if(atuendoGenerado == null){
			return null;
		}else{

			unaSugerencia.setUsuario(unUsuario);

			return unaSugerencia;
		}

	}

	//suponiendo que tiene de todos los tipos de prenda necesarios
	private int combinaciones(MoldeAtuendo unMolde, Set<Prenda> unasPrendas){

		int numeroCombinaciones = 1;

		List<Integer> cantidad_prendas_por_tipo = new ArrayList<>();

		for(TipoDePrenda unTipo:unMolde.getListaPrendas()){
			Set<Prenda> prendasFiltradasPorTipo =
					unasPrendas.stream().filter(unaPrenda -> unaPrenda.getTipo().equals(unTipo)).collect(Collectors.toSet());

			cantidad_prendas_por_tipo.add(new Integer(prendasFiltradasPorTipo.size()));
		}

		for(int i = 0; i < cantidad_prendas_por_tipo.size();i++){
			numeroCombinaciones = numeroCombinaciones*cantidad_prendas_por_tipo.get(i);
		}

		return numeroCombinaciones;
	}

	private MoldeAtuendo dameUnMolde(List<MoldeAtuendo> moldesAptos){

		int numeroRandom = devolvemeNumeroRandom(moldesAptos.size());

		if(numeroRandom < 0 ){
			return null;
		}

		MoldeAtuendo moldeElegido = moldesAptos.get(numeroRandom);

		return moldeElegido;

	}

	private int devolvemeNumeroRandom(int cantidadTotalLista) {

		if(cantidadTotalLista <= 0 ){
			return -1;
		}

		Random rand = new Random();
		int numeroRandom = rand.nextInt(cantidadTotalLista);

		return numeroRandom; 

	}

}
