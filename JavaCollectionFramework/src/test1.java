import java.io.*;
import java.util.*;
	
public class test1 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);
		String filename = sc.next();
		
		 try {
	            BufferedReader br = new BufferedReader(new FileReader(filename));
	            String line;
	            while ((line = br.readLine()) != null) {
	                System.out.println(line);
	            }
	        } catch (IOException e) {
	            System.out.println("파일을 읽어오는 도중 오류가 발생했습니다: " + e.getMessage());
	        }
		
	}

}
