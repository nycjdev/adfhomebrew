package com.adfhomebrew.view.graph.engine;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

public class Graph {

    public Node rootNode = null;
    public Stack currentPathStack = new Stack();

    public Graph(Node root) {
        //all connected
        rootNode = root;
    }


    public void printCurrentPath() {
       // GraphPrinterUtil.printCurrentPath(currentPathStack);
    }

    public void connetNodes(List<Node> allNodes) {
        for (int i = 0; i < allNodes.size(); i++) {
            if (i + 1 < allNodes.size()) {
                allNodes.get(i).addEdge(allNodes.get(i + 1));
            }
        }
    }



    //used for visual graphing, see createGoogleGraphVizHtml in GoogleGraphUtil.
    //problem with this code is if no edges are present, the adjacent matrix is not created.
    public void buildAdjacencyMatrix(Node node, ArrayList matrix, Stack dfsStack) {

        dfsStack.push(node);
        node.visit();
        matrix.add(node);
        if (node.isAllAdjacentOutNodeVisted()) {
            dfsStack.pop();
            return;
        }

        Node nextVisitNode = node.nextOutEdge().to;
        buildAdjacencyMatrix(nextVisitNode, matrix, dfsStack);
    }

    //depth first
    public Node searchDFS(Node startingNode, Node targetNode, Stack dfsStack) {

        if (startingNode.equals(targetNode)) {
            return startingNode;
        }

        dfsStack.push(startingNode);

        if (startingNode.isAllAdjacentOutNodeVisted()) {
            dfsStack.pop();
            return null;
        }

        Node nextVisitNode = startingNode.nextOutEdge().to;
        return searchDFS(nextVisitNode, targetNode, dfsStack);
    }

    //used for insert
    public void incrementSubGraph(Node node, Stack dfsStack) {
        dfsStack.push(node);
        node.vertex++;

        if (node.isAllAdjacentOutNodeVisted()) {
            dfsStack.pop();
            return;
        }

        Node nextVisitNode = node.nextOutEdge().to;
        incrementSubGraph(nextVisitNode, dfsStack);
    }

    public void insertAfterNode(Node node) {

        int newVertex = node.vertex + 1;
        Node newNode = new Node(newVertex);
        //check if node is last node
        if (node.outEdges.isEmpty()) {

            //its empty

            node.addEdge(newNode);
        } else {

            //remove edge

            if (!node.outEdges.isEmpty()) {

                //inserting between nodes
                Edge edge = node.outEdges.first();
                node.outEdges.remove(edge); //removed edge at node source

                Node toNode = edge.to;
                toNode.inEdges.remove(edge);

                node.addEdge(newNode);
                //toNode.vertex = newNode.vertex + 1;
                incrementSubGraph(toNode, new Stack());
                //increment toNode subgragh
                newNode.addEdge(toNode);
            }
            //increment subgraph

            //create new node
            //add edge from node to new node
        }

    }


}
