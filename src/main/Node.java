package main;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;

public class Node extends JButton implements ActionListener{

    // each node is a button
    
    // node settings
    Node parent;
    int col;
    int row;
    int gCost,hCost,fCost;
    boolean start;
    boolean goal;
    boolean solid;
    boolean open;
    boolean checked;
    
    public Node(int col, int row){
     
        this.col = col;
        this.row = row;
        
        this.setBackground(Color.white);
        this.setForeground(Color.BLACK);
        this.addActionListener(this);
        
        
    }
    
    public void setAsStart(){
        this.setBackground(Color.BLUE);
        this.setForeground(Color.white);
        this.setText("Start");
        start=true;
    }
    
    public void setAsGoal(){
        this.setBackground(Color.yellow);
        this.setForeground(Color.BLACK);
        this.setText("Goal");
        goal=true;
    }
    
    public void setAsSolid(){
        this.setBackground(Color.BLACK);
        this.setForeground(Color.BLACK);
        solid=true;
    }
    
    public void setAsOpen(){
        open=true;
    }
    
    public void setAsChecked(){
        
        if(start==false && goal==false){
            this.setBackground(Color.ORANGE);
            this.setForeground(Color.black);
        }
        
        checked=true;
    }
    
    public void setAsPath(){
        this.setBackground(Color.GREEN);
        this.setForeground(Color.black);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {    
        this.setBackground(Color.orange);
    }
    
}
