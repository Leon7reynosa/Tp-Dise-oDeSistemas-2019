package Domain.entidades;

// Opinion seria "FRIO", "CALOR", Por ejemplo!

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "opinion")
public class Opinion extends EntidadPersistente{

    @Column(name = "nombre")
    private String nombre;
    @Column(name = "puntos_modificadores")
    private int puntosAAgregar;

    public Opinion(){
        super();
    }

    public String getNombre() {
        return nombre;
    }

    public int getPuntosAAgregar(){
        return this.puntosAAgregar;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setPuntosAAgregar(int puntosAAgregar) {
        this.puntosAAgregar = puntosAAgregar;
    }

}
