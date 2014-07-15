package com.adfhomebrew.view.graph.engine;

public class Edge {
    public Node from;
    public Node to;
    public int fowardSelect;
    public int backwardSelect;
    public boolean visited;

    public void setVisited(boolean v) {
        this.visited = v;
    }

    public boolean getVisited() {
        return visited;
    }


    public void setFrom(Node from) {
        this.from = from;
    }

    public Node getFrom() {
        return from;
    }

    public void setTo(Node to) {
        this.to = to;
    }

    public Node getTo() {
        return to;
    }

    public int getFowardSelect() {
        return fowardSelect;
    }

    public int getBackwardSelect() {
        return backwardSelect;
    }


    public Edge(Node from, Node to, int foward_select) {
        this.from = from;
        this.to = to;
        this.fowardSelect = foward_select;
    }

    public Edge(Node from, Node to, int foward_select, int backward_select) {
        this.from = from;
        this.to = to;
        this.fowardSelect = foward_select;
        this.backwardSelect = backward_select;
    }

    public String toString() {
        StringBuffer buff = new StringBuffer();
        buff.append("\n Edge toString(): ");
        buff.append("\n Forward Input: " + fowardSelect + " for ");
        buff.append(" From: " + from);
        buff.append(" To: " + to);

        buff.append("\n Backward Input: " + backwardSelect + " for ");
        buff.append(" From: " + to);
        buff.append(" To: " + from);

        return buff.toString();
    }

    @Override
    public boolean equals(Object obj) {
        Edge e = (Edge) obj;
        return e.from == from && e.to == to;
    }

}

