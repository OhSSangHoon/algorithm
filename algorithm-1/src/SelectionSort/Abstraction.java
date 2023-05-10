package SelectionSort;

public abstract class Abstraction {
	public static void sort(Comparable[] a) { };
	
	//v와 w를 비교 v가 w보다 작다면 true 아니라면 false
	protected static boolean less(Comparable v, Comparable w) {
		return v.compareTo(w) < 0;
	}
	
	//swap 함수
	protected static void exch(Comparable[] a,int i, int j) {
		Comparable t = a[i];
		a[i] = a[j];
		a[j] = t;
	}
	
	//출력 함수
	protected static void show(Comparable[] a) {
		for(int i =0; i < a.length; i++) {
			System.out.println(a[i] + " ");
		}
		System.out.println();
	}
	
	//정렬 확인 함수 a[i]가 a[i-1]보다 작다면 false, 아니라면 true로 실행
	protected static boolean isSorted(Comparable[] a) {
		for(int i =1; i < a.length; i++) {
			if(less(a[i], a[i-1])) {
				return false;
			}
		}
		return true;
	}
}
