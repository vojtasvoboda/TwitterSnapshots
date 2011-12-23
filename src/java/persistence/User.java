package persistence;

import javax.xml.bind.annotation.XmlRootElement;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import javax.xml.bind.annotation.XmlAttribute;

/**
 * Trida reperezentujici jednoho uzivatele
 * @author Bc. Vojtěch Svoboda <svobovo3@fit.cvut.cz>
 */
@XmlRootElement
@PersistenceCapable(detachable="true")
public class User {

    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key id;
    // id uzivatele
    @Persistent
    private long user_id;
    // nick uzivatele
    @Persistent
    private String nickname;

    public User() {
    }

    public User(long user_id, String nickname) {
        this.user_id = user_id;
        this.nickname = nickname;
    }

    /*
    public Key getKey() {
        return id;
    }
    */

    public String getKeyWeb() {
        return KeyFactory.keyToString(this.id);
    }

    public void setKey(Key id) {
        this.id = id;
    }

    @XmlAttribute
    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    @XmlAttribute
    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

}
