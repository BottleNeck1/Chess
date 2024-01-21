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
    private boolean isWhiteCheck;

    /** Is Black King Under Check */
    private boolean isBlackCheck;

    // private boolean isCastl

    private Rook[] whiteRooks;

    private Rook[] blackRooks;

    /** ChessBoard Constructor */
    public ChessBoard(){

        //sets inital board
        setBoard();
    }

    /** Sets the chess board up for play */
    public void setBoard(){

        //sets all values in validMoves to false
        for(int i = 0; i < ARRAY_SIZE; i++){
            Arrays.fill(validMoves[i], false);
        }

        //sets Pawns
        for(int col = 0; col < ARRAY_SIZE; col++){
            pieces[1][col] = new Pawn(1, col, false);
            pieces[SIX_POS][col] = new Pawn(SIX_POS, col, true);
        }

        //sets Rooks
        blackRooks = new Rook[2];
        pieces[0][0] = new Rook(0, 0, false);
        pieces[0][SEVEN_POS] = new Rook(0, SEVEN_POS, false);
        blackRooks[0] = (Rook)pieces[0][0];
        blackRooks[1] = (Rook)pieces[0][SEVEN_POS];

        whiteRooks = new Rook[2];
        pieces[SEVEN_POS][0] = new Rook(SEVEN_POS, 0, true);        
        pieces[SEVEN_POS][SEVEN_POS] = new Rook(SEVEN_POS, SEVEN_POS, true);
        whiteRooks[0] = (Rook)pieces[SEVEN_POS][0];
        whiteRooks[1] = (Rook)pieces[SEVEN_POS][SEVEN_POS];

        //Sets Knights
        pieces[0][1] = new Knight(0, 1, false);
        pieces[0][SIX_POS] = new Knight(0, SIX_POS, false);
        pieces[SEVEN_POS][1] = new Knight(SEVEN_POS, 1, true);
        pieces[SEVEN_POS][SIX_POS] = new Knight(SEVEN_POS, SIX_POS, true);

        //Sets Bishops
        pieces[0][2] = new Bishop(0, 2, false);
        pieces[0][FIVE_POS] = new Bishop(0, FIVE_POS, false);
        pieces[SEVEN_POS][2] = new Bishop(SEVEN_POS, 2, true);
        pieces[SEVEN_POS][FIVE_POS] = new Bishop(SEVEN_POS, FIVE_POS, true);

        //Sets Kings
        pieces[0][FOUR_POS] = new King(0, FOUR_POS, false);
        blackKingRow = 0;
        blackKingCol = FOUR_POS;
        pieces[SEVEN_POS][FOUR_POS] = new King(SEVEN_POS, FOUR_POS, true);
        whiteKingRow = SEVEN_POS;
        whiteKingCol = FOUR_POS;        
        
        //Sets Queens
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

    /**
     * Gets the image icon at [row][col] 
     * @param row row
     * @param col column
     * @return accessed ImageIcon
     * @throws IllegalArgumentException if row or col is out of bounds
     */
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
     * @returns True if any valid move, otherwise return false
     * @throws IllegalArgumentException if row or col is out of bounds
     * @return return true if at least one valid move, false if no valid moves
     */
    public boolean setValidMoves(int startRow, int startCol){

        if(startRow < 0 || startRow >= ARRAY_SIZE || startCol < 0 || startCol >= ARRAY_SIZE){
            throw new IllegalArgumentException("Invalid row or col");
        }

        boolean hasValidMove = false;

        //iterates through pieces array to check if piece at [startRow][startCol] 
        //is able to move their (does not check for check)
        for(int row = 0; row < ARRAY_SIZE; row++){

            for(int col = 0; col < ARRAY_SIZE; col++){

                if(canMove(startRow, startCol, row, col)){//peice can move to [row][col]

                    //finds if the piece to move is white or black
                    boolean isWhitePiece = pieces[startRow][startCol].isWhitePiece();

                    boolean doContinue = false;
                    Piece temp;

                    // temp = pieces[row][col];
                    // pieces[row][col] = pieces[startRow][startCol];
                    // pieces[startRow][startCol] = null;

                    if(pieces[startRow][startCol] instanceof King){//Selected piece is king

                        //temp stores info from pieces[row][col]
                        temp = pieces[row][col];

                        //moves the King to the selected spot
                        pieces[row][col] = pieces[startRow][startCol];
                        pieces[startRow][startCol] = null;

                        if(isWhitePiece){//white side
                            
                            whiteKingRow = row;
                            whiteKingCol = col;

                            if(isCheck(true)){//if when the kings moves and it is now in check
                                doContinue = true; //it is invalid
                            }
                            whiteKingRow = startRow;
                            whiteKingCol = startCol;
                        }
                        else {//black side
                            blackKingRow = row;
                            blackKingCol = col;

                            if(isCheck(false)){
                                doContinue = true;
                            }
                            blackKingRow = startRow;
                            blackKingCol = startCol;
                        }

                        //resets the kings position to original spot
                        pieces[startRow][startCol] = pieces[row][col];
                        pieces[row][col] = temp;

                    }

                    //If Moving a king and does should not continue, all checks are performed and (dont go to below checks)
                    //and sets the valid move in the bool array and makes hasValidMove true
                    if(pieces[startRow][startCol] instanceof King && !doContinue){
                        validMoves[row][col] = true;
                        hasValidMove = true;
                        continue;
                    }
                    else if(doContinue){
                        continue;
                    }

                    //when moving a white piece and white king is in check
                    if(isWhitePiece && isWhiteCheck){

                        //move the selected piece to the desired [row][col]
                        //making the starting spot null and storing the piece at [row][col]
                        temp = pieces[row][col];
                        pieces[row][col] = pieces[startRow][startCol];
                        pieces[startRow][startCol] = null;

                        //check white side king for check
                        if(isCheck(true)){
                            doContinue = true;
                        }

                        //replace the moved pieces
                        pieces[startRow][startCol] = pieces[row][col];
                        pieces[row][col] = temp;
                    }
                    else if(!isWhitePiece && isBlackCheck){
                        
                        temp = pieces[row][col];
                        pieces[row][col] = pieces[startRow][startCol];
                        pieces[startRow][startCol] = null;

                        //check black side king for check
                        if(isCheck(false)){
                            doContinue = true;
                        }

                        pieces[startRow][startCol] = pieces[row][col];
                        pieces[row][col] = temp;
                    }

                    // pieces[startRow][startCol] = pieces[row][col];
                    // pieces[row][col] = temp;

                    if(doContinue){
                        continue;
                    }

                    // temp = pieces[row][col];
                    // pieces[row][col] = pieces[startRow][startCol];
                    // pieces[startRow][startCol] = null;

                    //check movement when same king is not in check
                    if((isWhitePiece && !isCheck(true)) ||
                        (!isWhitePiece && !isCheck(false))) {

                        temp = pieces[row][col];
                        pieces[row][col] = pieces[startRow][startCol];
                        pieces[startRow][startCol] = null;
                        

                        if(isWhitePiece){
                            
                            if(isCheck(true)){
                                doContinue = true;
                            }
                        }
                        else {

                            if(isCheck(false)){
                                doContinue = true;
                            }
                        }

                        pieces[startRow][startCol] = pieces[row][col];
                        pieces[row][col] = temp;
                    }

                    // pieces[startRow][startCol] = pieces[row][col];
                    // pieces[row][col] = temp;
                    
                    //if above are true dont add [row][col] to valid moves
                    if(doContinue){
                        continue;
                    }

                    //makes any piece unable to move to king positions
                    if((row == whiteKingRow && col == whiteKingCol) || 
                        (row == blackKingRow && col == blackKingCol)){
                        continue;
                    }

                    //makes [row][col] a valid move after checks
                    validMoves[row][col] = true;

                    //if at least one valid move if found set hasValidMove to true
                    hasValidMove = true;
                }
            }
        }

        return hasValidMove;
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
    public void resetAvailableMoves() {
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

        //for easy access to methods
        Piece currentPiece = pieces[currentRow][currentCol];
        Piece potentialPiece = pieces[potentialRow][potentialCol];

        //cant move a null piece
        if(currentPiece == null){
            return false;
        }

        //cant move current piece to a space occupied with same side
        if(pieces[potentialRow][potentialCol] != null &&
            (currentPiece.isWhitePiece() == 
            potentialPiece.isWhitePiece())) {

            return false;
        }

        //check for pawn attack
        if(currentPiece instanceof Pawn && currentCol != potentialCol){
            Pawn p = (Pawn)currentPiece;

            if(pieces[potentialRow][potentialCol] == null || 
                currentPiece.isWhitePiece() == 
                potentialPiece.isWhitePiece()){

                return false;
            }

            return p.canAttack(potentialRow, potentialCol);
        }

        if(currentPiece instanceof King && canCastle(currentPiece.isWhitePiece(), potentialRow, potentialCol)){
            return true;
        }

        //if potentionalRow/Col is a valid move and it is unobstructed, returns true
        return (currentPiece.canMove(potentialRow, potentialCol) &&
            isUnobstructed(currentPiece, potentialRow, potentialCol, false));
    }

    private boolean isUnobstructed(Piece p, int endRow, int endCol, boolean castling){

        //TODO: FIX KING CASTLING WHEN OBSTRUCTED

        //gets starting location
        int startRow = p.getRow();
        int startCol = p.getCol();

        //check how the moveing piece is moving
        boolean movingDown = isMovingDown(startRow, endRow);
        boolean movingRight = isMovingRight(startCol, endCol);

        if(p instanceof Knight){ //Knight does not get obstructed
            return true;
        }
        else if(p instanceof King && !castling){//King does not get obstructed (only moves 1 unit)

            // if(castling){//check king horizontal movement for castling

            // }
            // else {//King does not get obstructed (only moves 1 unit)
            //     return true;            
            // }

            return true;

        }
        else if(p instanceof Bishop){ //checks Bishop diagonal movements for occupied space

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
        else if(p instanceof Queen){ //Checks Queen diagonal, horizontal, and vertical movement
                                     //for occupied space

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
        else if(p instanceof Pawn){ //Checks pawn column for occupied space

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
        else if(p instanceof Rook || castling){ ///Checks rook horizonal or vertical movemet for 
                                    //occupied space
            
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

    /**
     * Checks if piece is not null
     * @param check piece object to check
     * @return true is check param is not null, else false
     */
    private boolean doesObstruct(Piece check){
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

        boolean isWhitePiece = pieces[currentRow][currentCol].isWhitePiece();
        boolean castling = false;

        

        //sets king position variables
        if(pieces[currentRow][currentCol] instanceof King){

            if(canCastle(isWhitePiece, newRow, newCol)){

                castling = true;
                boolean isMovingRight = isMovingDown(currentCol, newCol);

                //make king make thier first move
                pieces[currentRow][currentCol].firstMove();

                //make rook make their first move
                pieces[isWhitePiece ? SEVEN_POS : 0][isMovingRight ? SEVEN_POS : 0].firstMove();

                //set rook position and move it
                pieces[isWhitePiece ? SEVEN_POS : 0][isMovingRight ? SEVEN_POS : 0].setPosition(
                    isWhitePiece ? SEVEN_POS : 0, isMovingRight ? FIVE_POS : 3);

                pieces[isWhitePiece ? SEVEN_POS : 0][isMovingRight ? FIVE_POS : 3] = 
                    pieces[isWhitePiece ? SEVEN_POS : 0][isMovingRight ? SEVEN_POS : 0];
                pieces[isWhitePiece ? SEVEN_POS : 0][isMovingRight ? SEVEN_POS : 0] = null;

                //set king position
                pieces[isWhitePiece ? SEVEN_POS : 0][FOUR_POS].setPosition(
                    isWhitePiece ? SEVEN_POS : 0, isMovingRight ? SIX_POS : 2);

                pieces[isWhitePiece ? SEVEN_POS : 0][isMovingRight ? SIX_POS : 2] = 
                    pieces[isWhitePiece ? SEVEN_POS : 0][FOUR_POS];
                pieces[isWhitePiece ? SEVEN_POS : 0][FOUR_POS] = null;

                newCol = isMovingRight ? SIX_POS : 2;
            
            }
        
            if(isWhitePiece){
                whiteKingRow = newRow;
                whiteKingCol = newCol;
            }
            else {
                blackKingRow = newRow;
                blackKingCol = newCol;
            }        
        }

        if(castling){
            return;
        }

        //make piece make their first move is havnt already
        pieces[currentRow][currentCol].firstMove();

        //move the selected piece to new space
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
    public boolean isCheck(boolean side){

        //resets the king check boolean
        if(side){
            isWhiteCheck = false;
        }
        else {
            isBlackCheck = false;
        }

        //if the selected piece can move to king spot, return true
        for(int row = 0; row < ARRAY_SIZE; row++){

            for(int col = 0; col < ARRAY_SIZE; col++){

                if(canMove(row, col, getKingRow(side), getKingCol(side))) { 
                    if(side){
                        isWhiteCheck = true;
                    }
                    else {
                        isBlackCheck = true;
                    }
                    return true; 
                }
            }
        }

        //if cant move to king, return false
        return false;
    }

    /**
     * Checks if a king is checkmate
     * @param side what king to check for
     * @return true if king is in checkmate, else false
     */
    public boolean isCheckMate(boolean side){

        if(side){

            if(!isWhiteCheck) { return false; }
        }
        else {

            if(!isBlackCheck) { return false; }
        }

        boolean canKingMove = setValidMoves(getKingRow(side), getKingCol(side));

        boolean canKingBeSaved = false;

        for(int row = 0; row < ARRAY_SIZE; row++) {

            for(int col = 0; col < ARRAY_SIZE; col++) {

                if(pieces[row][col] == null) { continue; }
                if(pieces[row][col].isWhitePiece() != side) { continue; }

                if(setValidMoves(row, col)) { canKingBeSaved = true; }
            }
        }

        return !canKingMove && !canKingBeSaved;
    }

    /**
     * Checks around the king to see if it has an available move
     * @param side true to check white king, false to check black king
     * @return true if finds a spot that the king can move to, else false
     */
    public boolean canKingMove(boolean side){
        int[] rowMovement = {0, 0, 1, 1, 1, -1, -1, -1}; //0: Right 1: Left 2:Down-Right Diag 3:Down
        int[] colMovement = {1, -1, 1, 0, -1, 1, 0, -1}; //4:Down-Left 5:Up-Right 6: Up 7:Up-Left

        int kingRow = getKingRow(side);
        int kingCol = getKingCol(side);

        //Checks if king has any available move
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

    /**
     * Check if pawn can be promoted
     * @param startRow current row
     * @param startCol current col
     * @param endRow potential row
     * @param isWhiteSide what side the piece is on
     * @return if pawn can be promoted return true, else return false
     */
    public boolean canPromote(int startRow, int startCol, int endRow, boolean isWhiteSide){

        if(!(pieces[startRow][startCol] instanceof Pawn)){
            return false;
        }

        if(isWhiteSide){

            if(endRow == 0){
                return true; 
            }
        }
        else {

            if(endRow == SEVEN_POS){
                return true;
            }
        }

        return false;
    }

    /**
     * Promote a pawn to chosen piece
     * @param type what piece to promote to
     * @param row row to set
     * @param col col to set
     * @thorws IllegalArgumentException if type is invalid
     */
    public void promote(String type, int row, int col){

        boolean isWhitePiece = pieces[row][col].isWhitePiece();
        
        switch(type) {
            case "Queen":
            pieces[row][col] = new Queen(row, col, isWhitePiece);
            break;
            case "Knight":
            pieces[row][col] = new Knight(row, col, isWhitePiece);
            break;
            case "Bishop":
            pieces[row][col] = new Bishop(row, col, isWhitePiece);
            break;
            case "Rook":
            pieces[row][col] = new Rook(row, col, isWhitePiece);
            break;
            default:
            throw new IllegalArgumentException("Invalid promotion.");
        }
    }

    /**
     * Returns true is the move can be a possible castle and if it is
     * not blocked by other pieces
     * @param isWhiteSide side the piece is on
     * @param moveRow piece row
     * @param moveCol piece col
     * @return true is valid castle move, else false
     */
    private boolean canCastle(boolean isWhiteSide, int moveRow, int moveCol){

        if(isWhiteSide ? !whiteRooks[0].isFirstMove && !whiteRooks[1].isFirstMove : 
            !blackRooks[0].isFirstMove && !blackRooks[1].isFirstMove){
                return false;
        }//checks if rooks have moved, if they did return false

        King k = (King)pieces[isWhiteSide ? whiteKingRow : blackKingRow][isWhiteSide ? whiteKingCol : blackKingCol];
        
        if(k.getRow() != moveRow){
            return false;
        }

        if(k.isWhitePiece()){
            if(isWhiteCheck){
                return false;
            }
        }
        else {
            if(isBlackCheck){
                return false;
            }
        }

        if(isMovingRight(k.getCol(), moveCol)){

            for(int col = k.getCol(); col < SEVEN_POS; col++){

                if(col == k.getCol()){
                    continue;
                }

                if(pieces[k.getRow()][col] != null){
                    return false;
                }
            }
        }
        else {

            for(int col = k.getCol(); col > 0; col--){

                if(col == k.getCol()){
                    continue;
                }

                if(pieces[k.getRow()][col] != null){
                    return false;
                }
            }
        }

        return k.isPossibleCastle(moveRow, moveCol) && isUnobstructed(k, moveRow, moveCol, true) &&
            pieces[moveRow][moveCol] == null;   
    }
}
