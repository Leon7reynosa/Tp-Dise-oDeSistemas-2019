package Domain.entidades;

import Domain.Clima;
import Domain.entidades.UserLogin.UserLogin;
import RenderizacionImagen.RenderizadorImagen;

import javax.persistence.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "usuario")
public class Usuario extends EntidadPersistente{
	@OneToOne(cascade = {CascadeType.ALL})
	@JoinColumn(name = "user_id")
	private UserLogin user;
	@Column(name = "nombre")
	private String nombre;
	@Column(name = "email")
	private String email;
	@Column(name = "numero")
	private String numero;

	@OneToOne(cascade = {CascadeType.ALL})
	@JoinColumn(name = "estado_id")
	private Estado estado;

	@Embedded
	private AdministradorSugerencia administradorSugerencia;

	@OneToOne(cascade = {CascadeType.ALL})
	@JoinColumn(name = "sensibilidad_id")
	private SensibilidadUsuario sensibilidad;

	@OneToMany(mappedBy = "usuario",cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
	private List<Evento> eventos;

	@ManyToMany(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
	@JoinTable(name = "usuario_guardarropa",
			   joinColumns = @JoinColumn(name = "usuario_id"),
			   inverseJoinColumns = @JoinColumn(name = "guardarropa_id")
	)
	private List<Guardarropa> guardarropas;

	@OneToMany(mappedBy = "usuarioCompartidor",cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
	private List<GuardarropaCompartido> guardarropasCompartidos;

/////////////////////////////////////////////////////////CONSTRUCTOR////////////////////////////////////////////////////

	// HICIMOS QUE POR DEFECTO INICIE EN GRATUITO
	public Usuario(){

		this.eventos = new ArrayList<>();
		this.guardarropas = new ArrayList<>();
		this.guardarropasCompartidos = new ArrayList<>();

	}

	public Usuario(String _nombre, String _email , String _numero) {
		this.nombre = _nombre;
		this.email = _email;
		this.numero = _numero;
		this.estado = new EstadoGratuito();
		this.administradorSugerencia = new AdministradorSugerencia();
		this.sensibilidad = new SensibilidadUsuario();
		this.guardarropas = new ArrayList<Guardarropa>();
		this.eventos = new ArrayList<Evento>();
		this.guardarropasCompartidos = new ArrayList<GuardarropaCompartido>();

	}

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

//////////////////////////////////////////////////////GETTER AND SETTER/////////////////////////////////////////////////

	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getEmail() {
		return this.email;
	}
	public String getNumero() {
		return this.numero;
	}
	public UserLogin getUser(){
		return this.user;
	}
	public void setUser(UserLogin unUser){
		this.user = unUser;
	}

	public Estado getEstado() {
		return this.estado;

	}

	public List<Evento> getEventos() {

		return this.eventos;
	}



	public void setEmail(String email) {
		this.email = email;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	public AdministradorSugerencia getAdministradorSugerencia() {
		return administradorSugerencia;
	}

	public void setAdministradorSugerencia(AdministradorSugerencia administradorSugerencia) {
		this.administradorSugerencia = administradorSugerencia;
	}

	public void setUltimaEleccion(UndoableCommand _command) {
		this.administradorSugerencia.setUltimaEleccion(_command);
	}

	public void setSensibilidad(SensibilidadUsuario sensibilidad) {
		this.sensibilidad = sensibilidad;
	}

	public SensibilidadUsuario getSensibilidad() {
		return this.sensibilidad;
	}

	public List<Guardarropa> getGuardarropas() {
		return guardarropas;
	}

	public int getIndexGuardarropa(Guardarropa guardarropa) {
		return this.guardarropas.indexOf(guardarropa);
	}


////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	// =============================================GUARDARROPA================================================

	public boolean tenesEsteGuardarropa(Guardarropa unGuardarropa){
		return this.guardarropas.contains(unGuardarropa);
	}

	public boolean yaTenesUnGuardarropaDeEsteEstilo(EstiloVestimenta estiloGuardarropa) {

		return this.getGuardarropas().stream()
				.anyMatch(guardarropa -> guardarropa.sosEstiloVestimenta(estiloGuardarropa));

	}

	public void agregarGuardarropa(Guardarropa guardarropa) {

		this.guardarropas.add(guardarropa);

	}

	public void guardarPrenda(Prenda prenda, Guardarropa guardarropa) {
		guardarropa.guardarPrenda(prenda);
	}

	public int cantidadDePrendasDe(Guardarropa guardarropa) {
		return this.guardarropas.get(this.getIndexGuardarropa(guardarropa)).cantidadDePrendas();
	}

	public int cantidadTotalDePrendas() {
		return this.guardarropas.stream().mapToInt(Guardarropa::cantidadDePrendas).sum();
	}

	public Guardarropa obtenerGuardarropaSegunIndex(int index) {
		return this.getGuardarropas().get(index);
	}

	public Set<Prenda> prendasAUsar(Guardarropa guardarropa) {

		return this.getEstado().prendasAUsar(guardarropa);

	}

	//====================================================GUARDARROPA_COMPARTIDO===============================================

	public void compartirGuardarropa(Usuario usuarioACompartir, Guardarropa guardarropaACompartir) {
		
		GuardarropaCompartido guardarropaCompartidoNuevo = new GuardarropaCompartido(usuarioACompartir, guardarropaACompartir);

		guardarropaCompartidoNuevo.setUsuarioCompartidor(this);

		this.agregarGuardarropaCompartido(guardarropaCompartidoNuevo);
		
		usuarioACompartir.agregarGuardarropa(guardarropaACompartir);
	}
	
	private void agregarGuardarropaCompartido(GuardarropaCompartido guardarropaCompartido) {
		
		this.guardarropasCompartidos.add(guardarropaCompartido);
		
	}

	// =================================================SENSIBILIDAD_USUARIO====================================================

	public double cuantoAbrigoNecesitas(Clima clima) {

		return this.getSensibilidad().puntosNecesariosParaClima(clima);

	}

	public boolean satisfaceSensibilidad(MoldeAtuendo moldeAtuendo, Clima clima){

		return this.getSensibilidad().sosCubiertaPor(moldeAtuendo, clima);

	}

	public void ajustarSensisibilidad(Categoria unaCategoria, int unPuntaje){

		this.sensibilidad.acomodarSensiblidad(unaCategoria, unPuntaje);

	}

	// ===================================================ESTADO================================================================

	// ===================================================EVENTO================================================================

	public void aniadirEvento(Evento evento) {

		this.eventos.add(evento);

	}

	public int cantidadDeEventos() {

		return this.eventos.size();

	}

	public void eliminaEvento(Evento evento) {
		this.eventos.remove(evento);
	}

	public void agregarEvento(Evento unEvento){
		this.eventos.add(unEvento);
	}


	// ===================================================COMMANDOS===============================================================

	public void teGusta(Sugerencia sugerencia) {
		UndoableCommand ultimoComando = new Like_Undoable_Command(sugerencia, this.administradorSugerencia);
		ultimoComando.execute();
		this.setUltimaEleccion(ultimoComando);

	}

	public void noTeGusta(Sugerencia sugerencia) {
		UndoableCommand ultimoComando = new Like_Undoable_Command(sugerencia, this.administradorSugerencia);
		ultimoComando.execute();
		this.setUltimaEleccion(ultimoComando);
	}

	public void deshacerUltimaEleccion() {
		this.administradorSugerencia.ultimaEleccion().undo();
	}

	// =================================================SUGERENCIA================================================================

	public Sugerencia solicitarSugerencia(Guardarropa guardarropa) {

		Sugerencia sugerenciaPedida = this.administradorSugerencia.generarSugerencia(this,guardarropa);

		return sugerenciaPedida;

	}

	public void aceptarSugerencia(Sugerencia sugerenciaAceptada){

		Like_Undoable_Command like = new Like_Undoable_Command(sugerenciaAceptada, this.getAdministradorSugerencia());

		like.execute();
	}

	public void rechazarSugerencia(Sugerencia sugerenciaRechazada){

		UnlikeCommand dislike = new UnlikeCommand(sugerenciaRechazada, this.getAdministradorSugerencia());

		dislike.execute();
	}

	public boolean rechazasteAtuendo(Sugerencia unaSugerencia){

		return this.getAdministradorSugerencia().yaFueRechazada(unaSugerencia);
	}

	public boolean aceptasteSugerencia(Sugerencia unaSugerencia) {

		return this.getAdministradorSugerencia().yaFueAceptada(unaSugerencia);
	}
	// =================================================================FIN============================================================

	public void cargaImagen(String pathABuscar) throws IOException {
		try{
			System.out.println("EMPEZAMOS");
			RenderizadorImagen renderizador = new RenderizadorImagen();
			System.out.println("Vamo a renderizarno");
			renderizador.renderizarImagen(pathABuscar, this.nombre);
		}
		catch(IOException e){
			e.printStackTrace();
		}


	}
}
