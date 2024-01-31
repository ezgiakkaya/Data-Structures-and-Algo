package code;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class DirectedWeightedGraph<V> extends BaseGraph<V> {

  private HashMap<V, DirectedVertex> nodeMap; // Renamed 'vertices' to 'nodeMap'

  public DirectedWeightedGraph() {
    this.nodeMap = new HashMap<>();
  }

  @Override
  public String toString() {
    return "DirectedWeightedGraph with " + nodeMap.size() + " vertices";
  }

  @Override
  public void insertVertex(V vertex) {
    this.nodeMap.putIfAbsent(vertex, new DirectedVertex(vertex));
  }

  @Override
  public V removeVertex(V vertex) {
    if (!this.nodeMap.containsKey(vertex)) {
      return null;
    }

    Set<V> affectedVertices = new HashSet<>();
    affectedVertices.addAll(this.nodeMap.get(vertex).getOutgoingEdges().keySet());
    affectedVertices.addAll(this.nodeMap.get(vertex).getIncomingEdges().keySet());

    for (V v : affectedVertices) {
      this.nodeMap.get(v).getOutgoingEdges().remove(vertex);
      this.nodeMap.get(v).getIncomingEdges().remove(vertex);
    }

    this.nodeMap.remove(vertex);
    return vertex;
  }

  @Override
  public boolean areAdjacent(V v1, V v2) {
    DirectedVertex vertex = this.nodeMap.get(v1);
    return vertex != null && vertex.getOutgoingEdges().containsKey(v2);
  }

  @Override
  public void insertEdge(V source, V target) {
    this.insertEdge(source, target, 1.0f); // Default weight
  }

  @Override
  public void insertEdge(V source, V target, float weight) {
    this.insertVertex(source);
    this.insertVertex(target);

    Edge newEdge = new Edge(weight, source, target);
    this.nodeMap.get(source).getOutgoingEdges().put(target, newEdge);
    this.nodeMap.get(target).getIncomingEdges().put(source, newEdge);
  }

  @Override
  public boolean removeEdge(V source, V target) {
    boolean removed = false;

    if (this.nodeMap.containsKey(source) && this.nodeMap.get(source).getOutgoingEdges().containsKey(target)) {
      this.nodeMap.get(source).getOutgoingEdges().remove(target);
      removed = true;
    }

    if (this.nodeMap.containsKey(target) && this.nodeMap.get(target).getIncomingEdges().containsKey(source)) {
      this.nodeMap.get(target).getIncomingEdges().remove(source);
      removed = true;
    }

    return removed;
  }

  @Override
  public float getEdgeWeight(V source, V target) {
    Edge edge = this.nodeMap.get(source).getOutgoingEdges().get(target);
    return (edge != null) ? edge.getWeight() : Float.MAX_VALUE;
  }

  @Override
  public int numVertices() {
    return this.nodeMap.size();
  }

  @Override
  public Iterable<V> vertices() {
    return this.nodeMap.keySet();
  }

  @Override
  public int numEdges() {
    return this.nodeMap.values().stream().mapToInt(v -> v.getOutgoingEdges().size()).sum();
  }

  @Override
  public boolean isDirected() {
    return true;
  }

  @Override
  public boolean isWeighted() {
    return true;
  }

  @Override
  public int outDegree(V vertex) {
    DirectedVertex dv = this.nodeMap.get(vertex);
    return (dv != null) ? dv.getOutgoingEdges().size() : -1;
  }

  @Override
  public int inDegree(V vertex) {
    DirectedVertex dv = this.nodeMap.get(vertex);
    return (dv != null) ? dv.getIncomingEdges().size() : -1;
  }

  @Override
  public Iterable<V> outgoingNeighbors(V vertex) {
    DirectedVertex dv = this.nodeMap.get(vertex);
    return (dv != null) ? dv.getOutgoingEdges().keySet() : new HashSet<>();
  }

  @Override
  public Iterable<V> incomingNeighbors(V vertex) {
    DirectedVertex dv = this.nodeMap.get(vertex);
    return (dv != null) ? dv.getIncomingEdges().keySet() : new HashSet<>();
  }
}
