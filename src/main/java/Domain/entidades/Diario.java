package Domain.entidades;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.time.LocalDate;

@Entity
@DiscriminatorValue("diario")
public class Diario extends TipoDeFrecuencia {

	public Diario(LocalDate _fecha){

		super(_fecha);

	}

	public void finalizate(Usuario usuario, Evento evento) {

		fechaDelEvento.plusDays(this.getIntervalo());

	}

	public int getIntervalo() {
		return 1;
	}

}
