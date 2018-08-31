import java.util.*;

class MaybeMSTC {

    private static ArrayList<Edge> outputEdgeList = new ArrayList<Edge> ();
    private static Map<String, Vertex> vertexMap = new HashMap<String, Vertex> ();

    public static void main(String[] args) {

        // input data
        registerEdge("a", "b", 2);
        registerEdge("e", "f", 7);
        registerEdge("c", "e", 6);
        registerEdge("c", "i", 11);
        registerEdge("c", "j", 12);
        registerEdge("c", "k", 13);
        registerEdge("g", "f", 8);
        registerEdge("g", "h", 9);
        registerEdge("a", "d", 1);
        registerEdge("c", "d", 5);
        registerEdge("d", "b", 3);
        registerEdge("h", "e", 10);
        registerEdge("b", "c", 4);

        // print output list of edges in original order
        for (Edge edge: outputEdgeList) {
            System.out.println(edge);
        }

    }

    private static void registerEdge (String vertexName1, String vertexName2, Integer weight) {
        Vertex vertex1 = acquireVertex(vertexName1);
        Vertex vertex2 = acquireVertex(vertexName2);
        Edge edge = new Edge(vertex1, vertex2, weight);
        outputEdgeList.add(edge);
        vertex1.addEdge(edge);
        vertex2.addEdge(edge);
        Cluster cluster1 = vertex1.getRootCluster();
        Cluster cluster2 = vertex2.getRootCluster();
        if (cluster1.equals(cluster2)) {
            System.out.println("Cycle "+vertex1.getName()+"|"+vertex2.getName());
            ArrayList<Edge> path = new ArrayList<Edge>();
            path.add(edge);
            path = vertex1.bfs(vertex2.getName(), path);
            removeHeaviestEdge(path);
            return;
        }
        Cluster cluster = new Cluster();
        cluster1.setParent(cluster);
        cluster2.setParent(cluster);
        vertex1.addEdge(edge);
        vertex2.addEdge(edge);
    }

    private static void removeHeaviestEdge (ArrayList<Edge> path) {
        Edge heaviestEdge = path.get(0);
        for (Edge edge: path) {
            if (heaviestEdge.getWeight() < edge.getWeight()) {
                heaviestEdge = edge;
            }
        }
        outputEdgeList.remove(heaviestEdge);
        heaviestEdge.getVertex1().removeEdge(heaviestEdge);
        heaviestEdge.getVertex2().removeEdge(heaviestEdge);
    }

    private static Vertex acquireVertex (String vertexName) {
        if (vertexMap.get(vertexName) == null) {
            vertexMap.put(vertexName, new Vertex(vertexName));
        }
        return vertexMap.get(vertexName);
    }

}

class Edge {

    private Vertex vertex1;
    private Vertex vertex2;
    private Integer weight;

    public Edge(Vertex vertex1, Vertex vertex2, Integer weight) {
        this.vertex1 = vertex1;
        this.vertex2 = vertex2;
        this.weight = weight;
    }

    public Vertex getVertex1() {
        return vertex1;
    }

    public Vertex getVertex2() {
        return vertex2;
    }

    public Integer getWeight() {
        return weight;
    }

    public String toString() {
        return vertex1.getName()+"|"+vertex2.getName()+"|"+weight;
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

class Vertex {

    private String name;
    private Cluster cluster;
    private ArrayList<Edge> edgeList = new ArrayList<Edge>();

    public Vertex (String name) {
        this.name = name;
        this.cluster = new Cluster();
    }

    public String getName() {
        return name;
    }

    public void addEdge (Edge edge) {
        edgeList.add(edge);
    }

    public Cluster getRootCluster () {
        while (cluster.hasParent()) {
            cluster = cluster.getParent();
        }
        return cluster;
    }

    public void removeEdge (Edge edge) {
        edgeList.remove(edge);
    }

    public ArrayList<Edge> bfs (String targetVertex, ArrayList<Edge> path) {
        if(name == targetVertex) {
            return path;
        }
        Integer pathSize = path.size();
        Edge previousEdge = path.get(pathSize - 1);
        for (Edge edge: edgeList) {
            if (edge.equals(previousEdge)) {
                continue;
            }
            path.add(edge);
            Vertex nextVertex = edge.getVertex1();
            if (nextVertex.getName() == name) {
                nextVertex = edge.getVertex2();
            }
            path = nextVertex.bfs(targetVertex, path);
            if (path.size() > pathSize) {
                return path;
            }
        }
        path.remove(previousEdge);
        return path;
    }

}