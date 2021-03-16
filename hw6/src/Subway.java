import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.file.*;

public class Subway {

    public static void main(String[] args) {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String input = null;

        try {
            // input file from args[0]
            if(args.length != 1){throw new Exception();}
            Path path = Paths.get(args[0]);
            //TODO: 적절한 자료구조에 path입력하여 데이터 받아들이기

            // input stdin
            while (true){
                input = br.readLine();

                if(input.toUpperCase().equals("QUIT")){break;}

                String []str = input.split(" ");
                process_path(str[0], str[1]);
            }
            System.err.println("END");
        } catch (Exception e) {
            System.err.println("ERROR");
        }

    }

    public static void process_path(String src, String des){
        //TODO: 입력한 정보를 바탕으로 최단거리 출력
    }
}
