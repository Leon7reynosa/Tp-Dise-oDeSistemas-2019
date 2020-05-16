package Domain.entidades;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.time.LocalDate;

@Entity
@DiscriminatorValue("anual")
public class Anual extends TipoDeFrecuencia {

	public Anual(LocalDate _fecha){

		super(_fecha);

	}

	public void finalizate(Usuario usuario, Evento evento) {

		fechaDelEvento.plusYears(this.getIntervalo());

	}

	public int getIntervalo() {
		return 1;
	}

}
