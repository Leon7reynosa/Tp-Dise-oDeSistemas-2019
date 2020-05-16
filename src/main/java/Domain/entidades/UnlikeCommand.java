package Domain.entidades;

import Domain.entidades.AdministradorSugerencia;
import Domain.entidades.Sugerencia;

import javax.persistence.*;

public class UnlikeCommand extends UndoableCommand {

	@ManyToOne
	@JoinColumn(name = "sugerencia_id")
	private Sugerencia sugerencia_dislikeada;

	private AdministradorSugerencia receiver;

	public UnlikeCommand(Sugerencia sugerencia, AdministradorSugerencia _receiver) {
		this.sugerencia_dislikeada = sugerencia;
		this.receiver = _receiver;
	}

	public void execute() {

		if(!this.receiver.yaFueRechazada(this.sugerencia_dislikeada)){
			this.receiver.agregarAtuendoRechazado(this.sugerencia_dislikeada.atuendo());
		}
		if(this.sugerencia_dislikeada.tenesEvento()){
			if(this.sugerencia_dislikeada.getEvento().getSugerenciaActual().equals(this.sugerencia_dislikeada)){
				this.sugerencia_dislikeada.getEvento().rechazarSugerenciaActual();
			}
		}

		this.sugerencia_dislikeada.setRechazado(true);


	}

	public void undo() {

		this.receiver.eliminarAtuendoRechazado(this.sugerencia_dislikeada.atuendo());

		this.sugerencia_dislikeada.setRechazado(false);

	}

}
