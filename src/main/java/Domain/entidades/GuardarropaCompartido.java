package Domain.entidades;

import org.hibernate.annotations.AnyMetaDefs;

import javax.persistence.*;

@Entity
@Table(name = "comparticion")
public class GuardarropaCompartido extends EntidadPersistente {

	@ManyToOne
	@JoinColumn(name = "compartidor_id")
	private Usuario usuarioCompartidor;
	@ManyToOne
	@JoinColumn(name = "compartido_id")
	private Usuario usuarioCompartido;
	@ManyToOne
	@JoinColumn(name = "guardarropa_id")
	private Guardarropa guardarropaCompartido;

	public GuardarropaCompartido(){
		super();
	}

	public GuardarropaCompartido(Usuario usuario , Guardarropa guardarropa) {

		this.guardarropaCompartido = guardarropa;
		this.usuarioCompartido = usuario;

	}

	public void setGuardarropaCompartido(Guardarropa guardarropaCompartido) {
		this.guardarropaCompartido = guardarropaCompartido;
	}

	public void setUsuarioCompartido(Usuario usuarioCompartido) {
		this.usuarioCompartido = usuarioCompartido;
	}

	public void setUsuarioCompartidor(Usuario usuarioCompartidor) {
		this.usuarioCompartidor = usuarioCompartidor;
	}

	public Usuario getUsuarioCompartido() {
		return usuarioCompartido;
	}

	public Usuario getUsuarioCompartidor() {
		return usuarioCompartidor;
	}
	
	public Guardarropa getGuardarropaCompartido() {
		return this.guardarropaCompartido;
		
	}
	
	public Usuario usuarioCompartido() {
		
		return this.usuarioCompartido;
		
	}
	
	
}
