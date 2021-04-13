
import java.util.Iterator;
import java.util.NoSuchElementException;

public class MyLinkedList<T extends Comparable<T>> implements ListInterface<T> {
	Node<T> head;
	int numItems;

	public MyLinkedList() {
		head = new Node<T>(null);
	}

    /**
     * {@code Iterable<T>}를 구현하여 iterator() 메소드를 제공하는 클래스의 인스턴스는
     * 다음과 같은 자바 for-each 문법의 혜택을 볼 수 있다.
     * 
     * <pre>
     *  for (T item: iterable) {
     *  	item.someMethod();
     *  }
     * </pre>
     * 
     * @see PrintCmd#apply(MovieDB)
     * @see SearchCmd#apply(MovieDB)
     * @see java.lang.Iterable#iterator()
     */
    public final Iterator<T> iterator() {
    	return new MyLinkedListIterator<T>(this);
    }

	@Override
	public boolean isEmpty() {
		return head.getNext() == null;
	}

	@Override
	public int size() {
		return numItems;
	}

	@Override
	public T first() {
		return head.getNext().getItem();
	}

	@Override
	public void add(T item) {
		if(this.isEmpty()){
			head.setNext(new Node<>(item));
			numItems += 1;
			return;
		}

		Node<T> last = head;
		while (last.getNext() != null) {
			int cmp = item.compareTo(last.getNext().getItem());
			if(cmp == 0){ // already exist item in LL
				return;
			}else if(cmp < 0){ break;}
			last = last.getNext();
		}
		last.insertNext(item);
		numItems += 1;
	}

	public void remove(T item){
    	if(numItems < 2){return;}

		Node<T> last = head.getNext();
		while (last.getNext() != null) {
			if(item.equals(last.getNext().getItem())){
				last.removeNext();
				numItems -= 1;
				return;
			}
			last = last.getNext();
		}
	}

	@Override
	public void removeAll()
	{
		numItems = 0;
		head.setNext(null);
	}
}

class MyLinkedListIterator<T extends Comparable<T>> implements Iterator<T> {
	// FIXME implement this
	// Implement the iterator for MyLinkedList.
	// You have to maintain the current position of the iterator.
	private MyLinkedList<T> list;
	private Node<T> curr;
	private Node<T> prev;

	public MyLinkedListIterator(MyLinkedList<T> list) {
		this.list = list;
		this.curr = list.head;
		this.prev = null;
	}

	@Override
	public boolean hasNext() {
		return curr.getNext() != null;
	}

	@Override
	public T next() {
		if (!hasNext())
			throw new NoSuchElementException();

		prev = curr;
		curr = curr.getNext();

		return curr.getItem();
	}

	@Override
	public void remove() {
		if (prev == null)
			throw new IllegalStateException("next() should be called first");
		if (curr == null)
			throw new NoSuchElementException();
		prev.removeNext();
		list.numItems -= 1;
		curr = prev;
		prev = null;
	}
}