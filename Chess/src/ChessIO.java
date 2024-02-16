import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Scanner;

public class ChessIO {
    

    public static ArrayList<String[]> readChessPGN(String filename){

        ArrayList<String[]> moves = new ArrayList<>();

        try {
            Scanner file = new Scanner(new FileInputStream(filename));
            file.useDelimiter("1.");
            file.next();            
        } catch (Exception e) {
            // TODO: handle exception
        }

        return moves;
    }

    // private String makeLines(String movesString){

    // }
}
