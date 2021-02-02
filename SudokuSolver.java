/**
 * @author Jonas Silva
 * @version 1.0
 * This program solves a Sudoku board.
 */
public class SudokuSolver {

    static final int BOARD_SIZE = 9;
    static final char[] SYMBOLS = {'1','2','3','4','5','6','7','8','9'};
    public static void main(String[] args) {
        BoardSolver Game = new BoardSolver();

        //Board startingBoard = Game.textToBoard("11111111111111111111111111111111111111111111111111111111111111111111111111111111.");
        Board startingBoard = Game.textToBoard("9743..8126312985742854179367.61354895438297611986..3253597612488129436574675..193");
        //Board startingBoard = Game.textToBoard("...35...2...2...7.2.5..79...........5...2...1...6.432.3..7...4..1.94.6....7...1..");

        Game.addBoardState(startingBoard);

        Game.solve();
    }
}
