package Domain.entidades;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "tela")
public class Tela extends EntidadPersistente{

    @Column(name = "nombre")
    public String nombre;

    public Tela(){
        super();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}

