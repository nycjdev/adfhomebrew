package com.adfhomebrew.view.graph;

import com.adfhomebrew.view.graph.engine.Edge;
import com.adfhomebrew.view.graph.engine.Graph;

import com.adfhomebrew.view.graph.engine.Node;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;

public class DirectedAcyclicGraph extends SimpleRandomGraph {

    private RichSelectOneChoice fowardListComp;
    private List<SelectItem> fowardList;

    public DirectedAcyclicGraph() {
        super("100");
        fowardListComp = new RichSelectOneChoice();
    }

    public void traverse(ActionEvent actionEvent) {
        if (_currentNode == null) {
            _currentNode = _graph.rootNode;
            _graph.addToCurrentPath(_currentNode, null);
            updateNextMove();
        } else {
            
            String value = (String) fowardListComp.getValue();

            if (value != null) {
                //mark edge visited
                int nextNodeIndex = Integer.parseInt(value);
                Node toNode = _nodes.get(nextNodeIndex); //can also do DFS
   
                _graph.addToCurrentPath(_currentNode, toNode);
                _currentNode = toNode;
                updateNextMove();
            }
        }
    }


    public void updateNextMove() {
        fowardList = new ArrayList();
        //if there are no outedges, then terminiate program.
        if (_currentNode == null || _currentNode.outEdges.isEmpty()) {
            return;
        }

        for (Iterator iter = _currentNode.outEdges.iterator(); iter.hasNext();) {
            Edge outSelect = (Edge) iter.next();
            fowardList.add(new SelectItem(Integer.toString(outSelect.to.vertex),
                                          Integer.toString(outSelect.to.vertex)));
        }
    }

    public void initRandomEdges() {
        for (int i = 0; i < dagAdjacencyArray.length; i++) {
            for (int j = 0; j < dagAdjacencyArray.length; j++) {
                boolean randomBool = randInt(0, 1) == 0 ? false : true;

                if ((i == dagAdjacencyArray.length - 1 && j == 0) || i == j || j < i) {

                    //dont connect last
                    //dont connect self
                    //don go back
                } else if (randomBool) {
                    dagAdjacencyArray[i][j] = randomBool;
                }
            }
        }
        addEdges();
    }

    public Graph initDagGraphHelper() {
        _nodes = new ArrayList();
        _links = new ArrayList();

        reset();

        if (numofnodesInput != null && numofnodesInput.getValue() != null) {

            int randomMax = randInt(2, Integer.parseInt((String) numofnodesInput.getSubmittedValue()));

            for (int i = 0; i < randomMax; i++) {
                Node node = new Node(i);
                _nodes.add(node);
            }
            _graph = new Graph(_nodes.get(0));
            //connect sequential nodes.
            _graph.connetNodes(_nodes);
            initEdges(_graph.rootNode, _graph, false); //dont add links because addRandomEduges will do it
            initRandomEdges();
            return _graph;
        }
        return null;
    }

    public void reset() {
        super.reset();
        if (_graph != null) {
            _currentNode = _graph.rootNode;
        }
        if (fowardList != null) {
            fowardList = new <SelectItem>ArrayList();
            
        }
    }

    public void generate(ActionEvent actionEvent) {
        this.initDagGraphHelper();
        reset();
        updateNextMove();
    }

  
    public List getFowardList() {
        return fowardList;
    }

    public void setFowardListComp(RichSelectOneChoice fowardListComp) {
        this.fowardListComp = fowardListComp;
    }

    public RichSelectOneChoice getFowardListComp() {
        return fowardListComp;
    }

    public List<Node> getNodes() {
        if (_nodes == null) {
            initDagGraphHelper();
            updateNextMove();
        }
        return _nodes;
    }

}
