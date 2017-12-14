package org.visualdataweb.vowl.SwingElements.Controller;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.visualdataweb.vowl.SwingElements.View.NodeInformationJDialog;
import org.visualdataweb.vowl.protege.VOWLViewComponent;
import org.visualdataweb.vowl.storage.GraphStorage;
import org.visualdataweb.vowl.types.ColumnNames;
import org.visualdataweb.vowl.types.FontUsed;
import org.visualdataweb.vowl.types.Nodetype;
import prefuse.data.Node;

import javax.swing.*;

import static org.visualdataweb.vowl.languages.LanguageGraphEN.ISEXTERNAL;



public class JMenuNewNodeListener implements ActionListener{

    private String viewManagerID;
    private Node outgoingNode;

    public JMenuNewNodeListener(String id, Node node) {
        this.viewManagerID = id;
        this.outgoingNode = node;
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        NodeInformationJDialog nodeInformationJDialog = new NodeInformationJDialog(viewManagerID, outgoingNode);

    }




}
