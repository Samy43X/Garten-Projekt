package rest.server;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

@Path("/shutdown")
public class ShutdownEndpoint {

    @GET
    public Response shutdownProgram() {
        new Thread(() -> {
            try {
                Thread.sleep(1000); // Verzögerung, um die Antwort zu senden, bevor der Server stoppt
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            System.exit(0);
        }).start();

        return Response.ok("Anwendung wird beendet...").build();
    }
}
