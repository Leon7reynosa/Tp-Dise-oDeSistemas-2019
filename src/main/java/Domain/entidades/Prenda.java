package Domain.entidades;

import java.io.IOException;
import java.util.List;

import RenderizacionImagen.RenderizadorImagen;

import javax.persistence.*;

@Entity
@Table(name = "prenda")
public class Prenda extends EntidadPersistente {
	@ManyToOne
	@JoinColumn(name = "tipoPrenda_id")
	private TipoDePrenda tipo;
	@ManyToMany(cascade =
			{
					CascadeType.DETACH,
					CascadeType.MERGE,
					CascadeType.REFRESH,
					CascadeType.PERSIST
			}, fetch = FetchType.LAZY)
	@JoinTable(name = "prenda_color",
			joinColumns = @JoinColumn(name = "prenda_id"),
			inverseJoinColumns = @JoinColumn(name = "color_id")
	)
	private List<Color> color;
	@ManyToOne
	@JoinColumn(name = "tela_id")
	private Tela tela = null;
	@ManyToOne
	@JoinColumn(name = "material_id")
	private Material material = null;
	@Transient
	private RenderizadorImagen imagen;
	@Column(name = "en_uso")
	private boolean usado;
	@Column(name = "imagen")
	private String imagen_url;

	// No estoy seguro si esto se puede hacer, por que no deberia haber una prenda
	// que no tenga to-do
	public Prenda() {
		super();
	}

	public void setearPrendaUsada(boolean estasEnUso) {

		this.usado = estasEnUso;

	}

	public boolean noEstasUsada() {

		return !this.usado;

	}

	public Prenda(TipoDePrenda tipoDePrenda, List<Color> colores, Tela tela, Material material,
				  String pathImagenOriginal) throws IOException {
		super();
		this.tipo = tipoDePrenda;
		this.color = colores;
		this.tela = tela;
		this.material = material;
		this.usado = false;

/*		if (pathImagenOriginal != null) {
			this.agregarFoto(pathImagenOriginal);
		}
*/
	}


	public boolean isUsado() {
		return usado;
	}

	public void agregarFoto(String pathImagenOriginal, String nombre) throws IOException {

		imagen = new RenderizadorImagen();
		String pathImagen = imagen.renderizarImagen(pathImagenOriginal, nombre);

		this.setImagen_url(pathImagen);
	}

	public Categoria getCategoria() {
		return this.tipo.getCategoria();
	}

	public TipoDePrenda getTipo() {

		return this.tipo;

	}

	public void setUsado(boolean usado) {
		this.usado = usado;
	}

	public String getImagen_url() {
		return imagen_url;
	}

	public void setImagen_url(String imagen_url) {
		this.imagen_url = imagen_url;
	}

	public Tela getTela() {
		return this.tela;
	}

	public Color getColorPrimario() {
		return this.color.get(0);
	}

	public Color getColorSecundario() {
		return this.color.get(1);
	}

	public Material getMaterial() {
		return this.material;
	}

	public Boolean sosDeCategoria(Categoria categoria) {

		return this.getCategoria().equals(categoria);
	}

	public String getNombrePrenda() {
		return this.tipo.getNombrePrenda();
	}

	public void setTipo(TipoDePrenda tipo) {
		this.tipo = tipo;
	}

	public void setColores(List<Color> colores) {
		this.color = colores;
	}

	public void setTela(Tela tela) {
		this.tela = tela;
	}

	public void setMaterial(Material material) {
		this.material = material;
	}

}
