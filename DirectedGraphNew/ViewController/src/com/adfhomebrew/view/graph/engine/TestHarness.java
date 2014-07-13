package com.adfhomebrew.view.graph.engine;

import java.util.Iterator;
import java.util.Scanner;


public class TestHarness {

    public static void main(String[] args) {
        TestHarness th = new TestHarness();
        //th.runNoDataTest();
        th.graphit();      
    }
    
    
    public void graphit(){
        Graph g = getTestGraph();
       // String url = GoogleGraphUtil.createGoogleGraphVizHtml(g.rootNode, g);
       // System.out.println("url: "+url);
    }

    public void runNoDataTest() {
        //create a graph to store path.         
        Graph g = getTestGraph();
        System.out.println("Begin traverse: ");
        g.currentPathStack.push(g.rootNode);
        traverseGraphTest(g.rootNode,g);
        System.out.println("End traverse, path size: "+g.currentPathStack.size());
    }
    
    public void traverseGraphTest(Node node, Graph g) {

        //if there are no outedges, then terminiate program.       
        if (node == null || node.outEdges.isEmpty()) {
            return;
        }
        
        System.out.println("\nCurrent node: "+node.vertex);
        System.out.println("possible foward nodes choices: ");
        
        for (Iterator iter = node.outEdges.iterator(); iter.hasNext(); ) {
            Edge outSelect = (Edge)iter.next();
            System.out.print(outSelect+", ");          
        }
        
        System.out.println("\npossible backward choices: ");
        for (Iterator iter = node.inEdges.iterator(); iter.hasNext(); ) {
            Edge inSelect = (Edge)iter.next();
            System.out.println(inSelect+", ");          
        }
        
        Integer select = getConsoleInput();
        
        Node nextNode = null;
        
        if(select < node.vertex){ // the selection is smaller, meaning its a backwards step.
            System.out.println("stepping back...");
            nextNode =node.getNodeBySelect(select,node.inEdges,false);
            
        }else if(select>node.vertex){
            System.out.println("stepping foward...");
            nextNode =node.getNodeBySelect(select,node.outEdges,true);            
        }else{
            nextNode=node;//use self if invalid or equal input.
        }
        
        g.currentPathStack.push(nextNode);
        g.printCurrentPath();
        traverseGraphTest(nextNode,g);
        
    }
    
    public Graph getTestGraph(){
        
        
        //create a simplest set of nodes
        Node one = new Node(1);
        Node two = new Node(2);
        Node three = new Node(3);
      //  Node four = new Node(4);
        
        //add edges
        one.addEdge(two);
        one.addEdge(three);
       // one.addEdge(four);
        two.addEdge(three);
        //two.addEdge(four);
        //three.addEdge(four);
        
        //add it to the graph
    //    g.addNode(one);
      //  g.addNode(two);
      //  g.addNode(three);
       // g.addNode(four);
    
       Graph g = new Graph(one);
        return g;
    }

    
    public Integer getConsoleInput() {
        Scanner scanner = new Scanner(System.in);
        return Integer.parseInt(scanner.nextLine());
    }

}
