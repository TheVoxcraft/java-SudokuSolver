public class BenchmarkBoard extends Board{
    public void benchmark(){
        BoardSolver g = new BoardSolver();
        final int amount = 1000000;
        super.setGrid(g.textToBoard("........1....2..9.6....4......7..2....9.1..36.3...2..452.93....9..5...6...4..6.1.").copyGrid());

        long startTime = System.currentTimeMillis();
        for (int i = 0; i < amount; i++) {
            benchmarking_function1();
        }
        long elapsedTimeMillis = System.currentTimeMillis()-startTime;
        System.out.println("(1) Took: "+(elapsedTimeMillis)+" ms ("+amount+" runs)");

        startTime = System.currentTimeMillis();
        updateAllCaches(2, 2);
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
        get_cached_vertical(2);
    }
}