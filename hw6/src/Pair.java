public class Pair<T1, T2 extends Comparable<T2>> implements Comparable<Pair<T1, T2>> {
    public T1 first;
    public T2 second;
    Pair(T1 obj1, T2 obj2){this.first = obj1; this.second=obj2;}

    public final int compareTo(Pair other){return this.second.compareTo((T2)other.second);}
    public final boolean equals(Pair<T1, T2> obj){return this.first.equals(obj.first) && this.second.equals(obj.second);}

}