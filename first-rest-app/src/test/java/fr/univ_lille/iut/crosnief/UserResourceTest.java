package fr.univ_lille.iut.crosnief;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.net.URI;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserResourceTest extends JerseyTest {

    /**
     * Il est obligatoire de redéfinir cette méthode qui permet de configurer le contexte de Jersey
     */
    @Override
    protected Application configure() {
        return new ResourceConfig(UserResource.class);  
    }

    /**
     * Test de création d'un utilisateur (retour HTTP et envoi de l'URI de la nouvelle instance)
     */
    @Test
    public void test_B_CreateUser() {
        User user = new User("jsteed", "Steed", "jsteed@mi5.uk");
        // Conversion de l'instance de User au format JSON pour l'envoi
        Entity<User> userEntity = Entity.entity(user, MediaType.APPLICATION_JSON);

        // Envoi de la requête HTTP POST pour la création de l'utilisateur
        Response response = target("/users").request().post(userEntity);

        // Vérification du code de retour HTTP
        assertEquals(201, response.getStatus());

        // Vérification que la création renvoie bien l'URI de la nouvelle instance dans le header HTTP 'Location'
        // ici : http://localhost:8080/users/jsteed
        URI uriAttendue = target("/users").path(user.getLogin()).getUri();
        assertTrue(uriAttendue.equals(response.getLocation()));
    }

    /**
     * Test de création en double d'un utilisateur. Doit renvoyer 409
     * ! Cela fonctionne car le test précédent à déjà créé l'utilisateur et que le container est conservé !
     */
    @Test
    public void test_C_CreateSameUser() {
        User user = new User("jsteed", "Steed", "jsteed@mi5.uk");
        Entity<User> userEntity = Entity.entity(user, MediaType.APPLICATION_JSON);

        int same = target("/users").request().post(userEntity).getStatus();
        assertEquals(409, same);
    }
}
