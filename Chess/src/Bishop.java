/**
 * Bishop Class Object
 * 
 * @author David Martinez
 */
public class Bishop extends Piece {

    /** Name for piece */
    private static final String NAME = "B";

    /** White Piece File Path */
    private static final String WHITE_IMG_PATH = "src/resources/WhiteBishop.png";

    /** Black Piece File Path */
    private static final String BLACK_IMG_PATH = "src/resources/BlackBishop.png";

    /**
     * Bishop Constructor
     * 
     * @param row row postion to set
     * @param col column position to set
     * @param isWhitePiece side the piece is on
     * @throws IllegalArgumentException if row or col is out of bounds
     */
    public Bishop(int row, int col, boolean isWhitePiece){
        super(row, col, isWhitePiece, NAME, WHITE_IMG_PATH, BLACK_IMG_PATH);
    }

    public Bishop(Bishop other){
        super(other);
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
        
        return Math.abs(getRow() - moveRow) == Math.abs(getCol() - moveCol);
    }
}
