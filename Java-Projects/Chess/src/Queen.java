import java.io.File;

/**
 * Queen Class Object
 * 
 * @author David Martinez
 */
public class Queen extends Rook {

    /**
     * Bishop Constructor
     * 
     * @param row row postion to set
     * @param col column position to set
     * @param isWhitePiece side the piece is on
     * @throws IllegalArgumentException if row or col is out of bounds
     */
    public Queen(int row, int col, boolean isWhitePiece){
        super(row, col, isWhitePiece);
        this.name = "Q";
        this.whiteImgFile = new File("src/resources/WhiteQueen.png");
        this.blackImgFile = new File("src/resources/BlackQueen.png");
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
        
        Bishop b = new Bishop(row, col, isWhitePiece);
        Rook r = new Rook(row, col, isWhitePiece);

        return b.canMove(moveRow, moveCol) || r.canMove(moveRow, moveCol);
    }
}
