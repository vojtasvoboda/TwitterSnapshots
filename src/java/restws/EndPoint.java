package restws;

import java.util.Date;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import metadata.Metadata;
import metadata.Snapshots;
import metadata.Results;
import metadata.Users;
import services.GexfService;
import services.GexfFinalService;
import services.GoogleChart;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import services.ResultService;

/**
 * API Endpoint
 * @author Bc. Vojtěch Svoboda <svobovo3@fit.cvut.cz>
 */
@Path("/api/")
public class EndPoint {

    /**
     * Vratime XML s informaci o projektu
     * @return metadata.xml
     */
    @GET
    @Path("/v1/metadata")
    @Produces("application/xml")
    public Metadata getMetadata() {
        Metadata metad = new Metadata("Analyza Twitter Feedu",
                                    "Aplikace analyzuje twitter feed s identifikatorem #cloud",
                                    new Date(),
                                    10800);
        metad.addAuthor("Vojtěch Svoboda", "Praha 9");
        metad.addAuthor("Michal Šik", "Praha 1");
        metad.addAuthor("Zdeněk Tuháček", "Praha 2");
        // vyhodime metadata jako XML
        return metad;
    }

    /**
     * Vratime XML s informaci o snapshotech
     * @return snapshots.xml
     */
    @GET
    @Path("/v1/snapshots")
    @Produces("application/xml")
    public Snapshots getSnapshots() {
        Snapshots snpshts = new Snapshots();
        // vyhodime snapshoty jako XML
        return snpshts;
    }

    /**
     * Vratime graf se snapshoty
     * @return graph.png
     */
    @GET
    @Path("/v1/snapshots")
    @Produces("image/png")
    public Response getSnapshotsGraph() {
        ResponseBuilder builder;
        GoogleChart chart = new GoogleChart();
        byte[] graph = null;
        try {
                Snapshots snpshts = new Snapshots();
                graph = chart.getGoogleGraph(chart.getGoogleURL(snpshts.getSnapshots()));
                System.out.println("" + graph);
        } catch (Exception ex) {
                System.err.println("EndPoint: Chyba /v1/snapshots image/png: " + ex.toString());
                builder = Response.status(Response.Status.INTERNAL_SERVER_ERROR);
                builder.build();
        }
        builder = Response.status(Response.Status.OK);
        builder.entity(graph);
        return builder.build();
    }

    /**
     * Vratime graf se snapshoty
     * @return graph.png
     */
    @GET
    @Path("/v1/graph")
    @Produces("image/png")
    public Response getSnapshotsGraphPng() {
        ResponseBuilder builder;
        GoogleChart chart = new GoogleChart();
        byte[] graph = null;
        try {
                Snapshots snpshts = new Snapshots();
                graph = chart.getGoogleGraph(chart.getGoogleURL(snpshts.getSnapshots()));
                System.out.println("" + graph);
        } catch (Exception ex) {
                System.err.println("EndPoint: Chyba /v1/graph image/png: " + ex.toString());
                builder = Response.status(Response.Status.INTERNAL_SERVER_ERROR);
                builder.build();
        }
        builder = Response.status(Response.Status.OK);
        builder.entity(graph);
        return builder.build();
    }

    /**
     * Vratime URL grafu
     * @return URL graph
     */
    @GET
    @Path("/v1/graph/url")
    @Produces("text/html")
    public String getSnapshotsGraphUrl() {
        Snapshots snpshts = new Snapshots();
        GoogleChart chart = new GoogleChart();
        String url = chart.getGoogleURL(snpshts.getSnapshots());
        return url;
    }

    /**
     * Vratime XML s informaci o uzivatelich
     * @return users.xml
     */
    @GET
    @Path("/v1/users")
    @Produces("application/xml")
    public Users getUsers() {
        Users usrs = new Users();
        return usrs;
    }

    /**
     * Vracime zakladni rozcestnik
     * @return menu.html
     */
    @GET
    @Path("/")
    @Produces("text/html")
    public String info() {
        String out = "";
        out += "<html>";
        out += "<head>";
        out += "<title>Analýza Twitter Feedu</title>";
        out += "</head>";
        out += "<body>";
        out += "<h1>Dostupné webové služby</h1>";
        out += "<ul>";
        out += "<li><a href=\"/api/v1/metadata\">/api/v1/metadata</a></li>";
        out += "<li><a href=\"/api/v1/snapshots\">/api/v1/snapshots</a></li>";
        out += "<li><a href=\"/api/v1/graph\">/api/v1/graph</a></li>";
        out += "<li><a href=\"/api/v1/graph/url\">/api/v1/graph/url</a></li>";
        out += "<li><a href=\"/api/v1/gexf\">/api/v1/gexf</a></li>";
        out += "<li><a href=\"/api/v1/results\">/api/v1/results</a></li>";
        out += "<li><a href=\"/api/v1/gexffinal\">/api/v1/gexffinal</a></li>";
        out += "</ul>";
        out += "</body>";
        out += "</html>";
        return out;
    }

    /**
     * Vratime XML s grafem ve XML
     * @return graph.gexf
     */
    @GET
    @Path("/v1/gexf")
    @Produces("application/xml")
    public Response getGexf() {
        GexfService gs = new GexfService();
        Response getGexfString = gs.getGexf();
        return getGexfString;
    }


     /**
     * Vratime XML s grafem ve XML
     * @return graph.gexf
     */
    @GET
    @Path("/v1/results")
    @Produces("application/xml")
    public Results getResults() {
        ResultService rs = new ResultService();
        // Response getGexfString = gs.getResults();
        if(rs.getResults()) {
        }
        /*ResponseBuilder builder;
        builder = Response.status(Response.Status.OK);
        return builder.build();
        */
        Results reslts = new Results();
        // vyhodime snapshoty jako XML
        return reslts;
    }


    /**
     * Vratime XML s grafem ve XML
     * @return graph.gexf
     */
    @GET
    @Path("/v1/gexffinal")
    @Produces("application/gexf+xml")
    // @Produces("application/xml")
    public Response getGexfFinal() {
        GexfFinalService gs = new GexfFinalService();
        Response getGexfString = gs.getGexfFinal();
        return getGexfString;
    }
}
