/**
 * Move Class For Storing Chess Moves
 * @author David Martinez
 */
public class Move {
    
    /** Move Starting Row */
    private int startRow;

    /** Move Starting Col */
    private int startCol;

    /** Move Ending Row */
    private int endRow;

    /** Move Ending COl */
    private int endCol;

    /**
     * Move Constructor
     * @param startRow start row
     * @param startCol start col
     * @param endRow end row
     * @param endCol end col
     */
    public Move(int startRow, int startCol, int endRow, int endCol){
        this.startRow = startRow;
        this.startCol = startCol;
        this.endRow = endRow;
        this.endCol = endCol;
    }

    /**
     * Getter For startRow
     * @return startRow
     */
    public int getStartRow() {
        return startRow;
    }

    /**
     * Getter For startCol
     * @return startCol
     */
    public int getStartCol() {
        return startCol;
    }

    /**
     * Getter For endRow
     * @return endRow
     */
    public int getEndRow() {
        return endRow;
    }

    /**
     * Getter for endCol
     * @return endCol
     */
    public int getEndCol() {
        return endCol;
    }
    
    /** Convert fields in move to a string
     * @return formatted string StartRow:%d StartCol:%d EndRow:%d EndCol:%d
     */
    public String toString(){
        return String.format("StartRow:%d\nStartCol:%d\nEndRow:%d\nEndCol:%d\n", startRow, startCol, endRow, endCol);
    }
}
