import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * Genre, Title 을 관리하는 영화 데이터베이스.
 * 
 * MyLinkedList 를 사용해 각각 Genre와 Title에 따라 내부적으로 정렬된 상태를  
 * 유지하는 데이터베이스이다. 
 */
public class MovieDB extends MyLinkedList<Genre>{
    public MovieDB() {
        // FIXME implement this
    	super();
    	// HINT: MovieDBGenre 클래스를 정렬된 상태로 유지하기 위한
    	// MyLinkedList 타입의 멤버 변수를 초기화 한다.
    }

    public void insert(MovieDBItem item) {
        // Insert the given item to the MovieDB.
		Genre g_temp = new Genre(item.getGenre(), item.getTitle());
		for(Genre genre: this){
			if(g_temp.equals(genre)){
				genre.add(item.getTitle());
//				System.err.printf("[trace] MovieDB: INSERT [%s] [%s]\n", item.getGenre(), item.getTitle());
				return;
			}
		}
		this.add(g_temp);

    	// Printing functionality is provided for the sake of debugging.
        // This code should be removed before submitting your work.
//        System.err.printf("[trace] MovieDB: INSERT [%s] [%s]\n", item.getGenre(), item.getTitle());
    }

    public void delete(MovieDBItem item) {
        // Remove the given item from the MovieDB.
		Genre g_temp = new Genre(item.getGenre(), item.getTitle());
		for(Genre genre: this){
			if(g_temp.equals(genre)){
				genre.remove(item.getTitle());
				// Printing functionality is provided for the sake of debugging.
				// This code should be removed before submitting your work.
//				System.err.printf("[trace] MovieDB: DELETE [%s] [%s]\n", item.getGenre(), item.getTitle());
				return;
			}
		}
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
		//   	System.err.printf("[trace] MovieDB: SEARCH [%s]\n", term);
    	
    	// FIXME remove this code and return an appropriate MyLinkedList<MovieDBItem> instance.
    	// This code is supplied for avoiding compilation error.   
        MyLinkedList<MovieDBItem> results = new MyLinkedList<MovieDBItem>();
		for(Genre genre: this){
			for(String title: genre.list_movie){
				if(title.contains(term)){
					results.add(new MovieDBItem(genre.getItem(), title));
				}
			}
		}

        return results;
    }
    
    public MyLinkedList<MovieDBItem> items() {
        // Search the given term from the MovieDatabase.
        // You should return a linked list of QueryResult.
        // The print command is handled at PrintCmd class.

    	// Printing movie items is the responsibility of PrintCmd class. 
    	// So you must not use System.out in this method to achieve specs of the assignment.

    	// Printing functionality is provided for the sake of debugging.
        // This code should be removed before submitting your work.
//        System.err.printf("[trace] MovieDB: ITEMS\n");

    	// This code is supplied for avoiding compilation error.
        MyLinkedList<MovieDBItem> results = new MyLinkedList<MovieDBItem>();
		for(Genre genre: this){
			for(String title: genre.list_movie){
				results.add(new MovieDBItem(genre.getItem(), title));
			}
		}

		return results;
    }
}

class Genre extends Node<String> implements Comparable<Genre> {
	public MyLinkedList<String> list_movie;

	public Genre(String name) {
		super(name);
		this.list_movie = new MyLinkedList<>();
	}

	public Genre(String name, String title) {
		super(name);
		this.list_movie = new MyLinkedList<>();
		this.add(title);
	}

	public void add(String title){
		this.list_movie.add(title);
	}

	public void remove(String title){
		this.list_movie.remove(title);
	}
	
	@Override
	public int compareTo(Genre o) {
		if(o == null){return -1;} // o is the last value
		if(this.getItem() == null){return 1;} // this is head
		return this.getItem().compareTo(o.getItem());
	}

	@Override
	public boolean equals(Object o) {
		if(this == o) {return true;}
		if(o == null || getClass() != o.getClass()) {return false;}
		if(this.getItem() == null) {return false;}
		Genre genre = (Genre) o;
		return this.getItem().equals(genre.getItem());
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.getItem() == null) ? 0 : this.getItem().hashCode());
		result = prime * result + ((list_movie == null) ? 0 : list_movie.hashCode());
		return result;
	}
}

class MovieList extends MyLinkedList<String> implements ListInterface<String> {
//	String genre;

	public MovieList() {
		super();
	}
/*
	public MovieList(String g) {
		super();
		this.genre = g;
	}

	public MovieList(String g, String title) {
		super();
		this.genre = g;
		this.add(title);
	}
///*
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