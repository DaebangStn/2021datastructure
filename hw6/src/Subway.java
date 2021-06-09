import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.file.*;
import java.util.*;

public class Subway {
    static HashMap<String, StationInfo> Info = new HashMap<>();
    static HashMap<String, ArrayList<String>> Map_names = new HashMap<>();
    static final int TIME_TRANSFER = 5;

    public static void main(String[] args) {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String input = null;

        try {
            if(args.length != 1){throw new Exception();}
            List<String> subway_data = Files.readAllLines(Paths.get(args[0]));
            Iterator<String> iter = subway_data.iterator();
            // [mode] false: station id and name, true: duration time between stations
            boolean mode = false;
            while (iter.hasNext()){
                String data = iter.next();
                if("".equals(data)){
                    mode = true; continue;
                }
                process_data(data, mode);
            }

            connect_transfer_station();

            // input stdin
            while (true){
                input = br.readLine();

                if(input.equalsIgnoreCase("QUIT")){break;}

                System.out.println(process_path(input));
            }
        } catch (Exception e) {
            System.err.println("ERROR");
        }

    }

    public static void process_data(String data, boolean mode) throws Exception {
        if(mode){ // duration time between stations input
            String []str = data.split(" ");
            String dep = str[0];
            String dest = str[1];
            int time = Integer.parseInt(str[2]);

            if(!Info.containsKey(dep)){throw new Exception("there is ghost station");}
            Info.get(dep).add(dest, time);

        }else{ // station id and name input
            String []str = data.split(" ");
            String id = str[0];
            String name = str[1];

            if(Info.containsKey(id)){throw new Exception("multiple "+id+"exists");}
            Info.put(id, new StationInfo(name));

            if(!Map_names.containsKey(name)){
                Map_names.put(name, new ArrayList<>());
            }
            Map_names.get(name).add(id);
        }

    }

    // connects between transfer station
    public static void connect_transfer_station(){
        for(Map.Entry<String, ArrayList<String>> entry: Map_names.entrySet()){
            for(String id1: entry.getValue()){
                for(String id2: entry.getValue()){
                    if(!id1.equals(id2)){
                        Info.get(id1).add(id2, TIME_TRANSFER);} } } }
    }

    public static String process_path(String input) throws Exception{
        String[] str = input.split(" ");
        String dep = str[0];
        String dest = str[1];
        ArrayList<String> dep_ids = Map_names.get(dep);
        ArrayList<String> dest_ids = Map_names.get(dest);
        ArrayList<Path> paths = new ArrayList<>();

        for(String dep_id: dep_ids){
            paths.add(dijkstra(dep_id, dest_ids));
        }

        Collections.sort(paths);

        return path_to_string(paths.get(0));
    }

    public static Path dijkstra(String dep_id, ArrayList<String> dest_ids) throws Exception{
        // Store the shortest path. Key string is destination.
        HashMap<String, Path> paths = new HashMap<>();
        PriorityQueue<Pair<String, Integer>> heap_dijk = new PriorityQueue<>();

        paths.put(dep_id, new Path(dep_id));
        heap_dijk.add(new Pair(dep_id, 0));
        int temp_shortest = Integer.MAX_VALUE;
        String dest_id = "";

        while (heap_dijk.size()>0){
            Pair<String, Integer> pair = heap_dijk.poll();
            String sta_id = pair.first;
            int time_to_sta = paths.get(sta_id).getLength();

            // element in the heap is not the shortest |Or| there is a shorter path
            if(pair.second > time_to_sta || time_to_sta >= temp_shortest){continue;}

            ArrayList<Pair<String, Integer>> adj = Info.get(sta_id).getAdjacent();
            for(Pair<String, Integer> info: adj){
                String dest_temp = info.first;
                int time_of_line = info.second + time_to_sta;

                if(!paths.containsKey(dest_temp) || paths.get(dest_temp).getLength() >  time_of_line){
                    paths.put(dest_temp, paths.get(sta_id).add(dest_temp, info.second));
                    heap_dijk.add(new Pair<>(dest_temp, time_of_line));
                    // refresh the shortest line
                    if(dest_ids.contains(dest_temp) && temp_shortest > time_of_line){
                        temp_shortest = time_of_line;
                        dest_id = dest_temp;
                    }
                }
            }
        }

        return paths.get(dest_id);
    }

    public static String path_to_string(Path path){
        StringBuilder sb = new StringBuilder();
        boolean transfer = false;
        for(int i=0; i<path.passed.size(); i++){
            String sta_id = path.passed.get(i);
            String sta_name = Info.get(sta_id).getName();
            if(transfer){
                transfer = false;
                sb.append('[').append(sta_name).append(']').append(' ');
                continue;
            }
            if(i<path.passed.size()-1 && sta_name.equals(Info.get(path.passed.get(i+1)).getName())){
                transfer = true;
                continue;
            }
            sb.append(sta_name).append(' ');
        }
        sb.setLength(sb.length()-1);
        sb.append(System.lineSeparator()).append(path.getLength());
        return sb.toString();
    }

}
