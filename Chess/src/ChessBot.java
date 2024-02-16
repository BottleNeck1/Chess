import java.util.ArrayList;
import java.util.Random;

/**
 * Chess Bot
 * Utilizes minimax algorithm
 * @author David Martinez
 */
public class ChessBot {
    
    /** Optimal move to return */
    private Move moveChosen;

    /** Number of Moves that were checked */
    private int movesChecked;

    /** How far to check in the future board states */
    private int depth;

    /** Starting Evaluation */
    private int startEvaluation;

    /** Pawn Value */
    private static final int PAWN_VALUE = 10;

    /** Bishop and Knight Value */
    private static final int BISHOP_KNIGHT_VALUE = 30;

    /** Rook Value */
    private static final int ROOK_VALUE = 50;

    /** Queen Value */
    private static final int QUEEN_VALUE = 90;

    /** King Value */
    private static final int KING_VALUE = 900;

    /** Minimum Possible Evaluation Of Chess Board */
    private static final int MIN_EVALUATION = -900;

    /** Maximum Possible Evaluation Of Chess Board */
    private static final int MAX_EVALUATION = 900;

    // /** Point Evaluation of Current Board Layout */
    // private int evaluation;

    ChessBot(){
    }

    /**
     * Find the best move on the board for the given side
     * @param chessBoard board instance
     * @param depth depth to search
     * @return the best move
     */
    public Move findBestMove(ChessBoard chessBoard, int depth){
        this.depth = depth;
        this.startEvaluation = evaluateBoard(chessBoard);
        this.movesChecked = 0;
        
        minimax(chessBoard, depth, false);

        // System.out.println(moveChosen.toString());
        // System.out.println("\nReturn:\n" + chessBoard.toString());
        return moveChosen;
    }

    private int evaluateBoard(ChessBoard chessBoard){

        int evaluation = 0;

        for(int row = 0; row < ChessBoard.ARRAY_SIZE; row++){

            for(int col = 0; col < ChessBoard.ARRAY_SIZE; col++){

                if(chessBoard.isNull(row, col)) { continue; }

                Piece p = chessBoard.getPiece(row, col);

                if(p instanceof Pawn){
                    evaluation += p.isWhitePiece() ? PAWN_VALUE : PAWN_VALUE * -1;
                }
                if(p instanceof Bishop || p instanceof Knight){
                    evaluation += p.isWhitePiece() ? BISHOP_KNIGHT_VALUE : BISHOP_KNIGHT_VALUE * -1;
                }
                if(p instanceof Rook){
                    evaluation += p.isWhitePiece() ? ROOK_VALUE : ROOK_VALUE * -1;
                }
                if(p instanceof Queen){
                    evaluation += p.isWhitePiece() ? QUEEN_VALUE : QUEEN_VALUE * -1;
                }
                if(p instanceof King){
                    evaluation += p.isWhitePiece() ? KING_VALUE : KING_VALUE * -1;
                }
            }
        }

        return evaluation;
    }

