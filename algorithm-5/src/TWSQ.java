public class TWSQ {

	//charAt의 역할은?
	//문자열 s의 d번째 문자를 ASCII 숫자로 반환한다.
	//d가 문자열의 길이(s.length)를 초과하면 -1을 반환
	//문자열에 대해 인덱스 위치의 문자를 확인하는데 사용
	private static int charAt(String s, int d) {
		// TODO Auto-generated method stub
		if(d < s.length()) {
			return s.charAt(d);
		}else {
			return -1;
		}
	}
	//배열 전체를 정렬하는 작업
	public static void sort(String[] a) {
		sort(a, 0, a.length -1, 0);
	}
	
	//lo와 hi는 배열의 부분집합을 정의, d는 현재 문자 위치를 나타낸다
	private static void sort(String[] a, int lo, int hi, int d) {
		if(hi <= lo) return;
		
		int lt = lo;
		int gt = hi;
		int v = charAt(a[lo], d); //pivot 문자를 저장
		int i = lo + 1; //현재 검사할 배열의 위치
		
		while(i <= gt) {
			int t = charAt(a[i], d);
			//현재 문자가 pivot보다 작으면 왼쪽으로 이동 lt는 오른쪽으로 이동
			if(t < v) {
				exch(a, lt++, i++);
			//크면 오른쪽으로 이동 gt는 왼쪽으로 이동
			}else if(t > v) {
				exch(a, i, gt--);
			//그렇지 않으면 현재 위치에 둔다
			}else {
				i++;
			}
		}
		
		sort(a, lo, lt-1, d);
		if(v >= 0) {
			sort(a, lt, gt, d+1);//다음 문자위치로 이동
		}
		sort(a, gt+1, hi, d);
	}

	//문자 swap
	private static void exch(String[] a, int i, int j) {   
		// TODO Auto-generated method stub
		String temp = a[i];
		a[i] = a[j];
		a[j] = temp;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
        String[] a = {"she", "sells", "seashells", "by", "the", "sea", "shore", "the", "shells", "she", "shlls", "are", "surely", "seashells"};
        
        System.out.print("정렬 전: ");
        for(String s : a) {
        	System.out.print(s + " ");
        }
        
        System.out.println("\n");
        
        sort(a);
        
        System.out.print("정렬 후: ");
        for(String s : a) {
        	System.out.print(s + " ");
        }
	}

}


//시간 복잡도: N logN  /  키 연산: charAt() 함수