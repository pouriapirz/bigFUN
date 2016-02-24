package structure;

/**
 * @author pouria
 */
public class Pair implements Comparable<Pair> {
    int qid;
    int vid;

    public Pair(int qid, int vid) {
        this.qid = qid;
        this.vid = vid;
    }

    public int getQId() {
        return qid;
    }

    public int getVId() {
        return vid;
    }

    @Override
    public int hashCode() {
        return qid * vid;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || (this.getClass() != obj.getClass())) {
            return false;
        }
        return ((((Pair) (obj)).getQId() == this.getQId()) && (((Pair) (obj)).getVId() == this.getVId()));
    }

    @Override
    public int compareTo(Pair p) {
        if (qid < p.getQId()) {
            return -1;
        }
        if (qid > p.getQId()) {
            return 1;
        }
        if (vid < p.getVId()) {
            return -1;
        }
        return (vid > p.getVId() ? 1 : 0);
    }

    public String toString() {
        return "(" + qid + ", " + vid + ")";
    }
}
