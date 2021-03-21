public class AVLtree<K extends Comparable<K>, V extends Comparable<V>>{
    private Node_tree<K, V> root;

    AVLtree(K key, V value){
        root = new Node_tree<>(key, value);
    }

    public final void add(K key, V value){
        Node_tree<K, V> temp = this.root.findKey(key);
        if(temp != null){
            temp.addItem(value);
            return;
        }

        temp = root.addNode(new Node_tree<>(key, value));
        temp.balance();
        this.updateRoot();
    }

    public final Node_tree<K, V> getRoot(){return this.root;}

    public final String keyToString(){
        return this.getRoot().keyToString();
    }

    private void updateRoot(){
        Node_tree<K, V> temp = this.root;
        while (temp.getParent() != null){temp = temp.getParent();}
        this.root = temp;
    }
}

