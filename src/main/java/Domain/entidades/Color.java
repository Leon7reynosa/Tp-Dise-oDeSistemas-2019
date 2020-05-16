
package Domain.entidades;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "color")
public class Color extends EntidadPersistente{

	@Column(name = "colorHexa")
	private String colorHexa;

	public Color(){
		super();
	}

	public void setColorHexa(String colorHexa) {
		this.colorHexa = colorHexa;
	}

	public String getColorHexa() {
		return colorHexa;
	}
}

