package org.visualdataweb.vowl.SwingElements.Controller;

import org.visualdataweb.vowl.SwingElements.View.NodeInformationJDialog;
import org.visualdataweb.vowl.rendering.ControlListener;
import prefuse.data.Edge;
import prefuse.visual.VisualItem;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class JMenuExistingNodeListener implements ActionListener {

    private String viewManagerID;
    private VisualItem startNode;
    private VisualItem endNode;

    public JMenuExistingNodeListener(String id, VisualItem startNode, VisualItem endNode) {
        this.viewManagerID = id;
        this.startNode = startNode;
        this.endNode = endNode;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        ControlListener.secondNodeChosen = false;
    }
}
