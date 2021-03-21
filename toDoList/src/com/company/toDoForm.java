package com.company;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class toDoForm {
    private JButton TestButton;
    private JPanel toDoFormView;
    private JList list1;

    public toDoForm(){
        TestButton.addActionListener(new TestButtonClicked());
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }

    private class TestButtonClicked implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            TestDialog temp = new TestDialog();
            temp.pack();
            temp.setVisible(true);
            System.exit(0);
        }
    }
    public static void main(String[] args){
        JFrame frame = new JFrame("toDoForm");
        frame.setContentPane(new toDoForm().toDoFormView);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

    }
}
