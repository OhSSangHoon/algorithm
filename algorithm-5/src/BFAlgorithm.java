import java.util.Scanner;

//Text의 모든 위치에서 시작되는 pattern을 검사
public class BFAlgorithm {
	
	public static int search(String pat, String txt) {
		int M = pat.length(); //pattern 길이
		int N = txt.length(); //text 길이
		for(int i =0; i <= N - M; i++) {
			int j;
			for(j = 0; j < M; j++) {
				if(txt.charAt(i+j) != pat.charAt(j))
					break;
			}
			if(j == M)
				return i; //pattern이 시작되는 text의 위치(PATTERN을 찾기 까지의 동작 횟수)
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

//최악의 경우 pattern이 동일 문자가 반복될 경우, 성능 저하의 문제점이 생길 수 있다.
//시작 복잡도 worst: O(N*M)  |  best: O(N)