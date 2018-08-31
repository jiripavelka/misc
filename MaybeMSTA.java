import java.util.*;

class MaybeMSTA {

    private static ArrayList<Edge> inputEdgeList = new ArrayList<Edge> ();
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

        // sort list of edges according to weight
        Collections.sort(inputEdgeList, new WeightComparator());

        // loop through sorted list of edges
        for (Edge edge: inputEdgeList) {
            Vertex vertex1 = edge.getVertex1();
            Vertex vertex2 = edge.getVertex2();
            ArrayList<Edge> path = new ArrayList<Edge>();
            path.add(edge);
            path = vertex1.bfs(vertex2.getName(), path);
            for (Vertex vertex: vertexMap.values()) {
                vertex.setVisited(false);
            }
            if (path.size() == 0) {
                continue;
            }
            vertex1.removeEdge(edge);
            vertex2.removeEdge(edge);
            outputEdgeList.remove(edge);
        }

        // print output list of edges (in original order)
        System.out.println("Output:");
        for (Edge edge: outputEdgeList) {
            System.out.println(edge);
        }

    }

    private static void registerEdge(String vertexName1, String vertexName2, Integer weight) {
        Vertex vertex1 = acquireVertex(vertexName1);
        Vertex vertex2 = acquireVertex(vertexName2);
        Edge edge = new Edge(vertex1, vertex2, weight);
        vertex1.addEdge(edge);
        vertex2.addEdge(edge);
        inputEdgeList.add(edge);
        outputEdgeList.add(edge);
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

class WeightComparator implements Comparator<Edge> {

    public int compare(Edge edge1, Edge edge2) {
        return edge2.getWeight() - edge1.getWeight(); // decreasing order
    }

}

class Vertex {

    private String name;
    private Boolean visited = false;
    private ArrayList<Edge> edgeList = new ArrayList<Edge>();

    public Vertex (String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void addEdge (Edge edge) {
        edgeList.add(edge);
    }

    public void removeEdge (Edge edge) {
        edgeList.remove(edge);
    }

    public Boolean isVisited () {
        return visited;
    }

    public void setVisited (Boolean visited) {
        this.visited = visited;
    }

    public ArrayList<Edge> bfs (String targetVertex, ArrayList<Edge> path) {
        if(name == targetVertex) {
            return path;
        }
        visited = true;
        Integer pathSize = path.size();
        Edge previousEdge = path.get(pathSize - 1);
        for (Edge edge: edgeList) {
            if (edge.equals(previousEdge)) {
                continue;
            }
            Vertex nextVertex = edge.getVertex1();
            if (nextVertex.getName() == name) {
                nextVertex = edge.getVertex2();
            }
            if (nextVertex.isVisited()) {
                continue;
            }
            path.add(edge);
            path = nextVertex.bfs(targetVertex, path);
            if (path.size() > pathSize) {
                return path;
            }
        }
        path.remove(previousEdge);
        return path;
    }

}
