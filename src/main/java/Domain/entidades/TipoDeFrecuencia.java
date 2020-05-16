package Domain.entidades;

import java.io.IOException;
import java.time.LocalDate;

import Domain.entidades.Evento;
import Domain.entidades.Usuario;
import net.bytebuddy.asm.Advice;

import javax.persistence.*;

@Entity
@Table(name = "frecuencia")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo")
public abstract class TipoDeFrecuencia extends EntidadPersistente{

	@Column(name = "fecha")
	protected LocalDate fechaDelEvento ;

	@OneToOne
	@JoinColumn(name="evento_id")
	protected Evento evento;

	public TipoDeFrecuencia(){
		super();
	}

	public TipoDeFrecuencia(LocalDate _fecha ){
		this.fechaDelEvento = _fecha;

	}

	public abstract void finalizate(Usuario usuario, Evento evento);

	public LocalDate getFechaDelEvento() throws IOException {
		return fechaDelEvento;

	}

	public Evento getEvento(){
		return this.evento;
	}

	public void setEvento(Evento evento_guardar){
		this.evento = evento_guardar;
	}

}
