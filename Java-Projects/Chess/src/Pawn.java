import java.io.File;

/**
 * Pawn Class Object
 * 
 * @author David Martinez
 */
public class Pawn extends Rook {

    /**
     * Bishop Constructor
     * 
     * @param row row postion to set
     * @param col column position to set
     * @param isWhitePiece side the piece is on
     * @throws IllegalArgumentException if row or col is out of bounds
     */
    public Pawn(int row, int col, boolean isWhitePiece){
        super(row, col, isWhitePiece);
        this.name = "Pawn";
        this.whiteImgFile = new File("src/resources/WhitePawn.png");
        this.blackImgFile = new File("src/resources/BlackPawn.png");
        this.row = row;
        this.col = col;
        this.isWhitePiece = isWhitePiece;
        this.isFirstMove = true;
    }

    /**
     * Checks moveRow and moveCol for valid movement
     * @param moveRow new row to check 
     * @param moveCol new col to check
     * @return true if a valid movement, false otherwise
     * @throws IllegalArgumentException if row or col is out of bounds
     */
    public boolean canMove (int moveRow, int moveCol){

        if(moveRow < 0 || moveRow > MAX_INDEX){
            throw new IllegalArgumentException("Invalid Row or Col");
        }

        if(isWhitePiece){
            return (
                (row - 1 == moveRow && col == moveCol) ||
                (row - 2 == moveRow && col == moveCol && isFirstMove)
                );
        } 
        else {
            return (
                (row + 1 == moveRow && col == moveCol) ||
                (row + 2 == moveRow && col == moveCol && isFirstMove)
                );
        }
    }

    /**
     * Checks attackRow and attackCol for valid attack
     * @param attackRow attacking row to check
     * @param attackCol attacking col to check
     * @return true if valid attack, false otherwise
     * @throws IllegalArgumentException if row or col is out of bounds
     */
    public boolean canAttack(int attackRow, int attackCol){

        if(attackRow < 0 || attackRow > MAX_INDEX || attackCol < 0 || attackCol > MAX_INDEX){
            throw new IllegalArgumentException("Invalid Row or Col");
        }   

        if(isWhitePiece){
            return (
                (row - 1 == attackRow && col + 1 == attackCol) ||
                (row - 1 == attackRow && col - 1 == attackCol)
                );
        }
        else {
            return (
                (row + 1 == attackRow && col + 1 == attackCol) ||
                (row + 1 == attackRow && col - 1 == attackCol)
                );
        }
    }
}
