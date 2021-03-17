public class AVLtree<K extends Comparable<K>, V extends Comparable<V>> {
    Node_tree<K, V> root;
    AVLtree(){
        root = new Node_tree<>();
/*
usage of foreach with generic
        for(V v: root)
*/
    }

    public final void add(K key, V value){ }// TODO
    public Node_tree<K, V> get(K key){ return (Node_tree<K, V>) new Object(); }// TODO
    public final LinkedList<K> searchKey(K query){return (LinkedList<K>) new Object();}// TODO

}

class Node_tree<K extends Comparable<K>, V extends Comparable<V>> extends LinkedList<V>{
    Node_tree<K, V> left, right, parent;
    Node_tree(){
        super();
        this.parent = null;
        this.left = null;
        this.right = null;
    }

    Node_tree(Node_tree<K, V> parent){
        super();
        this.parent = parent;
        this.left = null;
        this.right = null;
    }

    public final void addItem(V item){}// TODO
    public final void setLeft(Node_tree<K, V> obj){this.left = obj;}
    public final void setRight(Node_tree<K, V> obj) {this.right = obj;}
    public final void setParent(Node_tree<K, V> obj) {this.parent = obj;}
    public final Node_tree<K, V> getLeft(){return this.left;}
    public final Node_tree<K, V> getRight() {return this.right;}
    public final Node_tree<K, V> getParent() {return this.parent;}
}

class Position extends Pair<Integer, Integer> implements Comparable<Position>{
    Position(Integer obj1, Integer obj2) { super(obj1, obj2); }

    final public String toString(){return "("+this.first+", "+this.second+")";}

    @Override
    public int compareTo(Position o) {
        if(this.first!=o.first){return this.first-o.first;}
        if(this.second!=o.second){return this.second-o.second;}
        return 0;
    }
}