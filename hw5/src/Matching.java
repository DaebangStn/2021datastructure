import java.io.*;
import java.util.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Matching
{
	static SubstringTable table;

	public static void main(String args[])
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		while (true){
			try
			{
				String input = br.readLine();
				if (input.compareTo("QUIT") == 0)
					break;

				command(input);
			}catch (IOException e){
				System.err.println("입력이 잘못되었습니다. 오류 : " + e.toString());
			}
		}
	}

	private static void command(String input) throws IOException
	{
		String[] inputs = input.split(" ");
		if(inputs[0].equals("<")){
			table = new SubstringTable();
			List<String> data_lst = Files.readAllLines(Paths.get(inputs[1]));

			for(int j=0; j<data_lst.size(); j++){
				String str = data_lst.get(j);
				String str_sub;
				for(int i=0; i<str.length()-6; i++){
					str_sub = str.substring(i, i+6);
					table.add(str_sub, new Position(j, i));
				}
			}
		}else if(inputs[0].equals("@")){
			System.out.println(table.print(Integer.parseInt(inputs[1])));
		}else if(inputs[0].equals("?")){
			for(Position p: table.search(inputs[1]))
				System.out.println(p.toString());
		}else{
			throw new IOException("invalid pattern");
		}
	}
}
