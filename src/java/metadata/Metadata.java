package metadata;

import java.util.Date;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Trida reprezentujici metadatama, ktere pak poskytneme pres API Endpoint
 * @author Bc. Vojtěch Svoboda <svobovo3@fit.cvut.cz>
 */
@XmlRootElement(name = "metadata")
@XmlType(propOrder = {"name", "description", "lastUpdate", "updateInterval", "authors"})
public class Metadata {

    private String name;
    private String description;
    private Date lastUpdate;
    private int updateInterval;
    private AuthorList authors;

    public Metadata(String name, String description, Date lastUpdate, int updateInterval) {
        this.name = name;
        this.description = description;
        this.lastUpdate = lastUpdate;
        this.updateInterval = updateInterval;
        this.authors = new AuthorList();
    }

    public Metadata() {
    }

    public void addAuthor(String name, String city) {
        this.authors.addAuthor(name, city);
    }

    @XmlElement
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlElement
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @XmlElement
    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    @XmlElement
    public int getUpdateInterval() {
        return updateInterval;
    }

    public void setUpdateInterval(int updateInterval) {
        this.updateInterval = updateInterval;
    }

    @XmlElement
    public AuthorList getAuthors() {
        return authors;
    }

    public void setAuthors(AuthorList authors) {
        this.authors = authors;
    }

}
