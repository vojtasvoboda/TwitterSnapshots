package persistence;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import javax.jdo.Query;
import java.util.List;
import javax.jdo.PersistenceManager;

/**
 * DAO pro praci s uzivateli
 * @author Bc. Vojtěch Svoboda <svobovo3@fel.cvut.cz>
 */
public class UserDAO {

    public PersistenceManager pm;

    public UserDAO() {
        pm = PMF.get().getPersistenceManager();
    }

    /**
     * Ulozi polozku
     * @param polozka
     */
    public void save(User c) {
        try {
            pm.makePersistent(c);
        } catch (Exception e) {
            System.err.println("UserDAO: Chyba save: " + e.toString());
        } finally {
            //pm.close();
        }
    }

    /**
     * Smazani kategorie
     * @param dodavatel
     */
    public void delete(User adm) {
        try {
            pm.deletePersistent(adm);
        } catch (Exception e) {
            System.err.println("UserDAO: Chyba delete: " + e.toString());
        } finally {
            //pm.close();
        }
    }

    /**
     * Ziska list vsech kategorii
     * @return List polozek
     */
    public List<User> getAll() {
        List<User> list = null;
        try {
            Query query = pm.newQuery(User.class);
            list = (List<User>) query.execute();
            return list;
        } catch (Exception e) {
            System.err.println("UserDAO: Chyba getAll: " + e.toString());
        } finally {
            // nesmime uzavirat, aby to slo vypsat
            // pm.close();
        }
        return null;
    }

    /**
     * Zjistime uzivatele dle klice
     * @param k
     * @return
     */
    public User get(Key k) {
        User c = null;
        try {
            c = pm.getObjectById(User.class, k);
        } catch (Exception e) {
            System.err.println("UserDAO: Chyba get(Key): " + e.toString());
        } finally {
            // pm.close();
        }
        return c;
    }

    /**
     * Zjistime uzivatele jenom podle id
     * @param id
     * @return
     */
    public User get(String id) {
        User c = null;
        // musime vytvorit klic
        Key k = KeyFactory.createKey(User.class.getSimpleName(),Long.parseLong(id));
        
        try {
            c = pm.getObjectById(User.class, k);
        } catch(Exception e) {
            System.err.println("UserDAO: Chyba get(String): " + e.toString());
        } finally {
            // pm.close();
        }
        return c;
    }

    /**
     * Zavre PersistentManagera
     */
    public void closePM(){
           pm.close();
    }
}
