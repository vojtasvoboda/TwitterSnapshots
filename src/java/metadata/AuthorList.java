package metadata;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Trida reprezentujici list autoru
 * @author Bc. Vojtěch Svoboda <svobovo3@fit.cvut.cz>
 */
@XmlRootElement
public class AuthorList {

    private List<Author> authors;

    public AuthorList() {
        this.authors = new ArrayList<Author>();
    }

    public AuthorList(List<Author> list) {
        this.authors = list;
    }

    public void addAuthor(String name, String city) {
        Author a = new Author(name, city);
        this.authors.add(a);
    }

    @XmlElement(name = "author")
    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> list) {
        this.authors = list;
    }

}
