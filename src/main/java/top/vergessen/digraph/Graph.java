package top.vergessen.digraph;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.TreeSet;

// 无权图（支持无向，有向）
public class Graph implements Cloneable{   // 支持深拷贝

    private int V; // 最大值的取值
    private int E; // 图的边数
    private TreeSet<Integer>[] adj;
    private boolean directed;
    private int[] indegrees, outdegrees;

    public Graph(String filename, boolean directed){

        this.directed = directed;
        File file = new File(filename);
        // 构造邻接表
        try(Scanner scanner = new Scanner(file)){

            V = scanner.nextInt();
            if ( V < 0){
                throw new IllegalArgumentException("V must be non-negative");
            }
            indegrees = new int[V];
            outdegrees = new int[V];
            adj = new TreeSet[V];
            for (int i = 0; i < V; i++)
                adj[i] = new TreeSet<>();

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
                if (adj[a].contains(b))
                    throw new IllegalArgumentException("Parallel Edges are Detected!");
                adj[a].add(b);

                // 如果是有向图，统计入度和出度
                if (directed){
                    outdegrees[a]++;
                    indegrees[b]++;
                }

                //无向图
                if (!directed)
                    adj[b].add(a);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    // 默认无向图
    public Graph(String fileName){
        this(fileName,false);
    }

    public Graph(TreeSet<Integer>[] adj, boolean directed){

        this.adj = adj;
        this.directed = directed;
        this.V = adj.length;
        this.E = 0;

        indegrees = new int[V];
        outdegrees = new int[V];
        for(int v = 0; v < V; v ++)
            for(int w: adj[v]){
                outdegrees[v] ++;
                indegrees[w] ++;
                this.E ++;
            }

        if(!directed) this.E /= 2;
    }

    // 判断是否是有向图
    public boolean isDirected() {
        return directed;
    }

    // 判断值是否合法
    public void validateVertex(int v){
        if (v < 0 || v >= V)
            throw new IllegalArgumentException("vertex " + v + " is invalid");
    }

    // 判断邻接表中是否有某条边
    public boolean hasEdge(int v, int w){
        validateVertex(v);
        validateVertex(w);
        return adj[v].contains(w);
    }

    //返回对应节点的所有相邻节点
    public Iterable<Integer> adj(int v){
        validateVertex(v);
        return adj[v];
    }

    //求一个顶点的度
    public int degree(int v){
        if (directed)
            throw new RuntimeException("degree only works in undirected graph");
        validateVertex(v);
        return adj[v].size();
    }

    // 返回入度
    public int indegree(int v){
        if (!directed)
            throw new RuntimeException("indegree only works in directed graph");
        validateVertex(v);
        return indegrees[v];
    }

    // 返回出度
    public int outdegree(int v){
        if (!directed)
            throw new RuntimeException("outdegree only works in directed graph");
        validateVertex(v);
        return outdegrees[v];
    }

    // 反转图
    public Graph reverseGraph(){

        TreeSet<Integer>[] rAdj = new TreeSet[V];
        for(int i = 0; i < V; i ++)
            rAdj[i] = new TreeSet<Integer>();

        for(int v = 0; v < V; v ++)
            for(int w : adj(v))
                rAdj[w].add(v);

        return new Graph(rAdj, directed);
    }

    // 删除一个边
    public void removeEdge(int v, int w){

        validateVertex(v);
        validateVertex(w);

        if (adj[v].contains(w)){
            E --;
            if (directed){
                outdegrees[v] --;
                indegrees[w] --;
            }
        }

        adj[v].remove(w);
        if (!directed)
            adj[w].remove(v);
    }

    @Override
    public Object clone(){
        try {
            Graph cloned = (Graph)super.clone();
            cloned.adj = new TreeSet[V];
            for (int v = 0; v < V; v++){
                cloned.adj[v] = new TreeSet<>();
                for (int w : adj[v])
                    cloned.adj[v].add(w);
            }
            return cloned;
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
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
        stringBuilder.append(String.format("V = %d, E = %d, directed = %b\n",
                                                        V, E, directed));
        for (int i = 0; i < V; i++) {
            stringBuilder.append(String.format("%d : ",i));
            for (int w : adj[i])
                stringBuilder.append(String.format("%d ", w));
            stringBuilder.append('\n');
        }
        return stringBuilder.toString();
    }

    public static void main(String[] args) {

        Graph adjList = new Graph("src/main/ug.txt",true);
        System.out.println(adjList);

        for (int v= 0; v < adjList.getV(); v++)
            System.out.println(adjList.indegree(v) + " " + adjList.outdegree(v));
    }
}
