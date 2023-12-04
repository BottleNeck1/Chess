import java.io.File;

/**
 * Knight Class Object
 * 
 * @author David Martinez
 */
public class Knight extends Rook {
    
    /**
     * Knight Constructor
     * 
     * @param row row postion to set
     * @param col column position to set
     * @param isWhitePiece side the piece is on
     * @throws IllegalArgumentException if row or col is out of bounds
     */
    public Knight(int row, int col, boolean isWhitePiece){
        super(row, col, isWhitePiece);
        this.name = "N";
        this.whiteImgFile = new File("src/resources/WhiteKnight.png");
        this.blackImgFile = new File("src/resources/BlackKnight.png");
        this.row = row;
        this.col = col;
        this.isWhitePiece = isWhitePiece;
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
            (row + 2 == moveRow && (col + 1 == moveCol || col - 1 == moveCol)) ||
            (row - 2 == moveRow && (col + 1 == moveCol || col - 1 == moveCol)) ||
            (col + 2 == moveCol && (row + 1 == moveRow || row - 1 == moveRow)) ||
            (col - 2 == moveCol && (row + 1 == moveRow || row - 1 == moveRow))
            );
    }
}
