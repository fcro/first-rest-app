package fr.univ_lille.iut.crosnief;

import javax.ws.rs.core.Application;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class MyResourceTest extends JerseyTest {

    @Override 
    protected Application configure() {
        return new ResourceConfig(MyResource.class);

    }

    /**
      * Test to see that the message "Got it!" is sent in the response.
     */
    @Test
    public void testGetIt() {
        String responseMsg = target("/myresource").request().get(String.class);
        assertEquals("Got it!", responseMsg);
    }
}
