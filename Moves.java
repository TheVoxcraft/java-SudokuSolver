import java.util.*;

/**
 * Data structure that holds possible moves on each tile.
 */
class Moves implements Comparable<Moves> {
    int x;
    int y;
    Set<Character> symbols;

    public Moves(int x, int y, Set<Character> symbols){
        this.x = x;
        this.y = y;
        this.symbols = symbols;
    }

    public int length(){
        return symbols.size();
    }

    @Override
    public String toString() { 
        return x+", "+y+" : "+symbols.toString();
    }

    @Override
    public int compareTo(Moves o) {
        return this.length() - o.length();
    }

}