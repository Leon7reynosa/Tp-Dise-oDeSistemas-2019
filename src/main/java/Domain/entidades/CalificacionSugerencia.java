package Domain.entidades;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "calificacion")
public class CalificacionSugerencia extends EntidadPersistente{

	public int getPuntaje() {
		return puntaje;
	}

	//esto me lo imagino como que salte con estrellas para calificar (del 0-5 en js)
	//esta meramente para tener registro, no afecta ningun calculo (capaz para saber si no tiene que volver a elejirla)
	@Column(name = "puntaje")
	private int puntaje;

	//esto seria los detalles por partes, para poder modificar los valores de la sensibilidad del usuario
	//capaz, tambien con js, se puede limitar cantidad de detalles a 2 (por que solo tenemos superior e inferior)
	@OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
	@JoinColumn(name = "calificacion_id")
	private List<DetalleCalificacion> detallesCalificacion;

	//sugerencia calificada
	@OneToOne(cascade = {CascadeType.ALL})
	@JoinColumn(name = "sugerencia_id")
	private Sugerencia sugerencia;

	public CalificacionSugerencia() {
		super();
		detallesCalificacion = new ArrayList<>();
	}

	public void setPuntaje(int puntaje) {
		this.puntaje = puntaje;
	}

	public void setSugerencia(Sugerencia sugerencia) {
		this.sugerencia = sugerencia;
	}

	public void setDetalle(DetalleCalificacion unDetalle){
		this.detallesCalificacion.add(unDetalle);
	}

	public void modificarSensibilidad(Usuario usuario){
		this.detallesCalificacion.forEach(detalle -> detalle.afectaSensibilidad(usuario));
	}
}
