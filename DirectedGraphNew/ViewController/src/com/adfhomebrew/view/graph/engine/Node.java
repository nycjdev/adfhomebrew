package com.adfhomebrew.view.graph.engine;

import java.io.Serializable;

import java.util.Iterator;
import java.util.TreeSet;


public class Node implements Comparable, Serializable {

    public int vertex;

    public Node(int vertex) {
        this.vertex = vertex;
        inEdges = new TreeSet<Edge>(new EdgeFromComparator());
        outEdges = new TreeSet<Edge>(new EdgeToComparator());
    }


    public void setInEdges(TreeSet<Edge> inEdges) {
        this.inEdges = inEdges;
    }

    public TreeSet<Edge> getInEdges() {
        return inEdges;
    }

    public void setOutEdges(TreeSet<Edge> outEdges) {
        this.outEdges = outEdges;
    }

    public TreeSet<Edge> getOutEdges() {
        return outEdges;
    }
    public TreeSet<Edge> inEdges;
    public TreeSet<Edge> outEdges;
    public boolean _visited;

    public Node() {
        new Node(-1);
    }

    public Node getNodeBySelect(int select, TreeSet edges, boolean foward) {


        if (foward) {
            for (Iterator iter = edges.iterator(); iter.hasNext();) {
                Edge edge = (Edge) iter.next();

                Node toNode = edge.to;

                if (toNode.vertex == select) {
                    return toNode; // found the selected node, return it;
                }
            }
        } else {
            for (Iterator iter = edges.iterator(); iter.hasNext();) {
                Edge edge = (Edge) iter.next();

                Node toNode = edge.from;

                if (toNode.vertex == select) {
                    return toNode; // found the selected node, return it;
                }
            }
        }

        return null; // bad select, nothing to return
    }

    public static void printEdges(Iterator<Edge> iter) {
        System.out.println("Printing inEdges...");
        if (!iter.hasNext()) {
            System.out.println("");
            return;
        }

        Edge edge = iter.next();
        System.out.print(" Edge from: " + edge.from + " to:" + edge.to);
        printEdges(iter);
    }


    public Edge nextOutEdge() {
        if (outEdges.isEmpty()) {
            return null;
        }
        //ordered out edge, will only print one
        return outEdges.first();
    }


    public boolean isAllAdjacentOutNodeVisted() {

        if (outEdges.isEmpty()) {
            return true;
        }

        Iterator<Edge> iter = outEdges.iterator();
        while (iter.hasNext()) {

            Edge outEdge = iter.next();
            if (!outEdge.to._visited) {
                return false;
            }

        }
        return true;
    }

    public Edge addEdge(Node node) {
        return addEdge(node, node.vertex, this.vertex);
    }

    public Edge addEdge(Node node, int forwardSelect) { //forward only

        if (node.vertex < this.vertex) {
            return null; //temp direction check
        }

        Edge e = new Edge(this, node, forwardSelect);
        outEdges.add(e);
        node.inEdges.add(e);

        return e;
    }

    public Edge addEdge(Node node, int forwardSelect, int backwardSelect) { // bidirectional

        Edge e = new Edge(this, node, forwardSelect, backwardSelect);
        outEdges.add(e);
        node.inEdges.add(e);

        return e;
    }


    public int compareTo(Object o) {
        if (o instanceof Node) {
            Integer.valueOf(vertex).compareTo(Integer.valueOf(((Node) o).vertex));
        }
        return 0;
    }

    public void visitDetail() {
        visit();

        printEdges(this.inEdges.iterator());
        printEdges(this.outEdges.iterator());
    }

    public void visit() {
        //System.out.println("Visting: " + this.vertex);
        _visited = true;
    }

    @Override
    public String toString() {
        return Integer.toString(vertex);
    }

    public void setVertex(int vertex) {
        this.vertex = vertex;
    }

    public int getVertex() {
        return vertex;
    }

    public int getUniqueNodeId() {
        System.out.println("getting getUniqueNodeId");
        return vertex;
    }

    public String getNodeLabel() {
        System.out.println("getting getNodeLabel");
        return toString();
    }

}


