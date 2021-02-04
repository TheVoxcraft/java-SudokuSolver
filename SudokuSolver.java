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
        Board startingBoard = Game.textToBoard(".......6.5...68...2.31...9........736.........4...18.69..3....28.42..3.7..2.5....");

        //Board startingBoard = Game.textToBoard("9.165..........1...249.......6..5...482..6......4..78.6.8..9.2...5.....72..8....5");
        
        // Hardest board:
        //Board startingBoard = Game.textToBoard("8..........36......7..9.2...5...7.......457.....1...3...1....68..85...1..9....4..");
        
        if(args.length > 0){
            String bText = args[0].toString();
            if(bText.length() != 81){
                System.out.println("Weird size. Must be 81 len");
            }
            startingBoard = Game.textToBoard(bText);
        }


        System.out.println("Board: \n");
        startingBoard.print();

        Game.addBoardState(startingBoard);

        BenchmarkBoard b = new BenchmarkBoard();
        b.benchmark();
        /* 
        long start = System.currentTimeMillis();
        Game.solve();
        long elapsedTimeMillis = System.currentTimeMillis()-start;
        float elapsedTimeSec = elapsedTimeMillis/1000F;
        System.out.println("Took: "+elapsedTimeSec+" s ("+elapsedTimeMillis+" ms)");  */
        
    }
}