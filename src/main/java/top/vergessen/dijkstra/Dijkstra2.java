package top.vergessen.dijkstra;

import top.vergessen.weight.WeightGraph;

import java.util.Arrays;
import java.util.PriorityQueue;

// Dijkstra算法  时间复杂度O(E logE)
public class Dijkstra2 {

    private WeightGraph G;
    private int s;
    private int[] dis;    // 当前节点距离原点的距离
    private boolean[] visited;  // 存储节点到原点的最小值是否已经求出

    // 元组内部类
    private class Node implements Comparable<Node>{

        public int v, dis;

        public Node(int v, int dis){
            this.v = v;
            this.dis = dis;
        }

        @Override
        public int compareTo(Node o) {
            return dis - o.dis;
        }
    }

    private Dijkstra2(WeightGraph G, int s){

        this.G = G;

        G.validateVertex(s);
        this.s = s;

        dis = new int[G.getV()];
        Arrays.fill(dis,Integer.MAX_VALUE);
        dis[s] = 0;

        visited = new boolean[G.getV()];

        // 优先队列优化Dijkstra算法  免去每次找距离原点距离最短节点都需要遍历一遍图
        PriorityQueue<Node> queue = new PriorityQueue<>();
        queue.add(new Node(s,0));
        while (!queue.isEmpty()){

            int cur = queue.remove().v;
            // 除去相同节点重复访问问题
            // 如果节点到原点最小值已经求出，则下一轮循环
            if (visited[cur]) continue;

            visited[cur] = true;
            // 更新与当前距离原点最短节点相邻的所有节点距离的dis
            for (int w : G.adj(cur)){
                if (!visited[w]) {
                    dis[w] = Math.min(dis[cur] + G.getWeight(cur, w), dis[w]);
                    // 可能存进dis不同的相同节点
                    queue.add(new Node(w, dis[w]));
                }
            }
        }
    }

    // 判断当前节点是否与图联通
    public boolean isConnected(int v){
        G.validateVertex(v);
        return visited[v];
    }

    // 返回传入节点与原点的最短距离
    public int disTo(int v){
        G.validateVertex(v);
        return dis[v];
    }

    public static void main(String[] args) {
        WeightGraph weightGraph = new WeightGraph("src/main/g8.txt");
        Dijkstra2 dijkstra = new Dijkstra2(weightGraph,0);
        for (int v = 0; v < weightGraph.getV(); v++)
            System.out.print(dijkstra.disTo(v) + " ");
        System.out.println();
    }
}
