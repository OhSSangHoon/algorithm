import java.io.*;
import java.util.*;


public class HW2 {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		String filename = sc.nextLine();
		
		BufferedReader reader = null;
		
		try {
			
			reader = new BufferedReader(new FileReader(filename));
			String fileScanner = null;
			
			while((fileScanner = reader.readLine()) != null) {
				System.out.println(fileScanner);
			}
			
		}catch(FileNotFoundException e) {
			System.out.println("File ã�� ����");
		}
		
		catch (IOException e) {
			System.out.println("file �о���� ����");
		}
	}

}
