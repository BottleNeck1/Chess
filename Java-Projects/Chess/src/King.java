import java.io.File;

/**
 * King Class Object
 * 
 * @author David Martinez
 */
public class King extends Rook {

    /**
     * Bishop Constructor
     * 
     * @param row row postion to set
     * @param col column position to set
     * @param isWhitePiece side the piece is on
     * @throws IllegalArgumentException if row or col is out of bounds
     */
    public King(int row, int col, boolean isWhitePiece){
        super(row, col, isWhitePiece);
        this.name = "K";
        this.whiteImgFile = new File("src/resources/WhiteKing.png");
        this.blackImgFile = new File("src/resources/BlackKing.png");
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
            (row + 1 == moveRow && col == moveCol) ||
            (row - 1 == moveRow && col == moveCol) ||
            (col + 1 == moveCol && row == moveRow) ||
            (col - 1 == moveCol && row == moveRow) ||
            (row + 1 == moveRow && col + 1 == moveCol) ||
            (row + 1 == moveRow && col - 1 == moveCol) ||
            (row - 1 == moveRow && col + 1 == moveCol) ||
            (row - 1 == moveRow && col - 1 == moveCol)
            );
    }
}
