/**
 * Rook Class Object
 * 
 * @author David Martinez
 */
public class Rook extends Piece {
    
    /** Name for piece */
    private static final String NAME = "Rook";

    /** White Piece File Path */
    private static final String WHITE_IMG_PATH = "src/resources/WhiteRook.png";

    /** Black Piece File Path */
    private static final String BLACK_IMG_PATH = "src/resources/BlackRook.png";

    /**
     * Rook Constructor
     * 
     * @param row row postion to set
     * @param col column position to set
     * @param isWhitePiece side the piece is on
     * @throws IllegalArgumentException if row or col is out of bounds
     */
    public Rook(int row, int col, boolean isWhitePiece){

        super(row, col, isWhitePiece, NAME, WHITE_IMG_PATH, BLACK_IMG_PATH);
    }

    /**
     * Checks moveRow and moveCol for valid movement
     * @param moveRow new row to check 
     * @param moveCol new column to check
     * @return true if a valid movement, false otherwise
     * @throws IllegalArgumentException if row or col is out of bounds
     */
    public boolean canMove(int moveRow, int moveCol){

        if(moveRow < 0 || moveRow > Piece.MAX_INDEX || moveCol < 0 || moveCol > MAX_INDEX){
            throw new IllegalArgumentException("Invalid Row or Col");
        }

        return getRow() == moveRow || getCol() == moveCol;
    }
}