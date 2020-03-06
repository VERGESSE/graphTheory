package top.vergessen.eulerloop;

import top.vergessen.graph.Graph;

public class EulerLoop {

    public Graph G;

    public EulerLoop(Graph G){
        this.G = G;
    }

    public boolean hasEulerLoop(){

        CC cc = new CC(G);
        return true;
    }
}
