package org.visualdataweb.vowl.SwingElements.Controller;

import org.visualdataweb.vowl.rendering.ControlListener;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class JMenuTripleToInstanceReasoningListener implements ActionListener {


    @Override
    public void actionPerformed(ActionEvent e) {
        ControlListener.tripleSelection = true;
    }
}
