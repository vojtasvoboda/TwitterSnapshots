package metadata;

import javax.xml.bind.annotation.XmlAttribute;

/**
 * Trida reprezentujici jednoho autora
 * @author Bc. Vojtěch Svoboda <svobovo3@fit.cvut.cz>
 */
public class Author {

    private String name;
    private String city;

    public Author(String name, String city) {
        this.name = name;
        this.city = city;
    }

    public Author() {
    }

    @XmlAttribute
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @XmlAttribute
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
