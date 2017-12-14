package org.visualdataweb.vowl.SwingElements.View;

import org.apache.log4j.Logger;
import org.visualdataweb.vowl.languages.LanguageGraphEN;
import org.visualdataweb.vowl.protege.VOWLViewComponent;
import org.visualdataweb.vowl.storage.GraphStorage;
import org.visualdataweb.vowl.types.ColumnNames;
import org.visualdataweb.vowl.types.EdgesType;
import org.visualdataweb.vowl.types.Nodetype;
import org.visualdataweb.vowl.types.PropertyType;
import prefuse.data.Edge;
import prefuse.data.Node;
import prefuse.visual.VisualItem;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EdgeInformationJDialog extends JDialog {

    private static final Logger logger = Logger.getLogger(EdgeInformationJDialog.class);

    String viewManagerID;

    public EdgeInformationJDialog(String id, Node startNode, Node endNode){

        this.viewManagerID = id;

        JLabel label = new JLabel("Name of the Edge");
        final JTextField nameField = new JTextField();
        nameField.setPreferredSize(new Dimension((int)(VOWLViewComponent.screenSize.width*0.1), (int)(VOWLViewComponent.screenSize.height*0.1)));
        JButton button = new JButton("Submit");

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addEdge(startNode, endNode, nameField.getText(), 0);
                dispose();
            }
        });

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(0, 1, 0, (int)(VOWLViewComponent.screenSize.height*0.1)));

        JPanel subPanel = new JPanel();
        subPanel.setLayout(new GridLayout(0, 2, 0, 0));
        subPanel.add(label);
        subPanel.add(nameField);
        mainPanel.add(subPanel);


        subPanel = new JPanel();
        subPanel.setLayout(new GridLayout(0, 2, 0, 0));

        subPanel.add(button);
        button = new JButton("Cancel");
        subPanel.add(button);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        mainPanel.add(subPanel);


        mainPanel.setBorder(new EmptyBorder((int)(VOWLViewComponent.screenSize.height*0.1),(int)(VOWLViewComponent.screenSize.width*0.1),
                (int)(VOWLViewComponent.screenSize.height*0.1),(int)(VOWLViewComponent.screenSize.width*0.1)));

        this.setBounds((int)(VOWLViewComponent.screenSize.width*0.2), (int)(VOWLViewComponent.screenSize.height*0.2),0,0);
        this.setPreferredSize(new Dimension((int)(VOWLViewComponent.screenSize.width*0.5), (int)(VOWLViewComponent.screenSize.height*0.5)));
        this.setMinimumSize(new Dimension((int)(VOWLViewComponent.screenSize.width*0.5), (int)(VOWLViewComponent.screenSize.height*0.5)));
        this.add(mainPanel);
        this.setVisible(true);
    }





    public EdgeInformationJDialog(String id, VisualItem startNode, VisualItem endNode){

        this.viewManagerID = id;

        JLabel label = new JLabel("Name of the Edge");
        final JTextField nameField = new JTextField();
        nameField.setPreferredSize(new Dimension((int)(VOWLViewComponent.screenSize.width*0.1), (int)(VOWLViewComponent.screenSize.height*0.1)));
        JButton button = new JButton("Submit");

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int startNameIndex = startNode.getColumnIndex(ColumnNames.FULL_NAME);
                int startVowlTypeIndex = startNode.getColumnIndex(ColumnNames.NODE_VOWL_TYPE);
                int startRdfsUriIndex = startNode.getColumnIndex(ColumnNames.RDFS_URI);

                String startName = (String) startNode.get(startNameIndex);
                String startVowlType = (String) startNode.get(startVowlTypeIndex);
                String startRdfsUri = (String) startNode.get(startRdfsUriIndex);

                int endNameIndex = startNode.getColumnIndex(ColumnNames.FULL_NAME);
                int endVowlTypeIndex = startNode.getColumnIndex(ColumnNames.NODE_VOWL_TYPE);
                int endRdfsUriIndex = startNode.getColumnIndex(ColumnNames.RDFS_URI);

                String endName = (String) endNode.get(endNameIndex);
                String endVowlType = (String) endNode.get(endVowlTypeIndex);
                String endRdfsUri = (String) endNode.get(endRdfsUriIndex);


                for (int i = 0; i < GraphStorage.getGraph(viewManagerID).getNodeCount(); i++) {
                    Node x = GraphStorage.getGraph(viewManagerID).getNode(i);
                    int startNameIndexSearch = x.getColumnIndex(ColumnNames.FULL_NAME);
                    int startVowlTypeIndexSearch = x.getColumnIndex(ColumnNames.NODE_VOWL_TYPE);
                    int startRdfsUriIndexSearch = x.getColumnIndex(ColumnNames.RDFS_URI);
                    try {
                        String startNameSearch = (String) x.get(startNameIndexSearch);
                        String startVowlTypeSearch = (String) x.get(startVowlTypeIndexSearch);
                        String startRdfsUriSearch = (String) x.get(startRdfsUriIndexSearch);
                        if (startNameSearch.equals(startName) && startVowlTypeSearch.equals(startVowlType) && startRdfsUriSearch.equals(startRdfsUri) ) {
                            for (int j = 0; j < GraphStorage.getGraph(viewManagerID).getNodeCount(); j++) {
                                Node y = GraphStorage.getGraph(viewManagerID).getNode(j);
                                int endNameIndexSearch = y.getColumnIndex(ColumnNames.FULL_NAME);
                                int endVowlTypeIndexSearch = y.getColumnIndex(ColumnNames.NODE_VOWL_TYPE);
                                int endRdfsUriIndexSearch = y.getColumnIndex(ColumnNames.RDFS_URI);


                                try {
                                    String endNameSearch = (String) y.get(endNameIndexSearch);
                                    String endVowlTypeSearch = (String) y.get(endVowlTypeIndexSearch);
                                    String endRdfsUriSearch = (String) y.get(endRdfsUriIndexSearch);
                                    if (endNameSearch.equals(endName) && endVowlTypeSearch.equals(endVowlType) && endRdfsUriSearch.equals(endRdfsUri) ) {
                                        addEdge(x, y, nameField.getText(), 0);
                                        break;
                                    }
                                } catch (NullPointerException npe) {

                                    // no class name? -> nothing to do
                                    logger.warn("possible mistake found: incoming node without name or id or vowl type attribute");
                                }
                            }
                            break;
                        }
                    } catch (NullPointerException npe) {

                        // no class name? -> nothing to do
                        logger.warn("possible mistake found: outgoing - node without name or id or vowl type attribute");
                    }
                }



                dispose();
            }
        });

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(0, 1, 0, (int)(VOWLViewComponent.screenSize.height*0.1)));

        JPanel subPanel = new JPanel();
        subPanel.setLayout(new GridLayout(0, 2, 0, 0));
        subPanel.add(label);
        subPanel.add(nameField);
        mainPanel.add(subPanel);


        subPanel = new JPanel();
        subPanel.setLayout(new GridLayout(0, 2, 0, 0));

        subPanel.add(button);
        button = new JButton("Cancel");
        subPanel.add(button);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        mainPanel.add(subPanel);


        mainPanel.setBorder(new EmptyBorder((int)(VOWLViewComponent.screenSize.height*0.1),(int)(VOWLViewComponent.screenSize.width*0.1),
                (int)(VOWLViewComponent.screenSize.height*0.1),(int)(VOWLViewComponent.screenSize.width*0.1)));

        this.setBounds((int)(VOWLViewComponent.screenSize.width*0.2), (int)(VOWLViewComponent.screenSize.height*0.2),0,0);
        this.setPreferredSize(new Dimension((int)(VOWLViewComponent.screenSize.width*0.5), (int)(VOWLViewComponent.screenSize.height*0.5)));
        this.setMinimumSize(new Dimension((int)(VOWLViewComponent.screenSize.width*0.5), (int)(VOWLViewComponent.screenSize.height*0.5)));
        this.add(mainPanel);
        this.setVisible(true);
    }












    /**
     * Adds a property to a node. This is a private function only used within this class
     * the propertyType defines the text background color (vowl 2)
     *
     * @param n1      the first node
     * @param n2      the second node
     * @param propertyLabel the label of the property
     * @param propertyType  the {@link PropertyType} for type definition
     */
    public void addEdge(Node n1, Node n2, String propertyLabel, int propertyType){
        int edgeID = GraphStorage.getNewID();
        if (n1 != null && n2 != null) {
            Edge edge = GraphStorage.getGraph(viewManagerID).addEdge(n1, n2);
            edge.set(ColumnNames.ID, edgeID);
            edge.setString(ColumnNames.NAME, propertyLabel);
            edge.setString(ColumnNames.FULL_NAME, propertyLabel);
            edge.set(ColumnNames.EDGE_VOWL_TYPE, PropertyType.type[propertyType]);
            edge.set(ColumnNames.TEXT_SIZE, 11);
            edge.set(ColumnNames.TEXT_COLOR_RED, 0);
            edge.set(ColumnNames.TEXT_COLOR_GREEN, 0);
            edge.set(ColumnNames.TEXT_COLOR_BLUE, 0);
            edge.set(ColumnNames.COLOR_RED, 0);
            edge.set(ColumnNames.COLOR_GREEN, 0);
            edge.set(ColumnNames.COLOR_BLUE, 0);
            edge.set(ColumnNames.EDGE_LENGTH, calculateDefaultEdgeLength(n1, n2));
            edge.set(ColumnNames.EDGE_ARROW_TYPE, EdgesType.arrowtype[1]);
            edge.set(ColumnNames.EDGE_LINE_TYPE, EdgesType.linetype[0]);
            switch (propertyType) {
                case (PropertyType.DATATYPE_PROPERTY):
                    // color #99cc66 for text background
                    edge.set(ColumnNames.TEXT_BACKGROUND_COLOR_RED, 153);
                    edge.set(ColumnNames.TEXT_BACKGROUND_COLOR_GREEN, 204);
                    edge.set(ColumnNames.TEXT_BACKGROUND_COLOR_BLUE, 102);
                    break;
                case (PropertyType.OBJECT_PROPERTY):
                    // color #aaccff for text background
                    edge.set(ColumnNames.TEXT_BACKGROUND_COLOR_RED, 170);
                    edge.set(ColumnNames.TEXT_BACKGROUND_COLOR_GREEN, 204);
                    edge.set(ColumnNames.TEXT_BACKGROUND_COLOR_BLUE, 255);
                    break;
                case (PropertyType.PROPERTY):
                    edge.set(ColumnNames.TEXT_BACKGROUND_COLOR_RED, 192);
                    edge.set(ColumnNames.TEXT_BACKGROUND_COLOR_GREEN, 183);
                    edge.set(ColumnNames.TEXT_BACKGROUND_COLOR_BLUE, 235);
                    break;
                case (PropertyType.SUBCLASS):
                    edge.setString(ColumnNames.NAME, LanguageGraphEN.IS_SUB_CLASS_OF);
                    edge.setString(ColumnNames.FULL_NAME, LanguageGraphEN.IS_SUB_CLASS_OF);
                    edge.set(ColumnNames.EDGE_VOWL_TYPE, PropertyType.type[3]);
					/* background color: white
					 * why? avoid the edge bashed through the text */
                    edge.set(ColumnNames.TEXT_BACKGROUND_COLOR_RED, 255);
                    edge.set(ColumnNames.TEXT_BACKGROUND_COLOR_GREEN, 255);
                    edge.set(ColumnNames.TEXT_BACKGROUND_COLOR_BLUE, 255);
                    edge.set(ColumnNames.EDGE_LENGTH, calculateDefaultEdgeLength(n1, n2));
                    edge.set(ColumnNames.EDGE_ARROW_TYPE, EdgesType.arrowtype[2]);
                    edge.set(ColumnNames.EDGE_LINE_TYPE, EdgesType.linetype[1]);
                    break;
                default:
                    // color : #b8d29a
                    edge.set(ColumnNames.TEXT_BACKGROUND_COLOR_RED, 184);
                    edge.set(ColumnNames.TEXT_BACKGROUND_COLOR_GREEN, 210);
                    edge.set(ColumnNames.TEXT_BACKGROUND_COLOR_BLUE, 154);
            }
        } else {
            logger.warn("ERROR : add property failed, node not found");
        }
    }


    public int calculateDefaultEdgeLength(Node n1, Node n2) {
        int n1_h = (Integer) n1.get(n1.getColumnIndex(ColumnNames.NODE_HEIGHT)) / 2;
        int n1_w = (Integer) n1.get(n1.getColumnIndex(ColumnNames.NODE_WIDTH)) / 2;
        // int n2_h = (Integer) n2.get(n2.getColumnIndex(ColumnNames.NODE_HEIGHT))/2;
        // int n2_w = (Integer) n2.get(n2.getColumnIndex(ColumnNames.NODE_WIDTH))/2;
        int n1_size = (int) Math.sqrt(n1_h * n1_h + n1_w * n1_w);
        // int n2_size = (int) Math.sqrt(n2_h*n2_h + n2_w*n2_h);
        // int edge_length = n1_size + n2_size + MIN_EDGE_LENGTH_CLASSES;
        String node2Type = (String) n2.get(n2.getColumnIndex(ColumnNames.NODE_VOWL_TYPE));
        int edge_length;
        if (node2Type != null && (Nodetype.vowltype[3].equals(node2Type)
                || Nodetype.vowltype[2].equals(node2Type)
                || Nodetype.vowltype[1].equals(node2Type))) {
            // edge between classes
            edge_length = n1_size + 200;
        } else {
            // edge between other objects like properties
            edge_length = n1_size + 120;
        }
        return edge_length;
    }





}
