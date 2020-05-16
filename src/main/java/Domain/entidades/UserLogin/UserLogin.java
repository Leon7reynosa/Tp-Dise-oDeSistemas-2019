package Domain.entidades.UserLogin;

import Domain.entidades.EntidadPersistente;
import Domain.entidades.Usuario;

import javax.persistence.*;

@Entity
@Table(name = "Login")
public class UserLogin extends EntidadPersistente {

    @Column(name = "user")
    private String user;
    @Column(name = "password")
    private String password;
    @Column(name = "salt")
    private String salt;
    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "usuario_id")
    private Usuario usuarioDatos;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public Usuario getUsuarioDatos(){
        return this.usuarioDatos;
    }

    public void setUsuarioDatos(Usuario unUsuario){
        this.usuarioDatos = unUsuario;
    }
}
