package rest.server;

import java.net.URI;

import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.jdkhttp.JdkHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import com.sun.net.httpserver.HttpServer;



public class Server {
    private static HttpServer server;
    
    //startet den HTTP Server
    public static void startServer(final String url, final String pack) {
        System.out.println("Start server");
        System.out.println(url);

        // Erstellen Sie eine ResourceConfig für Ihre Jersey-Anwendung
        final ResourceConfig rc = new ResourceConfig()
            .packages(pack)
            .register(JacksonFeature.class)  // Registrieren Sie den Jackson JSON-Provider
            .register(CorsFilter.class); // Registrieren Sie die CORS-Filter

        server = JdkHttpServerFactory.createHttpServer(URI.create(url), rc);
        System.out.println("Ready for Requests....");
    }

    // Stoppt den laufenden Server
    public static void stopServer() {
        if (server != null) {
            server.stop(0);
            System.out.println("Server stopped.");
        }
    }

}
