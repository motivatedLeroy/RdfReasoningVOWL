package org.visualdataweb.vowl.SwingElements.View;

import org.visualdataweb.vowl.SwingElements.Controller.JMenuExistingNodeListener;
import org.visualdataweb.vowl.SwingElements.Controller.JMenuNewNodeListener;
import org.visualdataweb.vowl.SwingElements.Controller.JMenuTripleToInstanceReasoningListener;
import org.visualdataweb.vowl.protege.VOWLViewComponent;
import prefuse.data.Node;
import prefuse.visual.VisualItem;

import javax.swing.*;
import java.awt.*;

public class NodePopupMenu extends JPopupMenu {

    private VOWLViewComponent vowlViewComponent;
    private String viewManagerID;
    public static VisualItem node2;

    public NodePopupMenu(VOWLViewComponent vowlViewComponent, String id, VisualItem node){

        this.viewManagerID = id;
        this.vowlViewComponent = vowlViewComponent;


        JMenuItem item;
        this.add(item = new JMenuItem("Create \"Relation-between-existing-Classes\" Rule"));
        item.setHorizontalTextPosition(JMenuItem.RIGHT);
        item.addActionListener(new JMenuExistingNodeListener(viewManagerID, node, node2));
        this.add(item = new JMenuItem("Create \"Relation-to-new-Node\" Rule"));
        item.setHorizontalTextPosition(JMenuItem.RIGHT);
        item.addActionListener(new JMenuNewNodeListener(viewManagerID, (Node)node));
        this.add(item = new JMenuItem("Add Triple to Instance Reasoning Session"));
        item.setHorizontalTextPosition(JMenuItem.RIGHT);
        item.addActionListener(new JMenuTripleToInstanceReasoningListener());

        Point reference = vowlViewComponent.getRootPane().getContentPane().getLocationOnScreen();
        int x = MouseInfo.getPointerInfo().getLocation().x-reference.x;
        int y = MouseInfo.getPointerInfo().getLocation().y-reference.y;
        this.show(this.vowlViewComponent,x,y);
    }
}
