package top.vergessen.digraph;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

// 拓扑排序（有向无环图才可进行拓扑排序）
public class TopoSort {

    private Graph G;
    private ArrayList<Integer> res;  // 拓扑排序结果
    private boolean hasCycle;        // 是否含有环

    public TopoSort(Graph G){

        if (!G.isDirected())
            throw new IllegalArgumentException("TopoSort only works in directed graph");

        this.G = G;

        res = new ArrayList<>();

        int[] indegrees = new int[G.getV()];
        // 存储入度值为0的顶点
        Queue<Integer> queue = new LinkedList<>();
        for (int v = 0; v < G.getV(); v++){
            indegrees[v] = G.indegree(v);
            // 如果入度值为0，则加入队列
            if (indegrees[v] == 0)
                queue.add(v);
        }

        while (!queue.isEmpty()){

            int cur = queue.remove();
            res.add(cur);

            for (int next :G.adj(cur)){
                // 当前节点相邻节点入度值减一
                indegrees[next] --;
                if (indegrees[next] == 0)
                    queue.add(next);
            }
        }

        if (res.size() != G.getV()){
            hasCycle = true;
            res.clear();
        }
    }

    public ArrayList<Integer> result() {
        return res;
    }

    public boolean hasCycle() {
        return hasCycle;
    }

    public static void main(String[] args) {

        Graph graph = new Graph("src/main/ug.txt", true);
        TopoSort topoSort = new TopoSort(graph);
        System.out.println(topoSort.result());
    }
}
