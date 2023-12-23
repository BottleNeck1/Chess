import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;

/**
 * Chess Game GUI
 * 
 * @author Dvid Martinez
 */
public class ChessUI extends JFrame implements ActionListener {

    /** Grid Board Size */
    private static final int GRID_SIZE = 8;

    /** Window Size */
    private static final int SIZE = 500;

    /** Grid Board JPanel */
    private JPanel board = new JPanel(new GridLayout(GRID_SIZE, GRID_SIZE));

    /** Buttons 2D Array */
    private JButton[][] buttons = new JButton[GRID_SIZE][GRID_SIZE];

    /** Chess Game Class Instance */
    private ChessBoard chessBoard;

    /** Game Starts with white side */
    private boolean isWhiteTurn = true;

    /** Boolean for is user locks a piece to move */
    private boolean pieceChosen = false;

    /** User selected piece to move row */
    private int selectPieceRow;

    /** User selected piece to move column */
    private int selectPieceCol;

    /** Piece Txt Value */
    private static final int PIECE_TXT_SIZE = 20;

    /** RGB Value */
    private static final int RED_VALUE_1 = 225;

    /** RGB Value */
    private static final int GREEN_VALUE_1 = 183;

    /** RGB Value */
    private static final int BLUE_VALUE_1 = 99;

    /** RGB Value */
    private static final int RED_VALUE_2 = 58;

    /** RGB Value */
    private static final int GREEN_VALUE_2 = 48;

    /** RGB Value */
    private static final int BLUE_VALUE_2 = 27;

    /** Top white side row of board */
    private static final int WHITE_TOP_ROW = 6;

    /** Bottom white side row of board */
    private static final int WHITE_BOTTOM_ROW = 7;

    /** ChessUI Contructor */
    public ChessUI() {
        super("Chess");
        setSize(SIZE, SIZE);
        setLocation(100, 100);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        board.setBackground(Color.DARK_GRAY);

        add(board); //add the primary JPanel

        chessBoard = new ChessBoard(); //create instance of ChessBoard Class to handle game

        
        boolean background = true; // used to color everyother button a different board color
        for(int row = 0; row < GRID_SIZE; row++){

            for(int col = 0; col < GRID_SIZE; col++){
                //Define a new button for the buttons array at [row][col]
                buttons[row][col] = new JButton();

                //if piece at [row][col] has image, set the button to have an ImageIcon
                if(chessBoard.getImage(row, col) != null){
                    buttons[row][col].setIcon(new ImageIcon(chessBoard.getImage(row, col)));
                }
                //Align icon to center
                buttons[row][col].setHorizontalTextPosition(SwingConstants.CENTER);
                //buttons[row][col].setFont(new Font("Default", Font.BOLD, PIECE_TXT_SIZE));
                //Add simple line border to each button
                buttons[row][col].setBorder(new LineBorder(Color.BLACK, 1));
                buttons[row][col].setFocusPainted(false); // remove focus box
                buttons[row][col].addActionListener(this); // make button usable
                board.add(buttons[row][col]); // add JButton to primary JPanel


                // if(row == 0 || row == 1){
                //     buttons[row][col].setForeground(Color.BLACK);
                // }
                // else if(row == WHITE_TOP_ROW || row == WHITE_BOTTOM_ROW){
                //     buttons[row][col].setForeground(Color.WHITE);
                // }

                //every other button is set to a color
                if(background){
                    buttons[row][col].setBackground(
                        new Color(RED_VALUE_1, GREEN_VALUE_1, BLUE_VALUE_1));
                }
                else {
                    buttons[row][col].setBackground(
                        new Color(RED_VALUE_2, GREEN_VALUE_2, BLUE_VALUE_2));
                }
                background = !background;
            }
            background = !background;
        }

        //make GUI visible to user
        setVisible(true);
    }

    /**
     * Performs specific action(s) based on the event that occurs
     * 
     * @param e event that occurred
     */
    public void actionPerformed(ActionEvent e) {
        
        //Iterates 2D buttons array to find the index for the pressed button
        for(int row = 0; row < GRID_SIZE; row++){

            for(int col = 0; col < GRID_SIZE; col++){

                if(buttons[row][col] == e.getSource()){ //row and col are now known                
    
                    if(!pieceChosen){ //user has not selected a piece to move

                        //checks if the piece is able to be moved by player1/player2
                        if(chessBoard.canSelect(row, col, isWhiteTurn)) { break; }
                        
                        //If the piece is selectable, marks all available moves green 
                        //with a raised bevel border
                        markAvailable(row, col);
                        break; //break out to wait for player move
                    }
                    else { //if a player has chosen a piece

                        //when player clicks on selected peice, unselects it and reset move markers
                        if(selectPieceRow == row && selectPieceCol == col){
                            unmark();
                            break;
                        }

                        //when player selects a button what is not a valid move, do nothing
                        if(!chessBoard.isValidMove(row, col)){
                            break;
                        }
                        
                        //if above are not true, player has selected a valid spot to move to,
                        //so move the selected piece to that spot
                        movePiece(row, col);
                        break;
                    }
                }
            }
        }
    }

