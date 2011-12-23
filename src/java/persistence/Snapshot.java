package persistence;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Trida reperezentujici snapshot
 * - obraz jednoho stavu analyzovaneho zdroje
 * @author Bc. Vojtěch Svoboda <svobovo3@fit.cvut.cz>
 */
@XmlRootElement(name = "snapshot")
@XmlType(propOrder = {"created", "nodescount", "edgescount"})
@PersistenceCapable(detachable="true")
public class Snapshot {

    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key id;
    @Persistent
    private Date created;
    @Persistent
    private List<User> nodes;
    @Persistent
    private List<Edge> edges;
    @Persistent
    private int nodescount;
    @Persistent
    private int edgescount;

//    @Persistent
//    private long clustering;

    public Snapshot() {
    }

    public Snapshot(Date created) {
        this.created = created;
        this.nodes = new ArrayList<User>(100);
        this.edges = new ArrayList<Edge>(100);
        this.nodescount = 0;
        this.edgescount = 0;
    }

    public Snapshot(Date created, List<User> nodes, List<Edge> edges) {
        this.created = created;
        this.nodes = nodes;
        this.edges = edges;
        this.nodescount = nodes.size();
        this.edgescount = edges.size();
        this.id = KeyFactory.createKey(Snapshot.class.getSimpleName(), created + "");
    }

    /**
     * Metoda pro pridani uzivatele do grafu
     * @param user
     */
    public void addUser(User user) {
        if (!this.nodes.contains(user)) {
            System.out.println("Snapshot: Uzivatel " + user.getNickname() + " neni pridan, pridame.");
            nodes.add(user);
            this.nodescount++;
        } else {
            System.out.println("Snapshot: Uzivatel " + user.getNickname() + " jiz existuje.");
        }
    }

    /**
     * Metoda pro pridani hrany do grafu
     * @param edge
     */
    public void addEdge(Edge edge) {
        this.edges.add(edge);
        this.edgescount++;
    }

    /*
    public Key getKey() {
        return id;
    }

    public String getKeyWeb() {
        return KeyFactory.keyToString(this.id);
    }

    public void setKey(Key id) {
        this.id = id;
    }
    */

    @XmlAttribute(name = "created")
    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }


    public List<Edge> dejEdges() {
        return edges;
    }

    public void setEdges(List<Edge> edges) {
        this.edges = edges;
    }

    public List<User> dejNodes() {
        return nodes;
    }

    public void setNodes(List<User> nodes) {
        this.nodes = nodes;
    }

    @XmlAttribute(name = "edge")
    public int getEdgescount() {
        return edgescount;
    }

    public void setEdgescount(int edgescount) {
        this.edgescount = edgescount;
    }

    @XmlAttribute(name = "node")
    public int getNodescount() {
        return nodescount;
    }

    public void setNodescount(int nodescount) {
        this.nodescount = nodescount;
    }

    /*
    public long getClustering() {
        return clustering;
    }

    @XmlAttribute(name = "clustering")
    public void setClustering(long clustering) {
        this.clustering = clustering;
    }
     *
     */

}
