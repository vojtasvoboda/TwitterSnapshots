package persistence;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlAttribute;

/**
 * Trida reprezentujici hranu grafu.
 * @author Bc. Vojtěch Svoboda <svobovo3@fit.cvut.cz>
 */
@XmlRootElement(name = "edge")
@PersistenceCapable(detachable="true")
public class Edge {

    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key id;
    // pocatecni uzel (uzivatel)
    @Persistent
    private long fromId;
    // koncovy uzel (uzivatel)
    @Persistent
    private long toId;

    public Edge() {
    }

    public Edge(long fromId, long toId) {
        this.fromId = fromId;
        this.toId = toId;
    }

    public Key getKey() {
        return id;
    }

    //@XmlElement(name = "id")
    @XmlAttribute(name = "id")
    public String getKeyWeb() {
        return KeyFactory.keyToString(this.id);
    }

    public void setKey(Key id) {
        this.id = id;
    }


    //@XmlElement(name = "from")
    @XmlAttribute(name = "from")
    public long getFromId() {
        return fromId;
    }

    public void setFromId(long fromId) {
        this.fromId = fromId;
    }

    //@XmlElement(name = "to")
    @XmlAttribute(name = "to")
    public long getToId() {
        return toId;
    }

    public void setToId(long toId) {
        this.toId = toId;
    }
}
