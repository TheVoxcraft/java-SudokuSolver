public class BenchmarkBoard extends CachedBoard{
    public void benchmark(){
        BoardSolver g = new BoardSolver();
        final int amount = 1000000;
        setGrid(g.textToBoard("........1....2..9.6....4......7..2....9.1..36.3...2..452.93....9..5...6...4..6.1.").copyGrid());
        for (int i = 0; i < amount; i++) {
            benchmarking_function1(i);
            benchmarking_function2(i);
        }
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < amount; i++) {
            benchmarking_function1(i);
        }
        long elapsedTimeMillis = System.currentTimeMillis()-startTime;
        System.out.println("(1) Took: "+(elapsedTimeMillis)+" ms ("+amount+" runs)");

        startTime = System.currentTimeMillis();
        updateAllCaches(2, 2);
        for (int i = 0; i < amount; i++) {
            benchmarking_function2(i);
        }
        elapsedTimeMillis = System.currentTimeMillis()-startTime;
        System.out.println("(2) Took: "+(elapsedTimeMillis)+" ms ("+amount+" runs)");
    }

    private void benchmarking_function1(int i){
        copyHCache();
    }
    private void benchmarking_function2(int i){
        // ...
    }
}