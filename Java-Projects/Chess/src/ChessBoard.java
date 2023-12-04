import java.awt.Image;
import java.util.Arrays;

/**
 * Chess Board Class Object
 * 
 * @author David Martinez
 */
public class ChessBoard {

    /** Chess Board  Size */
    private static final int ARRAY_SIZE = 8;
    
    /** 2D array of Chess pieces */
    private Piece[][] pieces = new Piece[ARRAY_SIZE][ARRAY_SIZE];

    /** 2D array of available moves */
    private boolean[][] validMoves = new boolean[ARRAY_SIZE][ARRAY_SIZE];

    /** 7 Grid Position */
    private static final int SEVEN_POS = 7;

    /** 6 Grid Position */
    private static final int SIX_POS = 6;

    /** 5 Grid Position */
    private static final int FIVE_POS = 5;

    /** 4 Grid Position */
    private static final int FOUR_POS = 4;

    /** White King Row Position */
    private int whiteKingRow;

    /** White King Col Position */
    private int whiteKingCol;

    /** Black King Row Position */
    private int blackKingRow;

    /** Black King Col Position */
    private int blackKingCol;

    /** Is White King Under Check */
    private boolean isWhiteMate;

    /** Is Black King Under Check */
    private boolean isBlackMate;

    /** ChessBoard Constructor */
    public ChessBoard(){

        setBoard();
    }

    /** Sets the chess board up for play */
    public void setBoard(){

        for(int i = 0; i < ARRAY_SIZE; i++){
            Arrays.fill(validMoves[i], false);
        }

        for(int col = 0; col < ARRAY_SIZE; col++){
            pieces[1][col] = new Pawn(1, col, false);
            pieces[SIX_POS][col] = new Pawn(SIX_POS, col, true);
        }

        pieces[0][0] = new Rook(0, 0, false);
        pieces[0][SEVEN_POS] = new Rook(0, SEVEN_POS, false);
        pieces[SEVEN_POS][0] = new Rook(SEVEN_POS, 0, true);
        pieces[SEVEN_POS][SEVEN_POS] = new Rook(SEVEN_POS, SEVEN_POS, true);

        pieces[0][1] = new Knight(0, 1, false);
        pieces[0][SIX_POS] = new Knight(0, SIX_POS, false);
        pieces[SEVEN_POS][1] = new Knight(SEVEN_POS, 1, true);
        pieces[SEVEN_POS][SIX_POS] = new Knight(SEVEN_POS, SIX_POS, true);

        pieces[0][2] = new Bishop(0, 2, false);
        pieces[0][FIVE_POS] = new Bishop(0, FIVE_POS, false);
        pieces[SEVEN_POS][2] = new Bishop(SEVEN_POS, 2, true);
        pieces[SEVEN_POS][FIVE_POS] = new Bishop(SEVEN_POS, FIVE_POS, true);

        pieces[0][FOUR_POS] = new King(0, FOUR_POS, false);
        blackKingRow = 0;
        blackKingCol = FOUR_POS;
        pieces[SEVEN_POS][FOUR_POS] = new King(SEVEN_POS, FOUR_POS, true);
        whiteKingRow = SEVEN_POS;
        whiteKingCol = FOUR_POS;        
        
        pieces[0][3] = new Queen(0, 3, false);
        pieces[SEVEN_POS][3] = new Queen(SEVEN_POS, 3, true);
    }

    /**
     * Passes the row and col parameter to get the name for that object in the Piece array
     * @param row row parameter
     * @param col col parameter
     * @return name for identified object in piece array
     * @throws IllegalArgumentException if row or col is out of bounds
     */
    public String getName(int row, int col){

        if(row < 0 || row >= ARRAY_SIZE || col < 0 || col >= ARRAY_SIZE){
            throw new IllegalArgumentException("Invalid row or col");
        }

        if(pieces[row][col] != null){
            return pieces[row][col].getName();
        }

        return "";
    }

