package services;

import com.ojn.gexf4j.core.Gexf;
import com.ojn.gexf4j.core.Graph;
import com.ojn.gexf4j.core.Metadata;
import com.ojn.gexf4j.core.Node;
import com.ojn.gexf4j.core.impl.GexfImpl;
import com.ojn.gexf4j.core.impl.StaxGraphWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;
import javax.ws.rs.core.Response;
import persistence.Edge;
import persistence.User;
import persistence.Snapshot;
import persistence.SnapshotDAO;
import persistence.Result;
import persistence.ResultDAO;
import java.util.ArrayList;
import java.util.List;

/**
 * Trida pro praci s knihovnou Gexf pro generovani Gexf XML
 * @author Bc. Vojtěch Svoboda <svobovo3@fit.cvut.cz>
 */
public class GexfService {

     public Response getGexf() {
        Gexf gexf = buildGraph();

        StaxGraphWriter gexfWriter = new StaxGraphWriter();
        try {
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            gexfWriter.writeToStream(gexf, os);
            String out = new String(os.toByteArray(), "UTF-8");
            return Response.ok(out).build();
        } catch (IOException e) {
            System.err.println("GexfService: getGexf() chyba: " + e.toString());
            return null;
        }
    }

    private Gexf buildGraph()
    {
        Gexf gexf = new GexfImpl();
        Metadata metadata = gexf.getMetadata();
        metadata.setLastModified(new Date());
        // metadata.setCreator("Svoboda Vojtech, Sik Michal, Tuhacek Zdenek");
        // metadata.setDescription("A Web network");

        Node source = null;
        Node target = null;
        Graph graph = gexf.getGraph();

        SnapshotDAO sndao = new SnapshotDAO();
        for (Snapshot snapshot : sndao.getAll()) {
            for (Edge edge : snapshot.dejEdges()) {

                //System.out.println("from "+edge.getFromId() + " to "+edge.getToId());
                source = graph.createNode("n" + edge.getFromId());
                target = graph.createNode("n" + edge.getToId());

                // tady jsem presne nenasel jak se graph zachova pokud uz node existuje, doufam ze ho pouzije znova :)
                // pokud ne, pak moyna neco takovehohle
                // int exist = gexf.getGraph().getNodes().indexOf(source);
                // source = gexf.getGraph().getNodes().get(exist);

                source.setLabel("Node " + edge.getFromId()).setStartDate(snapshot.getCreated());
                target.setLabel("Node " + edge.getToId()).setStartDate(snapshot.getCreated());
                source.connectTo("e" + edge.getKey(),target)
                      .setLabel("Edge "+edge.getKey())
                      .setStartDate(snapshot.getCreated())
                      .setEndDate(snapshot.getCreated());
            }
        }
        return gexf;
    }

    public Response getGexfFinal() {
        Gexf gexf = new GexfImpl();
        Metadata metadata = gexf.getMetadata();
        metadata.setLastModified(new Date());
        // metadata.setCreator("Svoboda Vojtech, Sik Michal, Tuhacek Zdenek");
        // metadata.setDescription("A Web network");

        Node source = null;
        Node target = null;
        Graph graph = gexf.getGraph();

        SnapshotDAO sndao = new SnapshotDAO();
        for (Snapshot snapshot : sndao.getAll()) {
            for (Edge edge : snapshot.dejEdges()) {

                //System.out.println("from "+edge.getFromId() + " to "+edge.getToId());
                source = graph.createNode("n" + edge.getFromId());
                target = graph.createNode("n" + edge.getToId());

                // tady jsem presne nenasel jak se graph zachova pokud uz node existuje, doufam ze ho pouzije znova :)
                // pokud ne, pak moyna neco takovehohle
                // int exist = gexf.getGraph().getNodes().indexOf(source);
                // source = gexf.getGraph().getNodes().get(exist);

                source.setLabel("Node " + edge.getFromId()).setStartDate(snapshot.getCreated());
                target.setLabel("Node " + edge.getToId()).setStartDate(snapshot.getCreated());
                source.connectTo("e" + edge.getKey(),target)
                      .setLabel("Edge "+edge.getKey())
                      .setStartDate(snapshot.getCreated())
                      .setEndDate(snapshot.getCreated());
            }
        }

        StaxGraphWriter gexfWriter = new StaxGraphWriter();
        try {
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            gexfWriter.writeToStream(gexf, os);
            String out = new String(os.toByteArray(), "UTF-8");
            return Response.ok(out).build();
        } catch (IOException e) {
            System.err.println("GexfService: getGexf() chyba: " + e.toString());
            return null;
        }
    }
}
