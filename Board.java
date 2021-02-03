import java.util.*;
import java.util.Set;
import java.util.HashMap;

class Key{
    private final int x;
    private final int y;

    public Key(int x, int y) {
        this.x = x;
        this.y = y;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Key)) return false;
        Key key = (Key) o;
        return x == key.x && y == key.y;
    }

    @Override
    public int hashCode() {
        return (x << 16) + y;
        /*int result = x;
        result = 31 * result + y;
        return result;*/
    }
}

public class Board {
    public boolean finished = false;
    public boolean upToDateCache = false;

    private char[][] grid;

    private HashMap<Integer, char[]> CachedHorizontal = new HashMap<Integer, char[]>();
    private HashMap<Integer, char[]> CachedVertical = new HashMap<Integer, char[]>();
    private HashMap<Key, char[]> CachedBlocks = new HashMap<Key, char[]>();

    public Board(){
        grid = new char[SudokuSolver.BOARD_SIZE][SudokuSolver.BOARD_SIZE];
    }

        /**
     * Returns if a element k can be placed in positon (x,y) 
     * @param x position
     * @param y position
     * @param k element to check
     * @return if possible to place or not
     */
    public Set<Character> getPossibleSymbols(int x, int y){
        Set<Character> found = new HashSet<>();
        if(grid[x][y] != '.'){
            return found;
        }
        for(char c : get_cached_horizontal(y))
            found.add(c);
        for(char c : get_cached_vertical(x))
            found.add(c);
        for(char c : get_cached_block(x,y))
            found.add(c);
        found.remove('.');
        Set<Character> symbols = new HashSet<>();
        for(char c : SudokuSolver.SYMBOLS)
            symbols.add(c);
        symbols.removeAll(found);
        return symbols;
    }

    public void setGrid(int x, int y, char val){
        upToDateCache = false;
        grid[x][y] = val;
    }

    public void setGrid(char[][] other){
        upToDateCache = false;
        grid = other;
    }

    public char getGrid(int x, int y){
        return grid[x][y];
    }

    private void updateCachedHorizontal(){
        CachedHorizontal.clear();
        for (int i = 0; i < SudokuSolver.BOARD_SIZE; i++) {
            CachedHorizontal.put((Integer) i, get_horizontal(i));
        }
    }

    private void updateCachedVertical(){
        CachedVertical.clear();
        for (int i = 0; i < SudokuSolver.BOARD_SIZE; i++) {
            CachedVertical.put((Integer) i, get_vertical(i));
        }
    }

    private void updateCachedBlock(){
        CachedBlocks.clear();
        for(int i = 0; i < SudokuSolver.BOARD_SIZE; i++){
            for(int j = 0; j < SudokuSolver.BOARD_SIZE; j++){
                CachedBlocks.put(new Key(j,i), get_block(j, i));
            }
        }
    }

    public void updateCache(){
        if(!upToDateCache){
            updateCachedHorizontal();
            updateCachedVertical();
            updateCachedBlock();
            upToDateCache = true;
        } else {
            System.out.println("Already up to date!");
        }
    }

    public char[] get_cached_vertical(int x){
        if(upToDateCache){
            return CachedVertical.get((Integer) x);
        } else{
            System.out.println("(V) Falling back to real time calculation");
            return get_vertical(x);
        }
    }

    public char[] get_cached_horizontal(int y){
        if(upToDateCache){
            return CachedHorizontal.get((Integer) y);
        } else{
            System.out.println("(H) Falling back to real time calculation");
            return get_horizontal(y);
        }
    }

    public char[] get_cached_block(int x, int y){
        if(upToDateCache){
            return CachedBlocks.get(new Key(x, y));
        } else{
            System.out.println("(B) Falling back to real time calculation");
            return get_block(x, y);
        }
    }

    /**
     * Gets array of symbols in x direction based off y-position
     * @param y y-position of the board, gets x symbols array
     * @return array of the horizontal symbols based on y position
     */
    public char[] get_vertical(int x){
        char[] horz = new char[SudokuSolver.BOARD_SIZE];
        for(int i = 0; i < SudokuSolver.BOARD_SIZE; i++){
            horz[i] = grid[x][i];
        }
        return horz;
    }

    /**
     * Gets array of symbols in y direction based off x-position
     * @param x x-position of the board, gets y symbols array
     * @return array of the vertical symbols based on y position
     */
    public char[] get_horizontal(int y){
        char[] vert = new char[SudokuSolver.BOARD_SIZE];
        for(int i = 0; i < SudokuSolver.BOARD_SIZE; i++){
            vert[i] = grid[i][y];
        }
        return vert;
    }

    /**
     * Gets all symbols from the same block (3x3) that the (x,y) element comes from
     * @param x x-pos
     * @param y y-pos
     * @return array of all items in correct block
     */
    public char[] get_block(int x, int y){
        int startX = (int) (Math.ceil(x / 3) * 3);
        int startY = (int) (Math.ceil(y / 3) * 3);
        char[] block = new char[SudokuSolver.BOARD_SIZE];
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                block[i+(j*3)] = grid[startX+i][startY+j];
            }
        }
        return block;
    }

    /**
     * Counts how many tiles are unsolved
     * @return count of all uncalculated spots
     */
    public int HowManyLeft(){
        int dots = 0;
        for(int i = 0; i < SudokuSolver.BOARD_SIZE; i++){
            for(int j = 0; j < SudokuSolver.BOARD_SIZE; j++){
                if(grid[j][i] == '.'){
                    dots++;
                }
            }
        }
        return dots;
    }

    
    /**
     * Custom print method for specific blocks
     * @param block
     */
    public void printBlock(char[][] block){
        for(char[] y: block) {
            for(char x : y) {
                System.out.print(x);
            }
            System.out.println();
        }
    }

    /**
     * Custom print method for the board
     */
    public void print(){
        for(int i = 0; i < SudokuSolver.BOARD_SIZE; i++){
            for(int j = 0; j < SudokuSolver.BOARD_SIZE; j++){
                if(j%3==0 && j!=0){
                    System.out.print(' ');
                }
                System.out.print(grid[j][i]);
            }
            if((i+1)%3==0 && i!=0 && i!=8){
                System.out.println();
            }
            System.out.println();
        }
        System.out.println();
    }

    public char[][] copyGrid(){
        char[][] n = new char[SudokuSolver.BOARD_SIZE][SudokuSolver.BOARD_SIZE];
        for(int i = 0; i < SudokuSolver.BOARD_SIZE; i++){
            for(int j = 0; j < SudokuSolver.BOARD_SIZE; j++){
                n[j][i] = grid[j][i];
            }
        }
        return n;
    }
}
