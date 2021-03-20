import java.util.AbstractList;
import java.util.ArrayList;

public class SubstringTable extends Hashtable<String, Position>{
    public static int CAPA = 100;
    SubstringTable(){super(CAPA);}

    @Override
    public int getHash(String key){
        int temp = 0;
        for(int i=0; i<key.length(); i++){temp += key.charAt(i);}
        return temp % this.size;
    }

    public final String search(String query){
        StringBuilder sb = new StringBuilder();
        Node_tree<String, Position> matched_first = this.findMatching(query, 0);
        if(matched_first == null){return "(0, 0)";}
        for(Position pos_candidate: matched_first){
            int idx = 6;
            boolean matched = true;

            while(idx < query.length()){
                if(idx > query.length() - 6){ idx = query.length() - 6; }
                matched = false;
                Node_tree<String, Position> temp = this.findMatching(query, idx);
                if(temp == null){return "(0, 0)";}
                for(Position pos_temp: temp){ if(pos_temp.shiftedTo(pos_candidate, idx)){matched = true;}}
                if(!matched){break;} // there is no matching in idx

                idx += 6;
            }

            if(!matched){continue;} // there is an mismatching one, do not append to sb

            sb.append(pos_candidate.toString()).append(" ");
        }

        if(sb.toString().equals("")){return "(0, 0)";}

        String temp = sb.toString();
        return temp.substring(0, temp.length()-1);
    }

    public final Node_tree<String, Position> findMatching(String query, int idx){
        String query_sub = getSubQuery(query, idx);
        if(this.slots.get(this.getHash(query_sub)) == null){return null;}
        return this.slots.get(this.getHash(query_sub)).getRoot().findKey(query_sub);
    }

    public static String getSubQuery(String query, int idx){return query.substring(idx, idx+6);}
}

class Hashtable<K extends Comparable<K>, V extends Comparable<V>> {
    int size;
    ArrayList<AVLtree<K, V>> slots;

    Hashtable(int size){
        this.size = size;
        this.slots = new ArrayList<AVLtree<K, V>>();
        for(int i=0; i<this.size; i++){slots.add(null);}
    }

    public int getHash(K obj){return obj.hashCode()%size;}

    public void add(K key, V value){
        int h = this.getHash(key);
        AVLtree<K, V> tree = slots.get(this.getHash(key));
        if(tree == null){slots.set(this.getHash(key), new AVLtree<>(key, value)); return;}
        tree.add(key, value);
    }

    public boolean contains(K key){
        AVLtree<K, V> tree = slots.get(this.getHash(key));
        if(tree.getRoot().findKey(key) == null) {return false;}
        return true;
    }

    public final String print(int idx){
        AVLtree<K, V> tree = slots.get(idx);
        if(tree == null){return "EMPTY";}
        String temp = tree.keyToString();
        return temp.substring(0, temp.length()-1);
    }

/*
    public Node_tree<K, V> get(K key){ return (Node_tree<K, V>) new Object(); }
    public void remove(K key){}
    public void remove(K key, V value){}
    public void removeAll(){}
*/
}
