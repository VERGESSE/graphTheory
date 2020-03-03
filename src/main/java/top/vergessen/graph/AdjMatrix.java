package top.vergessen.graph;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

// 邻接矩阵 空间O(V^2) 建图时间O(E)
//// 查看两点是否相邻O(1)  查找所有点临边O(V)
public class AdjMatrix {

    private int V; // 最大值的取值
    private int E; // 图的边数
    private int[][] adj;

    public AdjMatrix(String filename){

        File file = new File(filename);
        // 构造邻接矩阵
        try(Scanner scanner = new Scanner(file)){

            V = scanner.nextInt();
            if ( V < 0){
                throw new IllegalArgumentException("V must be non-negative");
            }
            adj = new int[V][V];
            E = scanner.nextInt();
            if ( E < 0){
                throw new IllegalArgumentException("E must be non-negative");
            }
            for (int i = 0; i < E; i++){
                int a = scanner.nextInt();
                validateVertex(a);
                int b = scanner.nextInt();
                validateVertex(b);
                // 简单图 无自环边
                if (a == b)
                    throw new IllegalArgumentException("Self Loop is Detected!");
                // 简单图 无平行边
                if (adj[a][b] == 1)
                    throw new IllegalArgumentException("Parallel Edges are Detected!");
                adj[a][b] = 1;
                adj[b][a] = 1;
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    // 判断值是否合法
    private void validateVertex(int v){
        if (v < 0 || v >= V)
            throw new IllegalArgumentException("vertex " + v + " is invalid");
    }

    // 判断邻接表中是否有某条边
    public boolean hasEdge(int v, int w){
        return adj[v][w] == 1;
    }

    //返回对应节点的所有相邻节点
    public ArrayList<Integer> adj(int v){
        validateVertex(v);
        ArrayList<Integer> res = new ArrayList<>();
        for (int i = 0; i < V; i++){
            if (adj[v][i] == 1)
                res.add(i);
        }
        return res;
    }

    //求一个顶点的度
    private int degree(int v){
        return adj(v).size();
    }

    public int getV() {
        return V;
    }

    public void setV(int v) {
        V = v;
    }

    public int getE() {
        return E;
    }

    public void setE(int e) {
        E = e;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(String.format("V = %d, E = %d\n", V, E));
        for (int i = 0; i < V; i++) {
            for (int j = 0; j < V; j++)
                stringBuilder.append(String.format("%d ", adj[i][j]));
                stringBuilder.append('\n');
        }
        return stringBuilder.toString();
    }

    public static void main(String[] args) {

        AdjMatrix adjMatrix = new AdjMatrix("src/main/g.txt");
        System.out.println(adjMatrix.toString());
    }
}
