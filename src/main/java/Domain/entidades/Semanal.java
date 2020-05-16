package Domain.entidades;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.time.LocalDate;

@Entity
@DiscriminatorValue("semanal")
public class Semanal extends TipoDeFrecuencia {

	public Semanal(){
		super();
	}

	public Semanal(LocalDate _fecha){

		super(_fecha);

	}

	public void finalizate(Usuario usuario, Evento evento) {

		fechaDelEvento.plusWeeks(this.getIntervalo());

	}

	public int getIntervalo() {
		return 1;
	}

}
