/**
 * Rook Class Object
 * 
 * @author David Martinez
 */
public class Rook implements Piece {
    
    /** Name for piece */
    protected String name = "R";

    /** Row Position */
    protected int row;

    /** Column Position */
    protected int col;

    /** Which Side Instance Piece is on */
    protected boolean isWhitePiece;

    /** Maximum board index */
    protected static final int  MAX_INDEX = 7;

    /** Each piece starts off with isFirstMove to true */
    protected boolean isFirstMove = true;

    /**
     * Rook Constructor
     * 
     * @param row row postion to set
     * @param col column position to set
     * @param isWhitePiece side the piece is on
     * @throws IllegalArgumentException if row or col is out of bounds
     */
    public Rook(int row, int col, boolean isWhitePiece){

        if(row < 0 || row > MAX_INDEX || col < 0 || col > MAX_INDEX){
            throw new IllegalArgumentException("Invalid Row or Col");
        }

        this.row = row;
        this.col = col;
        this.isWhitePiece = isWhitePiece;
    }

    /** Sets isFirstMove to false */
    public void firstMove(){
        this.isFirstMove = false;
    }

    /**
     * Get the name for object
     * @return static name
     */
    public String getName(){
        return name;
    }

    /**
     * Get the row for the object instance
     * @return row field
     */
    public int getRow() {
        return this.row;
    }

    /**
     * Get the column for the object instance
     * @return col field
     */
    public int getCol() {
        return this.col;
    }

    /**
     * Set row and column position for object instance
     * @param row new row to set
     * @param col new column to set
     * @throws IllegalArgumentException if row or col is out of bounds
     */
    public void setPosition(int row, int col){
        if(row < 0 || row > MAX_INDEX || col < 0 || col > MAX_INDEX){
            throw new IllegalArgumentException("Invalid row or col");
        }

        this.row = row;
        this.col = col;
    }

    /**
     * Accesor method for isWhitePiece
     * @return boolean value of isWhitePiece
     */
    public boolean isWhitePiece(){
        return isWhitePiece;
    }

    /**
     * Checks moveRow and moveCol for valid movement
     * @param moveRow new row to check 
     * @param moveCol new column to check
     * @return true if a valid movement, false otherwise
     * @throws IllegalArgumentException if row or col is out of bounds
     */
    public boolean canMove(int moveRow, int moveCol){

        if(moveRow < 0 || moveRow > MAX_INDEX || moveCol < 0 || moveCol > MAX_INDEX){
            throw new IllegalArgumentException("Invalid Row or Col");
        }

        return row == moveRow || col == moveCol;
    }
}