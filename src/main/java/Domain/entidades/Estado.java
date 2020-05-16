package Domain.entidades;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "estado")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo")
public abstract class Estado extends EntidadPersistente{
	// patron state
	public abstract boolean podesIngresarPrenda(Guardarropa guardarropa);

	public abstract Estado siguienteEstado();

	public abstract Set<Prenda> prendasAUsar(Guardarropa guardarropa);
	
}
