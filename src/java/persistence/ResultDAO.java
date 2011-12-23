package persistence;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import javax.jdo.Query;
import java.util.List;
import javax.jdo.PersistenceManager;

/**
 * DAO pro praci se Resulty
 * @author Bc. Vojtěch Svoboda <svobovo3@fel.cvut.cz>
 */
public class ResultDAO {

    public PersistenceManager pm;

    public ResultDAO() {
        try {
            pm = PMF.get().getPersistenceManager();
        } catch(Exception e) {
            System.err.println("ResultDAO: nepodarilo se ziskat PersistenceManagera: " + e.toString());
        }
    }

    /**
     * Ulozi polozku
     * @param polozka
     */
    public void save(Result c) {
        try {
            System.out.println("ResultDAO: pokusime se ulozit Result.");
            pm.makePersistent(c);
            System.out.println("ResultDAO: save() OK!");
        } catch (Exception e) {
            System.out.println("ResultDAO: chyba ulozeni Resultu: " + e.toString());
        } finally {
            // pm.close();
        }
    }

    public void update(Result c) {
        Result res = new Result();
        try {
            //res.setBetweeness(c.getBetweeness());
            res.setBetweeness(c.dejBetweeness());
            res.setClustering(c.dejClustering());
            res.setCreated(c.getCreated());
            res.setDensity(c.dejDensity());
            res.setEmbeddedness(c.dejEmbeddedness());
            res.setErdos(c.dejErdos());
            res.setHomophily(c.dejHomophily());
            res.setOverlap(c.dejOverlap());
            res.setReciprocity(c.dejReciprocity());
            delete(c);
            save(res);
        } catch (Exception e) {
            System.err.println("ResultDAO: update() chyba " + e.toString());
        }
    }

    /**
     * Smazani kategorie
     * @param dodavatel
     */
    public void delete(Result adm) {
        try {
            pm.deletePersistent(adm);
        } catch (Exception e) {
            System.err.println("ResultDAO: delete() chyba " + e.toString());
        } finally {
            //pm.close();
        }
    }

    /**
     * Smazani vseho
     * @param dodavatel
     */
    public void deleteAll() {
        try {
            pm.deletePersistentAll(getAll());
        } catch (Exception e) {
            System.err.println("ResultDAO: deleteAll() chyba " + e.toString());
        } finally {
            //pm.close();
        }
    }

    /**
     * Ziska list vsech kategorii
     * @return List polozek
     */
    public List<Result> getAll() {
        List<Result> list = null;
        try {
            System.out.println("ResultDAO: getAll()");
            Query query = pm.newQuery(Result.class);
            list = (List<Result>) query.execute();
            return list;
        } catch (Exception e) {
            System.err.println("ResultDAO: chyba getAll(): " + e.toString());
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
    public Result get(Key k) {
        Result c;
        try {
            c = pm.getObjectById(Result.class, k);
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
    public Result get(String id) {
        Result c;
        // musime vytvorit klic
        Key k = KeyFactory.createKey(Result.class.getSimpleName(),Long.parseLong(id));

        try {
            c = pm.getObjectById(Result.class, k);
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
