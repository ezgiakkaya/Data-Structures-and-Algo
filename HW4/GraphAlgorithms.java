package code;

import java.util.*;

public class GraphAlgorithms<V extends Comparable<V>> {

  private static boolean usageCheck = false;

  public Iterable<V> iterableToSortedIterable(Iterable<V> inIterable) {
    usageCheck = true;
    List<V> sortedList = new ArrayList<>();
    inIterable.forEach(sortedList::add);
    Collections.sort(sortedList);
    return sortedList;
  }

  public List<V> DFS(BaseGraph<V> G, V startVertex) {
    usageCheck = false;
    Set<V> visited = new HashSet<>();
    List<V> traversalOrder = new ArrayList<>();
    depthFirstSearch(G, startVertex, visited, traversalOrder);
    return traversalOrder;
  }

  private void depthFirstSearch(BaseGraph<V> G, V vertex, Set<V> visited, List<V> traversalOrder) {
    visited.add(vertex);
    traversalOrder.add(vertex);

    for (V neighbor : iterableToSortedIterable(G.outgoingNeighbors(vertex))) {
      if (!visited.contains(neighbor)) {
        depthFirstSearch(G, neighbor, visited, traversalOrder);
      }
    }
  }

  public List<V> BFS(BaseGraph<V> G, V startVertex) {
    usageCheck = false;
    Set<V> visited = new HashSet<>();
    List<V> traversalOrder = new ArrayList<>();
    Queue<V> queue = new LinkedList<>();

    queue.add(startVertex);
    visited.add(startVertex);

    while (!queue.isEmpty()) {
      V current = queue.poll();
      traversalOrder.add(current);

      for (V neighbor : iterableToSortedIterable(G.outgoingNeighbors(current))) {
        if (!visited.contains(neighbor)) {
          visited.add(neighbor);
          queue.add(neighbor);
        }
      }
    }

    return traversalOrder;
  }

  public HashMap<V, Float> Dijkstras(BaseGraph<V> G, V startVertex) {
    usageCheck = false;
    HashMap<V, Float> distances = new HashMap<>();
    PriorityQueue<V> queue = new PriorityQueue<>(Comparator.comparing(distances::get));
    distances.put(startVertex, 0.0f);
    queue.add(startVertex);

    while (!queue.isEmpty()) {
      V current = queue.poll();

      for (V neighbor : iterableToSortedIterable(G.outgoingNeighbors(current))) {
        float alternateDist = distances.get(current) + G.getEdgeWeight(current, neighbor);
        if (alternateDist < distances.getOrDefault(neighbor, Float.MAX_VALUE)) {
          distances.put(neighbor, alternateDist);
          queue.add(neighbor);
        }
      }
    }

    return distances;
  }

  public boolean isCyclic(BaseGraph<V> G) {
    Set<V> visited = new HashSet<>();
    Set<V> recursionStack = new HashSet<>();

    for (V vertex : G.vertices()) {
      if (detectCycleDFS(G, vertex, visited, recursionStack)) {
        return true;
      }
    }

    return false;
  }

  private boolean detectCycleDFS(BaseGraph<V> G, V vertex, Set<V> visited, Set<V> recursionStack) {
    if (recursionStack.contains(vertex)) {
      return true;
    }
    if (visited.contains(vertex)) {
      return false;
    }

    visited.add(vertex);
    recursionStack.add(vertex);

    for (V neighbor : iterableToSortedIterable(G.outgoingNeighbors(vertex))) {
      if (detectCycleDFS(G, neighbor, visited, recursionStack)) {
        return true;
      }
    }

    recursionStack.remove(vertex);
    return false;
  }
}
