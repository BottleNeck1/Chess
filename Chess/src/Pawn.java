/**
 * Pawn Class Object
 * 
 * @author David Martinez
 */
public class Pawn extends Piece {

    /** Name for piece */
    private static final String NAME = "P";

    /** White Piece File Path */
    private static final String WHITE_IMG_PATH = "src/resources/WhitePawn.png";

    /** Black Piece File Path */
    private static final String BLACK_IMG_PATH = "src/resources/BlackPawn.png";

    /** Starting Pawn Row */
    private int initialRow;

    /** Can the pawn be attacked by EnPassant */
    private boolean canEnPassant;

    /**
     * Bishop Constructor
     * 
     * @param row row postion to set
     * @param col column position to set
     * @param isWhitePiece side the piece is on
     * @throws IllegalArgumentException if row or col is out of bounds
     */
    public Pawn(int row, int col, boolean isWhitePiece){
        super(row, col, isWhitePiece, NAME, WHITE_IMG_PATH, BLACK_IMG_PATH);
        this.initialRow = row;
        this.canEnPassant = false;
    }

    public Pawn(Pawn other){
        super(other);
        this.initialRow = other.initialRow;
        this.canEnPassant = other.canEnPassant;
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

        if(isWhitePiece()){
            return (
                (getRow() - 1 == moveRow && getCol() == moveCol) ||
                (getRow() - 2 == moveRow && getCol() == moveCol && isFirstMove())
                );
        } 
        else {
            return (
                (getRow() + 1 == moveRow && getCol() == moveCol) ||
                (getRow() + 2 == moveRow && getCol() == moveCol && isFirstMove())
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

        if(isWhitePiece()){
            return (
                (getRow() - 1 == attackRow && getCol() + 1 == attackCol) ||
                (getRow() - 1 == attackRow && getCol() - 1 == attackCol)
                );
        }
        else {
            return (
                (getRow() + 1 == attackRow && getCol() + 1 == attackCol) ||
                (getRow() + 1 == attackRow && getCol() - 1 == attackCol)
                );
        }
    }

    /**
     * Check if a piece can attack the pawn by En Passant
     * can En Passant if pawn is on its second move
     * @param attackRow attack row
     * @param attackCol attack col
     * @return true if the piece can attack the pawn by En Passant, else false
     */
    public boolean canEnPassant(int attackRow, int attackCol){

        // if(this.col != attackCol || !canEnPassant){
        //     return false;
        // }

        // if(Math.abs(attackRow - initialRow) == 1 && Math.abs(this.row - initialRow) == 2 && !isFirstMove){
        //     return true;
        // }

        if(!canEnPassant || isFirstMove()){
            return false;
        }

        if(Math.abs(getRow() - initialRow) != 2){
            return false;
        }

        if(getRow() != attackRow || Math.abs(attackCol - getCol()) != 1){
            return false;
        }

        return true;
    }

    /**
     * Set if the pawn can be En Passant
     * @param set what to set
     */
    public void setEnPassant(boolean set){
        canEnPassant = set;
    }

    /**
     * Return field canEnPassant
     * @return canEnPassant
     */
    public boolean getCanEnPassant(){
        return canEnPassant;
    }

    
}
