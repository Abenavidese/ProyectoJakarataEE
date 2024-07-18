package web.ic.ec.main;

import java.util.HashSet;
import java.util.Set;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import web.ic.ec.dao.RolDAO;
import web.ic.ec.dao.UsuarioDAO;
import web.ic.ec.enums.RolNombre;
import web.ic.ec.model.Rol;
import web.ic.ec.model.Usuario;

@Singleton
@Startup
public class Main {

    @Inject
    private UsuarioDAO daoUsuario;
    
    @Inject
    private RolDAO daoRol;

    @PostConstruct
    public void init() {
        // Crear y persistir roles
        Rol rolAdmin = new Rol(RolNombre.ROLE_ADMIN);
        daoRol.insert(rolAdmin);
        
        Rol rolUser = new Rol(RolNombre.ROLE_USER);
        daoRol.insert(rolUser);

        // Crear un conjunto de roles
        Set<Rol> rolesAdmin = new HashSet<>();
        rolesAdmin.add(rolAdmin);

        Set<Rol> rolesUser = new HashSet<>();
        rolesUser.add(rolUser);

        // Crear y persistir usuarios
        Usuario usuarioAdmin = new Usuario();
        usuarioAdmin.setUsername("adminprueba");
        usuarioAdmin.setPassword("abc123");
        usuarioAdmin.setEmail("admin@gmail.com");
        usuarioAdmin.setRoles(rolesAdmin);
        daoUsuario.insert(usuarioAdmin);

        Usuario usuarioUser = new Usuario();
        usuarioUser.setUsername("userprueba");
        usuarioUser.setPassword("xyz987");
        usuarioUser.setEmail("user@gmail.com");
        usuarioUser.setRoles(rolesUser);
        daoUsuario.insert(usuarioUser);
    }
}
