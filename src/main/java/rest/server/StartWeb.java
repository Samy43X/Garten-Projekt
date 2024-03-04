package rest.server;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.server.StaticHttpHandler;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import javax.swing.*;
import java.awt.*;
import java.net.URI;

public class StartWeb {

    private HttpServer server;

    public void startServer() {
        URI baseUri = URI.create("http://localhost:8080/rest/");
        ResourceConfig config = new ResourceConfig().packages("dev.hv.rest.util");
        this.server = GrizzlyHttpServerFactory.createHttpServer(baseUri, config, false);

        StaticHttpHandler staticHttpHandler = new StaticHttpHandler("src/main/webapp/");
        server.getServerConfiguration().addHttpHandler(staticHttpHandler, "/");

        try {
            server.start();
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().browse(new URI("http://localhost:8080/"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stopServer() {
        if (server != null) {
            server.shutdownNow();
        }
    }

    // Optional: Methode zum Anzeigen des Steuerfensters
    public static void showControlWindow() {
        JFrame frame = new JFrame("Server Control");
        JButton stopButton = new JButton("Stop Server and Exit");
        stopButton.addActionListener(e -> {
            System.exit(0);
        });
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new FlowLayout());
        frame.add(stopButton);
        frame.pack();
        frame.setVisible(true);
    }
}