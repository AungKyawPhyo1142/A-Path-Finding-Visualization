package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
import javax.swing.JPanel;
import javax.swing.UIManager;

public class DemoPanel extends JPanel{
 
    // screen setting
    final int maxCol = 20;
    final int maxRow = 10;
    final int nodeSize = 70;
    final int screenWidth = nodeSize * maxCol;
    final int screenHeight = nodeSize * maxRow;
    
    // Nodes
    Node[][]node = new Node[maxCol][maxRow];
    Node startNode;
    Node goalNode;
    Node currentNode;
    ArrayList<Node>openList = new ArrayList<>();
    ArrayList<Node>checkedList = new ArrayList<>();
    
    boolean goalReached=false;
    int step=0;
    
    
    public DemoPanel(){
        
        this.setPreferredSize(new Dimension(screenWidth,screenHeight));
        this.setBackground(Color.black);
        this.setLayout(new GridLayout(maxRow,maxCol));
        
        setLookAndFeel();
        
        // place nodes on panel
        int col=0;
        int row=0;
        
        while(col < maxCol && row < maxRow){
        
            node[col][row] = new Node(col,row);
            this.add(node[col][row]);
            
            col++;
            if(col==maxCol){
                col=0;
                row++;
            }
        }
        
        //set startNode and goalNode
        setStartNode(3,6);
        setGoalNode(15,3);
        
        //set solidNode
        setSolidNode(10,2);
        setSolidNode(10,3);
        setSolidNode(10,4);
        setSolidNode(10,5);
        setSolidNode(10,6);
        setSolidNode(10,7);
        setSolidNode(6,2);
        setSolidNode(7,2);
        setSolidNode(8,2);
        setSolidNode(9,2);        
        setSolidNode(11,7);
        setSolidNode(12,7);
        setSolidNode(12,8);
        setSolidNode(6,1);
        
        // set Costs on Nodes
        setCostsOnNodes();
        
        this.addKeyListener(new KeyHandler(this));
        this.setFocusable(true);
    
    }
    
    //set start node
    private void setStartNode(int col, int row){
        node[col][row].setAsStart();
        startNode = node[col][row];
        currentNode=startNode;
    }
    
    //set goal node
    private void setGoalNode(int col, int row){
        node[col][row].setAsGoal();
        goalNode = node[col][row];        
    }
    
    //set solid node
    private void setSolidNode(int col, int row){
        node[col][row].setAsSolid();
    }
    
    //get cost
    private void getCost(Node node){
        
        // get G-Cost
        int xDistance = Math.abs(node.col-startNode.col);
        int yDistance = Math.abs(node.row-startNode.row);
        node.gCost = xDistance + yDistance;
        
        // get H-Cost
        xDistance = Math.abs(node.col-goalNode.col);
        yDistance = Math.abs(node.row-goalNode.row);
        node.hCost = xDistance + yDistance;
        
        // get F-Cost
        node.fCost = node.gCost+node.hCost;
        
        // display Costs on nodes
        if(node!=startNode && node!=goalNode){
            node.setText("<html>F:"+node.fCost+"<br>G:"+node.gCost+"</html>");
        }
        
    }
    
    private void setCostsOnNodes(){
        int col=0;
        int row=0;
        
        while(col<maxCol && row<maxRow){
            getCost(node[col][row]);
            col++;
            if(col==maxCol){
                col=0;
                row++;
            }
        }
        
    }
    
    // calculate the A*
    public void search(){
        if(goalReached==false){
            int col = currentNode.col;
            int row = currentNode.row;
            
            currentNode.setAsChecked();
            checkedList.add(currentNode);
            openList.remove(currentNode);
            
            // up
            if(row-1>=0){
                openNode(node[col][row-1]);
            }
            
            // left
            if(col-1>=0){
                openNode(node[col-1][row]);
            }
            
            // down
            if(row+1<maxRow){
                openNode(node[col][row+1]);
            }
            
            // right
            if(col+1<maxCol){
                openNode(node[col+1][row]);
            }
            
            // finding th best nodes
            int bestNodeIndex=0;
            int bestNodefCost=999;
            
            for(int i=0;i<openList.size();i++){
                // check if this node is better
                if(openList.get(i).fCost < bestNodefCost){
                    bestNodeIndex=i;
                    bestNodefCost = openList.get(i).fCost;
                }
                
                // if fCost is equal
                else if(openList.get(i).fCost==bestNodefCost){
                    if(openList.get(i).gCost < openList.get(bestNodeIndex).gCost){
                        bestNodeIndex=i;
                    }
                }
            }
            
            // after loop, we have the bestNode for nextStep
            currentNode = openList.get(bestNodeIndex);
            
            if(currentNode==goalNode){
                goalReached=true;
            }
            
        }
    }

    public void autoSearch(){
        
        while(goalReached==false && step<300){
            int col = currentNode.col;
            int row = currentNode.row;
            
            currentNode.setAsChecked();
            checkedList.add(currentNode);
            openList.remove(currentNode);
            
            // up
            if(row-1>=0){
                openNode(node[col][row-1]);
            }
            
            // left
            if(col-1>=0){
                openNode(node[col-1][row]);
            }
            
            // down
            if(row+1<maxRow){
                openNode(node[col][row+1]);
            }
            
            // right
            if(col+1<maxCol){
                openNode(node[col+1][row]);
            }
            
            // finding th best nodes
            int bestNodeIndex=0;
            int bestNodefCost=999;
            
            for(int i=0;i<openList.size();i++){
                // check if this node is better
                if(openList.get(i).fCost < bestNodefCost){
                    bestNodeIndex=i;
                    bestNodefCost = openList.get(i).fCost;
                }
                
                // if fCost is equal
                else if(openList.get(i).fCost==bestNodefCost){
                    if(openList.get(i).gCost < openList.get(bestNodeIndex).gCost){
                        bestNodeIndex=i;
                    }
                }
            }
            
            // after loop, we have the bestNode for nextStep
            currentNode = openList.get(bestNodeIndex);
            
            if(currentNode==goalNode){
                goalReached=true;
                trackPath();
            }
            
            step++;
        }
    }

    
    // backtrack path
    private void trackPath(){
        Node current = goalNode;
        while(current!=startNode){
            current = current.parent;
            if(current!=startNode){
                current.setAsPath();
            }
        }
    }
    
    private void openNode(Node node){
        if(node.open==false && node.checked==false && node.solid==false){
            node.setAsOpen();
            node.parent = currentNode;
            openList.add(node);
        }
    }
    
    // reset all the apple's UI Look and Feel
    public void setLookAndFeel(){
                try {
            UIManager.setLookAndFeel( UIManager.getCrossPlatformLookAndFeelClassName() );
         } catch (Exception e) {
                    e.printStackTrace();
         }

    }
    
    
}
