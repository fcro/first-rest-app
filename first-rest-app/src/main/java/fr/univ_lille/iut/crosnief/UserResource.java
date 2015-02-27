package fr.univ_lille.iut.crosnief;

import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.Path;
import javax.ws.rs.POST;
import javax.ws.rs.PathParam;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.Context;

import java.net.URI;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

/**
 * Ressource User (accessible avec le chemin "/users")
 */
@Path("users")
public class UserResource {
    // Pour l'instant, on se contentera d'une variable statique pour conserver l'état
    private static Map<String, User> users = new HashMap<>();

    // L'annotation @Context permet de récupérer des informations sur le contexte d'exécution de la ressource.
    // Ici, on récupère les informations concernant l'URI de la requête HTTP, ce qui nous permettra de manipuler
    // les URI de manière générique.
    @Context
    public UriInfo uriInfo;

    /**
     * Une ressource doit avoir un contructeur (éventuellement sans arguments)
     */
    public UserResource() {
    }

    /**
     * Method prenant en charge les requêtes HTTP GET.
     *
     * @return Une liste d'utilisateurs
     */
    @GET
    public List<User> getUsers() {
        return new ArrayList<User>(users.values());
    }

    /**
     * Méthode prenant en charge les requêtes HTTP GET sur /users/{login}
     *
     * @return Une instance de User
     */
    @GET
    @Path("{login}")
    public User getUser(@PathParam("login") String login) {
        // Si l'utilisateur est inconnu, on renvoie 404
        if (  ! users.containsKey(login) ) {
            throw new NotFoundException();
        }
        else {
            return users.get(login);
        }
    }

    /**
     * Méthode de création d'un utilisateur qui prend en charge les requêtes HTTP POST
     * La méthode renvoie l'URI de la nouvelle instance en cas de succès
     *
     * @param  user Instance d'utilisateur à créer
     * @return Response le corps de la réponse est vide, le code de retour HTTP est fixé à 201 si la création est faite
     *         L'en-tête contient un champs Location avec l'URI de la nouvelle ressource
     */
    @POST
    public Response createUser(User user) {
        // Si l'utilisateur existe déjà, renvoyer 409
        if ( users.containsKey(user.getLogin()) ) {
            return Response.status(Response.Status.CONFLICT).build();
        }
        else {
            users.put(user.getLogin(), user);

            // On renvoie 201 et l'instance de la ressource dans le Header HTTP 'Location'
            URI instanceURI = uriInfo.getAbsolutePathBuilder().path(user.getLogin()).build();
            return Response.created(instanceURI).build();
        }
    }
}
