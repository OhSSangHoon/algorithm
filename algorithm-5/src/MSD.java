public class MSD {
	private static int R = 256; //ASCII 코드의 기수 값 256
	private static int M = 15; //CUTOFF: M = 15
	private static String[] aux;
	
	private static int charAt(String s, int d) {
		if(d < s.length()) {
			return s.charAt(d);
		}else {
			return -1;
		}
	}
	
	public static void sort(String[] a) {
		int N = a.length;
		aux = new String[N];
		sort(a, 0, N-1, 0);
	}
	
	private static void sort(String[] a, int lo, int hi, int d) {
		if(hi <= lo + M) {
			insertionSort(a, lo, hi, d);
			return;
		}
		
		int[] count = new int[R + 2];
		for(int i = lo; i <= hi; i++) {
			int c = charAt(a[i], d);
			count[c + 2]++;
		}
		
		for(int r = 0; r < R + 1; r++) {
			count[r + 1] += count[r];
		}
		
		for(int i = lo; i <= hi; i++) {
			int c = charAt(a[i], d);
			aux[count[c + i]++] = a[i];
		}
		
		for(int i = lo; i <= hi; i++) {
			a[i] = aux[i-lo];
		}
		
		for(int r = 0; r < R; r++) {
			sort(a, lo + count[r], lo + count[r+1]-1, d + 1);
		}
	}
	
	private static void insertionSort(String[] a, int lo, int hi, int d) {
		// TODO Auto-generated method stub
		for(int i =lo; i <= hi; i++) {
			for(int j = i; j > lo && less(a[j], a[j-1], d); j--) {
				swap(a, j, j-1);
			}
		}
	}
	

	private static void swap(String[] a, int j, int i) {
		// TODO Auto-generated method stub
		String temp = a[i];
		a[i] = a[j];
		a[j] = temp;
	}

	//d번째 문자부터 비교하도록 less함수 수정
	private static boolean less(String v, String w, int d) {
		// TODO Auto-generated method stub
		for(int i = d; i < Math.min(v.length(), w.length()); i++) {
			if(v.charAt(i) < w.charAt(i)) return true;
			if(v.charAt(i) > w.charAt(i)) return false;
		}
		return v.length() < w.length();
		
		//return v.substring(d).compareTo(w.substring(d)) < 0;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String[] a = {"car", "bar", "bat", "cat", "cab", "rat", "tab", "bag", "c", "ba", "a"};
		
		sort(a);
		
		for(String s : a) {
			System.out.print(s + " ");
		}
	}

}
