package Domain.entidades;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.time.LocalDate;

@Entity
@DiscriminatorValue("Unico")
public class UnicaVez extends TipoDeFrecuencia {

	public UnicaVez(){
		super();
	}

	public UnicaVez(LocalDate _fecha){

		super(_fecha);

	}

	@Override
	public void finalizate(Usuario usuario, Evento evento) {
		//
	}

}
