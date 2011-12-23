package persistence;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import javax.jdo.Query;
import java.util.List;
import javax.jdo.PersistenceManager;

/**
 * DAO pro praci se snapshoty
 * @author Bc. Vojtěch Svoboda <svobovo3@fel.cvut.cz>
 */
public class SnapshotDAO {

    public PersistenceManager pm;

    public SnapshotDAO() {
        try {
            pm = PMF.get().getPersistenceManager();
        } catch(Exception e) {
            System.err.println("SnapshotDAO: nepodarilo se ziskat PersistenceManagera: " + e.toString());
        }
    }

    /**
     * Ulozi polozku
     * @param polozka
     */
    public void save(Snapshot c) {
        try {
            System.out.println("SnapshotDAO: pokusime se ulozit snapshot.");
            pm.makePersistent(c);
            System.out.println("SnapshotDAO: save() OK!");
        } catch (Exception e) {
            System.out.println("SnapshotDAO: chyba ulozeni snapshotu: " + e.toString());
        } finally {
            //pm.close();
        }
    }

    /**
     * Smazani kategorie
     * @param dodavatel
     */
    public void delete(Snapshot adm) {
        try {
            pm.deletePersistent(adm);
        } catch (Exception e) {

        } finally {
            //pm.close();
        }
    }

    /**
     * Ziska list vsech kategorii
     * @return List polozek
     */
    public List<Snapshot> getAll() {
        List<Snapshot> list = null;
        try {
            System.out.println("SnapshotDAO: getAll()");
            Query query = pm.newQuery(Snapshot.class);
            list = (List<Snapshot>) query.execute();
            return list;
        } catch (Exception e) {
            System.err.println("SnapshotDAO: chyba getAll(): " + e.toString());
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
    public Snapshot get(Key k) {
        Snapshot c;
        try {
            c = pm.getObjectById(Snapshot.class, k);
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
    public Snapshot get(String id) {
        Snapshot c;
        // musime vytvorit klic
        Key k = KeyFactory.createKey(Snapshot.class.getSimpleName(),Long.parseLong(id));
        
        try {
            c = pm.getObjectById(Snapshot.class, k);
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
