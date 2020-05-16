package Domain.Controladores;

import Domain.entidades.*;
import Domain.entidades.Helpers.SugerenciaHelper;
import models.Model;
import persistenciaDatos.Factories.*;
import persistenciaDatos.Repositiorios.Repositorio;
import persistenciaDatos.Repositiorios.RepositorioGeneral;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SugerenciaController {

    private RepositorioGeneral repo = FactoryRepositorioGuardarropa.get();
    private RepositorioGeneral repoSugerencia = FactoryRepositorioSugerencia.get();

    public SugerenciaController() {
        this.repo = FactoryRepositorioGuardarropa.get();
    }

    public ModelAndView SeleccionPageSugerencia(Request request, Response response){
        LoginController.ensureUserIsLoggedIn(request, response);

        Usuario usuario = LoginController.getCurrentUser(request);

        Map<String, Object> parametros = new HashMap<>();
        List<Guardarropa> guardarropas = usuario.getGuardarropas();
        parametros.put("guardarropas", guardarropas);
        return new ModelAndView(parametros, "seleccion_guardarropas_sugerencia.hbs");
    }

    public ModelAndView sugerenciaPage(Request request, Response response){

        LoginController.ensureUserIsLoggedIn(request, response);

        Map<String, Object> parametros = new HashMap<>();
        Usuario usuario = LoginController.getCurrentUser(request);
        Guardarropa guardarropa = GuardarropaController.getGuardarropaFromParams(request);

        if(guardarropa == null){
            return new ModelAndView(parametros, "error.hbs");
        }

        Sugerencia sugerenciaGenerada = usuario.solicitarSugerencia(guardarropa);

        /*Parametros rancios*/
        List<Prenda> listaSuperior      = new ArrayList<>();
        List<Prenda> listaInfrerior     = new ArrayList<>();
        Prenda calzado                  = new Prenda();
        Prenda prendaActiveSuperior     = new Prenda();
        Prenda prendaActiveInferior     = new Prenda();
        List<String> coloresSuperior    = new ArrayList<>();
        List<String> coloresInferior    = new ArrayList<>();
        String colorActiveSuperior      = null;
        String colorActiveInferior      = null;
        String colorCalzado             = null;

        /*if rancio para parametros rancios*/
        if(sugerenciaGenerada != null){

            FactoryRepositorioSugerencia.get().agregar(sugerenciaGenerada);

            listaSuperior =
                    sugerenciaGenerada.getAtuendo().getConjuntoPrendas().stream()
                            .filter(prenda -> prenda.getCategoria().equals(Categoria.SUPERIOR)).collect(Collectors.toList());

            prendaActiveSuperior = listaSuperior.get(0);

            listaSuperior.remove(0);

            listaInfrerior =
                    sugerenciaGenerada.getAtuendo().getConjuntoPrendas().stream()
                            .filter(prenda -> prenda.getCategoria().equals(Categoria.INFERIOR)).collect(Collectors.toList());

            prendaActiveInferior = listaInfrerior.get(0);

            calzado =
                    sugerenciaGenerada.getAtuendo().getConjuntoPrendas().stream()
                            .filter(prenda -> prenda.getCategoria().equals(Categoria.CALZADO)).collect(Collectors.toList()).get(0);

            colorActiveSuperior = prendaActiveSuperior.getColorPrimario().getColorHexa();
            colorActiveInferior = prendaActiveInferior.getColorPrimario().getColorHexa();
            colorCalzado = calzado.getColorPrimario().getColorHexa();

            listaSuperior.forEach(prenda -> coloresSuperior.add(prenda.getColorPrimario().getColorHexa()));

            listaInfrerior.forEach(prenda -> coloresInferior.add(prenda.getColorPrimario().getColorHexa()));

        }

        parametros.put("sugerencia" , sugerenciaGenerada);
        parametros.put("superior", listaSuperior);
        parametros.put("inferior", listaInfrerior);
        parametros.put("calzado" , calzado);
        parametros.put("activeSuperior" , prendaActiveSuperior);
        parametros.put("activeInferior" , prendaActiveInferior);

        parametros.put("colorActiveSuperior" , colorActiveSuperior);
        parametros.put("colorActiveInferior" , colorActiveInferior);
        parametros.put("coloresSuperior" , coloresSuperior);
        parametros.put("coloresInferior" , coloresInferior);
        parametros.put("colorCalzado" , colorCalzado);

        return new ModelAndView(parametros, "sugerencia_prueba_quemepongo.hbs");

    }

    public ModelAndView mostrarAceptadas(Request request, Response response){

        LoginController.ensureUserIsLoggedIn(request,response);

        Map<String, Object> parametros = new HashMap<>();

        Usuario usuario = LoginController.getCurrentUser(request);


        List<SugerenciaHelper> sugerenciasAceptadas = new ArrayList<>();

        usuario.getAdministradorSugerencia().getSugerenciasAceptadas().
                forEach(sugerencia -> {
                    sugerenciasAceptadas.add(new SugerenciaHelper(sugerencia));
                    sugerencia.getCalificacion();
                    sugerencia.getEvento();
                });

        sugerenciasAceptadas.forEach(sugerencia -> System.out.println(sugerencia.getId()));

        sugerenciasAceptadas.forEach(sugerencia -> sugerencia.getCalificacion());

        if(sugerenciasAceptadas.isEmpty()){
            parametros.put("sugerenciaPrincipal", null);
        }
        else{
            parametros.put("sugerenciaPrincipal", sugerenciasAceptadas.remove(0));
        }
        parametros.put("sugerenciasAceptadas",sugerenciasAceptadas);



        return new ModelAndView(parametros, "sugerencias_aceptadas.hbs");

    }

    public Response rechazarSugerencia(Request request, Response response){

        Integer sugerenciaId = new Integer(request.queryParams("sugerencia_id"));

        Sugerencia sugerenciaRechazada = this.repoSugerencia.buscar(sugerenciaId);

        Usuario usuario = LoginController.getCurrentUser(request);

        usuario.rechazarSugerencia(sugerenciaRechazada);

        FactoryRepositorioUsuario.get().modificar(usuario);
        this.repoSugerencia.modificar(sugerenciaRechazada);

        response.redirect("/");

        return response;
    }

    public Response aceptarSugerencia(Request request, Response response){

        Integer sugerenciaId = new Integer(request.queryParams("sugerencia_id"));

        Sugerencia sugerenciaAceptada = this.repoSugerencia.buscar(sugerenciaId);

        Usuario usuario = LoginController.getCurrentUser(request);

        usuario.aceptarSugerencia(sugerenciaAceptada);

        FactoryRepositorioUsuario.get().modificar(usuario);
        this.repoSugerencia.modificar(sugerenciaAceptada);

        response.redirect("/");

        return response;
    }

    public Response calificarSugerencia(Request request, Response response){
        Integer sugerenciaId = new Integer(request.params("sugerencia_id"));

        Sugerencia sugerenciaACalificar = this.repoSugerencia.buscar(sugerenciaId);

        CalificacionSugerencia calificacion = this.crearCalificacion(request);

        calificacion.setSugerencia(sugerenciaACalificar);

        sugerenciaACalificar.setCalificacion(calificacion);

        FactoryRepositorioSugerencia.get().modificar(sugerenciaACalificar);

        response.redirect("/sugerenciasAceptadas");

        return response;
    }

        public CalificacionSugerencia crearCalificacion(Request request){
        CalificacionSugerencia calificacion = new CalificacionSugerencia();

        Integer puntuacion = new Integer(request.queryParams("estrellas" + request.params("sugerencia_id")) );

            calificacion.setPuntaje(puntuacion);

        return calificacion;
    }

}
