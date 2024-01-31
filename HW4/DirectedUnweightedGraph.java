package code;

import java.util.HashMap;

public class DirectedUnweightedGraph<V> extends BaseGraph<V> {

  /*
   * YOU CAN ADD ANY FIELDS AND ADDITIONAL METHODS AS YOU LIKE
   *
   */
  private HashMap<V, DirectedVertex> nodeMap;

  public DirectedUnweightedGraph() {
    nodeMap = new HashMap<>();
  }

  @Override
  public String toString() {
    return "Simple Directed Graph with " + numVertices() + " vertices";
  }

  @Override
  public void insertVertex(V vertex) {
    nodeMap.putIfAbsent(vertex, new DirectedVertex(vertex));
    // Renamed v to vertex for clarity
  }

  @Override
  public V removeVertex(V vertex) {
    if (nodeMap.containsKey(vertex)) {
      nodeMap.get(vertex).getIncomingEdges().keySet()
          .forEach(src -> removeEdge(src, vertex));
      nodeMap.get(vertex).getOutgoingEdges().keySet()
          .forEach(dest -> nodeMap.get(dest).getIncomingEdges().remove(vertex));
      nodeMap.remove(vertex);
      return vertex;
    }
    return null;
  }

  @Override
  public boolean areAdjacent(V vertex1, V vertex2) {
    return nodeMap.containsKey(vertex1) && nodeMap.get(vertex1).getOutgoingEdges().containsKey(vertex2);
  }

  @Override
  public void insertEdge(V source, V target) {
    insertVertex(source);
    insertVertex(target);
    nodeMap.get(source).getOutgoingEdges().put(target, new Edge(0, source, target));
    nodeMap.get(target).getIncomingEdges().put(source, new Edge(0, source, target));
  }

  @Override
  public void insertEdge(V source, V target, float weight) {
    insertEdge(source, target); // Ignoring weight as graph is unweighted
  }

  @Override
  public boolean removeEdge(V source, V target) {
    if (!nodeMap.containsKey(source))
      return false;

    if (nodeMap.get(source).getOutgoingEdges().containsKey(target)) {
      nodeMap.get(source).getOutgoingEdges().remove(target);
      nodeMap.get(target).getIncomingEdges().remove(source);
      return true;
    }
    return false;
  }

  @Override
  public float getEdgeWeight(V source, V target) {
    return nodeMap.get(source).getOutgoingEdges().containsKey(target) ? 1 : 0;
  }

  @Override
  public int numVertices() {
    return nodeMap.size();
  }

  @Override
  public Iterable<V> vertices() {
    return nodeMap.keySet();
  }

  @Override
  public int numEdges() {
    return nodeMap.values().stream()
        .mapToInt(v -> v.getOutgoingEdges().size())
        .sum();
  }

  @Override
  public boolean isDirected() {
    return true;
  }

  @Override
  public boolean isWeighted() {
    return false;
  }

  @Override
  public int outDegree(V vertex) {
    return nodeMap.containsKey(vertex) ? nodeMap.get(vertex).getOutgoingEdges().size() : -1;
  }

  @Override
  public int inDegree(V vertex) {
    return nodeMap.containsKey(vertex) ? nodeMap.get(vertex).getIncomingEdges().size() : -1;
  }

  @Override
  public Iterable<V> outgoingNeighbors(V vertex) {
    return nodeMap.containsKey(vertex) ? nodeMap.get(vertex).getOutgoingEdges().keySet() : null;
  }

  @Override
  public Iterable<V> incomingNeighbors(V vertex) {
    return nodeMap.containsKey(vertex) ? nodeMap.get(vertex).getIncomingEdges().keySet() : null;
  }

}