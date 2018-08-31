import java.util.*;

class MaybeMSTB {

    private static ArrayList<Edge> outputEdgeList = new ArrayList<Edge> ();
    private static Map<String, Cluster> vertexMap = new HashMap<String, Cluster> ();

    public static void main(String[] args) {

        // input data
        registerEdge("a", "b");
        registerEdge("a", "c");
        registerEdge("b", "c"); // cycle
        registerEdge("d", "e");
        registerEdge("e", "f");
        registerEdge("e", "a");
        registerEdge("f", "b"); // cycle

        // print output list of edges in original order
        for (Edge edge: outputEdgeList) {
            System.out.println(edge);
        }

    }

    private static void registerEdge(String vertex1, String vertex2) {
        Edge edge = new Edge(vertex1, vertex2);
        Cluster cluster1 = getRootCluster(vertex1);
        Cluster cluster2 = getRootCluster(vertex2);
        if (cluster1.equals(cluster2)) {
            return;
        }
        Cluster cluster = new Cluster();
        cluster1.setParent(cluster);
        cluster2.setParent(cluster);
        outputEdgeList.add(edge);
    }

    private static Cluster getRootCluster (String vertex) {
        if (vertexMap.get(vertex) == null) {
            vertexMap.put(vertex, new Cluster());
        }
        Cluster cluster = vertexMap.get(vertex);
        while (cluster.hasParent()) {
            cluster = cluster.getParent();
        }
        return cluster;
    }

}

class Edge {

    private String vertex1;
    private String vertex2;

    public Edge(String vertex1, String vertex2) {
        this.vertex1 = vertex1;
        this.vertex2 = vertex2;
    }

    public String getVertex1() {
        return vertex1;
    }

    public String getVertex2() {
        return vertex2;
    }

    public String toString() {
        return vertex1+"|"+vertex2;
    }

}

class Cluster {

    private Cluster parentCluster = null;

    public Cluster () {}

    public Boolean hasParent () {
        return parentCluster != null;
    }

    public Cluster getParent () {
        return parentCluster;
    }

    public void setParent (Cluster parentCluster) {
        this.parentCluster = parentCluster;
    }

}