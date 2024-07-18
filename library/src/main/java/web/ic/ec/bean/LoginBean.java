package web.ic.ec.bean;

import java.io.Serializable;
import java.io.StringReader;
import java.util.Arrays;
import java.util.List;

import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import web.ic.ec.dao.UsuarioDAO;
import web.ic.ec.model.Usuario;

@Named
@RequestScoped
public class LoginBean {

    private String username;
    private String password;

    @Inject
    private UsuarioDAO usuarioDAO;

    public String login() {
        Usuario usuario = usuarioDAO.validateUser(username, password);
        if (usuario != null) {
            return "success.xhtml?faces-redirect=true"; // Redirige a la página de éxito
        } else {
            return "failure.xhtml?faces-redirect=true"; // Redirige a la página de error
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