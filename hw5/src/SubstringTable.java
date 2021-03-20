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
        return tree.keyToString().trim();
    }

    public LinkedList<V> search(K query){ return (LinkedList<V>) new Object();}// TODO

/*
    public Node_tree<K, V> get(K key){ return (Node_tree<K, V>) new Object(); }
    public void remove(K key){}
    public void remove(K key, V value){}
    public void removeAll(){}
*/
}
