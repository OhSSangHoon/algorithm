package ShellSort;

public class Shell extends Abstraction{

	//shell sort
	public static void sort(Comparable[] a) {
		int N = a.length;
		int h = 4;
		//while(h < N/3)
			//h = 3*h + 1; //1, 4, 13, 40, 121...(h만큼 떨어진 원소들과 삽입 정렬)
		
		//h가 1보다 작으면 탈출
		while(h > 1) {
			for(int i =h; i < N; i++) {
				//j -=h : j에서 h만큼 계속 뺀다.
				for(int j = i; j >= h && less(a[j], a[j-h]); j -= h)
					exch(a, j, j-h);
				}//h만큼 떨어진 원소들과 삽입 정렬
			h /= 4; //h = 3*h + 1를 (a.length / 3)만큼 곱한 수에서 3씩 나눠서 1이 될때 까지 한다 
		}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//Integer[] a= {20, 5, 12, 17, 8, 10, 4 ,7, 13, 2};
		
		//A B C D E F G H I J K L M N O P Q R S T U V W X Y Z
		
		Comparable[] a = {'E', 'A', 'N', 'Y', 'S', 'O', 'R', 'T', 'Q', 'U', 'E', 'S', 'T', 'I', 'O', 'S'};
		sort(a);
		show(a);
	}
}