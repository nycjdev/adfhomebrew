package com.adfhomebrew.view.test;

import java.util.ArrayList;
import java.util.List;

public class TestGraphModel {
    private List<SampleNode> _nodes;
    private List<SampleLink> _links;

    public TestGraphModel() {
        //Seed the diagram with three nodes
        _nodes = new ArrayList<SampleNode>(3);
        _nodes.add(new SampleNode(0, "First Node"));
        _nodes.add(new SampleNode(1, "Second Node"));
        _nodes.add(new SampleNode(2, "Third Node"));
        //And links to join each to the next
        _links = new ArrayList<SampleLink>(4);
        _links.add(new SampleLink(0, 1));
        _links.add(new SampleLink(0, 2));
        _links.add(new SampleLink(2, 0));
        
        //_links.add(new SampleLink(1, 2));
        //_links.add(new SampleLink(2, 0));
    }

    public List<SampleNode> getNodes() {
        return _nodes;
    }

    public List<SampleLink> getLinks() {
        return _links;
    }

    public class SampleLink {
        private int _sourceNodeId;
        private int _destinationNodeId;

        public SampleLink(int source, int destination) {
            _sourceNodeId = source;
            _destinationNodeId = destination;
        }

        public int getSourceNodeId() {
            return _sourceNodeId;
        }

        public int getDestinationNodeId() {
            return _destinationNodeId;
        }

    }

    public class SampleNode {
        private int _uniqueNodeId;
        private String _nodeLabel;

        public SampleNode(int id, String label) {
            _uniqueNodeId = id;
            _nodeLabel = label;
        }

        public int getUniqueNodeId() {
            return _uniqueNodeId;
        }

        public String getNodeLabel() {
            return _nodeLabel;
        }
    }
}
