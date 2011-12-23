package metadata;

import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import persistence.User;
import persistence.UserDAO;

/**
 * Trida pro vraceni vsech uzivatelu
 * @author Bc. Vojtěch Svoboda <svobovo3@fit.cvut.cz>
 */
@XmlRootElement(name = "users")
public class Users {

    private List<User> users = null;

    public Users() {
        UserDAO dao = new UserDAO();
        this.users = dao.getAll();
    }

    @XmlElement(name = "user")
    public List<User> getSnapshots() {
        return this.users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
