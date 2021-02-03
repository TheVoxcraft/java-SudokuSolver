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

        Board startingBoard = Game.textToBoard("9.165..........1...249.......6..5...482..6......4..78.6.8..9.2...5.....72..8....5");
        
        // Hardest board:
        //Board startingBoard = Game.textToBoard("8..........36......7..9.2...5...7.......457.....1...3...1....68..85...1..9....4..");
        
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
        System.out.println("Took: "+elapsedTimeSec+" s ("+elapsedTimeMillis+" ms)"); 
        */
    }
}


class BenchmarkBoard extends Board{
    public void benchmark(){
        BoardSolver g = new BoardSolver();
        final int amount = 100000;
        super.setGrid(g.textToBoard("........1....2..9.6....4......7..2....9.1..36.3...2..452.93....9..5...6...4..6.1.").copyGrid());

        long startTime = System.currentTimeMillis();
        for (int i = 0; i < amount; i++) {
            benchmarking_function1();
        }
        long elapsedTimeMillis = System.currentTimeMillis()-startTime;
        System.out.println("(1) Took: "+(elapsedTimeMillis)+" ms ("+amount+" runs)");

        startTime = System.currentTimeMillis();
        //updateCache();
        for (int i = 0; i < amount; i++) {
            benchmarking_function2();
        }
        elapsedTimeMillis = System.currentTimeMillis()-startTime;
        System.out.println("(2) Took: "+(elapsedTimeMillis)+" ms ("+amount+" runs)");
    }

    private void benchmarking_function1(){
        get_vertical(2);
    }
    private void benchmarking_function2(){
        get_horizontal(2);
    }
}