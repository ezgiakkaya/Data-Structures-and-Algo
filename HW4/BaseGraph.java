package code;

import given.iGraph;

import java.util.HashMap;
import java.util.Objects;

/*
 * A class given to you to handle common operations. 
 * Intentionally left empty for you to fill as you like.
 * You do not have to use this at all!
 */

public abstract class BaseGraph<V> implements iGraph<V> {

    /*
     * Fill as you like!
     * 
     */
    protected class Edge {
        float weight;
        V source;
        V destination;

        public Edge(float weight, V source, V destination) {
            this.weight = weight;
            this.destination = destination;
            this.source = source;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o)
                return true;
            if (o == null || getClass() != o.getClass())
                return false;

            Edge otherEdge = (Edge) o;

            if (Float.compare(otherEdge.weight, weight) != 0)
                return false;

            if (!Objects.equals(source, otherEdge.source))
                return false;
            return Objects.equals(destination, otherEdge.destination);
        }

        @Override
        public int hashCode() {
            return Objects.hash(weight, destination, source);
        }

        public float getWeight() {
            return weight;
        }

        public void setWeight(float weight) {
            this.weight = weight;
        }

        public void setSource(V source) {
            this.source = source;
        }

        public void setDestination(V destination) {
            this.destination = destination;
        }

        public V getSource() {
            return source;
        }

        public V getDestination() {
            return destination;
        }

    }

    protected class Vertex {
        V data; // Renamed from 'element' to 'data'

        public Vertex(V data) {
            this.data = data;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o)
                return true;
            if (o == null || getClass() != o.getClass())
                return false;
            Vertex vertex = (Vertex) o;
            return Objects.equals(data, vertex.data);
        }

        @Override
        public int hashCode() {
            return Objects.hash(data);

        }
    }

    protected class UndirectedVertex extends Vertex {
        HashMap<V, Edge> incidence = new HashMap<>();

        public UndirectedVertex(V element) {
            super(element);
        }

        public HashMap<V, Edge> getIncidenceEdges() {
            return incidence;
        }
    }

    protected class DirectedVertex extends Vertex {
        HashMap<V, Edge> outgoing = new HashMap<>();
        HashMap<V, Edge> incoming = new HashMap<>();

        public DirectedVertex(V element) {
            super(element);
        }

        public HashMap<V, Edge> getOutgoingEdges() {
            return outgoing;
        }

        public HashMap<V, Edge> getIncomingEdges() {
            return incoming;
        }
    }

}