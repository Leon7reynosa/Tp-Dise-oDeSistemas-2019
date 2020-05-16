package Domain.entidades;

import javax.persistence.*;

public class Like_Undoable_Command extends UndoableCommand {

	@OneToOne(cascade = {CascadeType.ALL})
	@JoinColumn(name = "sugerencia_id")
	private Sugerencia sugerencia_likeada;

	private AdministradorSugerencia receiver;

	public Like_Undoable_Command(Sugerencia sugerencia, AdministradorSugerencia _receiver) {
		this.sugerencia_likeada = sugerencia;
		this.receiver = _receiver;
	}

	public void execute() {

		if(!receiver.yaFueAceptada(this.sugerencia_likeada)){
			this.receiver.agregarSugerenciaAceptada(this.sugerencia_likeada);
		}

		
	}

	public void undo() {

		this.receiver.eliminarSugerenciaAceptada(this.sugerencia_likeada);

	}

}
