/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package persistence;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlAttribute;
/**
 *
 * @author Michal
 */

@XmlRootElement(name = "graph")
//@XmlType(propOrder = {"nodes", "edges"})
public class GexfGraph
{
    private UserList nodes;
    private EdgeList edges;

    public GexfGraph(){
        
    }

    public GexfGraph(UserList nodes, EdgeList edges)
    {
        this.nodes = nodes;
        this.edges = edges;
    }

    public void setNodes(UserList nodes)
    {
        this.nodes = nodes;
    }

    public void setEdges(EdgeList edges)
    {
        this.edges = edges;
    }       

    @XmlElement(name = "nodes")
    public UserList getNodes()
    {
        return nodes;
    }

    @XmlElement(name = "edges")
    public EdgeList getEdges()
    {
        return edges;
    }

    @XmlAttribute(name = "mode")
    public String getMode() {
        return "static";
    }

    @XmlAttribute(name = "defaultedgetype")
    public String getDefaultedgetype() {
        return "directed";
    }
}