/*
 * Copyright by The Regents of the University of California
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * you may obtain a copy of the License from
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
