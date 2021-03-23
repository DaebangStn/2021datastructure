import java.util.ArrayList;

public class Path implements Comparable<Path> {
    int length;
    public ArrayList<String> passed;

    // initializer always calls by the departure itself
    Path(String dep){
        this.passed = new ArrayList<>();
        this.passed.add(dep);
        this.length = 0;
    }

    public final int getLength(){return this.length;}

    // do not change <this>, returns added path
    public final Path add(String name, int time) throws Exception{
        Path other = new Path(this.departure());

        for(int i=1; i<this.passed.size(); i++){other.passed.add(this.passed.get(i));}
        other.passed.add(name);
        other.length = this.length + time;

        return other;
    }

    public final int compareTo(Path other){return this.length-other.getLength();}

    public final String departure() throws Exception{
        if(this.passed.isEmpty()){throw new Exception("copy empty path");}
        return this.passed.get(0);
    }

    /*
    public final void first(String name, int time) throws Exception{
        if(!this.passed.isEmpty()){throw new Exception("there shouldn't be stations in Path");}
        this.passed.add(name);
        this.length = time;
    }
     */
}
