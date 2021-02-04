import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class CachedBoard extends Board{

    public boolean upToDateCache = false;

    private HashMap<Integer, char[]> CachedHorizontal = new HashMap<Integer, char[]>();
    private HashMap<Integer, char[]> CachedVertical = new HashMap<Integer, char[]>();

    public Set<Character> getPossibleSymbols(int x, int y){
        Set<Character> found = new HashSet<>();
        if(grid[x][y] != '.'){
            return found;
        }
        for(char c : get_cached_horizontal(y))
            found.add(c);
        for(char c : get_cached_vertical(x))
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

    public void setGrid(int x, int y, char val){
        grid[x][y] = val;
        upToDateCache = false;
        updateAllCaches(x, y);
        upToDateCache = true; 
    }

    public void setGrid(char[][] other){
        grid = other;
        upToDateCache = false;
        regenerateAllCache();
    }

    private void updateCachedHorizontal(){
        for (int i = 0; i < SudokuSolver.BOARD_SIZE; i++) {
            CachedHorizontal.put((Integer) i, get_horizontal(i));
        }
    }

    private void updateCachedVertical(){
        for (int i = 0; i < SudokuSolver.BOARD_SIZE; i++) {
            CachedVertical.put((Integer) i, get_vertical(i));
        }
    }

    private void updateCachedHorizontal(int y){
        CachedHorizontal.put(y, get_horizontal(y));
    }

    private void updateCachedVertical(int x){
        CachedVertical.put(x, get_vertical(x));
    }

    public void regenerateAllCache(){
        if(!upToDateCache){
            updateCachedHorizontal();
            updateCachedVertical();
            upToDateCache = true;
        } else {
            System.out.println("All caches already up to date!");
        }
    }

    public void updateAllCaches(int x, int y){
        if(!upToDateCache){
            updateCachedHorizontal(y);
            updateCachedVertical(x);
        } else {
            System.out.println("Cache already up to date!");
        }
    }

    public char[] get_cached_vertical(int x){
        if(upToDateCache){
            return CachedVertical.get(x);
        } else{
            System.out.println("(V) Falling back to real time calculation.");
            return get_vertical(x);
        }
    }

    public char[] get_cached_horizontal(int y){
        if(upToDateCache){
            return CachedHorizontal.get(y);
        } else{
            System.out.println("(H) Falling back to real time calculation.");
            return get_horizontal(y);
        }
    }

    public HashMap<Integer, char[]> copyHCache(){
        HashMap<Integer, char[]> newCached = new HashMap<>();
        for (Map.Entry<Integer, char[]> entry : CachedHorizontal.entrySet()) {
            Integer key = entry.getKey();
            char[] value = entry.getValue();
            newCached.put(key, value);
        }
        return newCached;
    }

    public HashMap<Integer, char[]> copyVCache(){
        HashMap<Integer, char[]> newCached = new HashMap<>();
        for (Map.Entry<Integer, char[]> entry : CachedVertical.entrySet()) {
            Integer key = entry.getKey();
            char[] value = entry.getValue();
            newCached.put(key, value);
        }
        return newCached;
    }

    public void setHCache(HashMap<Integer, char[]> o){
        CachedHorizontal = o;
    }

    public void setVCache(HashMap<Integer, char[]> o){
        CachedVertical = o;
    }
}