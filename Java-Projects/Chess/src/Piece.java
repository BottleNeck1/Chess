/**
 * Piece Interface
 * 
 * @author David Martinez
 */
public interface Piece {

    /** 
     * Accesor for Piece Name
     * @return Piece Name
     */
    public String getName();

    /**
     * Checks for valid movement
     * @param moveRow row movement check
     * @param moveCol col movement check
     * @return true if valid move, false otherwise
     */
    public boolean canMove(int moveRow, int moveCol);

    /**
     * Sets piece position
     * @param row new row position to set
     * @param col new column position to set
     */
    public void setPosition(int row, int col);

    /**
     * Assesor method for isWhitePiece
     * @return isWhitePiece
     */
    public boolean isWhitePiece();

    /** Sets isFirstMove to false */
    public void firstMove();

    /**
     * Accesor method for instance field row
     * @return row
     */
    public int getRow();

    /**
     * Accesor method for instance field col
     * @return col
     */
    public int getCol();
}
