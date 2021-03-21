class Position extends Pair<Integer, Integer> implements Comparable<Position>{
    Position(Integer obj1, Integer obj2) { super(obj1, obj2); }

    public final String toString(){return "("+ (this.first + 1) +", "+ (this.second + 1) +")";}

    public final boolean shiftedTo(Position other, int shift){
        return this.first.equals(other.first) && this.second - shift == other.second;
    }

    @Override
    public int compareTo(Position o) {
        if(!this.first.equals(o.first)){ return this.first-o.first; }
        if(!this.second.equals(o.second)){ return this.second-o.second; }
        return 0;
    }
}