package Domain.Controladores;


import Domain.entidades.*;
import models.Model;
import org.uqbarproject.jpa.java8.extras.PerThreadEntityManagers;
import persistenciaDatos.Factories.*;
import persistenciaDatos.Repositiorios.Repositorio;
import persistenciaDatos.Repositiorios.RepositorioColor;
import persistenciaDatos.Repositiorios.RepositorioGeneral;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import javax.servlet.MultipartConfigElement;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class GuardarropaController {
    private RepositorioGeneral repo = FactoryRepositorioGuardarropa.get();

    public GuardarropaController() {
        this.repo = FactoryRepositorioGuardarropa.get();
    }


    private String getNombre(Request request){return request.queryParams("nombreGuardarropa");}

    private EstiloVestimenta getEstilo(Request request){

        String estilo = request.queryParams("estiloVestimenta");

        EstiloVestimenta[] estilos = EstiloVestimenta.values();

        if(estilo != null) {
            for (EstiloVestimenta unEstilo : estilos) {

                if (estilo.equals(unEstilo.toString())) {
                    return unEstilo;
                }

            }
        }
        return null; /*PELIGRO, no tanto, pero posible*/

    }

    private Categoria getCategoria(Request request){

        String categoria = request.queryParams("categoria_prenda");

        System.out.println("la categoria es:   " + categoria);

        Categoria[] categorias = Categoria.values();

        if(categoria != null){
            for (Categoria unaCategoria : categorias) {

                if (categoria.equals(unaCategoria.toString())) {
                    return unaCategoria;
                }
            }
        }
        return null;
    }

    private Tela getTela(Request request){

        String tela_id = request.queryParams("tela");

        Tela telaElegida = null;

        if(tela_id != null) {
            telaElegida = (Tela) FactoryRepositorioTela.get().buscar(new Integer(tela_id));
        }
        return telaElegida;

    }

    private String getNombreGuardarropa(Request request){
        return request.queryParams("nombreGuardarropa");
    }

    private String getDescripcionGuardarropa(Request request){
        return request.queryParams("descripcionGuardarropa");
    }

    private Material getMaterial(Request request){

        String material_id = request.queryParams("material");

        if(material_id == null){
            return null;
        }

        Material materialElegido = FactoryRepositorioMaterial.get().buscar(new  Integer(material_id));

        return materialElegido;
    }

    private List<Color> getColores(Request request){

        String hexacColorPrimario = request.queryParams("colorPrimario");

        String hexaColorSecundario = request.queryParams("colorSecundario");

        List<Color> colores = new ArrayList<>();

        RepositorioColor repoColor = FactoryRepositorioColor.get();

        Color colorPrimario = repoColor.buscar(hexacColorPrimario);

        if(colorPrimario == null){ //si no existe ese hexa en la BD, lo creo
            colorPrimario = new Color();
            colorPrimario.setColorHexa(hexacColorPrimario);
            repoColor.agregar(colorPrimario);
        }

        colores.add(colorPrimario);


        if(hexaColorSecundario != null){ //si pasaron un color secundario
            Color colorSecundario = repoColor.buscar(hexaColorSecundario);

            if(colorSecundario == null){ //si no existe ese hexa en la BD, lo creo
                colorSecundario = new Color();
                colorSecundario.setColorHexa(hexaColorSecundario);
                repoColor.agregar(colorSecundario);
            }

            colores.add(colorSecundario);

        }

        return colores;

    }

    private TipoDePrenda getTipoPrenda(Request request){

        String tipoPrenda_id = request.queryParams("tipoPrenda");
        TipoDePrenda tipo_de_prenda = null;

        if(tipoPrenda_id != null) {
            tipo_de_prenda = FactoryRepositorioTipoPrenda.get().buscar(new Integer(tipoPrenda_id));
        }
        return tipo_de_prenda;
    }

    public ModelAndView mostrarTodos(Request request, Response response) {

        LoginController.ensureUserIsLoggedIn(request, response);

        Usuario usuario = LoginController.getCurrentUser(request);

        Map<String, Object> parametros = new HashMap<>();
        List<Guardarropa> guardarropas = usuario.getGuardarropas();
        parametros.put("guardarropas", guardarropas);
        return new ModelAndView(parametros, "guardarropas_quemepongo.hbs");
    }

    public ModelAndView mostrarGuardarropasId(Request request , Response response){

        LoginController.ensureUserIsLoggedIn(request, response);

        Guardarropa guardarropa = this.getGuardarropaFromParams(request);

        Map<String, Object> parametros = new HashMap<>();

        if(guardarropa == null){
            return new ModelAndView(parametros,"error.hbs");
        }

        parametros.put("prendas",guardarropa.getPrendas());

        parametros.put("guardarropa_id",guardarropa.getId());

        return new ModelAndView(parametros,"prendas_quemepongo.hbs");

    }

    public ModelAndView ingresoGuardarropaPage(Request request, Response response){

        LoginController.ensureUserIsLoggedIn(request, response);

        Map<String, Object> parametros = new HashMap<>();

        List<EstiloVestimenta> estilos = new ArrayList<>();
        EstiloVestimenta[] estilosVestimenta = EstiloVestimenta.values();
        for(EstiloVestimenta unEstilo: estilosVestimenta){
            estilos.add(unEstilo);
        }

        parametros.put("estilos" , estilos);
        return new ModelAndView(parametros, "ingreso_guardarropa_quemepongo.hbs");

    }

    public ModelAndView ingresoPrendaGuardarropaPage(Request request , Response response) {

        LoginController.ensureUserIsLoggedIn(request, response);

        Guardarropa guardarropa = this.getGuardarropaFromParams(request);

        Map<String, Object> parametros = new HashMap<>();

        if(guardarropa == null){
            return new ModelAndView(parametros,"error.hbs");
        }

        List<Categoria> categorias = new ArrayList<>();
        Categoria[] categorias_prenda = Categoria.values();

        for (Categoria unaCategoria : categorias_prenda) {
            categorias.add(unaCategoria);
        }

        List<TipoDePrenda> tipos_de_prendas = FactoryRepositorioTipoPrenda.get().buscarTodos();

        List<Tela> telas = FactoryRepositorioTela.get().buscarTodos();

        List<Material> materiales = FactoryRepositorioMaterial.get().buscarTodos();

        parametros.put("materiales", materiales);

        parametros.put("telas", telas);

        parametros.put("tipos_de_prendas", tipos_de_prendas);

        parametros.put("categorias", categorias);

        parametros.put("guardarropa_id" , guardarropa.getId());

        return  new ModelAndView(parametros , "registro_prendas_quemepongo.hbs");
    }

    public Response crearPrenda(Request request , Response response){

        LoginController.ensureUserIsLoggedIn(request, response);

        request.attribute("org.eclipse.jetty.multipartConfig", new MultipartConfigElement("/temp"));

        Guardarropa guardarropa = GuardarropaController.getGuardarropaFromParams(request);

        Prenda nuevaPrenda = this.crearPrenda(request);

        guardarropa.guardarPrenda(nuevaPrenda);

        FactoryRepositorioPrenda.get().agregar(nuevaPrenda);

        this.repo.modificar(guardarropa);

        response.redirect("/guardarropas/" + request.params("guardarropa_id"));

        return response;

    }

    public Prenda crearPrenda(Request request){

        Prenda nuevaPrenda = new Prenda();

        Tela tela = this.getTela(request);

        Material material = this.getMaterial(request);

        Categoria categoria = getCategoria(request);

        TipoDePrenda tipoDePrenda = getTipoPrenda(request);

        List<Color> lista_colores = this.getColores(request);

        nuevaPrenda.setTela(tela);
        nuevaPrenda.setMaterial(material);
        nuevaPrenda.setTipo(tipoDePrenda);
        nuevaPrenda.setColores(lista_colores);

        Path tempfile = null;
        File uploadDir = new File("temporal_imagenes");
        uploadDir.mkdir();

        try {
            tempfile = Files.createTempFile(uploadDir.toPath(), "", "");
            System.out.println("Hago el inputStream");
            InputStream input = request.raw().getPart("imagen").getInputStream();
            System.out.println("renderizo la foto");
            if(input != null){
                Files.copy(input,tempfile, StandardCopyOption.REPLACE_EXISTING);

                nuevaPrenda.agregarFoto(
                        tempfile.toAbsolutePath().toString(),
                        LoginController.getCurrentUser(request).getNombre()
                );

            }



        }
        catch(Exception ex){

            System.out.println("EXCEPTION MESSAGE: "+ex.getMessage());
        }

        if(tempfile != null) {
            File temp = new File(tempfile.toString());
            temp.delete();
        }
        return nuevaPrenda;

    }

    public Response crearGuardarropa(Request request, Response response){

        LoginController.ensureUserIsLoggedIn(request, response);

        Usuario usuario = LoginController.getCurrentUser(request);

        Guardarropa nuevoGuardarropa = this.crearGuardarropa(request);

        usuario.agregarGuardarropa(nuevoGuardarropa);

        this.repo.agregar(nuevoGuardarropa);
        FactoryRepositorioUsuario.get().modificar(usuario);

        response.redirect("/guardarropas");

        return response;
    }

    public static Guardarropa getGuardarropaFromParams(Request request){

        Usuario usuario = LoginController.getCurrentUser(request);
        Integer guardarropaId;

        try {
            guardarropaId = new Integer(request.params("guardarropa_id"));
        }
        catch(NumberFormatException ex){
            return null;
        }

        Guardarropa guardarropa = FactoryRepositorioGuardarropa.get().buscar(guardarropaId);

        if(!usuario.tenesEsteGuardarropa(guardarropa)) {
            return null;
        }

        return guardarropa;
    }

    private Guardarropa crearGuardarropa(Request request){

        Guardarropa nuevoGuardarropa = new Guardarropa(this.getEstilo(request));

        nuevoGuardarropa.setDescripcion(this.getDescripcionGuardarropa(request));

        nuevoGuardarropa.setFecha_ingreso(LocalDate.now());

        nuevoGuardarropa.setNombre(this.getNombreGuardarropa(request));

        return nuevoGuardarropa;
    }

    public ModelAndView SeleccionPage(Request request, Response response){
        LoginController.ensureUserIsLoggedIn(request, response);

        Usuario usuario = LoginController.getCurrentUser(request);

        Map<String, Object> parametros = new HashMap<>();
        List<Guardarropa> guardarropas = usuario.getGuardarropas();
        parametros.put("guardarropas", guardarropas);
        return new ModelAndView(parametros, "seleccion_guardarropas.hbs");
    }

    public Response eliminarPrenda(Request request, Response response){

        RepositorioGeneral repoPrenda = FactoryRepositorioPrenda.get();

        Prenda prendaAEliminar = repoPrenda.buscar(new Integer(request.params("prenda_id")));

        this.eliminar_foto(prendaAEliminar);

        repoPrenda.eliminar(prendaAEliminar);

        return response;
    }



    private void eliminar_foto(Prenda unaPrenda){

        String path = unaPrenda.getImagen_url();

        File imagen = new File(path);

        imagen.delete();
    }
}


