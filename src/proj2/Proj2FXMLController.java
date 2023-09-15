/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proj2;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

/**
 * FXML Controller class
 *
 * @author Austin
 */
public class Proj2FXMLController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    private final int BOARDW = 7;
    
    private final int BOARDL = 6;
    
    char playerColor;
    
    private class chip{
        
        char color;

        public chip() {
            this.color = 'e';
            //e for empty
        }

        public void setColor(char color) {
            this.color = color;
        }

        public char getColor() {
            return color;
        } 
        
    }
    
    private chip[][] gameBoard = new chip[BOARDW][BOARDL];
    
    @FXML
    
    private GridPane gameGrid;
    
    private ObservableList<Node> gridNodes;
    
    @FXML
    
    Image redImage = new Image("images/redChip.png");
    
    Image blackImage = new Image("images/blackChip.png");
    
    Image chipImage = redImage;
    
    Image redWinImage = new Image("images/redWinChip.png");
    
    Image blackWinImage = new Image("images/blackWinChip.png");
    
    Image winChip;
    
    @FXML
    Label gameLabel;
    
    @Override

    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        gridNodes = gameGrid.getChildren();
        
        gameLabel.setText("Player 1's turn");
        
        playerColor = 'r';
        
        for(int i = 0; i < BOARDW; i++){
        
            for(int j = 0; j < BOARDL; j++){
            
                chip tempChip = new chip();
                
                gameBoard[i][j] = tempChip;
                
            }
            
        }
        
    }
    
    @FXML
    
    private void handleImageClick(MouseEvent event){
        
        Node clickedItem = (Node)event.getTarget();
        
        int gridIndex = -1;
        
        for(int i = 0; i < gridNodes.size(); i++) {
            if(clickedItem == gridNodes.get(i)) {
                
                if(fullBoard() || (winner() != 'e')){
                
                    emptyBoard();
                    
                    playerColor = 'r';
                    chipImage = redImage;
                    gameLabel.setText("Player 1's turn");
                    
                    break;
                    
                }
                
                if(i%7 == 0){
                
                    gridIndex = columnBottom(0, playerColor) * 7;
                    
                }
                
                if(i%7 == 1){
                
                    gridIndex = (columnBottom(1, playerColor) * 7) + 1;
                    
                }
                
                if(i%7 == 2){
                
                    gridIndex = (columnBottom(2, playerColor) * 7) + 2;
                    
                }
                
                if(i%7 == 3){
                
                    gridIndex = (columnBottom(3, playerColor) * 7) + 3;
                    
                }
                
                if(i%7 == 4){
                
                    gridIndex = (columnBottom(4, playerColor) * 7) + 4;
                    
                }
                
                if(i%7 == 5){
                
                    gridIndex = (columnBottom(5, playerColor) * 7) + 5;
                    
                }
                
                if(i%7 == 6){
                
                    gridIndex = (columnBottom(6, playerColor) * 7) + 6;
                    
                }
                
                
                if(gridIndex < 0){
                
                    gameLabel.setText("Column " + (i%7 + 1) + " full");
                    
                }
                else{
                    
                    ((ImageView)(gameGrid.getChildren().get(gridIndex))).setImage(chipImage);
                    
                    if(chipImage == redImage){
                    
                        playerColor = 'b';
                        chipImage = blackImage;
                        gameLabel.setText("Player 2's turn");
                        
                    }
                    else{
                    
                        playerColor = 'r';
                        chipImage = redImage;
                        gameLabel.setText("Player 1's turn");
                        
                    }
                     
                
                }
                
                if(winner() != 'e'){
                    
                    if(winner() == 'r'){
                        
                        winChip = redWinImage; 
                        
                        winner();
                        
                        gameLabel.setText("Player 1 wins! Click the board to reset.");
                        
                    }
                    else{
                        
                        winChip = blackWinImage; 
                        
                        winner();
                    
                        gameLabel.setText("Player 2 wins! Click the board to reset.");
                        
                    }
                    
                }
                
                if(fullBoard()){
                
                    gameLabel.setText("Game Board full, click it to reset.");
                    
                }
                break;
            }
        }    
        
    }
    
    int columnBottom(int columnInd, char color){
        //returns row id
        
        int i = BOARDL - 1;

        while(gameBoard[columnInd][i].getColor() != 'e'){

            i--;

            if(i < 0){
                //column full

                return -1;

            }

        }

        gameBoard[columnInd][i].setColor(color);
        
        return i;

    }

    public void emptyBoard(){

        for(int i = 0; i < gridNodes.size(); i++){
        
            ((ImageView)(gameGrid.getChildren().get(i))).setImage(null);
            
        }
        
        for(int i = 0; i < BOARDW; i++){
        
            for(int j = 0; j < BOARDL; j++){
            
                (gameBoard[i][j]).setColor('e');
                
            }
            
        }

        }

    public boolean checkHorizontal(int x, int y, char color){
        
            int[] winChips = new int[10];
        
            int checks = 1;

            int i = x;

            i++;
            
            while(i < BOARDW){

                if(gameBoard[i][y].getColor() == color){

                    winChips[checks - 1] = ((y*7) + i);
                    
                    checks++;

                }
                else{
                    
                    break;   
                    
                }

                
                i++;

            }

            i = x;

            i--;
            
            while(i >= 0){

                if(gameBoard[i][y].getColor() == color){

                    winChips[checks - 1] = ((y*7) + i);
                    
                    checks++;

                }
                else
                {
                    
                    break;
                    
                }
                    

                i--;

            }
            
            if(checks >= 4){
            
                ((ImageView)(gameGrid.getChildren().get((y*7) + x))).setImage(winChip);
                
                for(int j = 0; j < checks - 1; j++){
                
                     ((ImageView)(gameGrid.getChildren().get(winChips[j]))).setImage(winChip);
                    
                }
                
            }
            
            return checks >= 4;

    }

    public boolean checkVertical(int x, int y, char color){

        int[] winChips = new int[10];
        
        int checks = 1;

        int i = y;
        
        i++;

        while(i < BOARDL){

            if(gameBoard[x][i].getColor() == color){

                winChips[checks - 1] = ((i*7) + x);
                
                checks++;

            }
            else
                break;

            i++;

        }

        i = y;

        i--;
        
        while(i >= 0){

            if(gameBoard[x][i].getColor() == color){
                
                winChips[checks - 1] = ((i*7) + x);
                
                checks++;

            }
            else
                break;

            i--;

    }
        
        if(checks >= 4){
            
                ((ImageView)(gameGrid.getChildren().get((y*7) + x))).setImage(winChip);
                
                for(int j = 0; j < checks - 1; j++){
                
                     ((ImageView)(gameGrid.getChildren().get(winChips[j]))).setImage(winChip);
                    
                }
                
            }
        
        return checks >= 4;

    }

    public boolean checkDiagonal(int x, int y, char color){

        int[] winChips = new int[10];
        
        int checks = 1;

        int i = x;

        int j = y;
        
        i++;

        j++;
        
        while((i < BOARDW) && (j < BOARDL)){

            if(gameBoard[i][j].getColor() == color){

                winChips[checks - 1] = ((j*7) + i);
                
                    checks++;

            }
            else
                break;

            i++;

            j++;

        }

        i = x;

        j = y;

        i--;
        
        j--;
        
        while((i >= 0) && (j >= 0)){

            if(gameBoard[i][j].getColor() == color){

                winChips[checks - 1] = ((j*7) + i);
                
                    checks++;

            }
            else
                break;

            i--;

            j--;

    }

        if(checks >= 4){
            
            ((ImageView)(gameGrid.getChildren().get((y*7) + x))).setImage(winChip);
                
                for(int k = 0; k < checks - 1; k++){
                    
                     ((ImageView)(gameGrid.getChildren().get(winChips[k]))).setImage(winChip);
                    
                }
            
            return true;

        }

        checks = 1;

        i = x;

        j = y;
        
        i--;
        
        j++;

        while((i >= 0) && (j < BOARDL)){

            if(gameBoard[i][j].getColor() == color){

                winChips[checks - 1] = ((j*7) + i);
                
                    checks++;

            }
            else
                break;

            i--;

            j++;

        }

        i = x;

        j = y;
        
        i++;
        
        j--;

        while((i < BOARDW) && (j >= 0)){

            if(gameBoard[i][j].getColor() == color){

                winChips[checks - 1] = ((j*7) + i);
                
                    checks++;

            }
            else
                break;

            i++;

            j--;

        }

        if(checks >= 4){
            
            ((ImageView)(gameGrid.getChildren().get((y*7) + x))).setImage(winChip);
                
                for(int k = 0; k < checks - 1; k++){
                    
                     ((ImageView)(gameGrid.getChildren().get(winChips[k]))).setImage(winChip);
                    
                }
            
            return true;

        }
        
        return false;

    }

    boolean fullBoard(){

    int x = 0;

    for(int i = 0; i < BOARDW; i++){

        if(gameBoard[i][0].getColor() == 'e' ){

            return false;

        }

    }

    return true;

    }

    public char winner(){

            for(int i = 0; i < BOARDW; i++){

                for(int j = 0; j < BOARDL; j++){
                    
                    if(gameBoard[i][j].getColor() != 'e'){
                    
                        if(checkHorizontal(i, j, gameBoard[i][j].getColor()) == true){
                            
                        return gameBoard[i][j].getColor();

                    }

                    if(checkVertical(i, j, gameBoard[i][j].getColor()) == true){
                        
                        return gameBoard[i][j].getColor();

                    }

                    if(checkDiagonal(i, j, gameBoard[i][j].getColor()) == true){

                        return gameBoard[i][j].getColor();

                    }
                        
                    }
                    
                }

            }

            return 'e';

    }
    
     //@Override

}
    
   

    




