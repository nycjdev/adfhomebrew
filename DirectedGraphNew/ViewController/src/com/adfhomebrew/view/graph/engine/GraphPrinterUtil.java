package com.adfhomebrew.view.graph.engine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Stack;


public class GraphPrinterUtil {
    
    public static void printCurrentPath(Stack currentPathStack){
        System.out.print("Printing current path: ");
        
        for (Iterator iter = currentPathStack.iterator(); iter.hasNext(); ) {
            Node node = (Node)iter.next();
            System.out.print(node+",");
            
        }
    }
    
    public static String printArrayList(ArrayList list) {

        StringBuffer sb = new StringBuffer();
        Iterator<String[]> iter = list.iterator();

        while (iter.hasNext()) {
            String[] item = iter.next();
            for (int i = 0; i < item.length; i++) {
                sb.append(item[i]);
            }
            if (iter.hasNext()) {
                sb.append(",");
            }
        }

        return sb.toString();
    }
    
    
    
    public static void printAdjacencyArray(boolean[][] dagAdjacencyArray) {
        for (int i = 0; i < dagAdjacencyArray.length; i++) {
            for (int j = 0; j < dagAdjacencyArray.length; j++) {
                System.out.print("[" + dagAdjacencyArray[i][j] + "]");
            }
            System.out.println("");
        }
    }
    
  

    public static void printAllNodesEdges(Node[] allNodes, int index) {
        if (index >= allNodes.length) {
            return;
        }
        Node node = allNodes[index];

        Node.printEdges(node.inEdges.iterator());

        Node.printEdges(node.outEdges.iterator());
        printAllNodes(allNodes, ++index);
    }
    
    public static void printAllNodes(Node[] allNodes, int index) {

        if (index >= allNodes.length) {
            return;
        }
        Node node = allNodes[index];        
        printAllNodes(allNodes, ++index);

    }
    
    public static void printRootNodeWithEdges(Node node, Stack dfsStack) {
        dfsStack.push(node);
        node.visitDetail();
        if (node.isAllAdjacentOutNodeVisted()) {
            dfsStack.pop();
            return;
        }

        Node nextVisitNode = node.nextOutEdge().to;
        printRootNodeWithEdges(nextVisitNode, dfsStack);
    }
    

    public static void printRootNode(Node node, Stack dfsStack) {
        dfsStack.push(node);
        node.visit();
        if (node.isAllAdjacentOutNodeVisted()) {
            dfsStack.pop();
            return;
        }

        Node nextVisitNode = node.nextOutEdge().to;
        printRootNode(nextVisitNode, dfsStack);
    }


}
