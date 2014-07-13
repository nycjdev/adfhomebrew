package com.adfhomebrew.view.graph.engine;

import java.util.Comparator;

public class EdgeFromComparator implements Comparator{

    public EdgeFromComparator() {
    }

    public int compare(Object o1, Object o2) {
        
        if(o1 instanceof Edge && o2 instanceof Edge){
            Edge o1Edge = (Edge)o1;
            Edge o2Edge = (Edge)o2;
            
            Integer o1EdgeFromInt = Integer.valueOf(o1Edge.from.vertex); 
            Integer o2EdgeFromInt = Integer.valueOf(o2Edge.from.vertex); 
            
            return o1EdgeFromInt.compareTo(o2EdgeFromInt);
        }
        return -1;
        
    }
}
