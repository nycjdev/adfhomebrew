package com.adfhomebrew.view.graph;

import com.adfhomebrew.view.graph.engine.Edge;
import com.adfhomebrew.view.graph.engine.Graph;
import com.adfhomebrew.view.graph.engine.Node;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Stack;
import java.util.TreeSet;

import javax.faces.event.ActionEvent;

import oracle.adf.view.rich.component.rich.input.RichInputText;

public class SimpleRandomGraph {

    protected List<Node> _nodes;
    protected List<Edge> _links;
    protected List<Edge> vistedEdgesList;    
    protected Graph _graph;
    protected Node _currentNode;
    protected boolean[][] dagAdjacencyArray;
    protected RichInputText numofnodesInput;


    public SimpleRandomGraph() {

        numofnodesInput = new RichInputText();
        numofnodesInput.setSubmittedValue("10");
    }

    public SimpleRandomGraph(String maxNodes) {
        numofnodesInput = new RichInputText();
        numofnodesInput.setSubmittedValue(maxNodes);
    }

    public void initEdges(Node node, Graph g, boolean addLinks) {
        ArrayList adjacentList = new ArrayList();
        g.buildAdjacencyMatrix(node, adjacentList, new Stack());

        int size = adjacentList.size();

        dagAdjacencyArray = new boolean[size][size];

        initAdjacencyArray(dagAdjacencyArray);

        for (Iterator<Node> iter = adjacentList.iterator(); iter.hasNext();) {
            Node listNode = iter.next();
            TreeSet outEdges = listNode.outEdges;

            Node fromNode = listNode;
            int from = fromNode.vertex;

            for (Iterator<Edge> outEdgesIter = outEdges.iterator(); outEdgesIter.hasNext();) {
                Edge outEdge = outEdgesIter.next();
                Node toNode = outEdge.to;
                int to = toNode.vertex;

                dagAdjacencyArray[from][to] = true;
                if (addLinks) {
                    _links.add(outEdge);
                }
            }
        }
    }

    public void addEdges() {
        //add edge
        for (int i = 0; i < dagAdjacencyArray.length; i++) {
            for (int j = 0; j < dagAdjacencyArray.length; j++) {
                if (dagAdjacencyArray[i][j]) {
                    Node fromNode = _nodes.get(i);
                    Node toNode = _nodes.get(j);
                    _links.add(fromNode.addEdge(toNode, randInt(1, 100)));
                }
            }
        }
    }

    public void initAdjacencyArray(boolean[][] dagAdjacencyArray) {
        for (int i = 0; i < dagAdjacencyArray.length; i++) {
            for (int j = 0; j < dagAdjacencyArray.length; j++) {
                dagAdjacencyArray[i][j] = false;
            }
        }
    }

    public Graph initGraphHelper() {
        _nodes = new ArrayList();
        _links = new ArrayList();

        reset();

        if (numofnodesInput != null && numofnodesInput.getValue() != null) {
            Long total = Long.parseLong((String) numofnodesInput.getSubmittedValue());

            int randomMax = randInt(2, total.intValue());

            for (int i = 0; i < randomMax; i++) {
                Node node = new Node(i);
                _nodes.add(node);
            }
            
            _graph = new Graph(_nodes.get(0));
            _graph.connetNodes(_nodes);
            initEdges(_graph.rootNode, _graph, true);
            return _graph;
        }
        return null;
    }

    protected int randInt(int min, int max) {
        Random rand = new Random();
        int randomNum = rand.nextInt((max - min) + 1) + min;
        return randomNum;
    }

    public void reset() {
        if (_graph != null) {
            _graph.currentPathStack = new <Node>Stack();
            
            for(Iterator <Edge>iter = _links.iterator(); iter.hasNext();){
                Edge edge = iter.next();
                edge.setVisited(false);
            }
            
        }
    }


    /**getters and setters **/

    public List<Node> getNodes() {
        if (_nodes == null) {
            initGraphHelper();
        }
        return _nodes;
    }

    public List<Edge> getLinks() {
        return _links;
    }

    public void generate(ActionEvent actionEvent) {
        initGraphHelper();
    }

    public Node getCurrentNode() {
        return _currentNode;
    }

    public void traverse(ActionEvent actionEvent) {

        if (_graph.currentPathStack == null || _graph.currentPathStack.isEmpty()) {
            _graph.currentPathStack.push(_graph.rootNode);
            _currentNode = _graph.rootNode;

        }
        _currentNode =_graph.currentPathStack.pop();
        if (_currentNode.getOutEdges().iterator().hasNext()) {
            _graph.currentPathStack.push((_currentNode.getOutEdges().iterator().next()).to);
        }
    }

    public void resetAction(ActionEvent actionEvent) {
        reset();
        // Add event code here...
    }

    public void setNumofnodesInput(RichInputText numofnodesInput) {
        this.numofnodesInput = numofnodesInput;
    }

    public RichInputText getNumofnodesInput() {
        return numofnodesInput;
    }
}
