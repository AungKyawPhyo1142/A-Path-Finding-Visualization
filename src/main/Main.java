package main;

import javax.swing.JFrame;

public class Main extends JFrame{
    
    public Main( ) {
        
        super("A* Path Finding Algorithm");
        
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Panel
        DemoPanel panel = new DemoPanel();
        this.add(panel);
        
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        
        
    }
    
    public static void main(String[]args){
        Main main = new Main();
    }
    
}
