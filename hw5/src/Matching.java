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
		String in1 = input.substring(0, 1);
		String in2 = input.substring(2);
		if(in1.equals("<")){
			table = new SubstringTable();
			List<String> data_lst = Files.readAllLines(Paths.get(in2));

			for(int j=0; j<data_lst.size(); j++){
				String str = data_lst.get(j);
				String str_sub;
				for(int i=0; i<=str.length()-6; i++){
					str_sub = str.substring(i, i+6);
					table.add(str_sub, new Position(j, i));
				}
			}
		}else if(in1.equals("@")){
			System.out.println(table.print(Integer.parseInt(in2)));
		}else if(in1.equals("?")){
			System.out.println(table.search(in2));
		}else{
			throw new IOException("invalid pattern");
		}
	}
}
