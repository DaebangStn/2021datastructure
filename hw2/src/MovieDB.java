import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Genre, Title 을 관리하는 영화 데이터베이스.
 * 
 * MyLinkedList 를 사용해 각각 Genre와 Title에 따라 내부적으로 정렬된 상태를  
 * 유지하는 데이터베이스이다. 
 */
public class MovieDB {
	private MyLinkedList<Genre> list_genre;

    public MovieDB() {
        // FIXME implement this
    	list_genre = new MyLinkedList<>();

    	Genre g = new Genre("ulala");
    	System.out.println(g.add("man"));
    }

    public void insert(MovieDBItem item) {
        // FIXME implement this
        // Insert the given item to the MovieDB.
		String title = item.getTitle();
		Genre g = new Genre(item.getGenre());
		Genre g_searched = this.find_genre(g);

		if(g.compareTo(g_searched) != 0){
			// add genre to list_genre
			throw new UnsupportedOperationException();
		}



    	// Printing functionality is provided for the sake of debugging.
        // This code should be removed before submitting your work.
        System.err.printf("[trace] MovieDB: INSERT [%s] [%s]\n", item.getGenre(), item.getTitle());
    }

    public void delete(MovieDBItem item) {
        // FIXME implement this
        // Remove the given item from the MovieDB.
    	
    	// Printing functionality is provided for the sake of debugging.
        // This code should be removed before submitting your work.
        System.err.printf("[trace] MovieDB: DELETE [%s] [%s]\n", item.getGenre(), item.getTitle());
    }

    public MyLinkedList<MovieDBItem> search(String term) {
        // FIXME implement this
        // Search the given term from the MovieDB.
        // You should return a linked list of MovieDBItem.
        // The search command is handled at SearchCmd class.
    	
    	// Printing search results is the responsibility of SearchCmd class. 
    	// So you must not use System.out in this method to achieve specs of the assignment.
    	
        // This tracing functionality is provided for the sake of debugging.
        // This code should be removed before submitting your work.
    	System.err.printf("[trace] MovieDB: SEARCH [%s]\n", term);
    	
    	// FIXME remove this code and return an appropriate MyLinkedList<MovieDBItem> instance.
    	// This code is supplied for avoiding compilation error.   
        MyLinkedList<MovieDBItem> results = new MyLinkedList<MovieDBItem>();

        return results;
    }
    
    public MyLinkedList<MovieDBItem> items() {
        // FIXME implement this
        // Search the given term from the MovieDatabase.
        // You should return a linked list of QueryResult.
        // The print command is handled at PrintCmd class.

    	// Printing movie items is the responsibility of PrintCmd class. 
    	// So you must not use System.out in this method to achieve specs of the assignment.

    	// Printing functionality is provided for the sake of debugging.
        // This code should be removed before submitting your work.
        System.err.printf("[trace] MovieDB: ITEMS\n");

    	// FIXME remove this code and return an appropriate MyLinkedList<MovieDBItem> instance.
    	// This code is supplied for avoiding compilation error.   
        MyLinkedList<MovieDBItem> results = new MyLinkedList<MovieDBItem>();
        
    	return results;
    }
/*
	find the nearest genre
	if there is the same genre, same genre will returned.
	else if genre_in is between two genre in order, right before genre will returned.
	else(genre_in is the first order), genre with name NULL will returned.
*/
    private Genre find_genre(Genre genre_in){
    	Genre genre_before = new Genre("NULL");
		for(Genre genre: this.list_genre){
			int cmp = genre.compareTo(genre_in);
			if(cmp == 0){
				return genre;
			}else if(cmp > 0){
				return genre_before;
			}
			genre_before = genre;
		}
		return genre_before;
	}
}

class Genre extends Node<String> implements Comparable<Genre> {
	private MovieList list;

	public Genre(String name) {
		super(name);
		list = new MovieList(this);
		System.err.printf("[trace] Genre creation: " + this.getItem() + "\n");
	}

	public boolean add(String title) {
		return list.add_title(title);
	}

	@Override
	public int compareTo(Genre o)
	{
		throw new UnsupportedOperationException("not implemented yet");
	}

	@Override
	public int hashCode() {
		throw new UnsupportedOperationException("not implemented yet");
	}

	@Override
	public boolean equals(Object obj) {
		throw new UnsupportedOperationException("not implemented yet");
	}
}

class MovieList extends MyLinkedList<String> implements ListInterface<String> {
	private Genre genre;

	public MovieList() {
		super();
	}

	public MovieList(Genre g) {
		super();
		genre = g;
	}

	// returns true when the there is no added movie.
	// false when the there is added movie.
	public boolean add_title(String title){
		Node<String> m_searched = this.find_movie(title);

		if(m_searched != this.head){
			if(title.compareTo(m_searched.getItem()) == 0){return false;}
		}

		Node<String> m_next = m_searched.getNext();
		if(m_next == null){
			Node<String> m_new = new Node<>(title);
			m_searched.setNext(m_new);
		}else{
			Node<String> m_new = new Node<>(title, m_next);
			m_searched.setNext(m_new);
		}
		System.err.printf("[trace] title addition: " + title + "\n");
		return true;
	}

	/*
        find the nearest movie
        if there is the same movie, same movie will returned.
        else if genre_in is between two movie in order, right after movie will returned.
        else(movie_in is the first order), head returned.
    */
	public Node<String> find_movie(String movie_in){
		Node<String> idx = head;
		Node<String> idx_before = head;
		while (idx.getNext() != null){
			idx = idx.getNext();
			int cmp = idx.getItem().compareTo(movie_in);
			if(cmp == 0){
				return idx;
			}else if(cmp > 0){
				return idx_before;
			}
			idx_before = idx;
		}
		return idx_before;
	}

/*
	@Override
	public Iterator<String> iterator() {
		throw new UnsupportedOperationException("not implemented yet");
	}

	@Override
	public boolean isEmpty() {
		throw new UnsupportedOperationException("not implemented yet");
	}

	@Override
	public int size() {
		throw new UnsupportedOperationException("not implemented yet");
	}

	@Override
	public void add(String item) {
		throw new UnsupportedOperationException("not implemented yet");
	}

	@Override
	public String first() {
		throw new UnsupportedOperationException("not implemented yet");
	}

	@Override
	public void removeAll() {
		throw new UnsupportedOperationException("not implemented yet");
	}
 */
}