    private void markAvailable(int startRow, int startCol){

        //sets all valid moves by [row][col]
        chessBoard.setValidMoves(startRow, startCol);

        //iterates throught the buttons 2D array
        for(int row = 0; row < GRID_SIZE; row++){

            for(int col = 0; col < GRID_SIZE; col++){

                //if [row][col] is a valid move and isnt the selected piece,
                //gives the button at [row][col] a green backgroudn and raised bevel border
                if(chessBoard.isValidMove(row, col) && 
                    (startRow != row || startCol != col)){

                    buttons[row][col].setBorder(new BevelBorder(BevelBorder.RAISED));
                    buttons[row][col].setBackground(
                        new Color(0, GREEN_VALUE_1, 0));                    
                }
                else if(startRow == row && startCol == col){//marks selected piece
                    pieceChosen = true;
                    selectPieceRow = row;
                    selectPieceCol = col;
                }
            }
        }

        //calls to check if a king is in check to mark it
        isCheck();

        //the selected button is given a gray background and a lowered bevel border
        buttons[startRow][startCol].setBackground(Color.GRAY);
        buttons[startRow][startCol].setBorder(new BevelBorder(BevelBorder.LOWERED));
    }

    private void unmark(){

        //calls the chessboard method to reset the valid moves in the 2D boolean array
        chessBoard.resetAvailableMoves();

        //next player can now choose their piece to move
        pieceChosen = false;
        //resets the background colors/borders for all buttons
        boolean background = true;
        for(int row = 0; row < GRID_SIZE; row++){

            for(int col = 0; col < GRID_SIZE; col++){

                if(background){
                    buttons[row][col].setBackground(
                        new Color(RED_VALUE_1, GREEN_VALUE_1, BLUE_VALUE_1));
                }
                else {
                    buttons[row][col].setBackground(
                        new Color(RED_VALUE_2, GREEN_VALUE_2, BLUE_VALUE_2));
                }
                background = !background;
                buttons[row][col].setBorder(new LineBorder(Color.BLACK, 1));              
            }
            background = !background;
        }

        //calls to check if a king is in check to mark it
        isCheck();
    }

    private void movePiece(int row, int col){
        //buttons[row][col].setText(chessBoard.getName(selectPieceRow, selectPieceCol));

        //when moving a piece, given the target button the corresponding ImageIcon
        if(chessBoard.getImage(selectPieceRow, selectPieceCol) != null){
            buttons[row][col].setIcon(
                new ImageIcon(chessBoard.getImage(selectPieceRow, selectPieceCol)));
            buttons[selectPieceRow][selectPieceCol].setIcon(null);
        }

        // if(isWhiteTurn){
        //     buttons[row][col].setForeground(Color.WHITE);
        // }
        // else {
        //     buttons[row][col].setForeground(Color.BLACK);
        // }

        //buttons[selectPieceRow][selectPieceCol].setText("");

        //moves the piece in the piece 2D array is chessboard class
        chessBoard.setPosition(selectPieceRow, selectPieceCol, row, col);

        //unmarks the marked spaces for a new turn
        unmark();
        //resets all available moves for a new turn
        chessBoard.resetAvailableMoves();
        //make it the next players turn after they have moved a piece
        isWhiteTurn = !isWhiteTurn;
    }

    private void isCheck(){

        int kingRow;
        int kingCol;

        if(chessBoard.isCheck(true)){//check white king is in check

            //gets the row and col for the checked white king
            kingRow = chessBoard.getKingRow(true);
            kingCol = chessBoard.getKingCol(true);

            //makes the white kings background red
            buttons[kingRow][kingCol].setBackground(
                new Color(GREEN_VALUE_1, 0, 0)//RED COLOR 
            );
        }

        if(chessBoard.isCheck(false)){//check black king is in check

            //gets the row and col for the checked black king
            kingRow = chessBoard.getKingRow(false);
            kingCol = chessBoard.getKingCol(false);

            //makes the black kings background red
            buttons[kingRow][kingCol].setBackground(
                new Color(GREEN_VALUE_1, 0, 0)//RED COLOR 
            );
        }
    }

    /** Resets Game */
    private void resetBoard(){

        chessBoard = new ChessBoard();

        for(int row = 0; row < GRID_SIZE; row++){

            for(int col = 0; col < GRID_SIZE; col++){
                buttons[row][col].setText(chessBoard.getName(row, col));
                buttons[row][col].setForeground(Color.WHITE);

                if(row == 0 || row == 1){
                    buttons[row][col].setForeground(Color.BLACK);
                }
                else if(row == WHITE_TOP_ROW || row == WHITE_BOTTOM_ROW){
                    buttons[row][col].setForeground(Color.WHITE);
                }
            }
        }
    }

    /**
     * Starts the program
     * 
     * @param args array of command line arguments
     */
    public static void main(String[] args) {
        new ChessUI(); //calls the GUI on start
    }
}