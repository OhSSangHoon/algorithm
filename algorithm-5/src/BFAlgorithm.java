import java.util.Scanner;

//Text�� ��� ��ġ���� ���۵Ǵ� pattern�� �˻�
public class BFAlgorithm {
	
	public static int search(String pat, String txt) {
		int M = pat.length(); //pattern ����
		int N = txt.length(); //text ����
		for(int i =0; i <= N - M; i++) {
			int j;
			for(j = 0; j < M; j++) {
				if(txt.charAt(i+j) != pat.charAt(j))
					break;
			}
			if(j == M)
				return i; //pattern�� ���۵Ǵ� text�� ��ġ(PATTERN�� ã�� ������ ���� Ƚ��)
		}
		return N; //not found
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Scanner sc = new Scanner(System.in);
		String s = sc.next();
		String p = sc.next();
		
		int pattern = search(p, s);
		
		System.out.println("Find Match: " + pattern);
	}

}

//�־��� ��� pattern�� ���� ���ڰ� �ݺ��� ���, ���� ������ �������� ���� �� �ִ�.
//���� ���⵵ worst: O(N*M)  |  best: O(N)