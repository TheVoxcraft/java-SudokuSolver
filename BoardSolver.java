import java.util.*;


/**
 * Board object for a Sudoku board
 * Solves a board
 */
class BoardSolver {

    private List<CachedBoard> boardStates = new ArrayList<>();
    private List<CachedBoard> finishedBoards = new ArrayList<>();
    
    public BoardSolver(){
        // empty for now
    }

    public void addBoardState(CachedBoard b){
        boardStates.add(b);
    }


    public List<Moves> calcPossibles(CachedBoard b){
        ArrayList<Moves> possibleMoves = new ArrayList<>();
        for(int i = 0; i < SudokuSolver.BOARD_SIZE; i++){
            for(int j = 0; j < SudokuSolver.BOARD_SIZE; j++){
                Set<Character> possibleSymbols = b.nocache_getPossibleSymbols(j, i);
                if(!possibleSymbols.isEmpty()){
                    possibleMoves.add(new Moves(j, i, possibleSymbols));
                }
            }
        }
        return possibleMoves;
    }

   
    /**
     * Solve method, solved boards grid variable.
     */
    public void stepSolutions(CachedBoard b){
        List<Moves> possibleMoves = calcPossibles(b);
        Collections.sort(possibleMoves);
        
        if(possibleMoves.isEmpty()){
            /*
            if(b.HowManyLeft() != 0){
                System.out.println("Unfinished board ("+b.HowManyLeft()+" empty cells)");
            } else {
                System.out.println("Found a solution board");
            }*/
            
            b.finished = true;
            return;
        }

        Moves bestMove = possibleMoves.get(0);
        if(bestMove.length() == 1){
            char sol = (char) bestMove.symbols.iterator().next();
            //System.out.println("("+bestMove.x+","+bestMove.y+") symbol: "+sol);
            b.nocache_setGrid(bestMove.x, bestMove.y, sol);
        } else {
            boolean first = true;
            for (Character possibleMoveOnSlot : bestMove.symbols) {
                char sol = (char) possibleMoveOnSlot;
                if(first){
                    b.setGrid(bestMove.x, bestMove.y, sol);
                    first = false;
                } else {
                    CachedBoard childBoard = new CachedBoard();
                
                    childBoard.nocache_setGrid(b.copyGrid());
                    //childBoard.setHCache(b.copyHCache());
                    //childBoard.setVCache(b.copyVCache());
                    childBoard.nocache_setGrid(bestMove.x, bestMove.y, sol);
                    addBoardState(childBoard);
                }
            }
        }
    }

    public void solve(){
        while( !boardStates.isEmpty() ){
            //System.out.println("Size: "+boardStates.size());
            if(boardStates.get(0).finished){
                //System.out.println(boardStates.get(0).grid[8][8]+",,"+boardStates.get(2).grid[8][8]);
                finishedBoards.add(boardStates.get(0));
                boardStates.remove(0);
            } else{
                stepSolutions(boardStates.get(0));
            }
        }

        System.out.println("No more board states to solve. Finished boards: "+finishedBoards.size());

        int t = 0;
        for (CachedBoard b : finishedBoards) {
            if(b.HowManyLeft() < 1){
                System.out.println("Solved board: \n");
                b.print();
                t+=1;
            }
        }
        System.out.println("Total solved: "+t+" / "+finishedBoards.size());

    }

    
    /**
     * Converter function from oneliner text to grid object
     * @param text one liner text ex: "3..5.1....6.31..7.1... ..."
     */
    public CachedBoard textToBoard(String text){
        CachedBoard b = new CachedBoard();
        char[] text_symbols = text.toCharArray();

        for(int i = 0; i < SudokuSolver.BOARD_SIZE; i++){
            for(int j = 0; j < SudokuSolver.BOARD_SIZE; j++){
                b.nocache_setGrid(j, i, text_symbols[j + (i * SudokuSolver.BOARD_SIZE)]);
                //b.regenerateAllCache();
            }
        }

        return b;
    }
}