    public Image getImage(int row, int col){
        if(row < 0 || row >= ARRAY_SIZE || col < 0 || col >= ARRAY_SIZE){
            throw new IllegalArgumentException("Invalid row or col");
        }

        if(pieces[row][col] != null){
            return pieces[row][col].getImage();
        }

        return null;
    }

    /**
     * Using the starting row and col, find all available moves
     * @param startRow selected piece row to check
     * @param startCol selected piece column to check
     */
    public void setValidMoves(int startRow, int startCol){

        for(int row = 0; row < ARRAY_SIZE; row++){

            for(int col = 0; col < ARRAY_SIZE; col++){

                if(canMove(startRow, startCol, row, col)){

                    boolean isWhitePiece = pieces[startRow][startCol].isWhitePiece();
                    boolean doContinue = false;
                    Piece temp;

                    // temp = pieces[row][col];
                    // pieces[row][col] = pieces[startRow][startCol];
                    // pieces[startRow][startCol] = null;

                    if(pieces[startRow][startCol] instanceof King){

                        temp = pieces[row][col];
                        pieces[row][col] = pieces[startRow][startCol];
                        pieces[startRow][startCol] = null;

                        if(isWhitePiece){//white side
                            
                            whiteKingRow = row;
                            whiteKingCol = col;

                            if(isMate(true)){
                                doContinue = true;
                            }
                            whiteKingRow = startRow;
                            whiteKingCol = startCol;
                        }
                        else {//black side
                            blackKingRow = row;
                            blackKingCol = col;

                            if(isMate(false)){
                                doContinue = true;
                            }
                            blackKingRow = startRow;
                            blackKingCol = startCol;
                        }

                        pieces[startRow][startCol] = pieces[row][col];
                        pieces[row][col] = temp;

                    }

                    if(isWhitePiece && isWhiteMate){

                        temp = pieces[row][col];
                        pieces[row][col] = pieces[startRow][startCol];
                        pieces[startRow][startCol] = null;

                        if(isMate(true)){
                            doContinue = true;
                        }

                        pieces[startRow][startCol] = pieces[row][col];
                        pieces[row][col] = temp;
                    }
                    else if(!isWhitePiece && isBlackMate){
                        
                        temp = pieces[row][col];
                        pieces[row][col] = pieces[startRow][startCol];
                        pieces[startRow][startCol] = null;

                        if(isMate(false)){
                            doContinue = true;
                        }

                        pieces[startRow][startCol] = pieces[row][col];
                        pieces[row][col] = temp;
                    }

                    // pieces[startRow][startCol] = pieces[row][col];
                    // pieces[row][col] = temp;

                    // if(doContinue){
                    //     continue;
                    // }

                    // temp = pieces[row][col];
                    // pieces[row][col] = pieces[startRow][startCol];
                    // pieces[startRow][startCol] = null;

                    
                    if((isWhitePiece && !isMate(true)) ||
                        (!isWhitePiece && !isMate(false))) {

                        temp = pieces[row][col];
                        pieces[row][col] = pieces[startRow][startCol];
                        pieces[startRow][startCol] = null;
                        
                        if(isWhitePiece){
                            
                            if(isMate(true)){
                                doContinue = true;
                            }
                        }
                        else {

                            if(isMate(false)){
                                doContinue = true;
                            }
                        }

                        pieces[startRow][startCol] = pieces[row][col];
                        pieces[row][col] = temp;
                    }

                    // pieces[startRow][startCol] = pieces[row][col];
                    // pieces[row][col] = temp;
                    
                    if(doContinue){
                        continue;
                    }

                    if((row == whiteKingRow && col == whiteKingCol) || 
                        (row == blackKingRow && col == blackKingCol)){
                        continue;
                    }

                    validMoves[row][col] = true;
                }
            }
        }
    }

    /**
     * Given the potential moves row and column, checks if is a valid move
     * @param endRow potential move row to check
     * @param endCol potential move column to check
     * @return true if a valid move, false otherwise
     */
    public boolean isValidMove(int endRow, int endCol){
        return validMoves[endRow][endCol];
    }

