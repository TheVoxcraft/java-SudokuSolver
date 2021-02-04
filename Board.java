import java.util.*;
import java.util.Set;

public class Board {
    public boolean finished = false;

    protected char[][] grid;

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
    public Set<Character> nocache_getPossibleSymbols(int x, int y){
        Set<Character> found = new HashSet<>();
        if(grid[x][y] != '.'){
            return found;
        }
        for(char c : get_horizontal(y))
            found.add(c);
        for(char c : get_vertical(x))
            found.add(c);
        for(char c : get_block(x,y))
            found.add(c);
        found.remove('.');
        Set<Character> symbols = new HashSet<>();
        for(char c : SudokuSolver.SYMBOLS)
            symbols.add(c);
        symbols.removeAll(found);
        return symbols;
    }

    public void nocache_setGrid(int x, int y, char val){
        grid[x][y] = val;
    }

    public void nocache_setGrid(char[][] other){
        grid = other;
    }

    public char getGrid(int x, int y){
        return grid[x][y];
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