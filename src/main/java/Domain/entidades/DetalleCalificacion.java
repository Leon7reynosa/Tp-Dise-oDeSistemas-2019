package Domain.entidades;

import javax.persistence.*;

@Entity
@Table(name = "detalle_calificacion")
public class DetalleCalificacion extends EntidadPersistente{

    @Enumerated(EnumType.STRING)
    @Column(name = "parte_cuerpo")
    private Categoria parteDelCuerpo;

    @ManyToOne
    @JoinColumn(name = "opinion_id")
    private Opinion opinion;

    @Column(name = "descripcion")
    private String descripcion;

    public DetalleCalificacion(){
        super();
    }


    public void setParteDelCuerpo(Categoria parteDelCuerpo) {
        this.parteDelCuerpo = parteDelCuerpo;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setOpinion(Opinion opinion) {
        this.opinion = opinion;
    }

    public void afectaSensibilidad(Usuario usuario){

        usuario.ajustarSensisibilidad(this.parteDelCuerpo, this.opinion.getPuntosAAgregar());

    }

}
