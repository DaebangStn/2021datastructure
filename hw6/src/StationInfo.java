import java.util.ArrayList;

public class StationInfo {
    private String name;
    private ArrayList<Pair<String, Integer>> adjacent;

    StationInfo(String name){
        this.name = name;
        adjacent = new ArrayList<>();
    }

    public final void add(String name_neighbor, int length){ this.adjacent.add(new Pair<>(name_neighbor, length));}
    public final ArrayList<Pair<String, Integer>> getAdjacent(){return this.adjacent;}
    public final String getName(){return this.name;}
}