    /** Sets all values in validMoves to false */
    public void resetAvailableMoves(){
        for(int i = 0; i < ARRAY_SIZE; i++){
            Arrays.fill(validMoves[i], false);
        }
    }

    /**
     * Calls canMove method for an object in Piece array
     * @param currentRow current row
     * @param currentCol current column
     * @param potentialRow new row 
     * @param potentialCol new column 
     * @return true if objects canMove method returns true, else returns false
     * @throws IllegalArgumentException if current row or col is out of bounds
     * @throws IllegalArgumentException if potential row or col is out of bounds
     */
    public boolean canMove(
        int currentRow, int currentCol, int potentialRow, int potentialCol){

        if(currentRow < 0 || currentRow >= ARRAY_SIZE || 
            currentCol < 0 || currentCol >= ARRAY_SIZE){
            throw new IllegalArgumentException("Invalid currnet row or col");
        }

        if(potentialRow < 0 || potentialRow >= ARRAY_SIZE || 
            potentialCol < 0 || potentialCol >= ARRAY_SIZE){
            throw new IllegalArgumentException("Invalid potential row or col");
        }

        Piece currentPiece = pieces[currentRow][currentCol];
        Piece potentialPiece = pieces[potentialRow][potentialCol];

        if(currentPiece == null){
            return false;
        }

        if(pieces[potentialRow][potentialCol] != null &&
            (currentPiece.isWhitePiece() == 
            potentialPiece.isWhitePiece())) {

            return false;
        }

        if(currentPiece instanceof Pawn && currentCol != potentialCol){
            Pawn p = (Pawn)currentPiece;

            if(pieces[potentialRow][potentialCol] == null || 
                currentPiece.isWhitePiece() == 
                potentialPiece.isWhitePiece()){

                return false;
            }

            return p.canAttack(potentialRow, potentialCol);
        }

        return currentPiece.canMove(potentialRow, potentialCol) &&
            isUnobstructed(currentPiece, potentialRow, potentialCol);
    }

    private boolean isUnobstructed(Piece p, int endRow, int endCol){

        int startRow = p.getRow();
        int startCol = p.getCol();

        boolean movingDown = isMovingDown(startRow, endRow);
        boolean movingRight = isMovingRight(startCol, endCol);

        if(p instanceof Knight){
            return true;
        }
        else if(p instanceof King){
            return true;            
        }
        else if(p instanceof Bishop){

            if(movingDown){

                if(movingRight){// down and right

                    for(int move = 1; move + startRow < endRow; move++){

                        if(doesObstruct(pieces[startRow + move][startCol + move])) 
                        { return false; }
                    }
                }
                else {//down and left

                    for(int move = 1; move + startRow < endRow; move++){

                        if(doesObstruct(pieces[startRow + move][startCol + move * -1])) 
                        { return false; }
                    }
                }
            }
            else {

                if(movingRight){//up and right

                    for(int move = 1; startRow - move > endRow; move++){

                        if(doesObstruct(pieces[startRow + move * -1][startCol + move])) 
                        { return false; }
                    }
                }
                else {//up and left

                    for(int move = 1; startRow - move > endRow; move++){

                        if(doesObstruct(pieces[startRow + move * -1][startCol + move * -1])) 
                        { return false; }
                    }
                }
            }
        }
        else if(p instanceof Queen){

            if(startRow == endRow){

                if(movingRight){

                    for(int col = startCol + 1; col < endCol; col++){

                        if(doesObstruct(pieces[startRow][col])) { return false; }
                    }
                }
                else {

                    for(int col = startCol - 1; col > endCol; col--){

                        if(doesObstruct(pieces[startRow][col])) { return false; }
                    }
                }
            }
            else if(startCol == endCol){

                if(movingDown){

                    for(int row = startRow + 1; row < endRow; row++){

                        if(doesObstruct(pieces[row][startCol])) { return false; }
                    }
                }
                else {

                    for(int row = startRow - 1; row > endRow; row--){

                        if(doesObstruct(pieces[row][startCol])) { return false; }
                    }
                }
            }
            else if(movingDown){

                if(movingRight){// down and right

                    for(int move = 1; move + startRow < endRow; move++){

                        if(doesObstruct(pieces[startRow + move][startCol + move])) 
                        { return false; }
                    }
                }
                else {//down and left

                    for(int move = 1; move + startRow < endRow; move++){

                        if(doesObstruct(pieces[startRow + move][startCol + move * -1])) 
                        { return false; }
                    }
                }
            }
            else {

                if(movingRight){//up and right

                    for(int move = 1; startRow - move > endRow; move++){

                        if(doesObstruct(pieces[startRow + move * -1][startCol + move])) 
                        { return false; }
                    }
                }
                else {//up and left

                    for(int move = 1; startRow - move > endRow; move++){

                        if(doesObstruct(pieces[startRow + move * -1][startCol + move * -1])) 
                        { return false; }
                    }
                }
            }
        }
        else if(p instanceof Pawn){

            if(movingDown){

                for(int row = startRow + 1; row <= endRow; row++){

                    if(doesObstruct(pieces[row][startCol])) { return false; }
                }
            }
            else {

                for(int row = startRow - 1; row >= endRow; row--){

                    if(doesObstruct(pieces[row][startCol])) { return false; }
                }
            }
        }
        else if(p instanceof Rook){
            
            if(startRow == endRow){

                if(movingRight){

                    for(int col = startCol + 1; col < endCol; col++){

                        if(doesObstruct(pieces[startRow][col])) { return false; }
                    }
                }
                else {

                    for(int col = startCol - 1; col > endCol; col--){

                        if(doesObstruct(pieces[startRow][col])) { return false; }
                    }
                }
            }
            else {

                if(movingDown){

                    for(int row = startRow + 1; row < endRow; row++){

                        if(doesObstruct(pieces[row][startCol])) { return false; }
                    }
                }
                else {

                    for(int row = startRow - 1; row > endRow; row--){

                        if(doesObstruct(pieces[row][startCol])) { return false; }
                    }
                }
            }
        }

        return true;
    }

