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

    /** 7 Grid Position */
    private static final int SEVEN_POS = 7;

    /** 6 Grid Position */
    private static final int SIX_POS = 6;

    /** 5 Grid Position */
    private static final int FIVE_POS = 5;

    /** 4 Grid Position */
    private static final int FOUR_POS = 4;

    /** ChessBoard Constructor */
    public ChessBoard(){

        setBoard();
    }

    /** Sets the chess board up for play */
    public void setBoard(){
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

        pieces[0][3] = new King(0, 3, false);
        pieces[SEVEN_POS][3] = new King(SEVEN_POS, 3, true);
        
        pieces[0][FOUR_POS] = new Queen(0, FOUR_POS, false);
        pieces[SEVEN_POS][FOUR_POS] = new Queen(SEVEN_POS, FOUR_POS, true);
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

        if(pieces[currentRow][currentCol] == null){
            return false;
        }

        if(pieces[potentialRow][potentialCol] != null &&
            (pieces[currentRow][currentCol].isWhitePiece() == 
            pieces[potentialRow][potentialCol].isWhitePiece())) {

            return false;
        }

        if(pieces[currentRow][currentCol] instanceof Pawn && currentCol != potentialCol){
            Pawn p = (Pawn)pieces[currentRow][currentCol];

            if(pieces[potentialRow][potentialCol] == null || 
                pieces[currentRow][currentCol].isWhitePiece() == 
                pieces[potentialRow][potentialCol].isWhitePiece()){

                return false;
            }

            return p.canAttack(potentialRow, potentialCol);
        }

        return pieces[currentRow][currentCol].canMove(potentialRow, potentialCol) &&
            isUnobstructed(pieces[currentRow][currentCol], potentialRow, potentialCol);
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
     * If the object at index row,col is null return true
     * @param row row parameter
     * @param col column parameter
     * @return returns true if object in array is null, else return false
     * @throws IllegalArgumentException if row or col is out of bounds
     */
    private boolean isNull(int row, int col){

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

        pieces[currentRow][currentCol].firstMove();
        pieces[currentRow][currentCol].setPosition(newRow, newCol);
        pieces[newRow][newCol] = pieces[currentRow][currentCol];
        pieces[currentRow][currentCol] = null;
    }    
}
