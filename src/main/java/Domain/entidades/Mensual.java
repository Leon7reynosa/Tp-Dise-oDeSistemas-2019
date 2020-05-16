package Domain.entidades;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.time.LocalDate;

@Entity
@DiscriminatorValue("mensual")
public class Mensual extends TipoDeFrecuencia {

	public Mensual(){
		super();
	}

	public Mensual(LocalDate _fecha){

		super(_fecha);


	}

	public void finalizate(Usuario usuario, Evento evento) {

		fechaDelEvento.plusMonths(this.getIntervalo());

	}

	public int getIntervalo() {
		return 1;
	}

}
