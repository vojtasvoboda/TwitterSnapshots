/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package persistence;


import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlAttribute;
/**
 *
 * @author Michal
 */
@XmlRootElement(name = "gexf")
public class GexfRoot {

    private GexfGraph graph;

    public GexfRoot()
    {
        
    }

    public GexfRoot(GexfGraph graph) {
	this.graph = graph;
    }

    @XmlAttribute
    public String getXmlns() {
        return "http://www.gexf.net/1.2draft";
    }

    @XmlAttribute
    public String getVersion() {
        return "1.2";
    }

    @XmlElement(name="graph")
    public GexfGraph getGraph() {
	return graph;
    }

    public void setGraph(GexfGraph graph) {
	this.graph = graph;
    }

    

}
