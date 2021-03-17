import java.io.*;

public class Matching
{
	public static void main(String args[])
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		SubstringTable table = new SubstringTable();

		// handle string input
		while (true){
			try{
				String[] inputs = br.readLine().split(" ");
				if(inputs[0].equals("<")){
					File file = new File(inputs[1]);
					if(!file.exists()){throw new IOException("No file, path: " + inputs[1]);}
					BufferedReader read_file = new BufferedReader(new FileReader(file));
					String str = "";
					while ((str = read_file.readLine()) != null){System.out.println(str);}

					break;
				}else{
					throw new IOException("invalid designator");
				}
			}catch (IOException e){
				System.err.println("입력이 잘못되었습니다. 오류 : " + e.toString());
			}
		}

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
		File f = new File("./test.txt");

		System.out.println("파일의 존재 여부 " + f.exists());
	}
}
