package Domain.Controladores;

import Domain.entidades.*;
import jdk.nashorn.internal.ir.IdentNode;
import models.Model;
import persistenciaDatos.Factories.FactoryRepositorioEvento;
import persistenciaDatos.Factories.FactoryRepositorioGuardarropa;
import persistenciaDatos.Factories.FactoryRepositorioSugerencia;
import persistenciaDatos.Factories.FactoryRepositorioUsuario;
import persistenciaDatos.Repositiorios.RepositorioGeneral;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class EventoController {

    public RepositorioGeneral repo = FactoryRepositorioEvento.get();

    private Guardarropa getGuardarrapa(Request request){

        int id_guardarropa = new Integer(request.queryParams("guardarropa"));

        Guardarropa guardarropa = FactoryRepositorioGuardarropa.get().buscar(id_guardarropa);

        return guardarropa;
    }

    private TipoDeFrecuencia getFrecuencia(Request request){

        String fechaString = request.queryParams("fecha_evento");

        LocalDate fecha = LocalDate.parse(fechaString);

        String frecuenciaString = request.queryParams("frecuencia");

        switch (frecuenciaString){
            case "Mensual":
                System.out.println("entre al mensualaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
                return new Mensual(fecha);

            case "Semanal":
                return new Semanal(fecha);

            case "UnicaVez":
                return new UnicaVez(fecha);

            default:
                return null;
        }

    }

    private EstiloVestimenta getEstilo(Request request){

        String estilo = request.queryParams("estilo");

        EstiloVestimenta[] estilos = EstiloVestimenta.values();

        for(EstiloVestimenta unEstilo : estilos){

            if(estilo.equals(unEstilo.toString())){
                return unEstilo;
            }

        }

        return null; /*PELIGRO, no tanto, pero posible*/

    }

    public ModelAndView calendarPage(Request request, Response response){

        LoginController.ensureUserIsLoggedIn(request, response);

        Usuario usuario = LoginController.getCurrentUser(request);

        Map<String, Object> parametros = new HashMap<>();

        List<Evento> lista_eventos = usuario.getEventos();

        List<String> fechas = new ArrayList<>();

        DateTimeFormatter formatters = DateTimeFormatter.ofPattern("MM/dd/uuuu");

        lista_eventos.forEach(evento -> fechas.add(evento.getDate().format(formatters)));

        parametros.put("eventos", lista_eventos);
        parametros.put("fecha", fechas);

        return new ModelAndView(parametros, "calendario_quemepongo.hbs");

    }

    public ModelAndView crearEventoPage(Request request, Response response){

        LoginController.ensureUserIsLoggedIn(request, response);

        List<EstiloVestimenta> estilos = new ArrayList<>();
        EstiloVestimenta[] estilosVestimenta = EstiloVestimenta.values();
        for(EstiloVestimenta unEstilo: estilosVestimenta){
            estilos.add(unEstilo);
        }

        Map<String, Object> parametros = new HashMap<>();

        parametros.put("estilos" , estilos);

        List<Guardarropa> guardarropas = new ArrayList<>();
        guardarropas.addAll(LoginController.getCurrentUser(request).getGuardarropas());

        parametros.put("guardarropas",guardarropas);

        return new ModelAndView(parametros, "creacion_evento_quemepongo.hbs");

    }

    public ModelAndView crearListadoEventosPage(Request request , Response response){

        LoginController.ensureUserIsLoggedIn(request, response);

        Map<String, Object> parametros = new HashMap<>();
        List<Evento> eventos;

        try {
            LocalDate fechaEvento = LocalDate.parse(request.params("fecha"));

            Usuario usuario = LoginController.getCurrentUser(request);

            eventos = usuario.getEventos().stream().filter(evento -> evento.getDate().isEqual(fechaEvento)).collect(Collectors.toList());

            System.out.println("Cantidad de eventos del dia : " + eventos.size());

            eventos.forEach(evento -> evento.getDate());
            eventos.forEach(evento -> evento.getEstiloVestimenta());

        }catch (Exception ex){
            return new ModelAndView(parametros, "error.hbs");
        }

        parametros.put("evento" , eventos );

        return new ModelAndView(parametros, "listado_evento_quemepongo.hbs");

    }

    public Response registrarEvento(Request request, Response response){

        LoginController.ensureUserIsLoggedIn(request, response);

        TipoDeFrecuencia frecuencia_elegida = this.getFrecuencia(request);

        String descripcion = request.queryParams("descripcion");

        EstiloVestimenta estilo = this.getEstilo(request);

        Usuario usuario = LoginController.getCurrentUser(request);

        Guardarropa guardarropa = this.getGuardarrapa(request);

        Evento eventoNuevo = new Evento(descripcion, frecuencia_elegida , estilo, usuario, guardarropa );

        usuario.agregarEvento(eventoNuevo);

        this.repo.agregar(eventoNuevo);

        FactoryRepositorioUsuario.get().modificar(usuario);

        response.redirect("/calendario");

        return response;


    }

    public ModelAndView mostrarSugerenciaActual(Request request, Response response) {
        LoginController.ensureUserIsLoggedIn(request, response);

        Map<String, Object> parametros = new HashMap<>();
        Evento evento = EventoController.getEventoFromParams(request);

        if(evento == null){
            return new ModelAndView(parametros, "error.hbs");
        }

        Sugerencia sugerenciaActual = evento.getSugerenciaActual();

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

        if(sugerenciaActual != null){

            FactoryRepositorioSugerencia.get().agregar(sugerenciaActual);

            listaSuperior =
                    sugerenciaActual.getAtuendo().getConjuntoPrendas().stream()
                            .filter(prenda -> prenda.getCategoria().equals(Categoria.SUPERIOR)).collect(Collectors.toList());

            prendaActiveSuperior = listaSuperior.get(0);

            listaSuperior.remove(0);

            listaInfrerior =
                    sugerenciaActual.getAtuendo().getConjuntoPrendas().stream()
                            .filter(prenda -> prenda.getCategoria().equals(Categoria.INFERIOR)).collect(Collectors.toList());

            prendaActiveInferior = listaInfrerior.get(0);

            calzado =
                    sugerenciaActual.getAtuendo().getConjuntoPrendas().stream()
                            .filter(prenda -> prenda.getCategoria().equals(Categoria.CALZADO)).collect(Collectors.toList()).get(0);

            colorActiveSuperior = prendaActiveSuperior.getColorPrimario().getColorHexa();
            colorActiveInferior = prendaActiveInferior.getColorPrimario().getColorHexa();
            colorCalzado = calzado.getColorPrimario().getColorHexa();

            listaSuperior.forEach(prenda -> coloresSuperior.add(prenda.getColorPrimario().getColorHexa()));

            listaInfrerior.forEach(prenda -> coloresInferior.add(prenda.getColorPrimario().getColorHexa()));

        }

        parametros.put("sugerencia" , sugerenciaActual);
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

        return new ModelAndView(parametros, "sugerenciaActual_evento_quemepongo.hbs");
    }

    public static Evento getEventoFromParams(Request request){
        Usuario usuario = LoginController.getCurrentUser(request);
        Integer eventoId;

        try{
            eventoId = new Integer(request.params("evento_id"));
        }
        catch(NumberFormatException ex){
            return null;
        }

        Evento evento = FactoryRepositorioEvento.get().buscar(eventoId);

        if(evento != null) {
            if (!evento.tenesEsteUsuario(usuario)) {
                return null;
            }
        }
        return evento;
    }




}

