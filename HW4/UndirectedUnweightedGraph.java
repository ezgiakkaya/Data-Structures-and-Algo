package code;

import java.util.HashMap;

public class UndirectedUnweightedGraph<V> extends BaseGraph<V> {
  private HashMap<V, UndirectedVertex> vertexMap; // Renamed 'vertices' to 'vertexMap'

  public UndirectedUnweightedGraph() {
    vertexMap = new HashMap<>();
  }

  @Override
  public String toString() {
    return "UndirectedUnweightedGraph with " + vertexMap.size() + " vertices";
  }

  @Override
  public void insertVertex(V v) {
    vertexMap.computeIfAbsent(v, UndirectedVertex::new);
  }

  @Override
  public V removeVertex(V v) {
    UndirectedVertex removedVertex = vertexMap.remove(v);
    if (removedVertex != null) {
      removedVertex.getIncidenceEdges().forEach((adjacent, edge) -> {
        vertexMap.get(adjacent).getIncidenceEdges().remove(v);
      });
      return v;
    }
    return null;
  }

  @Override
  public boolean areAdjacent(V v1, V v2) {
    return vertexMap.containsKey(v1) && vertexMap.get(v1).getIncidenceEdges().containsKey(v2);
  }

  @Override
  public void insertEdge(V source, V target) {
    this.insertEdge(source, target, 0); // Unweighted graph, weight is 0
  }

  @Override
  public void insertEdge(V source, V target, float weight) {
    this.insertVertex(source);
    this.insertVertex(target);

    vertexMap.get(source).getIncidenceEdges().put(target, new Edge(weight, source, target));
    vertexMap.get(target).getIncidenceEdges().put(source, new Edge(weight, target, source));
  }

  @Override
  public boolean removeEdge(V source, V target) {
    boolean removedSource = vertexMap.getOrDefault(source, new UndirectedVertex(source))
        .getIncidenceEdges().remove(target) != null;

    boolean removedTarget = vertexMap.getOrDefault(target, new UndirectedVertex(target))
        .getIncidenceEdges().remove(source) != null;

    return removedSource && removedTarget;
  }

  @Override
  public float getEdgeWeight(V source, V target) {
    Edge edge = vertexMap.getOrDefault(source, new UndirectedVertex(source))
        .getIncidenceEdges().get(target);

    return edge != null ? edge.getWeight() : 0;
  }

  @Override
  public int numVertices() {
    return vertexMap.size();
  }

  @Override
  public Iterable<V> vertices() {
    return vertexMap.keySet();
  }

  @Override
  public int numEdges() {
    return vertexMap.values().stream().mapToInt(v -> v.getIncidenceEdges().size()).sum() / 2;
    // Divide by 2 as each edge is counted twice (once for each vertex)
  }

  @Override
  public boolean isDirected() {
    return false;
  }

  @Override
  public boolean isWeighted() {
    return false;
  }

  @Override
  public int outDegree(V v) {
    return vertexMap.getOrDefault(v, new UndirectedVertex(v)).getIncidenceEdges().size();
  }

  @Override
  public int inDegree(V v) {
    // In an undirected graph, in-degree is the same as out-degree
    return this.outDegree(v);
  }

  @Override
  public Iterable<V> outgoingNeighbors(V v) {
    return vertexMap.getOrDefault(v, new UndirectedVertex(v)).getIncidenceEdges().keySet();
  }

  @Override
  public Iterable<V> incomingNeighbors(V v) {
    // In an undirected graph, incoming neighbors are the same as outgoing neighbors
    return this.outgoingNeighbors(v);
  }
}
