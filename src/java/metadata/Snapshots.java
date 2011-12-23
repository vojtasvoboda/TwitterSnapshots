package metadata;

import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import persistence.Snapshot;
import persistence.SnapshotDAO;

/**
 * Trida pro vraceni XML kolekce snapshotu
 * @author Bc. Vojtěch Svoboda <svobovo3@fit.cvut.cz>
 */
@XmlRootElement(name = "snapshots")
public class Snapshots {

    private List<Snapshot> snapshots = null;

    public Snapshots() {
        SnapshotDAO dao = new SnapshotDAO();
        this.snapshots = dao.getAll();
    }

    @XmlElement(name = "snapshot")
    public List<Snapshot> getSnapshots() {
        return this.snapshots;
    }

    public void setSnapshots(List<Snapshot> snapshots) {
        this.snapshots = snapshots;
    }
}