    private boolean doesObstruct(Piece check){      
        
        // if(check.getRow() < 0 || check.getRow() >= ARRAY_SIZE ||
        //     check.getCol() < 0 || check.getRow() >= ARRAY_SIZE){

        //     return false;
        // }

        return check != null;
    }

    /**
     * Checks which direction piece is navigating through rows
     * @param startRow starting row
     * @param endRow ending row
     * @return true if startRow minus endRow is less than 0, false otherwise
     */
    private boolean isMovingDown(int startRow, int endRow){
        return startRow - endRow < 0;
    }

    /**
     * Checks which direction piece is navigating through columns
     * @param startCol starting row
     * @param endCol ending row
     * @return true if startCol minus endCol is less than 0, false otherwise
     */
    private boolean isMovingRight(int startCol, int endCol){
        return startCol - endCol < 0;
    }

    /**
     * Calls ChessBoard methods isNull and isCorrectSide
     * @param row pieces row to check
     * @param col pieces column to check
     * @param isWhiteTurn to match with piece isWhitePiece method
     * @return returns negated isCorrectSide with param or isNull
     * @throws IllegalArgumentException if row or col is out of bounds
     */
    public boolean canSelect(int row, int col, boolean isWhiteTurn){

        if(row < 0 || row >= ARRAY_SIZE || col < 0 || col >= ARRAY_SIZE){
            throw new IllegalArgumentException("Invalid row or col");
        }

        return !isCorrectSide(row, col, isWhiteTurn) || isNull(row, col);
    }

    /**
     * If the object at index
     *  row,col is null return true
     * @param row row parameter
     * @param col column parameter
     * @return returns true if object in array is null, else return false
     * @throws IllegalArgumentException if row or col is out of bounds
     */
    public boolean isNull(int row, int col){

        if(row < 0 || row >= ARRAY_SIZE || col < 0 || col >= ARRAY_SIZE){
            throw new IllegalArgumentException("Invalid row or col");
        }

        if(pieces[row][col] == null){
            return true;
        }

        return false;
    }

