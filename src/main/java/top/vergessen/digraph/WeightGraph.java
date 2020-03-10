package top.vergessen.digraph;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

// 带权图（支持无向，有向）
public class WeightGraph implements Cloneable{   // 支持深拷贝

    private int V; // 最大值的取值
    private int E; // 图的边数
    private TreeMap<Integer, Integer>[] adj;
    private boolean directed;

    public WeightGraph(String filename, boolean directed){

        this.directed = directed;

        File file = new File(filename);
        // 构造邻接表
        try(Scanner scanner = new Scanner(file)){

            V = scanner.nextInt();
            if ( V < 0){
                throw new IllegalArgumentException("V must be non-negative");
            }
            adj = new TreeMap[V];
            for (int i = 0; i < V; i++)
                adj[i] = new TreeMap<>();

            E = scanner.nextInt();
            if ( E < 0){
                throw new IllegalArgumentException("E must be non-negative");
            }
            for (int i = 0; i < E; i++){
                int a = scanner.nextInt();
                validateVertex(a);
                int b = scanner.nextInt();
                validateVertex(b);
                int weight = scanner.nextInt();
                // 简单图 无自环边
                if (a == b)
                    throw new IllegalArgumentException("Self Loop is Detected!");
                // 简单图 无平行边(如果有多条边，修改为只添加权最小的)
                if (adj[a].containsKey(b))
                    throw new IllegalArgumentException("Parallel Edges are Detected!");
                adj[a].put(b,weight);
                if (!directed)
                    adj[b].put(a,weight);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    // 判断值是否合法
    public void validateVertex(int v){
        if (v < 0 || v >= V)
            throw new IllegalArgumentException("vertex " + v + " is invalid");
    }

    // 默认无向图
    public WeightGraph(String fileName){
        this(fileName,false);
    }

    // 判断是否是有向图
    public boolean isDirected() {
        return directed;
    }

    // 判断邻接表中是否有某条边
    public boolean hasEdge(int v, int w){
        validateVertex(v);
        validateVertex(w);
        return adj[v].containsKey(w);
    }

    //返回对应节点的所有相邻节点
    public Iterable<Integer> adj(int v){
        validateVertex(v);
        return adj[v].keySet();
    }

    // 返回所给边的权值
    public int getWeight(int v, int w){
        if (hasEdge(v,w))
            return adj[v].get(w);
        throw new IllegalArgumentException(String.format("No edge %d-%d ",v,w));
    }

    //求一个顶点的度
//    public int degree(int v){
//        validateVertex(v);
//        return adj[v].size();
//    }

    // 删除一个边
    public void removeEdge(int v, int w){

        validateVertex(v);
        validateVertex(w);

        adj[v].remove(w);
        if (!directed)
            adj[w].remove(v);
    }

    @Override
    public Object clone(){
        try {
            WeightGraph cloned = (WeightGraph)super.clone();
            cloned.adj = new TreeMap[V];
            for (int v = 0; v < V; v++){
                cloned.adj[v] = new TreeMap<>();
                for (Map.Entry<Integer,Integer> entry : adj[v].entrySet())
                    cloned.adj[v].put(entry.getKey(),entry.getValue());
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
        stringBuilder.append(String.format("V = %d, E = %d, directed\n",
                                                        V, E, directed));
        for (int i = 0; i < V; i++) {
            stringBuilder.append(String.format("%d : ",i));
            for (Map.Entry<Integer,Integer> entry : adj[i].entrySet())
                stringBuilder.append(String.format("(%d: %d)",
                        entry.getKey(),entry.getValue()));
            stringBuilder.append('\n');
        }
        return stringBuilder.toString();
    }

    public static void main(String[] args) {

        WeightGraph weightGraph = new WeightGraph("src/main/wg.txt",true);
        System.out.println(weightGraph);
    }
}
