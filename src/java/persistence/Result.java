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
/*
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;
*/
import javax.xml.bind.annotation.*;

/**
 * Trida reperezentujici snapshot
 * - obraz jednoho stavu analyzovaneho zdroje
 * @author Bc. Vojtěch Svoboda <svobovo3@fit.cvut.cz>
 */
@XmlRootElement(name = "result")
@XmlType(propOrder = {"created", "erdos", "clustering", "overlap", "embeddedness", "betweeness", "density", "reciprocity", "homophily"})
@PersistenceCapable(detachable="true")
public class Result {

    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key id;
    @Persistent
    private Date created;    

    @Persistent
    private double erdos;
    @Persistent
    private double clustering;
    @Persistent
    private double overlap;
    @Persistent
    private double embeddedness;
    @Persistent
    private double betweeness;
    @Persistent
    private double density;
    @Persistent
    private double reciprocity;
    @Persistent
    private double homophily;

       
    public Result() {
    }

    public Result(Date created) {
        this.created = created;        
        this.betweeness = 0;
        this.clustering = 0;
        this.density = 0;
        this.embeddedness = 0;
        this.erdos = 0;
        this.homophily = 0;
        this.overlap = 0;
        this.reciprocity = 0;
    }

    public Result(Date created, double erdos, double clustering, double overlap, double embeddedness, double betweeness, double density, double reciprocity, double homophily) {
        this.created = created;                
        this.betweeness = betweeness;
        this.clustering = clustering;
        this.density = density;
        this.embeddedness = embeddedness;
        this.erdos = erdos;
        this.homophily = homophily;
        this.overlap = overlap;
        this.reciprocity = reciprocity;

        this.id = KeyFactory.createKey(Snapshot.class.getSimpleName(), created + "");
    }

    @XmlAttribute(name = "snapshotCreated")
    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }
    
    @XmlElement(name = "metric")
    public Metric getBetweeness() {        
        Metric m = new Metric("betweeness", betweeness);
        return m;
    }
    public double dejBetweeness() {
        return betweeness;
    }

    public void setBetweeness(double betweeness) {
        this.betweeness = betweeness;
    }

    @XmlElement(name = "metric")
    //@XmlAttribute(name = "clustering")
    public Metric getClustering() {
        Metric m = new Metric("clustering", clustering);
        return m;
    }
    public double dejClustering() {
        return clustering;
    }

    public void setClustering(double clustering) {
        this.clustering = clustering;
    }

    @XmlElement(name = "metric")
    //@XmlAttribute(name = "density")
    public Metric getDensity() {
        Metric m = new Metric("density", density);
        return m;
    }
    public double dejDensity() {
        return density;
    }

    public void setDensity(double density) {
        this.density = density;
    }

    @XmlElement(name = "metric")
    //@XmlAttribute(name = "embeddedness")
    public Metric getEmbeddedness() {
        Metric m = new Metric("embeddedness", embeddedness);
        return m;
    }
    public double dejEmbeddedness() {
        return embeddedness;
    }

    public void setEmbeddedness(double embeddedness) {
        this.embeddedness = embeddedness;
    }

    @XmlElement(name = "metric")
    //@XmlAttribute(name = "erdos")
    public Metric getErdos() {
        Metric m = new Metric("erdos", erdos);
        return m;
    }
    public double dejErdos() {
        return erdos;
    }

    public void setErdos(double erdos) {
        this.erdos = erdos;
    }

    @XmlElement(name = "metric")
    //@XmlAttribute(name = "homophily")
    public Metric getHomophily() {
        Metric m = new Metric("homophily", homophily);
        return m;
    }
    public double dejHomophily() {
        return homophily;
    }

    public void setHomophily(double homophily) {
        this.homophily = homophily;
    }

    @XmlElement(name = "metric")
    //@XmlAttribute(name = "overlap")
    public Metric getOverlap() {
        Metric m = new Metric("overlap", overlap);
        return m;
    }
    public double dejOverlap() {
        return overlap;
    }

    public void setOverlap(double overlap) {
        this.overlap = overlap;
    }

    @XmlElement(name = "metric")
    //@XmlAttribute(name = "reciprocity")
    public Metric getReciprocity() {
        Metric m = new Metric("reciprocity", reciprocity);
        return m;
    }
    public double dejReciprocity() {
        return reciprocity;
    }

    public void setReciprocity(double reciprocity) {
        this.reciprocity = reciprocity;
    }


    public String toString()
    {
        return "created "+created+" |erdos "+erdos+ " |clustering "+clustering+ " |overlap "+overlap+ " |embeddedness "+embeddedness+
                " |betweeness "+betweeness+ " |density "+density+ " |reciprocity "+reciprocity+ " |homophily "+homophily;
    }

   

}
