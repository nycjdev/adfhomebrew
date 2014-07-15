package com.adfhomebrew.view.graph;

import com.adfhomebrew.view.graph.engine.Edge;
import com.adfhomebrew.view.graph.engine.GoogleGraphUtil;
import com.adfhomebrew.view.graph.engine.Graph;
import com.adfhomebrew.view.graph.engine.GraphPrinterUtil;
import com.adfhomebrew.view.graph.engine.Node;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Stack;
import java.util.TreeSet;

import javax.faces.application.ViewHandler;
import javax.faces.component.UIComponent;
import javax.faces.component.UISelectItem;
import javax.faces.component.UISelectItems;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import oracle.adf.view.rich.component.rich.data.RichTable;

import oracle.adf.view.rich.component.rich.input.RichInputText;

import oracle.adf.view.rich.component.rich.input.RichSelectItem;
import oracle.adf.view.rich.component.rich.input.RichSelectManyChoice;

import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;

import org.apache.myfaces.trinidad.model.CollectionModel;
import org.apache.myfaces.trinidad.model.RowKeySet;

public class DirectedAcyclicGraph extends SimpleRandomGraph {

    private RichSelectOneChoice fowardListComp;
    private List fowardList;

    public DirectedAcyclicGraph() {
        super("100");
        fowardListComp = new RichSelectOneChoice();
    }


    public void traverse(ActionEvent actionEvent) {

        if (_currentNode == null) {
            _currentNode = _graph.rootNode;
            updateNextMove();
        } else {
            String value = (String) fowardListComp.getValue();
            if (value != null) {
                _currentNode = _nodes.get(Integer.parseInt(value)); //can also do DFS
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

                //dont connect the last
                //dont connect self
                //don go back
                if ((i == dagAdjacencyArray.length - 1 && j == 0) || i == j || j < i) {

                } else if (randomBool) {
                    dagAdjacencyArray[i][j] = randomBool;
                }
            }
        }
        addEdges();
    }

    public Graph genAdvanceGraphHelper() {
        _nodes = new ArrayList();
        _links = new ArrayList();

        reset();

        if (numofnodesInput != null && numofnodesInput.getValue() != null) {

            int randomMax = randInt(2, Integer.parseInt((String)numofnodesInput.getSubmittedValue()));

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
        if (fowardListComp != null) {
            fowardListComp = new RichSelectOneChoice();
        }
    }

    public void generate(ActionEvent actionEvent) {
        this.genAdvanceGraphHelper();
        updateNextMove();
    }

    public void resetAction(ActionEvent actionEvent) {
        reset();
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
            genAdvanceGraphHelper();
        }
        return _nodes;
    }

}
