import java.util.*;


/**
 * Board object for a Sudoku board
 * Solves a board
 */
class BoardSolver {

    private List<Board> boardStates = new ArrayList<>();
    private List<Board> finishedBoards = new ArrayList<>();
    
    public BoardSolver(){
        // empty for now
    }

    public void addBoardState(Board b){
        boardStates.add(b);
    }


    public List<Moves> calcPossibles(Board b){
        ArrayList<Moves> possibleMoves = new ArrayList<>();
        for(int i = 0; i < SudokuSolver.BOARD_SIZE; i++){
            for(int j = 0; j < SudokuSolver.BOARD_SIZE; j++){
                Set<Character> possibleSymbols = b.getPossibleSymbols(j, i);
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
    public void stepSolutions(Board b){
        List<Moves> possibleMoves = calcPossibles(b);
        Collections.sort(possibleMoves);
        
        if(possibleMoves.isEmpty()){
            if(b.HowManyLeft() != 0){
                System.out.println("Unfinished board ("+b.HowManyLeft()+" empty)");
            } else {
                System.out.println("Found a solution board");
            }
            
            b.finished = true;
            return;
        }

        //Moves bestMove = possibleMoves.get(0);
        int count = 0;
        for(Moves move : possibleMoves){
            //System.out.println("("+move.x+","+move.y+") symbols: "+move.symbols.toString());
            if(move.length() == -1) {
                //b.grid[move.x][move.y] = sol;
            } else {
                for (Character possibleMoveOnSlot : move.symbols) {
                    Board childBoard = new Board();
                
                    childBoard.grid = b.copyGrid();

                    /* //TESTS for copy of grid
                    char t1 = b.grid[0][0];
                    char t2 = childBoard.grid[0][0];
                    childBoard.grid[0][0] = '9';
                    if(t1 != b.grid[0][0]){System.out.println("!!!! BIG ERROR !!!!"+t1+" "+t2+"-"+b.grid[0][0]);}// else {System.out.println("worked "+t1+" "+t2+"-"+b.grid[0][0]);}
                    childBoard.grid[0][0] = t2;
                    */
                    count+=1;

                    char sol = (char) possibleMoveOnSlot;
                    childBoard.grid[move.x][move.y] = sol;
                    addBoardState(childBoard); // PROBLEM: Seems same (non-matching hashcodes) board gets put into the board state queue (only finished boards?), 
                                            // Seems childBoard object is the same object being updated each loop
                                            // Clone bug?
                                            // Seems to work with custom b.copyGrid() fucntion
                }
            }
        }
        //System.out.println("Created "+count+" new board states to solve.");
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
                if(!boardStates.get(0).finished){
                    boardStates.remove(0);
                }
            }
        }

        System.out.println("No more board states to solve. Finished boards: "+finishedBoards.size());

        int t = 0;
        for (Board b : finishedBoards) {
            if(b.HowManyLeft() < 1){
                //System.out.println("Solved board: \n");
                //b.print();
                t+=1;
            }
        }
        System.out.println("Total solved: "+t+" / "+finishedBoards.size());

    }

    
    /**
     * Converter function from oneliner text to grid object
     * @param text one liner text ex: "3..5.1....6.31..7.1... ..."
     */
    public Board textToBoard(String text){
        Board b = new Board();
        char[] text_symbols = text.toCharArray();

        for(int i = 0; i < SudokuSolver.BOARD_SIZE; i++){
            for(int j = 0; j < SudokuSolver.BOARD_SIZE; j++){
                b.grid[j][i] = text_symbols[j + (i * SudokuSolver.BOARD_SIZE)];
            }
        }

        return b;
    }
}