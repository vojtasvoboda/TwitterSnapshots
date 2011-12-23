/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package persistence;


import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
/**
 *
 * @author Michal
 */


@XmlRootElement(name="edges")
public class EdgeList {

    private List<Edge> edges;

    public EdgeList(){
        
    }

    public EdgeList(List<Edge> edges) {
	this.edges = edges;
    }

    @XmlElement(name = "edge")
    public List<Edge> getEdges() {
	return edges;
    }

    public void setEdges(List<Edge> edges) {
	this.edges = edges;
    }
}