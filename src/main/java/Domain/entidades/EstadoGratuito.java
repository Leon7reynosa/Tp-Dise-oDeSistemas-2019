package Domain.entidades;

import java.util.Set;

import Domain.entidades.Guardarropa;
import Domain.entidades.Prenda;
import Json_Configuracion.ObtenerConfiguracion;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("gratuito")
public class EstadoGratuito extends Estado {

	@Column(name = "capacidad_maxima")
	private int capacidadMaxima = ObtenerConfiguracion.getInstance().getPrendasMaximasGratuitas();

	public boolean podesIngresarPrenda(Guardarropa guardarropa) {
		return this.tieneEspacioSuficiente(guardarropa);
	}

	public boolean tieneEspacioSuficiente(Guardarropa guardarropa) {
		return  guardarropa.cantidadDePrendas()  < capacidadMaxima;
	}

	public Estado siguienteEstado() {
		return new EstadoPremium();
	}
	
	public Set<Prenda> prendasAUsar(Guardarropa guardarropa){

		return guardarropa.getNPrendas(capacidadMaxima);
		
	}

}


