package Domain.entidades;

import Domain.Clima;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "sensibilidad")
public class SensibilidadUsuario extends EntidadPersistente{

	@OneToMany(mappedBy = "sensibilidadGeneral",cascade = {CascadeType.ALL})
	private List<SensibilidadPorParte> sensibilidades;

	public SensibilidadUsuario() {

		sensibilidades = new ArrayList<SensibilidadPorParte>();

		SensibilidadPorParte sensibilidadSuperior = new SensibilidadPorParte(this,
				Categoria.SUPERIOR, -4.0/5.0, 25);
		SensibilidadPorParte sensibilidadInferior = new SensibilidadPorParte(this,
				Categoria.INFERIOR, -1.0/5.0, 10);

		sensibilidades.add(sensibilidadSuperior);
		sensibilidades.add(sensibilidadInferior);
	}
/*
prom : 15,27
	puntosNecesariosSuperior = 12,78
	puntosNecesariosInferior = 6,94
	19,72

*/


	public double puntosNecesariosParaClima(Clima clima) {

		double chanchada = this.pendiente() * clima.getClimaPromedio() + this.ordenadaAlOrigen();

		return chanchada;

	}

//	private boolean cumpleSensibilidades(Molde moldeAtuendo, Clima climaDado) {
//
//		return this.sensibilidades.stream().allMatch(sensibilidad -> sensibilidad.sosCubiertaPor(atuendo));
//
//	}
	
	private double pendiente() {

		double chanchada = this.sensibilidades.stream().mapToDouble(sensibilidad -> sensibilidad.getPendiente()).sum();

		return chanchada;
		
	}
	
	private double ordenadaAlOrigen() {

		double chanchada = this.sensibilidades.stream().mapToDouble(sensibilidad -> sensibilidad.getOrdenadaAlOrigen()).sum();

		return chanchada;
		
	}

	public boolean sosCubiertaPor(MoldeAtuendo moldeAtuendo, Clima clima){

		return this.sensibilidades.stream().allMatch(sensibilidad -> sensibilidad.sosCubiertaPor(moldeAtuendo, clima));

	}

	//aca habria que tener cuidado de que get parte no devuelva null...
	// PERO no deberia, todos tienen todas las sensibilidades
	public void acomodarSensiblidad(Categoria unaCategoria, int unPuntaje){

		this.getParte(unaCategoria).modificarSoporteTemperatura(unPuntaje);

	}

	private SensibilidadPorParte getParte(Categoria unaCategoria){

		SensibilidadPorParte parteElegida =
				this.sensibilidades.stream()
				.filter(sensibilidad -> sensibilidad.sosDeCategoria(unaCategoria))
				.collect(Collectors.toList())
				.get(0);

		return parteElegida;

	}


}
