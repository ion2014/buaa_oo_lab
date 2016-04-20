package CallSystem;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashSet;
import java.util.LinkedList;

/**
 * Project: buaa_oo_lab
 * Created by longx on 2016/4/20.
 */
public class PathFinder {
    private int[] citiMap = new int[6724];
    private Pinpoint[] crossRoads = new Pinpoint[6724];

    public PathFinder() {
        try {
            File fin = new File("map.txt");
            BufferedReader bf = new BufferedReader(new FileReader(fin));
            for (int i = 0; i < 81; i++)
                for (int j = 0; j < 81; j++)
                    citiMap[(i + 1) * 82 + j + 1] = bf.read() - '0';
            for (int i = 0; i < 6724; i++) crossRoads[i] = new Pinpoint(i);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getHeading(int r, int c, int desr, int desc) {
        return crossRoads[(r + 1) * 82 + c + 1].getHead((desr + 1) * 81 + desc + 1);
    }

    private class Pinpoint {
        Integer id;
        HashSet<Integer> headN, headS, headW, headE = new HashSet<Integer>();

        public Pinpoint(int x) {
            id = x;
            generateMap();
        }

        private void generateMap() {
            boolean[] searched = new boolean[6724];
            for (int i = 0; i < 6724; i++) searched[i] = i == id;
            LinkedList<Integer> bfsQ = new LinkedList<Integer>();
            if (id / 82 > 0) {
                bfsQ.add(id - 82);
            }
            if (id % 82 > 0) {
                bfsQ.add(id - 1);
            }
            if (id / 82 < 81) {
                bfsQ.add(id + 82);
            }
            if (id % 82 < 81) {
                bfsQ.add(id + 1);
            }
            while (!bfsQ.isEmpty()) {
                int len = bfsQ.size(), now = 0;
                for (int i = 0; i < len; i++) {
                    now = bfsQ.poll();
                    HashSet<Integer> type;
                    if (headN.contains(now)) type = headN;
                    else if (headE.contains(now)) type = headE;
                    else if (headW.contains(now)) type = headW;
                    else type = headS;
                    if (now / 82 > 0 && !searched[now - 82] && (citiMap[now - 82] & 2) > 0) {
                        searched[now - 82] = true;
                        bfsQ.add(now - 82);
                        type.add(now - 82);
                    }
                    if (now % 82 > 0 && !searched[now - 1] && (citiMap[now - 1] & 1) > 0) {
                        searched[now - 1] = true;
                        bfsQ.add(now - 1);
                        type.add(now - 1);
                    }
                    if (now / 82 < 81 && !searched[now + 82] && (citiMap[now] & 2) > 0) {
                        searched[now + 82] = true;
                        bfsQ.add(now + 82);
                        type.add(now + 82);
                    }
                    if (now % 82 < 81 && !searched[now + 82] && (citiMap[now] & 1) > 0) {
                        searched[now + 1] = true;
                        bfsQ.add(now + 1);
                        type.add(now + 1);
                    }
                }
            }
        }

        public Integer getHead(int des) {
            if (headN.contains(des)) return -82;
            if (headE.contains(des)) return 1;
            if (headW.contains(des)) return -1;
            else return 82;
        }
    }
}
