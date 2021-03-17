import java.util.Iterator;
import java.util.NoSuchElementException;

public class LinkedList<T extends Comparable<T>> implements Iterable<T>{

    Node<T> head;
    int numItems;

    LinkedList(){
        this.head = new Node<>();
        numItems = 0;
    }

    public final Iterator<T> iterator(){return new LinkedListIterator<>(this);}

    public boolean isEmpty(){return this.head.getNext() == null;}
    public int size(){return this.numItems;}

    public void add(T obj){
        if(this.isEmpty()){
            this.head.insertNext(obj);
            this.numItems++;
            return;
        }

        Node<T> last = this.head;
        while (last.getNext() != null){
            int cmp = obj.compareTo(last.getNext().getItem());
            if(cmp == 0){return;} // already exist item in LL
            if(cmp < 0){break;} // order in [last:obj:last.getNext()]
            last = last.getNext();
        }
        last.insertNext(obj);
        numItems++;
    }

    public void remove(T obj){
        Node<T> last = this.head;
        while (last.getNext() != null){
            if(obj.equals(last.getNext().getItem())){
                last.removeNext();
                numItems--;
                return;
            }
            last=last.getNext();
        }
    }

    public void removeAll(){
        numItems=0;
        this.head.setNext(null);
    }
}

class Node<T>{
    private T item;
    private Node<T> next;

    Node(){ item = null; next = null;}
    Node(T obj){this.item = obj; next = null;}
    Node(T obj, Node<T> node){this.item = obj; this.next = node;}

    public final T getItem(){return item;}
    public final void setItem(T obj){this.item = obj;}
    public final Node<T> getNext(){return next;}
    public final void setNext(Node<T> node){this.next = node;}
    public final void insertNext(T obj){
        Node<T> node = new Node<>(obj, this.getNext());
        this.setNext(node);
    }
    public final void removeNext(){
        if(this.getNext() == null){return;}
        this.setNext(this.getNext().getNext());
    }
}

class LinkedListIterator<T extends Comparable<T>> implements Iterator<T>{
    private LinkedList<T> list;
    private Node<T> curr;
    private Node<T> prev;

    public LinkedListIterator(LinkedList<T> lst){
        this.list = lst;
        this.curr = lst.head;
        this.prev = null;
    }

    @Override
    public boolean hasNext(){return this.curr.getNext() != null;}
    @Override
    public T next(){
        if(!hasNext()){throw new NoSuchElementException(); }
        this.prev = this.curr;
        this.curr = this.curr.getNext();
        return this.curr.getItem();
    }
    @Override
    public void remove(){
        if(this.prev==null){throw new IllegalStateException("next() should be called");}
        if(this.curr==null){throw new NoSuchElementException();}
        this.prev.removeNext();
        this.list.numItems--;
        this.curr = this.prev;
        this.prev = null;
    }
}