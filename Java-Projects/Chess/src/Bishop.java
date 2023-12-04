import java.io.File;

/**
 * Bishop Class Object
 * 
 * @author David Martinez
 */
public class Bishop extends Rook {

    /**
     * Bishop Constructor
     * 
     * @param row row postion to set
     * @param col column position to set
     * @param isWhitePiece side the piece is on
     * @throws IllegalArgumentException if row or col is out of bounds
     */
    public Bishop(int row, int col, boolean isWhitePiece){
        super(row, col, isWhitePiece);
        this.name = "B";
        this.whiteImgFile = new File("src/resources/WhiteBishop.png");
        this.blackImgFile = new File("src/resources/BlackBishop.png");
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
        
        return Math.abs(row - moveRow) == Math.abs(col - moveCol);
    }
}
