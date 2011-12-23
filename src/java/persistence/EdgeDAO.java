/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package persistence;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import javax.jdo.Query;
import java.util.List;
import javax.jdo.PersistenceManager;
/**
 *
 * @author Michal
 */
public class EdgeDAO {
public PersistenceManager pm;

    public EdgeDAO() {
        pm = PMF.get().getPersistenceManager();
    }

    /**
     * Ulozi polozku
     * @param polozka
     */
    public void save(Edge c) {
        try {
            pm.makePersistent(c);
        } catch (Exception e) {
            System.err.println("EdgeDAO: Chyba save: " + e.toString());
        } finally {
            //pm.close();
        }
    }

    /**
     * Smazani kategorie
     * @param dodavatel
     */
    public void delete(Edge adm) {
        try {
            pm.deletePersistent(adm);
        } catch (Exception e) {
            System.err.println("EdgeDAO: Chyba delete: " + e.toString());
        } finally {
            //pm.close();
        }
    }

    /**
     * Ziska list vsech kategorii
     * @return List polozek
     */
    public List<Edge> getAll() {
        List<Edge> list = null;
        try {
            Query query = pm.newQuery(Edge.class);
            list = (List<Edge>) query.execute();
            return list;
        } catch (Exception e) {
            System.err.println("EdgeDAO: Chyba getAll: " + e.toString());
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
    public Edge get(Key k) {
        Edge c = null;
        try {
            c = pm.getObjectById(Edge.class, k);
        } catch (Exception e) {
            System.err.println("EdgeDAO: Chyba get(Key): " + e.toString());
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
    public Edge get(String id) {
        Edge c = null;
        // musime vytvorit klic
        Key k = KeyFactory.createKey(Edge.class.getSimpleName(),Long.parseLong(id));

        try {
            c = pm.getObjectById(Edge.class, k);
        } catch(Exception e) {
            System.err.println("EdgeDAO: Chyba get(String): " + e.toString());
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
