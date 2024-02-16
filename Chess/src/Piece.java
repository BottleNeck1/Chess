import java.awt.Image;
import java.io.File;

import javax.imageio.ImageIO;

/**
 * Piece Abstract Class
 * Superclass for Chess Pieces
 * 
 * @author David Martinez
 */
public abstract class Piece {

    /** Name for piece */
    private String name;
    
    /** Row Position */
    private int row;

    /** Column Position */
    private int col;

    /** Which Side Instance Piece is on */
    private boolean isWhitePiece;

    /** Maximum board index */
    public static final int MAX_INDEX = 7;

    /** Each piece starts off with isFirstMove to true */
    private boolean isFirstMove;

    /** White Piece File Path */
    protected File whiteImgFile;

    /** Black Piece File Path */
    protected File blackImgFile;

    /**
     * Constructor For Piece Type
     * @param row piece row
     * @param col piece column
     * @param isWhitePiece piece side
     * @param name piece name
     * @param whiteImgFileName piece white img path
     * @param blackImgFileName piece black img path
     */
    public Piece(int row, int col, boolean isWhitePiece, String name, String whiteImgFileName, String blackImgFileName){
        setRow(row);
        setCol(col);
        setIsWhitePiece(isWhitePiece);
        setName(name);
        setImgFiles(whiteImgFileName, blackImgFileName);
        this.isFirstMove = true;
    }

    public Piece(Piece other){
        this.row = other.row;
        this.col = other.col;
        this.isWhitePiece = other.isWhitePiece;
        this.isFirstMove = other.isFirstMove;
        this.name = other.name;
        this.whiteImgFile = other.whiteImgFile;
        this.blackImgFile = other.blackImgFile;
    }

    /** 
     * Accesor for Piece Name
     * @return Piece Name
     */
    public String getName(){
        return name;
    }

    /**
     * Set Name
     * @param name name
     */
    public void setName(String name){

        if(name == null || "".equals(name)){
            throw new IllegalArgumentException("Name is null or empty");
        }

        this.name = name;
    }

    /**
     * Checks for valid movement
     * @param moveRow row movement check
     * @param moveCol col movement check
     * @return true if valid move, false otherwise
     */
    public abstract boolean canMove(int moveRow, int moveCol);

    /**
     * Set row and column position for object instance
     * @param row new row to set
     * @param col new column to set
     * @throws IllegalArgumentException if row or col is out of bounds
     */
    public void setPosition(int row, int col){
        if(row < 0 || row > MAX_INDEX || col < 0 || col > MAX_INDEX){
            throw new IllegalArgumentException("Invalid row or col");
        }

        this.row = row;
        this.col = col;
    }

    /**
     * Accesor method for isWhitePiece
     * @return boolean value of isWhitePiece
     */
    public boolean isWhitePiece(){
        return isWhitePiece;
    }

    /**
     * Set isWhitePiece
     * @param isWhitePiece isWhitePiece
     */
    public void setIsWhitePiece(boolean isWhitePiece){
        this.isWhitePiece = isWhitePiece;
    }

    /** Sets isFirstMove 
     * @param isFirstMove isFirstMove
     */
    public void setFirstMove(boolean isFirstMove){
        this.isFirstMove = isFirstMove;
    }

    /** 
     * Returns isFirstMove
     * @return isFirstMove
     */
    public boolean isFirstMove(){
        return isFirstMove;
    }

    /**
     * Get the row for the object instance
     * @return row field
     */
    public int getRow() {
        return this.row;
    }

    /**
     * Setter Method For Row
     * @param row row
     */
    public void setRow(int row){
        if(row < 0 || row > MAX_INDEX){
            throw new IllegalArgumentException("Invalid Row or Col");
        }

        this.row = row;
    }

    /**
     * Setter Method For Row
     * @param col col
     */
    public void setCol(int col){
        if(col < 0 || col > MAX_INDEX){
            throw new IllegalArgumentException("Invalid Row or Col");
        }

        this.col = col;
    }

    /**
     * Get the column for the object instance
     * @return col field
     */
    public int getCol() {
        return this.col;
    }

    /**
     * Set Image Files for white and black side
     * @param whiteImgFileName file path for white image
     * @param blackImgFileName file path for black image
     */
    public void setImgFiles(String whiteImgFileName, String blackImgFileName){

        if(whiteImgFileName == null || "".equals(whiteImgFileName)){
            throw new IllegalArgumentException("File name is null or empty");
        }

        if(blackImgFileName == null || "".equals(blackImgFileName)){
            throw new IllegalArgumentException("File name is null or empty");
        }

        whiteImgFile = new File(whiteImgFileName);
        blackImgFile = new File(blackImgFileName);
    }

    /** 
     * Find image from file and return it
     * @return Image
     */
    public Image getImage(){
        try {
            Image img = ImageIO.read(isWhitePiece ? whiteImgFile : blackImgFile);
            return img;
        } catch (Exception e) {
            return null;
        }
    }
}