package web.ic.ec.bean;

import java.io.Serializable;
import java.io.StringReader;
import java.util.Arrays;
import java.util.List;

import io.jsonwebtoken.io.IOException;
import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import web.ic.ec.dao.UsuarioDAO;
import web.ic.ec.jwt.JwtProvider;
import web.ic.ec.model.Usuario;

@Named
@RequestScoped
public class LoginBean {

    private String username;
    private String password;

    @Inject
    private UsuarioDAO usuarioDAO;
    
    @Inject
    private JwtProvider jwtProvider; // Inyectar JwtProvider

    public void login() {
        Usuario usuario = usuarioDAO.validateUser(username, password);
        if (usuario != null) {
            String jwt = jwtProvider.createToken(username, usuario.getRoles()); // Generar el JWT

            // Redirigir a Angular con el token JWT
            try {
                HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
                response.sendRedirect("http://localhost:4200/inicio?token=" + jwt);
                FacesContext.getCurrentInstance().responseComplete(); // Marcar la respuesta como completada
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            // Redirigir a la página de error
            try {
                HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
                response.sendRedirect("failure.xhtml");
                FacesContext.getCurrentInstance().responseComplete(); // Marcar la respuesta como completada
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    // Getters y setters para username y password
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
