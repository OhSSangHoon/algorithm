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
	            System.out.println("������ �о���� ���� ������ �߻��߽��ϴ�: " + e.getMessage());
	        }
		
	}

}
