package Arquitectura_Web.server;

import Arquitectura_Web.spark.utils.BooleanHelper;
import Domain.Controladores.*;
import Domain.Controladores.EventoController;
import Json_Configuracion.ObtenerConfiguracion;
import org.uqbarproject.jpa.java8.extras.PerThreadEntityManagers;
import spark.Spark;
import spark.template.handlebars.HandlebarsTemplateEngine;
import Arquitectura_Web.spark.utils.HandlebarsTemplateEngineBuilder;

public class Router {

    private static HandlebarsTemplateEngine engine;

    private static void initEngine() {
        Router.engine = HandlebarsTemplateEngineBuilder.create().withDefaultHelpers().withHelper("isTrue", BooleanHelper.isTrue).build();
    }

    public static void init() {
        Router.initEngine();
        //Spark.staticFileLocation("/public"); <---
        Spark.externalStaticFileLocation(ObtenerConfiguracion.getInstance().getExternalStaticFiles());
        Router.configure();
    }

    private static void configure() {
        GuardarropaController guardarropaController = new GuardarropaController();
        LoginController loginController = new LoginController();
        UsuarioController usuarioController = new UsuarioController();
        EventoController eventoController = new EventoController();
        SugerenciaController sugerenciaController = new SugerenciaController();

        Spark.get("/" , loginController::inicioPage, Router.engine);

        Spark.get("/seleccionGuardarropas" , guardarropaController::SeleccionPage , Router.engine);

        Spark.get("/seleccionGuardarropasSuge" , sugerenciaController::SeleccionPageSugerencia , Router.engine);

        Spark.get("/calendario" , eventoController::calendarPage , Router.engine);

        Spark.get("/ingreso_evento" , eventoController::crearEventoPage , Router.engine);

        Spark.get("/guardarropas", guardarropaController::mostrarTodos, Router.engine);

        Spark.get("/guardarropas/:guardarropa_id", guardarropaController::mostrarGuardarropasId, Router.engine);

        Spark.get("/guardarropas/:guardarropa_id/sugerencia" , sugerenciaController::sugerenciaPage , Router.engine);

        Spark.get("/evento/:evento_id/sugerenciaActual", eventoController::mostrarSugerenciaActual, Router.engine);

        Spark.get("/login", loginController::loginPage, Router.engine);

        Spark.get("/eventos/:fecha" , eventoController::crearListadoEventosPage, Router.engine);

        Spark.get("/registrarUsuario", loginController::registrarPage, Router.engine);

        Spark.get("/ingreso_prenda/:guardarropa_id" , guardarropaController::ingresoPrendaGuardarropaPage, Router.engine);

        Spark.get("/ingresoGuardarropa" , guardarropaController::ingresoGuardarropaPage, Router.engine);

        Spark.get("/sugerenciasAceptadas",sugerenciaController::mostrarAceptadas, Router.engine);

        Spark.post("/registrarEvento" , eventoController::registrarEvento);

        Spark.post("/logout",loginController::handleLogoutPost);

        Spark.post("/registrarUsuario", loginController::registrarUsuario);

        Spark.post("/login",loginController::handleLoginPost,Router.engine);

        Spark.post("/ingresoGuardarropa", guardarropaController::crearGuardarropa);

        Spark.post("/ingreso_prenda/:guardarropa_id", guardarropaController::crearPrenda);

        Spark.post("/rechazar",sugerenciaController::rechazarSugerencia);

        Spark.post("/aceptar",sugerenciaController::aceptarSugerencia);

        Spark.post("/calificarSugerencia/:sugerencia_id", sugerenciaController::calificarSugerencia);

        Spark.delete("/prenda/:prenda_id",guardarropaController::eliminarPrenda);

        Spark.after((req, res) -> {
            try {
                PerThreadEntityManagers.closeEntityManager();
            }
            catch(Exception ex){
                /*no habia entity manager para cerrar*/
            }
        });

    }

}


