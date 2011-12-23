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
@XmlRootElement(name="nodes")
public class UserList {
    private List<User> nodes;

    public UserList()
    {
        
    }

    public UserList(List<User> nodes) {
	this.nodes = nodes;
    }

    @XmlElement(name = "node")
    public List<User> getNodes() {
	return nodes;
    }

    public void setNodes(List<User> nodes) {
	this.nodes = nodes;
    }
}
