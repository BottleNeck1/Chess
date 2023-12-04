import java.awt.*;
import java.awt.event.*;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;

import java.awt.Image;
import javax.imageio.ImageIO;

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

        add(board);

        chessBoard = new ChessBoard();

        boolean background = true;
        for(int row = 0; row < GRID_SIZE; row++){

            for(int col = 0; col < GRID_SIZE; col++){
                buttons[row][col] = new JButton();
                if(chessBoard.getImage(row, col) != null){
                    buttons[row][col].setIcon(new ImageIcon(chessBoard.getImage(row, col)));
                }
                buttons[row][col].setHorizontalTextPosition(SwingConstants.CENTER);
                buttons[row][col].setFont(new Font("Default", Font.BOLD, PIECE_TXT_SIZE));
                buttons[row][col].setBorder(new LineBorder(Color.BLACK, 1));
                buttons[row][col].setFocusPainted(false);
                buttons[row][col].addActionListener(this);
                board.add(buttons[row][col]);

                if(row == 0 || row == 1){
                    buttons[row][col].setForeground(Color.BLACK);
                }
                else if(row == WHITE_TOP_ROW || row == WHITE_BOTTOM_ROW){
                    buttons[row][col].setForeground(Color.WHITE);
                }

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

        setVisible(true);
    }

    /**
     * Performs specific action(s) based on the event that occurs
     * 
     * @param e event that occurred
     */
    public void actionPerformed(ActionEvent e) {
        
        for(int row = 0; row < GRID_SIZE; row++){

            for(int col = 0; col < GRID_SIZE; col++){

                if(buttons[row][col] == e.getSource()){                    
                    
                    if(!pieceChosen){

                        if(chessBoard.canSelect(row, col, isWhiteTurn)) { break; }
                        
                        markAvailable(row, col);
                        break;
                    }
                    else {

                        if(selectPieceRow == row && selectPieceCol == col){
                            unmark();
                            break;
                        }

                        if(!chessBoard.isValidMove(row, col)){
                            break;
                        }
                        
                        movePiece(row, col);
                        break;
                    }
                }
            }
        }
    }

    private void markAvailable(int startRow, int startCol){

        chessBoard.setValidMoves(startRow, startCol);

        for(int row = 0; row < GRID_SIZE; row++){

            for(int col = 0; col < GRID_SIZE; col++){

                if(chessBoard.isValidMove(row, col) && 
                    (startRow != row || startCol != col)){

                    buttons[row][col].setBorder(new BevelBorder(BevelBorder.RAISED));
                    buttons[row][col].setBackground(
                        new Color(0, GREEN_VALUE_1, 0));                    
                }
                else if(startRow == row && startCol == col){
                    pieceChosen = true;
                    selectPieceRow = row;
                    selectPieceCol = col;
                }
            }
        }

        isMate();
        buttons[startRow][startCol].setBackground(Color.GRAY);
        buttons[startRow][startCol].setBorder(new BevelBorder(BevelBorder.LOWERED));
    }

    private void unmark(){

        chessBoard.resetAvailableMoves();

        pieceChosen = false;
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

        isMate();
    }

    private void movePiece(int row, int col){
        //buttons[row][col].setText(chessBoard.getName(selectPieceRow, selectPieceCol));

        if(chessBoard.getImage(selectPieceRow, selectPieceCol) != null){
            buttons[row][col].setIcon(
                new ImageIcon(chessBoard.getImage(selectPieceRow, selectPieceCol)));
            buttons[selectPieceRow][selectPieceCol].setIcon(null);
        }

        if(isWhiteTurn){
            buttons[row][col].setForeground(Color.WHITE);
        }
        else {
            buttons[row][col].setForeground(Color.BLACK);
        }

        buttons[selectPieceRow][selectPieceCol].setText("");
        chessBoard.setPosition(selectPieceRow, selectPieceCol, row, col);

        unmark();
        chessBoard.resetAvailableMoves();
        isWhiteTurn = !isWhiteTurn;
    }

    private void isMate(){

        int kingRow;
        int kingCol;

        if(chessBoard.isMate(true)){//check white king is mate

            kingRow = chessBoard.getKingRow(true);
            kingCol = chessBoard.getKingCol(true);

            buttons[kingRow][kingCol].setBackground(
                new Color(GREEN_VALUE_1, 0, 0)//RED COLOR 
            );
            // buttons[kingRow][kingCol].setBorder(
            //     new LineBorder(Color.WHITE, 3)
            // );
        }

        if(chessBoard.isMate(false)){//check black king is mate
            kingRow = chessBoard.getKingRow(false);
            kingCol = chessBoard.getKingCol(false);

            buttons[kingRow][kingCol].setBackground(
                new Color(GREEN_VALUE_1, 0, 0)//RED COLOR 
            );
            // buttons[kingRow][kingCol].setBorder(
            //     new LineBorder(Color.BLACK, 3)
            // );
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
        new ChessUI();
    }
}