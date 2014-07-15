package com.adfhomebrew.view.graph.engine;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Stack;
import java.util.TreeSet;

public class GoogleGraphUtil {

    public void createHtml(Node node, ArrayList all, Stack dfsStack) {
        dfsStack.push(node);
        node.visit();

        String[] values = new String[3];
        values[0] = "";
        values[1] = "'";
        values[2] = "";

        values[0] =
            "[{v:'" + Integer.toString(node.vertex) + "',f:'" + Integer.toString(node.vertex) +
            "<div style=\"color:red; font-style:italic\">Node</div>'" + "},";

        TreeSet inEdges = node.inEdges;
        //from each edge, get the from
        int children = 0;

        Iterator<Edge> iter = inEdges.iterator();

        while (iter.hasNext()) {


            Edge edge = iter.next();
            if (edge.from.vertex > -1) {
                values[1] = values[1] + edge.from.vertex;
                children++;
                if (iter.hasNext()) {
                    values[1] = values[1] + ",";
                }
            }

        }


        values[1] = values[1] + "',";
        values[2] = "'Number of parents: " + children + "']";

        all.add(values);

        if (node.isAllAdjacentOutNodeVisted()) {
            dfsStack.pop();
            return;
        }

        Node nextVisitNode = node.nextOutEdge().to;
        createHtml(nextVisitNode, all, dfsStack);
        //[{v:'Mike', f:'Mike<div style="color:red; font-style:italic">President</div>'}, '', 'The President']
    }


    public static String createGoogleGraphVizHtml(Node node, Graph g) {
        ArrayList adjacentList = new ArrayList();
        g.buildAdjacencyMatrix(node, adjacentList, new Stack());

        int size = adjacentList.size();

        boolean[][] dagAdjacencyArray = new boolean[size][size];
        initAdjacencyArray(dagAdjacencyArray);

        for (Iterator<Node> iter = adjacentList.iterator(); iter.hasNext();) {
            Node listNode = iter.next();
            TreeSet outEdges = listNode.outEdges;

            int row = listNode.vertex;
            for (Iterator<Edge> outEdgesIter = outEdges.iterator(); outEdgesIter.hasNext();) {
                int col = outEdgesIter.next().to.vertex;
                dagAdjacencyArray[row - 1][col - 1] = true;
            }
        }

        //printAdjacencyArray(dagAdjacencyArray);
        String url = encodeGoogleGrapghVizReuqestValue(dagAdjacencyArray);
        return url;

    }

    public static void initAdjacencyArray(boolean[][] dagAdjacencyArray) {
        for (int i = 0; i < dagAdjacencyArray.length; i++) {
            for (int j = 0; j < dagAdjacencyArray.length; j++) {
                dagAdjacencyArray[i][j] = false;
            }
        }
    }


    public static String encodeGoogleGrapghVizReuqestValue(boolean[][] dagAdjacencyArray) {
        StringBuffer buff = new StringBuffer("https://chart.googleapis.com/chart?cht=gv&chl=digraph{");

        for (int i = 0; i < dagAdjacencyArray.length - 1; i++) {

            for (int j = 0; j < dagAdjacencyArray.length; j++) {
                if (dagAdjacencyArray[i][j]) {
                    buff.append((i + 1) + "->" + (j + 1) + ";");
                }
            }
            //buff.append(";");
        }
        buff.append("}");

        return buff.toString();
    }
}
