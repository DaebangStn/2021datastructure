import java.util.AbstractList;
import java.util.ArrayList;

public class SubstringTable extends Hashtable<String, Position>{
    public static int CAPA = 100;
    SubstringTable(){super(CAPA);}

    @Override
    public int getHash(String key){
        int temp = 0;
        for(int i=0; i<key.length(); i++){temp += key.charAt(i);}
        System.out.println("key "+key+" hashed to "+ temp % this.size);
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
    // TODO: using AVLtree's operation
    public void add(K key, V value){ }// TODO
    public boolean contains(K key){return false;}// TODO
    public LinkedList<K> search(int idx){return (LinkedList<K>) new Object();}// TODO
    public LinkedList<V> search(K query){return (LinkedList<V>) new Object(); }// TODO

/*
    public Node_tree<K, V> get(K key){ return (Node_tree<K, V>) new Object(); }
    public void remove(K key){}
    public void remove(K key, V value){}
    public void removeAll(){}
*/
}
