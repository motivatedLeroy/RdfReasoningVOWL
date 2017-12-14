package org.visualdataweb.vowl.SwingElements.View;

import org.apache.log4j.Logger;
import org.visualdataweb.vowl.languages.LanguageGraphEN;
import org.visualdataweb.vowl.protege.VOWLViewComponent;
import org.visualdataweb.vowl.storage.GraphStorage;
import org.visualdataweb.vowl.types.*;
import prefuse.data.Edge;
import prefuse.data.Node;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static org.visualdataweb.vowl.languages.LanguageGraphEN.ISEXTERNAL;

public class NodeInformationJDialog extends JDialog {

    private static final Logger logger = Logger.getLogger(NodeInformationJDialog.class);

    String viewManagerID;

    public NodeInformationJDialog(String id, Node node){

        this.viewManagerID = id;

        JLabel label = new JLabel("Name");
        final JTextField name = new JTextField();
        final JTextField comment = new JTextField();
        name.setPreferredSize(new Dimension((int)(VOWLViewComponent.screenSize.width*0.1), (int)(VOWLViewComponent.screenSize.height*0.1)));
        JButton button = new JButton("Submit");

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int nodeID = GraphStorage.getNewID();
                Node endNode = GraphStorage.getGraph(viewManagerID).addNode();
                endNode.set(ColumnNames.CLASS_INSTANCE_COUNT, 0);
                endNode.set(ColumnNames.ID, nodeID);
                endNode.set(ColumnNames.FULL_NAME, name.getText());
                endNode.set(ColumnNames.NODE_FORM, Nodetype.nodetype[0]); // type circle
                endNode.set(ColumnNames.NODE_HEIGHT, 80);
                endNode.set(ColumnNames.NODE_WIDTH, 80);
                endNode.set(ColumnNames.TEXT_SIZE, 11);
                endNode.set(ColumnNames.RDFS_COMMENT, comment.getText());
                endNode.set(ColumnNames.RDFS_URI, "eineURI");
                endNode.set(ColumnNames.RDFS_DEFINED_BY, "definiertVon");
                endNode.set(ColumnNames.OWL_VERSION_INFO, "1.0");
                // vowl type none
                endNode.set(ColumnNames.NODE_VOWL_TYPE, Nodetype.vowltype[0]);
                // rdfs:class #c0b7eb

                endNode.set(ColumnNames.NAME_DATA, getEnoughLineBreaksForSecondLine(name.getText()) + ISEXTERNAL);
                // color #3366cc for imported classes
                endNode.set(ColumnNames.COLOR_RED, 51);
                endNode.set(ColumnNames.COLOR_GREEN, 102);
                endNode.set(ColumnNames.COLOR_BLUE, 0);
                // imported classes have a white text color
                endNode.set(ColumnNames.TEXT_COLOR_RED, 255);
                endNode.set(ColumnNames.TEXT_COLOR_GREEN, 255);
                endNode.set(ColumnNames.TEXT_COLOR_BLUE, 255);
                endNode.set(ColumnNames.NODE_VOWL_TYPE, Nodetype.vowltype[3]);

                endNode.set(ColumnNames.NAME, name.getText());

                int nameIndex = node.getColumnIndex(ColumnNames.FULL_NAME);
                int vowlTypeIndex = node.getColumnIndex(ColumnNames.NODE_VOWL_TYPE);
                int rdfsUriIndex = node.getColumnIndex(ColumnNames.RDFS_URI);
                String name = (String) node.get(nameIndex);
                String vowlType = (String) node.get(vowlTypeIndex);
                String rdfUri = (String) node.get(rdfsUriIndex);
                Node node1 = GraphStorage.getGraph(viewManagerID).getNode(0);


                for (int i = 0; i < GraphStorage.getGraph(viewManagerID).getNodeCount(); i++) {
                    Node startNode = GraphStorage.getGraph(viewManagerID).getNode(i);
                    int nameIndexSearch = startNode.getColumnIndex(ColumnNames.FULL_NAME);
                    int vowlTypeIndexSearch = startNode.getColumnIndex(ColumnNames.NODE_VOWL_TYPE);
                    int rdfsUriIndexSearch = startNode.getColumnIndex(ColumnNames.RDFS_URI);
                    try {
                        String nameSearch = (String) startNode.get(nameIndexSearch);
                        String vowlTypeSearch = (String) startNode.get(vowlTypeIndexSearch);
                        String rdfsUriSearch = (String) startNode.get(rdfsUriIndexSearch);
                        if (nameSearch.equals(name) && vowlTypeSearch.equals(vowlType) && rdfsUriSearch.equals(rdfUri) ) {
                            EdgeInformationJDialog edgeInformationJDialog = new EdgeInformationJDialog(viewManagerID,startNode,endNode);
                            //addEdge(endNode, startNode, "Hallo", 0);
                            break;
                        }
                    } catch (NullPointerException npe) {
                        // no class name? -> nothing to do
                        logger.warn("possible mistake found: node without name or id or vowl type attribute");
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
        subPanel.add(name);
        mainPanel.add(subPanel);

        subPanel = new JPanel();
        subPanel.setLayout(new GridLayout(0, 2, 0, 0));
        label = new JLabel("Comment");
        comment.setPreferredSize(new Dimension((int)(VOWLViewComponent.screenSize.width*0.1), (int)(VOWLViewComponent.screenSize.height*0.1)));
        subPanel.add(label);
        subPanel.add(comment);
        mainPanel.add(subPanel);


        subPanel = new JPanel();
        subPanel.setLayout(new GridLayout(0, 2, 0, 0));

        comment.setPreferredSize(new Dimension((int)(VOWLViewComponent.screenSize.width*0.1), (int)(VOWLViewComponent.screenSize.height*0.1)));
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


    public String getEnoughLineBreaksForSecondLine(String shortName) {
        Canvas c = new Canvas();
        FontMetrics fm = c.getFontMetrics(FontUsed.getFont());
        double hight = fm.getStringBounds(shortName, c.getGraphics()).getHeight();
        int highCounter = 1;
        while (shortName.contains("\n")) {
            shortName = shortName.replaceFirst("\n", "");
            highCounter++;
        }
        hight = hight * highCounter;
        String xDistance = "";
        double xhight = fm.getStringBounds(xDistance, c.getGraphics()).getHeight();
        double xhightOneLine = xhight;
        while (hight >= xhight) {
            xDistance = xDistance + "\n";
            xhight = xhight + xhightOneLine;
        }
        xDistance = xDistance + "\n";
        return xDistance;
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
