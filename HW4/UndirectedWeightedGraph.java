package code;

import java.util.HashMap;
import java.util.HashSet;

public class UndirectedWeightedGraph<V> extends BaseGraph<V> {
  private HashMap<V, UndirectedVertex> nodeRegistry; // Renamed 'vertices' to 'nodeRegistry'

  public UndirectedWeightedGraph() {
    this.nodeRegistry = new HashMap<>();
  }

  @Override
  public String toString() {
    return "Graph Type: Undirected Weighted, Vertices: " + nodeRegistry.size();
    // Enhanced string representation
  }

  @Override
  public void insertVertex(V vertex) {
    nodeRegistry.computeIfAbsent(vertex, UndirectedVertex::new);
  }

  @Override
  public V removeVertex(V vertex) {
    UndirectedVertex removedVertex = nodeRegistry.remove(vertex);
    if (removedVertex != null) {
      removedVertex.getIncidenceEdges().keySet().forEach(adjVertex -> {
        nodeRegistry.get(adjVertex).getIncidenceEdges().remove(vertex);
      });
      return vertex;
    }
    return null;
  }

  @Override
  public boolean areAdjacent(V v1, V v2) {
    return nodeRegistry.containsKey(v1) && nodeRegistry.get(v1).getIncidenceEdges().containsKey(v2);
  }

  @Override
  public void insertEdge(V source, V target) {
    insertEdge(source, target, 1.0f); // Default weight for unweighted edges
  }

  @Override
  public void insertEdge(V source, V target, float weight) {
    nodeRegistry.computeIfAbsent(source, UndirectedVertex::new);
    nodeRegistry.computeIfAbsent(target, UndirectedVertex::new);

    Edge newEdge = new Edge(weight, source, target);
    nodeRegistry.get(source).getIncidenceEdges().put(target, newEdge);
    nodeRegistry.get(target).getIncidenceEdges().put(source, newEdge);
  }

  @Override
  public boolean removeEdge(V source, V target) {
    boolean sourceRemoved = nodeRegistry.getOrDefault(source, new UndirectedVertex(source)).getIncidenceEdges()
        .remove(target) != null;
    boolean targetRemoved = nodeRegistry.getOrDefault(target, new UndirectedVertex(target)).getIncidenceEdges()
        .remove(source) != null;
    return sourceRemoved && targetRemoved;
  }

  @Override
  public float getEdgeWeight(V source, V target) {
    Edge edge = nodeRegistry.getOrDefault(source, new UndirectedVertex(source)).getIncidenceEdges().get(target);
    return edge != null ? edge.getWeight() : Float.MAX_VALUE;
  }

  @Override
  public int numVertices() {
    return nodeRegistry.size();
  }

  @Override
  public Iterable<V> vertices() {
    return new HashSet<>(nodeRegistry.keySet());
  }

  @Override
  public int numEdges() {
    return nodeRegistry.values().stream().mapToInt(v -> v.getIncidenceEdges().size()).sum() / 2;
  }

  @Override
  public boolean isDirected() {
    return false;
  }

  @Override
  public boolean isWeighted() {
    return true;
  }

  @Override
  public int outDegree(V vertex) {
    return nodeRegistry.getOrDefault(vertex, new UndirectedVertex(vertex)).getIncidenceEdges().size();
  }

  @Override
  public int inDegree(V vertex) {
    // In an undirected graph, in-degree is the same as out-degree
    return outDegree(vertex);
  }

  @Override
  public Iterable<V> outgoingNeighbors(V vertex) {
    return new HashSet<>(nodeRegistry.getOrDefault(vertex, new UndirectedVertex(vertex)).getIncidenceEdges().keySet());
  }

  @Override
  public Iterable<V> incomingNeighbors(V vertex) {
    // In an undirected graph, incoming neighbors are the same as outgoing neighbors
    return outgoingNeighbors(vertex);
  }
}
