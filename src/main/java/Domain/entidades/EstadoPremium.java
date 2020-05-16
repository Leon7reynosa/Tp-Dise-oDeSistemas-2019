package Domain.entidades;

import java.util.Set;

import Domain.entidades.Guardarropa;
import Domain.entidades.Prenda;
import Domain.entidades.Usuario;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("premium")
public class EstadoPremium extends Estado {

	public boolean podesIngresarPrenda(Usuario usuario, Guardarropa guardarropa) {
		return true;
	}

	public Estado siguienteEstado() {
		return new EstadoGratuito();
	}
	
	public boolean podesIngresarPrenda(Guardarropa guardarropa) {
		return true;
	}
	
	public Set<Prenda> prendasAUsar(Guardarropa guardarropa){
		
		return guardarropa.getPrendas();
		
	}

}
