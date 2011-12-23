package metadata;

import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import persistence.Result;
import persistence.ResultDAO;

/**
 * Trida pro vraceni XML kolekce resultu
 * @author Bc. Vojtěch Svoboda <svobovo3@fit.cvut.cz>
 */
@XmlRootElement(name = "results")
public class Results {

    private List<Result> results = null;

    public Results() {
        ResultDAO dao = new ResultDAO();
        this.results = dao.getAll();
    }

    @XmlElement(name = "result")
    public List<Result> getResults() {
        return this.results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }
}
