package com.adfhomebrew.view.graph;

import com.adfhomebrew.view.graph.engine.Edge;
import com.adfhomebrew.view.graph.engine.GoogleGraphUtil;
import com.adfhomebrew.view.graph.engine.Graph;
import com.adfhomebrew.view.graph.engine.Node;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Stack;
import java.util.TreeSet;

import javax.faces.application.ViewHandler;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import oracle.adf.view.rich.component.rich.data.RichTable;

import oracle.adf.view.rich.component.rich.input.RichInputText;

import org.apache.myfaces.trinidad.model.CollectionModel;
import org.apache.myfaces.trinidad.model.RowKeySet;

public class DiagramDataModel {
    private List<Node> _nodes;
    private List<Edge> _links;
    private RichInputText numofnodesInput;
    private Graph _graph;
    private Node _currentNode;
    
    public DiagramDataModel() {
        numofnodesInput = new RichInputText();
        numofnodesInput.setSubmittedValue("10");
    }
    
    public void reset() {
        _nodes.clear();
        _links.clear();
        _graph = null;
        _currentNode = null;
    }

    public void simpleTraverse() {

        if (_graph.currentPathStack == null || _graph.currentPathStack.isEmpty()) {
            _graph.currentPathStack.push(_graph.rootNode);
            _currentNode = _graph.rootNode;

        }
        _currentNode = (Node) _graph.currentPathStack.pop();
        if (_currentNode.getOutEdges().iterator().hasNext()) {
            _graph.currentPathStack.push((_currentNode.getOutEdges().iterator().next()).to);
        }
    }

    public void initEdges(Node node, Graph g) {
        ArrayList adjacentList = new ArrayList();
        g.buildAdjacencyMatrix(node, adjacentList, new Stack());

        int size = adjacentList.size();

        boolean[][] dagAdjacencyArray = new boolean[size][size];


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
                _links.add(outEdge);
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

    private int randInt(int min, int max) {
        Random rand = new Random();
        int randomNum = rand.nextInt((max - min) + 1) + min;
        return randomNum;
    }


    public List<Node> getNodes() {
        if (_nodes == null) {
            genGraphHelper();
        }
        return _nodes;
    }


    public List<Edge> getLinks() {
        return _links;
    }


    public void setNumofnodesInput(RichInputText numofnodesInput) {
        this.numofnodesInput = numofnodesInput;
    }

    public RichInputText getNumofnodesInput() {
        return numofnodesInput;
    }

    public void generate(ActionEvent actionEvent) {
        genGraphHelper();
    }


    public Graph genGraphHelper() {
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
            initEdges(_graph.rootNode, _graph);
            return _graph;
        }
        return null;
    }

    public void simpleTraverse(ActionEvent actionEvent) {
        simpleTraverse();
    }

    public Node getCurrentNode() {
        return _currentNode;
    }
}