    private int minimax(ChessBoard chessBoard, int depth, boolean isMaximizing){

        //ChessBoard editBoard = new ChessBoard(chessBoard);

        ArrayList<Move> allPossibleMoves = chessBoard.allPossibleMoves();

        if(depth == 0 || chessBoard.allPossibleMoves().size() == 0){
            return evaluateBoard(chessBoard);
        }

        Move bestMove = allPossibleMoves.get(0);
        int bestEvaluation = isMaximizing ? MIN_EVALUATION : MAX_EVALUATION;

        if(isMaximizing){
            bestEvaluation = MIN_EVALUATION;
            bestMove = allPossibleMoves.get(0);

            for (Move m : allPossibleMoves) {
                movesChecked++;
                ChessBoard boardState = new ChessBoard(chessBoard);
                //System.out.println("\nPre:" + boardState.toString() + "\n");
                chessBoard.setMove(m);
                //System.out.println("Move:" + chessBoard.toString() + "\n");
                int evaluation = minimax(chessBoard, depth - 1, false);
                //chessBoard.revertMove(m);
                chessBoard.setBoardState(boardState);
                //System.out.println("Reset:" + chessBoard.toString() + "\n");

                if(evaluation > bestEvaluation){
                    bestEvaluation = evaluation;
                    bestMove = m;
                }
            }

            // moveChosen = bestMove;
            // return bestEvaluation;
        }
        else {
            bestEvaluation = MAX_EVALUATION;
            bestMove = allPossibleMoves.get(0);

            for (Move m : allPossibleMoves) {
                movesChecked++;
                ChessBoard boardState = new ChessBoard(chessBoard);
                //System.out.println("Pre:\n" + boardState.toString() + "\n");
                chessBoard.setMove(m);
                //System.out.println("Move:\n" + chessBoard.toString() + "\n");
                int evaluation = minimax(chessBoard, depth - 1, true);
                chessBoard.setBoardState(boardState);
                //System.out.println("Reset:\n" + chessBoard.toString() + "\n");

                if(evaluation < bestEvaluation){
                    bestEvaluation = evaluation;
                    bestMove = m;
                }
            }

            // moveChosen = bestMove;
            // return bestEvaluation;
        }

        if(this.depth == depth && startEvaluation == bestEvaluation){
            bestMove = allPossibleMoves.get(new Random().nextInt(allPossibleMoves.size() - 1));
        }

        moveChosen = bestMove;
        return bestEvaluation;
    }

    /**
     * Return the amount of moves that have been checked
     * @return movesChecked
     */
    public int getMovesChecked(){
        return this.movesChecked;
    }

    // private ArrayList<Move> allPossibleMoves(ChessBoard chessBoard){

    //     ArrayList<Move> moves = new ArrayList<>();

    //     for(int startRow = 0; startRow < ChessBoard.ARRAY_SIZE; startRow++){

    //         for(int startCol = 0; startCol < ChessBoard.ARRAY_SIZE; startCol++){

    //             chessBoard.resetAvailableMoves();

    //             if(!chessBoard.setValidMoves(startRow, startCol)) { continue; }

    //             for(int endRow = 0; endRow < ChessBoard.ARRAY_SIZE; endRow++){

    //                 for(int endCol = 0; endCol < ChessBoard.ARRAY_SIZE; endCol++){

    //                     if(!chessBoard.isValidMove(endRow, endCol)) { continue; }

    //                     moves.add(new Move(startRow, startCol, endRow, endCol));
    //                 }
    //             }
    //         }
    //     }

    //     return moves;
    // }

    // private ArrayList<ChessBoard> allBoardStates(ChessBoard chessBoard, boolean side){

    //     ArrayList<ChessBoard> boardStates = new ArrayList<>();

    //     //ChessBoard editBoard = 

    //     for(int startRow = 0; startRow < ChessBoard.ARRAY_SIZE; startRow++){

    //         for(int startCol = 0; startCol < ChessBoard.ARRAY_SIZE; startCol++){

    //             if(chessBoard.getPiece(startRow, startCol).isWhitePiece() != side) { continue; }

    //             chessBoard.resetAvailableMoves();

    //             if(!chessBoard.setValidMoves(startRow, startCol)) { continue; }

    //             for(int endRow = 0; endRow < ChessBoard.ARRAY_SIZE; endRow++){

    //                 for(int endCol = 0; endCol < ChessBoard.ARRAY_SIZE; endCol++){

    //                     if(!chessBoard.isValidMove(endRow, endCol)) { continue; }

    //                     ChessBoard addBoard = new ChessBoard(chessBoard);
    //                     addBoard.setPosition(startRow, startCol, endRow, endCol);
    //                     boardStates.add(addBoard);
    //                 }
    //             }
    //         }
    //     }

    //     return boardStates;
    // }

    
}
