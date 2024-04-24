import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

/**
 * Chess Input/Output File
 * uses .pgn files
 * @author David Martinez
 */
public class ChessIO {
    
    /**
     * Reads a .pgn file (Chess game format), and returns an array of chess moves as strings
     * @param filename file to read
     * @return ArrayList of chess moves
     */
    public static ArrayList<String> readChessPGN(String filename) {

        int i = filename.lastIndexOf('.');
        if (i > 0) {
            if(!"pgn".equals(filename.substring(i + 1))){
                throw new IllegalArgumentException("Incorrect file type.");
            }
        }

        ArrayList<String> moves = new ArrayList<>();

        Scanner file = null;
        Scanner txtScanner = null;
        Scanner moveScanner = null;

        try {
            file = new Scanner(new FileInputStream(filename));
            file.useDelimiter("1. ");
            file.next();
            String fileTxt = "";

            while (file.hasNextLine()) {
                fileTxt += file.nextLine() + "\n";
            }

            String movesTxt = "";

            txtScanner = new Scanner(fileTxt);
            txtScanner.useDelimiter("\\d\\. |\\d\\d\\. |\\d\\d\\d\\. |\\d\\.\\.\\. |\\d\\d\\.\\.\\. |\\d\\d\\d\\.\\.\\. " +
                    "|\\d\\.\\n|\\d\\d\\.\\n|\\d\\d\\d\\.\\n|\\d\\.\\.\\.\\n|\\d\\d\\.\\.\\.\\n|\\d\\d\\d\\.\\.\\.\\n");

            while (txtScanner.hasNext()){
                movesTxt += txtScanner.next();
            }

            movesTxt = movesTxt.replaceAll("\\{.*?}", "").replaceAll("\\$\\d", "");

            moveScanner = new Scanner(movesTxt);

            while(moveScanner.hasNext()){
                moves.add(moveScanner.next());
            }

        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("Unable to read file: " + filename);
        } catch (Exception e) {
            throw new IllegalArgumentException("error reading the file: " + filename);
        } finally {
            if(file != null)
                file.close();
            if(txtScanner != null)
                txtScanner.close();
            if(moveScanner != null)
                moveScanner.close();
        }

        System.out.println(moves);

        return moves;
    }
    public static void saveChessPGN(String filename, ArrayList<String[]> moves){

        try (PrintWriter print = new PrintWriter(filename)) {

            print.print("[Event \"Name\"]\n[Site \"City, Region COUNTRY\"]\n[Date \"YYYY.MM.DD\"]\n[Round \"##\"]\n" +
                "[White \"Player Name\"]\n[Black \"Player Name\"]\n[Result \"1/2-1/2\"]\n");

            int round = 1;
            String result = ChessBoard.STALEMATE; //default to stalemate

            if(ChessBoard.WHITE_WIN.equals(moves.get(moves.size() - 1)[0]) || ChessBoard.BLACK_WIN.equals(moves.get(moves.size() - 1)[0])){
                result = moves.get(moves.size() - 1)[0];
            }
            else if(ChessBoard.WHITE_WIN.equals(moves.get(moves.size() - 1)[1]) || ChessBoard.BLACK_WIN.equals(moves.get(moves.size() - 1)[1])){
                result = moves.get(moves.size() - 1)[1];
            }

            print.println(String.format("[Result \"%s\"]\n", result));

            for (String[] move : moves) {
                print.println(String.format("%d. %s %s", round++, move[0], move[1] == null ? "" : move[1]));
            }
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("Cannot save file.");
        }
    }
}