    /**
     * If object at [row][col] matches the turn returns true
     * @param row pieces row to check
     * @param col pieces column to check
     * @param isWhiteTurn to match with piece isWhitePiece method
     * @return true if booleans match, else false
     * @throws IllegalArgumentException if row or col is out of bounds
     */
    private boolean isCorrectSide(int row, int col, boolean isWhiteTurn){

        if(row < 0 || row >= ARRAY_SIZE || col < 0 || col >= ARRAY_SIZE){
            throw new IllegalArgumentException("Invalid row or col");
        }

        if(pieces[row][col] == null){
            return true;
        }

        return pieces[row][col].isWhitePiece() == isWhiteTurn;
    }

    /**
     * Sets the position of object at currentRow/Col to the newRow/Col
     * and makes the object at currentRow/Col to null
     * @param currentRow current Object Row
     * @param currentCol current Object Col
     * @param newRow new Object Row to set
     * @param newCol new object column to set
     * @throws IllegalArgumentException if current row/col is out of bounds
     * @throws IllegalArgumentException if new row/col is out of bounds
     */
    public void setPosition(int currentRow, int currentCol, int newRow, int newCol){

        if(currentRow < 0 || currentRow >= ARRAY_SIZE || 
            currentCol < 0 || currentCol >= ARRAY_SIZE){
            throw new IllegalArgumentException("Invalid current row or col");
        }

        if(newRow < 0 || newRow >= ARRAY_SIZE || newCol < 0 || newCol >= ARRAY_SIZE){
            throw new IllegalArgumentException("Invalid new row or col");
        }

        if(pieces[currentRow][currentCol] instanceof King){

            if(pieces[currentRow][currentCol].isWhitePiece()){
                whiteKingRow = newRow;
                whiteKingCol = newCol;
            }
            else {
                blackKingRow = newRow;
                blackKingCol = newCol;
            }
        }

        pieces[currentRow][currentCol].firstMove();
        pieces[currentRow][currentCol].setPosition(newRow, newCol);
        pieces[newRow][newCol] = pieces[currentRow][currentCol];
        pieces[currentRow][currentCol] = null;
    }

    /**
     * Checks if any piece in the pieces array is able to attack a king
     * and but them in check
     * @param side true to check white king, false to check black king
     * @return true is any opposing piece is able to put the king in check
     */
    public boolean isMate(boolean side){

        if(side){
            isWhiteMate = false;
        }
        else {
            isBlackMate = false;
        }

        for(int row = 0; row < ARRAY_SIZE; row++){

            for(int col = 0; col < ARRAY_SIZE; col++){

                if(canMove(row, col, getKingRow(side), getKingCol(side))) { 
                    if(side){
                        isWhiteMate = true;
                    }
                    else {
                        isBlackMate = true;
                    }
                    return true; 
                }
            }
        }

        return false;
    }

    /**
     * Checks around the king to see if it has an available move
     * @param side true to check white king, false to check black king
     * @return true if finds a spot that the king can move to, else false
     */
    public boolean canKingMove(boolean side){
        int[] rowMovement = {0, 0, 1, 1, 1, -1, -1, -1};//0: Right 1: Left 2:Down-Right Diag 3:Down
        int[] colMovement = {1, -1, 1, 0, -1, 1, 0, -1};//4:Down-Left 5:Up-Right 6: Up 7:Up-Left

        int kingRow = getKingRow(side);
        int kingCol = getKingCol(side);

        for(int i = 0; i < rowMovement.length; i++){

            if(canMove(kingRow, kingCol, rowMovement[i], colMovement[i])){
                return true;
            }
        }

        return false;
    }

    /**
     * Return kings row for the side
     * @param side true to get white king row, false for black king row
     * @return king row 
     */
    public int getKingRow(boolean side){
        if(side){
            return whiteKingRow;
        }
        return blackKingRow;
    }

    /**
     * Return kings column for the side
     * @param side true to get white king column, false for black king column
     * @return king column 
     */
    public int getKingCol(boolean side){
        if(side){
            return whiteKingCol;
        }
        return blackKingCol;
    }
}
