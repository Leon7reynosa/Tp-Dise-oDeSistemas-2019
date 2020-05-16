package Domain.entidades;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = "comando")
@DiscriminatorColumn(name = "tipo")
public abstract class UndoableCommand extends EntidadPersistente {

	public abstract void execute();

	public abstract void undo();
}
