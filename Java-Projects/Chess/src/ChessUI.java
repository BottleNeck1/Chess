import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;
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
    private JPanel board;

    /** Buttons 2D Array */
    private JButton[][] buttons;

    /** Chess Game Class Instance */
    private ChessBoard chessBoard;

    /** Game Starts with white side */
    private boolean isWhiteTurn;

    /** Boolean for is user locks a piece to move */
    private boolean pieceChosen;

    /** Can user play */
    private boolean canPlay;

    /** Stop Game From Playing */
    private boolean gameEnd;

    /** User selected piece to move row */
    private int selectPieceRow;

    /** User selected piece to move column */
    private int selectPieceCol;

    /** Row to Promote */
    private int promotionRow;

    /** Col to Promote */
    private int promotionCol;

    /** Player Side to Play on */
    private Boolean playerSide;

    /** Computer Side to Play on */
    private boolean computerSide;

    /** Play against Computer or not */
    private boolean playComputer;

    /** Promotion Menu */
    private JPopupMenu promoteMenu;

    /** Pawn Promotion Option */
    private Piece b, q, r, n;

    /** Menu Items */
    private JMenuItem queenItem, rookItem, knightItem, bishopItem;

    /** GUI Tabs */
    JTabbedPane tabbedPane;

    /** Press To Move to Chess Board */
    JButton playButton;

    /** Settings Tab */
    JPanel settingsPane;

    /** Player Side Choose Panel */
    JPanel playerSidePanel;

    /** Player Side Choose Label */
    JLabel playerSideLabel;

    /** Player Side Choose Combo Box */
    JComboBox<String> playerSideBox;

    /** Play Computer Panel */
    JPanel playComputerPanel;

    /** Play Computer Label */
    JLabel playComputerLabel;

    /** Play Computer Choose Box */
    JComboBox<String> playComputerBox;

    /** Computer Level Panel */
    JPanel computerLevelPanel;

    /** Computer Level Label */
    JLabel computerLevelLabel;

    /** Computer Level Choose Box */
    JComboBox<String> computerLevelBox;

    /** Player Side Choices Options */
    String[] playerSideChoices = {"White", "Black", "None"};

    /** Playing Computer Choices Options */
    String[] playComputerChoices = {"No", "Yes"};

    /** computer Level Choices Options */
    String[] computerLevelChoices = {"0", "1"};

    /** Computer Bot Level */
    int computerLevel;

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

    /** ChessUI Contructor */
    public ChessUI() {
        super("Chess");
        setSize(SIZE, SIZE);
        setLocation(100, 100);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        //Default Values
        playerSide = true;
        playComputer = false;
        computerLevel = 0;
        isWhiteTurn = true;
        pieceChosen = false;
        canPlay = true;
        gameEnd = false;

        tabbedPane = new JTabbedPane();

        board = new JPanel(new GridLayout(GRID_SIZE, GRID_SIZE));
        buttons = new JButton[GRID_SIZE][GRID_SIZE];

        settingsPane = new JPanel(new FlowLayout());

        playButton = new JButton("Play");

        playButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tabbedPane.setSelectedIndex(0);
            }            
        });

        //Player Side Panel Initialitation
        //Combo Box changes player side when changing item
        playerSidePanel = new JPanel();
        playerSidePanel.setLayout(new BoxLayout(playerSidePanel, BoxLayout.PAGE_AXIS));
        playerSideLabel = new JLabel("Choose Your Side");
        playerSidePanel.setBackground(Color.LIGHT_GRAY);
        playerSideBox = new JComboBox<>(playerSideChoices);
        playerSideBox.setAlignmentX(Component.LEFT_ALIGNMENT);
        playerSidePanel.add(playerSideLabel);
        playerSidePanel.add(playerSideBox);

        playerSideBox.addActionListener(new ActionListener() {            
            public void actionPerformed(ActionEvent e){
                String choice = (String)playerSideBox.getSelectedItem();

                switch (choice) {
                    case "White":
                        playerSide = true;
                        break;
                    case "Black":
                        playerSide = false;
                        break;
                    case "None":
                        playerSide = null;
                        break;
                    default:
                        break;
                }

                if(playComputer && playerSide != null){
                    computerSide = !playerSide;

                }

                if(playComputer && playerSide == null){
                    tabbedPane.setSelectedIndex(0);
                }

                computerMove();
            }
        });

        //Wether to play against Computer Initialitation
        //Combo Box changes if to play against computer
        playComputerPanel = new JPanel();
        playComputerPanel.setLayout(new BoxLayout(playComputerPanel, BoxLayout.PAGE_AXIS));
        playComputerLabel = new JLabel("Play Against Computer?");
        playComputerPanel.setBackground(Color.LIGHT_GRAY);
        playComputerBox = new JComboBox<>(playComputerChoices);
        playComputerBox.setAlignmentX(Component.LEFT_ALIGNMENT);
        playComputerPanel.add(playComputerLabel);
        playComputerPanel.add(playComputerBox);

        playComputerBox.addActionListener(new ActionListener() {            
            public void actionPerformed(ActionEvent e){
                String choice = (String)playComputerBox.getSelectedItem();

                switch (choice) {
                    case "No":
                        playComputer = false;
                        break;
                    case "Yes":
                        playComputer = true;
                        break;
                    default:
                        break;
                }

                if(playComputer && playerSide == null){
                    tabbedPane.setSelectedIndex(0);
                }

                computerMove();
            }
        });

        //Computer Level initialization
        //Changes the computer level
        computerLevelPanel = new JPanel();
        computerLevelPanel.setLayout(new BoxLayout(computerLevelPanel, BoxLayout.PAGE_AXIS));
        computerLevelLabel = new JLabel("Choose Computer Level");
        computerLevelPanel.setBackground(Color.LIGHT_GRAY);
        computerLevelBox = new JComboBox<>(computerLevelChoices);
        computerLevelBox.setAlignmentX(Component.LEFT_ALIGNMENT);
        computerLevelPanel.add(computerLevelLabel);
        computerLevelPanel.add(computerLevelBox);

        computerLevelBox.addActionListener(new ActionListener() {            
            public void actionPerformed(ActionEvent e){
                String choice = (String)computerLevelBox.getSelectedItem();

                switch (choice) {
                    case "0":
                        computerLevel = 0;
                        break;
                    case "1":
                        computerLevel = 1;
                        break;
                    default:
                        break;
                }

                computerMove();
            }
        });

        //Add Created Panels For Combo Box
        settingsPane.add(playerSidePanel);
        settingsPane.add(playComputerPanel);
        settingsPane.add(computerLevelPanel);
        settingsPane.add(playButton);

        //Add Component to tabs
        tabbedPane.add("Chess", (Component)board);
        tabbedPane.add("Settings", (Component)settingsPane);

        settingsPane.setBackground(Color.LIGHT_GRAY);
        board.setBackground(Color.DARK_GRAY);
        add(tabbedPane); //add the primary JPanel

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

        //new pop up menu for promotion
        promoteMenu = new JPopupMenu();

        q = new Queen(0, 0, isWhiteTurn);
        r = new Rook(0, 0, isWhiteTurn);
        b = new Bishop(0, 0, isWhiteTurn);
        n = new Knight(0, 0, isWhiteTurn);

        queenItem = new JMenuItem(new ImageIcon(q.getImage()));    
        rookItem = new JMenuItem(new ImageIcon(r.getImage()));
        bishopItem = new JMenuItem(new ImageIcon(b.getImage()));
        knightItem = new JMenuItem(new ImageIcon(n.getImage()));

        //add event to pop up menu items
        queenItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                promote(q);
            }
        });

        rookItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                promote(r);
            }
        });

        bishopItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                promote(b);
            }
        });

        knightItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                promote(n);
            }
        });
        
        //add menu items to pop-up menu
        promoteMenu.add(queenItem);
        promoteMenu.add(rookItem);
        promoteMenu.add(bishopItem);
        promoteMenu.add(knightItem);

        //make GUI visible to user
        setVisible(true);

        computerMove();
        
    }

    /**
     * Performs specific action(s) based on the event that occurs
     * 
     * @param e event that occurred
     */
    public void actionPerformed(ActionEvent e) {

        if(!canPlay) { return; }
        
        //Iterates 2D buttons array to find the index for the pressed button
        for(int row = 0; row < GRID_SIZE; row++){

            for(int col = 0; col < GRID_SIZE; col++){

                if(buttons[row][col] == e.getSource()){ //row and col are now known                
    
                    if(!pieceChosen){ //user has not selected a piece to move

                        //checks if the piece is able to be moved by player1/player2
                        if(!chessBoard.canSelect(row, col, isWhiteTurn)) { break; }
                        
                        //If the piece is selectable, marks all available moves green 
                        //with a raised bevel border
                        markAvailable(row, col);
                        break; //break out to wait for player move
                    }
                    else if(pieceChosen && chessBoard.canSelect(row, col, isWhiteTurn)){
                        unmark();

                        //when player clicks on selected peice, unselects it and reset move markers
                        if(selectPieceRow == row && selectPieceCol == col){
                            break;
                        }

                        markAvailable(row, col);
                    }
                    else { //if a player has chosen a piece

                        //when player selects a button what is not a valid move, do nothing
                        if(!chessBoard.isValidMove(row, col)){
                            break;
                        }
                        
                        //if above are not true, player has selected a valid spot to move to,
                        //so move the selected piece to that spot
                        movePiece(row, col);

                        computerMove();
                        break;
                    }
                }
            }
        }
    }

    private void markAvailable(int startRow, int startCol){

        chessBoard.resetAvailableMoves();

        //sets all valid moves by [row][col]
        boolean hasValidMove = chessBoard.setValidMoves(startRow, startCol);

        //mark selected piece
        pieceChosen = true;
        selectPieceRow = startRow;
        selectPieceCol = startCol;

        //the selected button is given a gray background and a lowered bevel border
        buttons[startRow][startCol].setBackground(Color.GRAY);
        buttons[startRow][startCol].setBorder(new BevelBorder(BevelBorder.LOWERED));

        if(!hasValidMove) { return; }

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
            }
        }

        //calls to check if a king is in check to mark it
        //isCheck();        
    }

    private void unmark(){

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

        isCheck();
    }

    private void movePiece(int row, int col){

        if(chessBoard.canPromote(selectPieceRow, selectPieceCol, row, isWhiteTurn)){

            q = new Queen(0, 0, isWhiteTurn);
            r = new Rook(0, 0, isWhiteTurn);
            b = new Bishop(0, 0, isWhiteTurn);
            n = new Knight(0, 0, isWhiteTurn);

            queenItem.setIcon(new ImageIcon(q.getImage())); 
            rookItem.setIcon(new ImageIcon(r.getImage()));
            bishopItem.setIcon(new ImageIcon(b.getImage()));
            knightItem.setIcon(new ImageIcon(n.getImage()));

            promoteMenu.show(this, selectPieceCol * 50, selectPieceRow * 50);
            
            promotionRow = row;
            promotionCol = col;
        }

        //moves the piece in the piece 2D array is chessboard class
        chessBoard.setPosition(selectPieceRow, selectPieceCol, row, col);

        processMove();

        if(gameEnd){
            gameEnd = false;
            resetBoard();
        }
    }

    private void computerMove(){

        if((Boolean)isWhiteTurn != computerSide || !playComputer || !canPlay) { return; }

        chessBoard.computerMove(computerSide, computerLevel);

        // if(playerSide == null){
        //     chessBoard.computerMove(!computerSide, 0);
        // }

        processMove();

        if(playerSide == null && !gameEnd){
            computerMove();
        }

        if(gameEnd){
            gameEnd = false;
            resetBoard();
            return;
        }
    }

    private void processMove(){

        //Update board after moving
        updateBoard();
        
        //unmarks the marked spaces for a new turn
        unmark();    

        //calls to check if a king is in check to mark it
        isCheck();
        
        //checks for stalemate
        isStaleMate();
        
        //make it the next players turn after they have moved a piece
        isWhiteTurn = !isWhiteTurn;

        //If White king is in checkmate call for game to end with black winning
        if(chessBoard.isCheckMate(true)) {
            gameWin(false);
        }       

        //If Black King is in checkmate call for game to end with white winning
        if(chessBoard.isCheckMate(false)){
            gameWin(true);
        }
    }

    private void updateBoard() {
        //when moving a piece, re-do all icons in grid
        for(int gridRow = 0; gridRow < GRID_SIZE; gridRow++){

            for(int gridCol = 0; gridCol < GRID_SIZE; gridCol++){

                if(chessBoard.getImage(gridRow, gridCol) != null){
                    buttons[gridRow][gridCol].setIcon(
                        new ImageIcon(chessBoard.getImage(gridRow, gridCol)));
                }
                else {
                    buttons[gridRow][gridCol].setIcon(null);
                }
            }
            // if(chessBoard.getImage(selectPieceRow, selectPieceCol) != null){
            //     buttons[row][col].setIcon(
            //         new ImageIcon(chessBoard.getImage(selectPieceRow, selectPieceCol)));
            //     buttons[selectPieceRow][selectPieceCol].setIcon(null);
            // }
        }       
    }

    private void isStaleMate(){
        if(chessBoard.isStaleMate(isWhiteTurn)) {

            //Show Dialog Box with Yes/No/Cancel option
            int choice = JOptionPane.showConfirmDialog(null, "Game Ends in Stalemate! Play Again? ");
            
            if(choice == JOptionPane.YES_OPTION){
                gameEnd = true;
            }
            else if(choice == JOptionPane.NO_OPTION){
                System.exit(1);
            }        
            else {
                canPlay = false;
            }
            
        }
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

    private void gameWin (boolean side){

        //Show Dialog Box with Yes/No/Cancel option
        int choice = JOptionPane.showConfirmDialog(null, String.format("%s Wins! Play Again? ", side ? "White" : "Black"));
        
        if(choice == JOptionPane.YES_OPTION){
            gameEnd = true;
        }
        else if(choice == JOptionPane.NO_OPTION){
            System.exit(1);
        }        
        else {
            canPlay = false;
        }
    }

    private void promote(Piece p){

        chessBoard.promote(p.getName(), promotionRow, promotionCol);
        buttons[promotionRow][promotionCol].setIcon(new ImageIcon(p.getImage()));
    }

    /** Resets Game */
    private void resetBoard(){

        //Resets turn to white and piece is not chosen on start
        pieceChosen = false;
        isWhiteTurn = true;

        //makes new chessboard instance
        chessBoard = new ChessBoard();

        boolean background = true;
        for(int row = 0; row < GRID_SIZE; row++){

            for(int col = 0; col < GRID_SIZE; col++){

                //if piece at [row][col] has image, set the button to have an ImageIcon
                if(chessBoard.getImage(row, col) != null){
                    buttons[row][col].setIcon(new ImageIcon(chessBoard.getImage(row, col)));
                }
                else { //reset images
                    buttons[row][col].setIcon(new ImageIcon());
                }
                //Align icon to center
                buttons[row][col].setHorizontalTextPosition(SwingConstants.CENTER);
                //Add simple line border to each button
                buttons[row][col].setBorder(new LineBorder(Color.BLACK, 1));
                buttons[row][col].setFocusPainted(false); // remove focus box

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

        if(playerSide == null){
            computerMove();
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