import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

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

    /** Window Width Size */
    private static final int WIDTH = 700;
    /** Window Height */
    private static final int HEIGHT = 500;

    private static ChessUI ui;

    /** Chess Panel for setting board */
    private JPanel chessPanel;
    /** Side panel */
    private JPanel sidePanel;
    /** Moves Panel for switching board states */
    private JPanel movesPanel;
    /** Moves Grid */
    private JPanel movesGrid;
    /** Grid layout */
    private GridLayout movesGL;
    /** 2D array of buttons for moves */
    private ArrayList<JButton[]> movesButtons;
    /** Scroll Bar for moves */
    private JScrollPane movesScroll;
    /** Action Listener for side panel */
    private ActionListener side;
    /** Grid Board JPanel */
    private JPanel board;
    /** Buttons 2D Array */
    private JButton[][] buttons;
    /** play button */
    private JButton playButton;
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
    /** bool for if want to watch the bot move by move */
    private boolean watchComputer;
    /** bool for if should play the next move */
    private boolean continueGame;
    /** bool for if the game is a stalemate */
    private boolean isStaleMate;

    private boolean waitForPromote;

    private boolean promote;
    /** Promotion Menu */
    private JPopupMenu promoteMenu;
    /** Pawn Promotion Option */
    private Piece b, q, r, n;
    /** Menu Items */
    private JMenuItem queenItem, rookItem, knightItem, bishopItem;
    /** GUI Tabs */
    private JTabbedPane tabbedPane;
    /** Press To Move to Chess Board */
    private JButton settingsPlayButton;
    /** Settings Tab */
    private JPanel settingsPanel;
    /** Player controls */
    private JPanel controlsPanel;
    /** input output panel */
    private JPanel ioPanel;
    /** Player Side Choose Panel */
    private JPanel playerSidePanel;
    /** Player Side Choose Label */
    private JLabel playerSideLabel;
    /** Player Side Choose Combo Box */
    private JComboBox<String> playerSideBox;
    /** Play Computer Panel */
    private JPanel playComputerPanel;
    /** Play Computer Label */
    private JLabel playComputerLabel;
    /** Play Computer Choose Box */
    private JComboBox<String> playComputerBox;
    /** Computer Level Panel */
    private JPanel computerLevelPanel;
    /** Computer Level Label */
    private JLabel computerLevelLabel;
    /** Computer Level Choose Box */
    private JComboBox<String> computerLevelBox;
    /** Panel for watch computer button */
    private JPanel watchComputerPanel;
    /** label for the watch computer button */
    private JLabel watchComputerLabel;
    /** drop down box for watch commputer button */
    private JComboBox<String> watchComputerBox;
    /** Player Side Choices Options */
    private final String[] playerSideChoices = {"White", "Black", "None"};
    /** Playing Computer Choices Options */
    private final String[] playComputerChoices = {"No", "Yes"};
    /** computer Level Choices Options */
    private final String[] computerLevelChoices = {"0", "1", "2", "3"};
    /** Watch Computer Turn by Turn Options */
    private final String[] watchComputerChoices = {"Yes", "No"};
    /** Load Button */
    private JButton loadButton;
    /** Save Button */
    private JButton saveButton;
    /** New Game Button */
    private JButton newButton;

    private JButton disabledButton;
    /** Computer Bot Level */
    private int computerLevel;
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
    private ChessUI() {
        super("Chess");
        setSize(WIDTH, HEIGHT);
        setLocation(100, 100);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        ui = this;

        //Default Values
        playerSide = true;
        playComputer = false;
        computerLevel = 0;
        isWhiteTurn = true;
        pieceChosen = false;
        canPlay = true;
        gameEnd = false;
        continueGame = false;
        watchComputer = true;
        waitForPromote = false;

        tabbedPane = new JTabbedPane();

        //Master Chess Panel
        chessPanel = new JPanel(new BorderLayout());

        board = new JPanel(new GridLayout(GRID_SIZE, GRID_SIZE));
        buttons = new JButton[GRID_SIZE][GRID_SIZE];

        
        playButton = new JButton("Play");
        playButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                //if(!watchComputer){ return; }

                continueGame = true; //Continue sim when user clicks the button
                computerSide = isWhiteTurn;
                computerMove();
            }
        });

        settingsPanel = new JPanel(new BorderLayout());
        controlsPanel = new JPanel(new FlowLayout());
        ioPanel = new JPanel(new FlowLayout());

        settingsPlayButton = new JButton("Play");
        settingsPlayButton.addActionListener(new ActionListener() {
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
                    //tabbedPane.setSelectedIndex(0);
                    computerSide = isWhiteTurn;
                    watchComputerBox.setEnabled(true);
                    chessPanel.add(playButton, BorderLayout.SOUTH);
                }
                else if(playComputer) {
                    continueGame = true;
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
                    //tabbedPane.setSelectedIndex(0);
                    computerSide = isWhiteTurn;
                    watchComputerBox.setEnabled(true);
                    chessPanel.add(playButton, BorderLayout.SOUTH);
                }
                else if(playComputer) {
                    continueGame = true;
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
                    case "2":
                        computerLevel = 2;
                        break;
                    case "3":
                        computerLevel = 3;
                        break;    
                    default:
                        break;
                }

                computerMove();
            }
        });

        //Watch Computer Play ComboBox Initialitation
        //Combo Box changes player side when changing item
        watchComputerPanel = new JPanel();
        watchComputerPanel.setLayout(new BoxLayout(watchComputerPanel, BoxLayout.PAGE_AXIS));
        watchComputerLabel = new JLabel("Watch Game?");
        watchComputerPanel.setBackground(Color.LIGHT_GRAY);
        watchComputerBox = new JComboBox<>(watchComputerChoices);
        watchComputerBox.setAlignmentX(Component.LEFT_ALIGNMENT);
        watchComputerPanel.add(watchComputerLabel);
        watchComputerPanel.add(watchComputerBox);

        watchComputerBox.addActionListener(new ActionListener() {            
            public void actionPerformed(ActionEvent e){
                String choice = (String)watchComputerBox.getSelectedItem();

                switch (choice) {
                    case "Yes":
                        watchComputer = true;
                        continueGame = false;
                        break;
                    case "No":
                        watchComputer = false;

                        if(!(playComputer && playerSide == null)){
                            chessPanel.remove(playButton);
                        }
                        break;
                    default:
                        break;
                }

                computerMove();
            }
        });

        watchComputerBox.setEnabled(false);

       

        loadButton = new JButton("Load");
        loadButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                try {
                    String filename = fileChooser(true);
                    if(filename == null)
                        throw new IllegalArgumentException("File not found.");

                    resetBoard();
                    chessBoard.loadChessFromFile(filename);

                    decrementMovesGrid();
                    incrementMovesGrid();

                    updateInstance(ChessBoard.getChessBoardListSize() - 1, chessBoard.isWhiteTurn() ? 1 : 0);

                    for(int i = 0; i < ChessBoard.getMovesList().size(); i++){
                        movesButtons.get(i)[0].setText(ChessBoard.getMovesList().get(i)[0]);
                        if(ChessBoard.getMovesList().get(i)[1] != null){
                            movesButtons.get(i)[1].setText(ChessBoard.getMovesList().get(i)[1]);
                        }
                    }
                } catch (IllegalArgumentException exp){
                    resetBoard();
                    JOptionPane.showMessageDialog(ui, exp.getMessage());
                }

                playComputer = false;
                playerSide = true;
            }
        });


        saveButton = new JButton("Save");
        saveButton.addActionListener(new ActionListener() {
            
            public void actionPerformed(ActionEvent e) {
                try {
                String filename = fileChooser(false);
                if(filename == null)
                    throw new IllegalArgumentException("File not found.");

                if(filename.contains(".pgn")){
                    int idx = filename.indexOf(".pgn");
                    if(!".pgn".equals(filename.substring(idx))){
                        throw new IllegalArgumentException("Incorrect file type.");
                    }
                }
                else{
                    filename += ".pgn";
                }


                    ChessIO.saveChessPGN(filename, ChessBoard.getMovesList());
                } catch (IllegalArgumentException exp){
                    JOptionPane.showMessageDialog(ui, exp.getMessage());
                }
            }
        });


        newButton = new JButton("New ");
        newButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                resetBoard();
            }
        });

        ioPanel.add(newButton);
        ioPanel.add(loadButton);
        ioPanel.add(saveButton);

        settingsPanel.add(controlsPanel, BorderLayout.NORTH);
        settingsPanel.add(ioPanel, BorderLayout.SOUTH);

        //Add Created Panels For Combo Box
        controlsPanel.add(playerSidePanel);
        controlsPanel.add(playComputerPanel);
        controlsPanel.add(computerLevelPanel);
        controlsPanel.add(watchComputerPanel);
        controlsPanel.add(settingsPlayButton);

        //Add Component to tabs
        tabbedPane.add("Chess", (Component)chessPanel);
        tabbedPane.add("Settings", (Component)settingsPanel);

        controlsPanel.setBackground(Color.LIGHT_GRAY);
        settingsPanel.setBackground(Color.LIGHT_GRAY);
        ioPanel.setBackground(Color.LIGHT_GRAY);
        board.setBackground(Color.DARK_GRAY);
        add(tabbedPane); //add the primary JPanel

        chessBoard = ChessBoard.getInstance(); //create instance of ChessBoard Class to handle game

        
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

        //Add board to Chess Panel after buttons are initialized
        chessPanel.add(board, BorderLayout.CENTER);

        //Side panel
        sidePanel = new JPanel(new BorderLayout());
        //Moves List Panel
        movesPanel = new JPanel(new BorderLayout());
        movesGL = new GridLayout(1, 3);
        movesGrid = new JPanel(movesGL);
        movesPanel.add(movesGrid, BorderLayout.CENTER);


        //movesGL.setRows(2);

        movesButtons = new ArrayList<>();
        movesButtons.add(new JButton[2]);
        side = new SideActionPerformed(movesButtons);

        //round label set
        JLabel round = new JLabel(ChessBoard.getRound() + ": ");
        round.setHorizontalAlignment(SwingConstants.RIGHT);
        round.setPreferredSize(new Dimension(30, 30));
        movesGrid.add(round);

        //set inital side buttons
        for(int j = 0; j < 2; j++){
            movesButtons.get(0)[j] = new JButton();
            movesButtons.get(0)[j].setPreferredSize(new Dimension(75, 30));
            movesButtons.get(0)[j].addActionListener(side);
            movesGrid.add(movesButtons.get(0)[j]);
        }

        movesScroll = new JScrollPane(movesGrid, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        movesScroll.getVerticalScrollBar().setUnitIncrement(15);
        movesPanel.add(movesScroll);

        //movesPanel.add(movesScroll, BorderLayout.EAST);

        sidePanel.add(movesPanel, BorderLayout.EAST);
        chessPanel.add(sidePanel, BorderLayout.EAST);

        //incrementMovesGrid();

        //new pop up menu for promotion
        promoteMenu = new JPopupMenu();
        promote = false;

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

//        movesButtons.get(0)[0].setEnabled(false);
//        disabledButton = movesButtons.get(0)[0];

        //make GUI visible to user
        setVisible(true);

        computerMove();
        
    }

    private String fileChooser(boolean load){
        JFileChooser fc = new JFileChooser("./");

        if(load){
            fc.showOpenDialog(this);
        }
        else {
            fc.showSaveDialog(this);
        }

        if(fc.getSelectedFile() == null)
            return null;

        return fc.getSelectedFile().getAbsolutePath();
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

                        if(!canPlay) { return; }

                        //when player selects a button what is not a valid move, do nothing
                        if(!chessBoard.isValidMove(row, col) || playerSide == null){
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

    public static ChessUI getUI(){
        return ui;
    }

    private void incrementMovesGrid(){

//        if(movesButtons.size() == ChessBoard.getChessBoardListSize()){
//            return;
//        }

        movesGL.setRows(movesGL.getRows() + 1);

        JLabel round = new JLabel();
        round.setHorizontalAlignment(SwingConstants.RIGHT);
        round.setPreferredSize(new Dimension(30, 30));
        movesGrid.add(round);

        movesButtons.add(new JButton[2]);
        round.setText(movesButtons.size() + ": ");
        for(int j = 0; j < 2; j++){
//            movesButtons.get(ChessBoard.getRound() - 1)[j] = new JButton();
//            movesButtons.get(ChessBoard.getRound() - 1)[j].setPreferredSize(new Dimension(50, 30));
//            movesButtons.get(ChessBoard.getRound() - 1)[j].addActionListener(side);
            movesButtons.get(movesButtons.size() - 1)[j] = new JButton();
            movesButtons.get(movesButtons.size() - 1)[j].setPreferredSize(new Dimension(75, 30));
            movesButtons.get(movesButtons.size() - 1)[j].addActionListener(side);
            movesGrid.add(movesButtons.get(movesButtons.size() - 1)[j]);
        }

        movesScroll.getVerticalScrollBar().setValue(movesScroll.getVerticalScrollBar().getMaximum());

        if(movesButtons.size() < ChessBoard.getChessBoardListSize())
            incrementMovesGrid();
    }

    private void decrementMovesGrid(){

        if(movesButtons.size() == 1){
            return;
        }

        movesGrid.remove(movesGrid.getComponentCount() - 1);
        movesGrid.remove(movesGrid.getComponentCount() - 1);
        movesGrid.remove(movesGrid.getComponentCount() - 1);
        movesGL.setRows(movesGL.getRows() - 1);

        movesButtons.remove(movesButtons.size() - 1);
        if(movesButtons.size() > ChessBoard.getChessBoardListSize())
            decrementMovesGrid();
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

            waitForPromote = true;
            promoteMenu.show(this, selectPieceCol * 50, selectPieceRow * 50);
            promoteMenu.setVisible(true);


            promotionRow = row;
            promotionCol = col;
        }

        //moves the piece in the piece 2D array is chessboard class
        //chessBoard.setPosition(selectPieceRow, selectPieceCol, row, col);
        chessBoard.setMove(selectPieceRow, selectPieceCol, row, col);

//        for(ChessBoard[] cb : ChessBoard.getChessBoardList()){
//            System.out.println(cb[0]);
//            System.out.println(cb[1]);
//        }

        processMove();

        if(gameEnd && canPlay){
            resetBoard();
        }
    }

    private void computerMove(){

        if(waitForPromote) { return; }

        if((Boolean)isWhiteTurn != computerSide && playerSide != null) { return; }

        if(!playComputer || !canPlay || (!continueGame && watchComputer && playerSide == null)) { return; }

        try {
            chessBoard.computerMove(computerSide, computerLevel);
        } catch (IllegalArgumentException e) {
            isStaleMate = true;
            isStaleMate();
            isStaleMate = false;
            chessBoard.resetSimTurns();
        }

        // if(playerSide == null){
        //     chessBoard.computerMove(!computerSide, 0);
        // }

        processMove();

        if(watchComputer && continueGame){
            continueGame = false;
        }

        if(playerSide == null && !gameEnd){
            computerSide = isWhiteTurn;
            computerMove();
        }

        if(gameEnd && canPlay){
            resetBoard();
            return;
        }
    }

    private void processMove(){

        if(gameEnd) { return; }

        //Update board after moving
        updateBoard();
        
        //unmarks the marked spaces for a new turn
        unmark();    

        //calls to check if a king is in check to mark it
        isCheck();
        
        //checks for stalemate
        isStaleMate();

        int addIdx = isWhiteTurn ? 0 : 1;
        int buttonIdx = ChessBoard.getRound() - (isWhiteTurn ? 1 : 2);

        if(disabledButton != null)
            disabledButton.setEnabled(true);

        movesButtons.get(buttonIdx)[addIdx].setText(ChessBoard.getMoveString(buttonIdx, addIdx));
        movesButtons.get(buttonIdx)[addIdx].setEnabled(false);
        disabledButton = movesButtons.get(buttonIdx)[addIdx];

        //make it the next players turn after they have moved a piece
        isWhiteTurn = !isWhiteTurn;

        if(movesButtons.size() > ChessBoard.getChessBoardListSize())
            decrementMovesGrid();

        if(isWhiteTurn)
            incrementMovesGrid();

        movesScroll.getVerticalScrollBar().setValue(movesScroll.getVerticalScrollBar().getMaximum());

        //If White king is in checkmate call for game to end with black winning
        if(chessBoard.isCheckMate(true)) {
            gameWin(false);
        }       

        //If Black King is in checkmate call for game to end with white winning
        if(chessBoard.isCheckMate(false)){
            gameWin(true);
        }
    }

    public void updateInstance(int row, int col){
        chessBoard = ChessBoard.getInstance();
        this.isWhiteTurn = chessBoard.isWhiteTurn();
        updateBoard();
        unmark();

        if(disabledButton != null){
            disabledButton.setEnabled(true);
        }
        disabledButton = movesButtons.get(row)[col];
        disabledButton.setEnabled(false);
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

        
        if(chessBoard.isStaleMate(isWhiteTurn) || isStaleMate) {

            //Reset simulated turns after game ends
            chessBoard.resetSimTurns();

            //Show Dialog Box with Yes/No/Cancel option
            int choice = JOptionPane.showConfirmDialog(null, "Game Ends in Stalemate! Play Again? ");
            gameEnd = true;
            
            if(choice == JOptionPane.YES_OPTION){
                //
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

        //Reset simulated turns after game ends
        chessBoard.resetSimTurns();

        //Show Dialog Box with Yes/No/Cancel option
        int choice = JOptionPane.showConfirmDialog(null, String.format("%s Wins! Play Again? ", side ? "White" : "Black"));
        gameEnd = true;
        
        if(choice == JOptionPane.YES_OPTION){
            //
        }
        else if(choice == JOptionPane.NO_OPTION){
            System.exit(1);
        }        
        else {
            canPlay = false;
        }
    }

    private void promote(Piece p){

        promote = false;

        chessBoard.promote(p.getName(), promotionRow, promotionCol);
        buttons[promotionRow][promotionCol].setIcon(new ImageIcon(p.getImage()));

        movesButtons.get(ChessBoard.getRound() - 2)[1].setText(ChessBoard.getMoveString(ChessBoard.getRound() - 2, 1));
        movesButtons.get(ChessBoard.getRound() - 2)[1].setMargin(new Insets(0, 0, 0, 0));
        movesButtons.get(ChessBoard.getRound() - 1)[0].setText(ChessBoard.getMoveString(ChessBoard.getRound() - 1, 0));
        movesButtons.get(ChessBoard.getRound() - 1)[0].setMargin(new Insets(0, 0, 0, 0));

        waitForPromote = false;
        computerMove();
    }

    /** Resets Game */
    private void resetBoard(){

        //Resets turn to white and piece is not chosen on start
        pieceChosen = false;
        isWhiteTurn = true;
        gameEnd = false;
        canPlay = true;

        //makes new chessboard instance
        chessBoard = ChessBoard.resetChessBoard();
        decrementMovesGrid();

        movesButtons.get(0)[0].setText("");
        movesButtons.get(0)[1].setText("");

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