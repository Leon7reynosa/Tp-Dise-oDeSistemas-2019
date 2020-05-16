package Domain.entidades;

import persistenciaDatos.Factories.FactoryRepositorioMoldeAtuendo;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "tipos_de_prenda")
public class TipoDePrenda extends EntidadPersistente{
	@Column(name = "nombre")
	private String nombrePrenda;
	@Enumerated(EnumType.STRING)
	@Column(name = "categoria")
	private Categoria categoria;
	@ManyToMany(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
	@JoinTable(name = "Tela_Permitida",
			   joinColumns = @JoinColumn(name = "tipo_prenda_id"),
			   inverseJoinColumns = @JoinColumn(name = "tela_id")
	)
	private List<Tela> telasPermitidas;
	@ManyToMany(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
	@JoinTable(name = "Material_Permitido",
			   joinColumns = @JoinColumn(name = "tipo_prenda_id"),
			   inverseJoinColumns = @JoinColumn(name = "material_id")
	)
	private List<Material> materialesPermitidos;
	@ManyToMany(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
	@JoinTable(name = "tipos_Superponibles",
			joinColumns = @JoinColumn(name = "prendaBase_id"),
			inverseJoinColumns = @JoinColumn(name = "prendaSuperponible_id")
	)
	private List<TipoDePrenda> prendasSuperponibles;
	@ManyToMany(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
	@JoinTable(name = "tipos_Combinables",
			joinColumns = @JoinColumn(name = "prendaBase_id"),
			inverseJoinColumns = @JoinColumn(name = "prendaCombinable_id")
	)
	private Set<TipoDePrenda> prendasCombinables;
	@Column(name = "abrigo")
	private int puntosDeAbrigo;
	@Column(name = "imagen")
	private String imagen;

	public TipoDePrenda(){
		this.telasPermitidas = new ArrayList<>();
		this.materialesPermitidos = new ArrayList<>();
		this.prendasSuperponibles = new ArrayList<>();
		this.prendasCombinables = new HashSet<>();
	}

	// constructor para Accesorio
	public TipoDePrenda(String nombrePrenda, Categoria categoria, List<Tela> telasPermitidas,
						List<Material> materialesPermitidos, List<TipoDePrenda> prendasSuperponibles,
						Set<TipoDePrenda> prendasCombinadas, int abrigacion) {
		this.nombrePrenda = nombrePrenda;
		this.categoria = categoria;
		this.telasPermitidas = new ArrayList<Tela>();
		this.materialesPermitidos = new ArrayList<Material>();
		this.prendasSuperponibles = new ArrayList<TipoDePrenda>();
		this.prendasCombinables = new HashSet<TipoDePrenda>();
		this.telasPermitidas.addAll(telasPermitidas);
		this.materialesPermitidos.addAll(materialesPermitidos);
		this.prendasSuperponibles.addAll(prendasSuperponibles);
		this.agregarCombinaciones(prendasCombinadas);
		this.puntosDeAbrigo = abrigacion;

	}

	public String getImagen() {
		return imagen;
	}

	public void setImagen(String imagen) {
		this.imagen = imagen;
	}

	public String getNombrePrenda() {
		return nombrePrenda;
	}

	public void setNombrePrenda(String nombrePrenda) {
		this.nombrePrenda = nombrePrenda;
	}

	//=====================================================CATEGORIA=================================================
	
	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}
	
	public boolean sosDeCategoria(Categoria categoria) {

		return this.getCategoria() == categoria;

	}
	
	public boolean sosMismaCategoria(TipoDePrenda tipoPrenda) {
		
		return !this.equals(tipoPrenda) && this.sosDeCategoria(tipoPrenda.getCategoria());
		
	}
	
	public boolean tenesUnoDeCadaCategoria() {
		
		return this.tenesAlgunoDeCategoria(Categoria.CALZADO)
				&& this.tenesAlgunoDeCategoria(Categoria.INFERIOR)
				&& this.tenesAlgunoDeCategoria(Categoria.SUPERIOR);
		
	}
	
	public boolean tenesAlgunoDeCategoria(Categoria categoria) {
		
		return this.prendasCombinables.stream().anyMatch(tipoPrenda -> tipoPrenda.sosDeCategoria(categoria));
		
	}
	
	//=====================================================MATERIALES=================================================

	public List<Tela> getTelasPermitidas() {
		return telasPermitidas;
	}

	public void setTelasPermitidas(List<Tela> telasPermitidas) {
		this.telasPermitidas = telasPermitidas;
	}

	public List<Material> getMaterialesPermitidos() {
		return materialesPermitidos;
	}

	public void setMaterialesPermitidos(List<Material> materialesPermitidos) {
		this.materialesPermitidos = materialesPermitidos;
	}

	public boolean permitisTela(Tela tela) {
		return this.telasPermitidas.contains(tela);
	}

	public boolean permitisMaterial(Material material) {
		return this.materialesPermitidos.contains(material);
	}

	public void agregarTela(Tela unaTela){
		this.telasPermitidas.add(unaTela);
	}

	public void agregarMaterial(Material unMaterial){
		this.materialesPermitidos.add(unMaterial);
	}

	//==========================================================ABRIGACION==================================================

	public int getPuntosDeAbrigo() {
		return this.puntosDeAbrigo;
	}

	public void setPuntosDeAbrigo(int unosPuntos) {
		this.puntosDeAbrigo = unosPuntos;
	}
	
	//===================================================PRENDAS_COMBINABLES=================================================

	public boolean sosCombinableCon(TipoDePrenda tipo){
		return this.prendasCombinables.contains(tipo);
	}

	public void agregarPrendaCombinable(TipoDePrenda prendaCombinable) {
		
		
		if(this.esElPrimeroDeEsaCategoria(prendaCombinable.getCategoria())) {
			
			this.prendasCombinables.add(prendaCombinable);
			
			if(this.tenesUnoDeCadaCategoria()) {

				this.generarMoldesDesdeCero();

			}
			
			
			
		}else {
			
			this.prendasCombinables.add(prendaCombinable);

			this.generarMoldesNuevos(prendaCombinable);
			
		}
		


	}
	

	
	
	
	public void generarMoldesDesdeCero() {
		
		List<TipoDePrenda> prendasSuperiores = this.obtenerPrendasSegun(Categoria.SUPERIOR);
		List<TipoDePrenda> prendasInferiores = this.obtenerPrendasSegun(Categoria.INFERIOR);
		List<TipoDePrenda> prendasCalzado = this.obtenerPrendasSegun(Categoria.CALZADO);
		
		
		
		for(int i = 0 ; i < prendasSuperiores.size() ; i++) {
			
			TipoDePrenda prendaSuperrior = prendasSuperiores.get(i);
			
			for(int j = 0 ; j < prendasInferiores.size() ; j++) {
				
				TipoDePrenda prendaInferior = prendasInferiores.get(j);
				
				for(int k = 0 ; k < prendasCalzado.size() ; k ++) {
					
					TipoDePrenda prendaCalzado = prendasCalzado.get(k);
						
					List<TipoDePrenda> prendasMolde = new ArrayList<TipoDePrenda>();
					
					prendasMolde.add(prendaCalzado);
					prendasMolde.add(prendaInferior);
					prendasMolde.add(prendaSuperrior);
					
					MoldeAtuendo nuevoMolde = new MoldeAtuendo(prendasMolde);
					
				}
				
			}
			
			
		}
		
	}
	
	public void agregarALaLista(List<TipoDePrenda> prendasMolde , TipoDePrenda tipodePrenda) {
		
		if( !prendasMolde.stream().anyMatch(tipoPrenda -> tipoPrenda.sosDeCategoria(tipodePrenda.getCategoria())) ) {
			
			prendasMolde.add(tipodePrenda);
			
		}
		
	}
	
	public List<TipoDePrenda> obtenerPrendasSegun(Categoria categoria){
		
		return this.prendasSuperponibles.stream().filter(tipoPrenda -> tipoPrenda.sosDeCategoria(categoria)).collect(Collectors.toList());
		
	}
	
	
	
	public boolean esElPrimeroDeEsaCategoria(Categoria categoria) {
		
		return !this.prendasCombinables.stream().anyMatch(prenda -> prenda.sosDeCategoria(categoria));
		
	}


	private void generarMoldesNuevos(TipoDePrenda prendaNueva) {

		MoldeAtuendoClonador clonador = new MoldeAtuendoClonador();

		List<MoldeAtuendo> moldesClonados = clonador.generarNuevosMoldesSegun(this);

		for (int i = 0; i < moldesClonados.size(); i++) {

			MoldeAtuendo moldeClonado = moldesClonados.get(i);

			moldeClonado.agregarNuevaPrenda(prendaNueva);

			FactoryRepositorioMoldeAtuendo.get().agregar(moldeClonado);

		}

	}
	
	public void agregarCombinaciones(Set<TipoDePrenda> prendasCombinadas) {
		
		for(int i = 0;  i < prendasCombinadas.size() ; i ++) {
			
			TipoDePrenda prendaCombinable = prendasCombinadas.stream().collect(Collectors.toList()).get(i);
			
			this.agregarPrendaCombinable(prendaCombinable);
			
		}
		
	}



	public boolean esSuperponible(TipoDePrenda unTipo){

		return this.prendasSuperponibles.contains(unTipo);
	}

	public boolean algunoEsSuperponible(Collection<TipoDePrenda> unosTipos){

		return unosTipos.stream().anyMatch(tipoDePrenda -> this.esSuperponible(tipoDePrenda));
	}

	// ================================================================================================================

	public boolean sosCombinable(Collection<TipoDePrenda> unosTipos) {

		return unosTipos.stream().anyMatch(tipoPrenda -> tipoPrenda.sosCombinableCon(this));

	}

	public boolean noSosCombinable(Collection<TipoDePrenda> unosTipos) {

		return !this.sosCombinable(unosTipos);

	}

}
