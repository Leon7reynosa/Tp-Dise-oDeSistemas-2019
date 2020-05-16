package Domain.Controladores;

import Domain.entidades.Guardarropa;
import Domain.entidades.UserLogin.UserLogin;
import Domain.entidades.Usuario;
import models.Model;
import persistenciaDatos.Factories.FactoryRepositorioUserLogin;
import persistenciaDatos.Factories.FactoryRepositorioUsuario;
import persistenciaDatos.Repositiorios.RepositorioGeneral;
import persistenciaDatos.Repositiorios.RepositorioLoginUser;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import org.mindrot.jbcrypt.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoginController {

    public RepositorioLoginUser repoLogin = FactoryRepositorioUserLogin.get();
    public RepositorioGeneral repoUsuario = FactoryRepositorioUsuario.get();

    public ModelAndView loginPage(Request request, Response response){

        Map<String, Object> parametros = new HashMap<>();

        if(request.session().attribute("currentUserId") != null){

            request.session().attribute("afterLoginRedirect", request.pathInfo());

            response.redirect("/");

        }

        return new ModelAndView(parametros, "login_quemepongo.hbs");

    }

    public ModelAndView inicioPage(Request request, Response response){

        Map<String, Object> parametros = new HashMap<>();

        this.ensureUserIsLoggedIn(request, response);

        return new ModelAndView(parametros, "inicio_quemepongo.hbs");

    }

    public ModelAndView registrarPage(Request req, Response res){

        Map<String, Object> parametros = new HashMap<>();

        return new ModelAndView(parametros, "creacion_usuario_quemepongo.hbs");

    }

    public Response registrarUsuario(Request req, Response res){

        UserLogin nuevoLogin = new UserLogin();
        this.crearLogin(nuevoLogin, req);

        Usuario nuevoUsuario = this.crearUsuario(nuevoLogin, req);

        repoLogin.agregar(nuevoLogin);
        repoUsuario.agregar(nuevoUsuario);

        res.redirect("/login");

        return res;

    }

    public ModelAndView handleLoginPost(Request request, Response response){

        Map<String, Object> parametros = new HashMap<>();

        if(this.autenticarUsuario(request)){
            if(request.session().attribute("afterLoginRedirect") != null){
                response.redirect(request.session().attribute("afterLoginRedirect"));
            }else {
                response.redirect("/");
            }
        }
        else {
            parametros.put("authenticationFailed", true);
            return new ModelAndView(parametros, "login_quemepongo.hbs"); /**/

        }

        return null; /* O.o >.> */
    }

    public Response handleLogoutPost(Request request, Response response){

        if(request.session().attribute("currentUserId") != null){
            request.session().removeAttribute("currentUserId");
            request.session().removeAttribute("afterLoginRedirect");
        }
        response.redirect("/login");

        return response;
    }

    private Boolean autenticarUsuario(Request req){

        String usuario = this.getUsuario(req);
        String contrasenia = this.getContrasenia(req);

        if(usuario.isEmpty() || contrasenia.isEmpty()){
            System.out.println("Tenes que especificar todos los parametros");
            return false;
        }

        System.out.println("Usuario: " + usuario);

        UserLogin usuarioEncontrado = this.repoLogin.buscar(usuario);

        if(usuarioEncontrado == null){
            System.out.println("No encontro el usuario: " + usuario);
            return false;
        }

        System.out.println("Encontro al usuario");

        String contraseniaHasheada = BCrypt.hashpw(contrasenia,usuarioEncontrado.getSalt());
        if(!contraseniaHasheada.equals(usuarioEncontrado.getPassword())){
            return false;
        }

        req.session().attribute("currentUserId",usuarioEncontrado.getUsuarioDatos().getId());

        return true;
    }

    private String getUsuario(Request req){
        return req.queryParams("usuario");
    }

    private String getContrasenia(Request req){
        return req.queryParams("contrasenia");
    }

    private String getNombre(Request req){return req.queryParams("nombre");}

    private String getEmail(Request req){return req.queryParams("email");}

    private String getTelefono(Request req){return req.queryParams("telefono");}

    private String getAfterLoginRedirect(Request request){return request.session().attribute("afterLoginRedirect");}

    public static Usuario getCurrentUser(Request request){

        int user_id = request.session().attribute("currentUserId");

        Usuario currentUser = FactoryRepositorioUsuario.get().buscar(user_id);

        return currentUser;
    }

    public static void ensureUserIsLoggedIn(Request request, Response response){

        if(request.session().attribute("currentUserId") == null){

            request.session().attribute("afterLoginRedirect", request.pathInfo());

            response.redirect("/login");

        }

    }

    private void crearLogin(UserLogin nuevoLogin, Request req){

        nuevoLogin.setUser(this.getUsuario(req));
        nuevoLogin.setSalt(BCrypt.gensalt());
        nuevoLogin.setPassword(
                BCrypt.hashpw(this.getContrasenia(req), nuevoLogin.getSalt())
        );


    }

    private Usuario crearUsuario( UserLogin nuevoLogin, Request req){

        Usuario nuevoUsuario = new Usuario(this.getNombre(req), this.getEmail(req), this.getTelefono(req));

        nuevoUsuario.setUser(nuevoLogin);
        nuevoLogin.setUsuarioDatos(nuevoUsuario);

        return nuevoUsuario;
    }

}
