package services;

/*
import com.ojn.gexf4j.core.Gexf;
import com.ojn.gexf4j.core.Graph;
import com.ojn.gexf4j.core.Metadata;
import com.ojn.gexf4j.core.Node;
//import com.ojn.gexf4j.core.Edge;
import com.ojn.gexf4j.core.impl.GexfImpl;
import com.ojn.gexf4j.core.impl.StaxGraphWriter;
*/
import com.ojn.gexf4j.core.*;
import com.ojn.gexf4j.core.impl.*;
import com.ojn.gexf4j.core.data.*;
import com.ojn.gexf4j.core.impl.data.*;

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
public class GexfFinalService {

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

    private Node buildNode(Node source, Graph graph, long from, Snapshot snapshot, Attribute attDegree){
        boolean foundFrom = false;
        for(Node n: graph.getNodes()){
            if(n.getId().equals("n"+from)){
                source = n;
                foundFrom = true;
                //System.out.println("uz tam byl "+from);
            }
        }
        if(!foundFrom){
            source = graph.createNode("n" + from);
            source.setLabel("Node " + from);
            //System.out.println("pridam novy "+from);
        }

        //int count = n.getEdges().size();
        int count = 0;
        for (Edge e : snapshot.dejEdges()) {
            if((e.getFromId() == from) || (e.getToId() == from)){
                count++;
            }
        }
        int atr = source.getAttributeValues().size();
        //System.out.println("node "+n.getId()+" attribs "+atr);
        if(atr == 0){
            source.getAttributeValues().addValue(attDegree, ""+count)
                                  .get(atr).setStartDate(snapshot.getCreated());
        }else if((!source.getAttributeValues().get(atr-1).getValue().equals(""+count))
//|| (n.getAttributeValues().get(atr-1).hasEndDate())
        ){
            source.getAttributeValues().get(atr-1).setEndDate(snapshot.getCreated());
            source.getAttributeValues().addValue(attDegree, ""+count)
                                  .get(atr).setStartDate(snapshot.getCreated());
        }
        return source;
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

        AttributeList attrList = new AttributeListImpl(AttributeClass.NODE);
        graph.getAttributeLists().add(attrList);

        Attribute attDegree = attrList.createAttribute("degree", AttributeType.INTEGER, "degree");
        //Attribute attTst = attrList.createAttribute("1", AttributeType., "tst").
//        Attribute attUrl = attrList.createAttribute("0", AttributeType.STRING, "url");
//        Attribute attIndegree = attrList.createAttribute("1", AttributeType.FLOAT, "indegree");
//        Attribute attFrog = attrList.createAttribute("2", AttributeType.BOOLEAN, "frog");

        //ArrayList<Node> savedN = new ArrayList<Node>();
        ArrayList<Long> savedNodes = new ArrayList<Long>();
        ArrayList<String> savedEdges = new ArrayList<String>();

        int i = 0;
        SnapshotDAO sndao = new SnapshotDAO();
        for (Snapshot snapshot : sndao.getAll()) {
            ArrayList<Long> pomNodes = (ArrayList<Long>)savedNodes.clone();
            ArrayList<Long> pomEdges = (ArrayList<Long>)savedEdges.clone();

            if(snapshot.getEdgescount() > 1) {
                i++;

                 for (Edge edge : snapshot.dejEdges()) {
                    long from = edge.getFromId();
                    long to = edge.getToId();

                    boolean foundFrom = false;
                    boolean foundTo = false;

                    source = buildNode(source, graph, from, snapshot, attDegree);
                    target = buildNode(target, graph, to, snapshot, attDegree);


                    boolean connected = false;
                    for(com.ojn.gexf4j.core.Edge e: graph.getAllEdges()){                        
                        if((e.getSource() == source && e.getTarget() == target)/*&&(!e.hasEndDate())*/){
                                //System.out.println("uz tam je: "+(e.getSource().getId()+"-"+e.getTarget().getId()));
                                connected = true;                           
                        }
                    }
                    if(!connected){
                        source.connectTo("e" + edge.getKey(),target)
                                  .setLabel("Edge "+edge.getKey())
                                  .setStartDate(snapshot.getCreated());
                                  //.setEndDate(snapshot.getCreated());                            
                            //System.out.println(source.getId()+"-"+target.getId());
                    }
                }

                 //yavreme aktualne nepouzivana spojeni
                boolean found = false;
                for(com.ojn.gexf4j.core.Edge e: graph.getAllEdges()){
                    for(Edge edge: snapshot.dejEdges()){
                        //System.out.println("ulozeno "+e.getSource().getId()+"-"+e.getTarget().getId());
                        //System.out.println("hledano n"+edge.getFromId()+"-n"+edge.getToId());
                        found = false;
                        if((e.getSource().getId().equals("n"+edge.getFromId()))&&(e.getTarget().getId().equals("n"+edge.getToId()))){
                            //System.out.println("stale existuje n"+edge.getFromId()+"-n"+edge.getToId());
                            found = true;
                        }
                        if(!found){
                            //System.out.println("uz neexistuje "+e.getSource().getId()+"-"+e.getTarget().getId());
                            e.setEndDate(snapshot.getCreated());
                        }
                    }
                }


                 //yavreme vsechno nezavrene
                for(Node n : graph.getNodes()){
                    int atr = n.getAttributeValues().size();
                    if(atr > 0 && (!n.getAttributeValues().get(atr-1).hasEndDate())){
                        n.getAttributeValues().get(atr-1).setEndDate(new Date());
                    }
                }
                for(com.ojn.gexf4j.core.Edge e: graph.getAllEdges()){
                    if(!e.hasEndDate()) e.setEndDate(new Date());
                }

/*

                for (Edge edge : snapshot.dejEdges()) {

                    long from = edge.getFromId();
                    long to = edge.getToId();

                    boolean foundFrom = false;
                    boolean foundTo = false;
                    for(Node n : graph.getNodes()) {
                        if(n.getId().equals("n"+from)) foundFrom = true;
                        if(n.getId().equals("n"+to)) foundTo = true;
                    }

                    if(!savedNodes.contains(from)){                                             //jestli uzlik neexistoval, tak ho tam pridame
                    //if(!foundFrom){                                             //jestli uzlik neexistoval, tak ho tam pridame
                        savedNodes.add(from);
                        source = graph.createNode("n" + from);
                        source.setLabel("Node " + from);
                              //.setStartDate(snapshot.getCreated());                                     //nastavime pocatecni datum
                        //savedN.add(source);
    //                    System.out.println("node "+from+ " pridan "+snapshot.getCreated());
                    }else {                                                 //kdyz existoval, tak si ho najdeme
                       for(Node n: graph.getNodes()){
                            if(n.getId().equals("n"+from)){
                                source = n;
                            }
                       }
                    }

                    if(!savedNodes.contains(to)){                                                   //zese neexistoval
                    //if(!foundTo){
                        savedNodes.add(to);
                        target = graph.createNode("n" + to);
                        target.setLabel("Node " + to);
                              //.setStartDate(snapshot.getCreated());
                        //savedN.add(target);
    //                    System.out.println("node "+to+ " pridan "+snapshot.getCreated());
                    }else {                                                             //zase najdeme
                       for(Node n: graph.getNodes()){
                            if(n.getId().equals("n"+to)){
                                target = n;
                            }
                        }
                    }


                    if(!savedEdges.contains(edge.getFromId()+"-"+edge.getToId()))                                   //jestli hrana neexistovala, tak ji pridame
                    {
                         for(com.ojn.gexf4j.core.Edge e: graph.getAllEdges()){
                            if((e.getSource().getId()+"-"+e.getTarget().getId()).equals(edge)){
                                    System.out.println("uz tam je: "+(e.getSource().getId()+"-"+e.getTarget().getId()));
                                }
                        }


                        savedEdges.add(source.getId()+"-"+target.getId());
                        source.connectTo("e" + edge.getKey(),target)
                              .setLabel("Edge "+edge.getKey())
                              .setStartDate(snapshot.getCreated());
                              //.setEndDate(snapshot.getCreated());
                        //System.out.println("edge "+source.getId()+" to "+target.getId() +" pridan "+snapshot.getCreated());
                        System.out.println(source.getId()+"-"+target.getId());
                    }
                }

                for(Node n : graph.getNodes()){
                    int count = n.getEdges().size();
                    int atr = n.getAttributeValues().size();
                    //System.out.println("node "+n.getId()+" attribs "+atr);
                    if(atr == 0){
                        n.getAttributeValues().addValue(attDegree, ""+count)
                                              .get(atr).setStartDate(snapshot.getCreated());
                    }else if((!n.getAttributeValues().get(atr-1).getValue().equals(""+count)) 
 //|| (n.getAttributeValues().get(atr-1).hasEndDate())
                    ){
                        n.getAttributeValues().get(atr-1).setEndDate(snapshot.getCreated());
                        n.getAttributeValues().addValue(attDegree, ""+count)
                                              .get(atr).setStartDate(snapshot.getCreated());
                    }
                }

*/

    //            for(Node n : graph.getNodes()){
    //                for(Node ns : savedN){
    //                    if(n.getId().equals(ns.getId())){
    //                        if(n.getEdges().size() != ns.getEdges().size()){
    //                            System.out.println("node "+n.getId()+" degree "+n.getEdges().size()+" v case "+snapshot.getCreated());
    //                        }
    ////                        if(n.getAttributeValues().get(0) != ns.getAttributeValues().get(0)){
    ////                            System.out.println("node "+n.getId()+" degree "+n.getAttributeValues().get(0)+" v case "+snapshot.getCreated());
    ////                        }
    //                    }
    //                }
    //            }

//                if(i == 0){                                                     //v prvnim pruchodu tam nic neni, neni s cim porovnavat
//                    ArrayList<Long> remNodes = new ArrayList<Long>();
//                    for(Long node : savedNodes) {                               //hledame uzly co predtim byly a ted nejsou a oznacime je jako konec
//                        if(!pomNodes.contains(node)){
//                            for(Node n: graph.getNodes()){
//                                if(n.getId().equals("n"+node)){
//                                    //savedN.remove(n);
//                                    //n.setEndDate(snapshot.getCreated());
//                                    int atr = n.getAttributeValues().size();
//                                    if(atr > 0){
//                                        n.getAttributeValues().get(atr-1).setEndDate(snapshot.getCreated());
//                                    }
//                                }
//                            }
//                            remNodes.add(node);
//        //                    System.out.println("node "+node+" zrusen "+snapshot.getCreated());
//                        }
//                    }
//
//                    for(Long node : remNodes){
//                        savedNodes.remove(node);
//                    }
//
//
//                    ArrayList<String> remEdges = new ArrayList<String>();           //obdobne procistime hrany
//                    for(String edge : savedEdges) {
//                        if(!pomEdges.contains(edge)){
//                            for(com.ojn.gexf4j.core.Edge e: graph.getAllEdges()){
//                                if((e.getSource().getId()+"-"+e.getTarget().getId()).equals(edge)){
//                                    e.setEndDate(snapshot.getCreated());
//                                    System.out.println("mazu: "+(e.getSource().getId()+"-"+e.getTarget().getId()));
//                                }
//                            }
//                            remEdges.add(edge);
//                            System.out.println("edge "+edge+" zrusen "+snapshot.getCreated());
//                        }
//                    }
//                    for(String edge : remEdges){
//                        savedEdges.remove(edge);
//                    }
//                }
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
