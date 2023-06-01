
public class BoyerMoore {
	
	private static final int[] right = null;
	private String pat;
	
	public int search(String txt) {
		int M = pat.length(); //patter의 길이
		int N = txt.length(); //text의 길이
		int skip;
		
		for(int i =0; i < N-M; i += skip) {
			skip = 0;
			for(int j = M-1; j >= 0; j--) {
				if(pat.charAt(j) != txt.charAt(i+j)) { //mismatch
					skip = Math.max(1, j-right[txt.charAt(i+j)]);
					break;
				}
			}
			if(skip == 0)
				return i;
		}
		return N;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
