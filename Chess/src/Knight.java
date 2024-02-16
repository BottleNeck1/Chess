/**
 * Knight Class Object
 * row
 * @author David Martinez
 */
public class Knight extends Piece {

    /** Name for piece */
    private static final String NAME = "Knight";

    /** White Piece File Path */
    private static final String WHITE_IMG_PATH = "src/resources/WhiteKnight.png";

    /** Black Piece File Path */
    private static final String BLACK_IMG_PATH = "src/resources/BlackKnight.png";

    /**
     * Knight Constructor
     * 
     * @param row row postion to set
     * @param col column position to set
     * @param isWhitePiece side the piece is on
     * @throws IllegalArgumentException if row or col is out of bounds
     */
    public Knight(int row, int col, boolean isWhitePiece){
        super(row, col, isWhitePiece, NAME, WHITE_IMG_PATH, BLACK_IMG_PATH);
    }

    public Knight(Knight other){
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
        
        return (
            (getRow() + 2 == moveRow && (getCol() + 1 == moveCol || getCol() - 1 == moveCol)) ||
            (getRow() - 2 == moveRow && (getCol() + 1 == moveCol || getCol() - 1 == moveCol)) ||
            (getCol() + 2 == moveCol && (getRow() + 1 == moveRow || getRow() - 1 == moveRow)) ||
            (getCol() - 2 == moveCol && (getRow() + 1 == moveRow || getRow() - 1 == moveRow))
            );
    }
}
