package web.ic.ec.security;

import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import web.ic.ec.dao.UsuarioDAO;
import web.ic.ec.model.Rol;
import web.ic.ec.model.Usuario;
import jakarta.json.Json;
import jakarta.json.JsonObject;

@Path("/auth")
public class LoginServicio {

    private static final String KEY = "Miclave12341111AleMiclave12341111Ale123213322Miclave12341111AleMiclave12341111Ale123213322Miclave12341111AleMiclave12341111Ale123213322";

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response validar(Usuario usuario) {
        Usuario usuarioBD = UsuarioDAO.obtenerUsuario(usuario.getUsername(), usuario.getPassword());

        if (usuarioBD != null) {
            long tiempo = System.currentTimeMillis();
            Set<Rol> roles = usuarioBD.getRoles();
            String rolesString = roles.stream()
                                      .map(rol -> rol.getRolNombre().name())
                                      .collect(Collectors.joining(","));

            String jwt = Jwts.builder()
                    .signWith(SignatureAlgorithm.HS256, KEY)
                    .setSubject("Importante")
                    .setIssuedAt(new Date(tiempo))
                    .setExpiration(new Date(tiempo + 900000)) // 15 minutos
                    .claim("username", usuario.getUsername())
                    .claim("roles", rolesString)
                    .compact();

            JsonObject json = Json.createObjectBuilder()
                                  .add("JWT", jwt)
                                  .add("roles", rolesString)
                                  .build();

            return Response.status(Response.Status.CREATED).entity(json).build();
        }

        return Response.status(Response.Status.UNAUTHORIZED).build();
    }
}
