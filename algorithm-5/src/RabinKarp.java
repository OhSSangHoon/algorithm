import java.math.BigInteger;
import java.util.Random;

//맨앞제거 맨뒤에 추가 방식

public class RabinKarp {
	private String pat;
	private long patHash;
	private int M, R = 256;
	private long Q;
	private long RM;
	
	public RabinKarp(String pat) {
		this.pat = pat;
		M = pat.length();
		Q = longRandomPrime(); //실제 해시 테이블을 만들지 않았기 때문에, 매우 큰 수로
		
		RM = 1;
		for(int i =0; i <= M-1; i++) {
			RM = (R * RM) % Q;
			patHash = hash(pat,M);
		}
	}
	
	private long hash(String key, int M) {
		// TODO Auto-generated method stub
		long h = 0;
		for(int j = 0; j < M; j++) {
			h = (R * h + key.charAt(j)) % Q;
		}
		return h;
	}

	private long longRandomPrime() {
		// TODO Auto-generated method stub
		BigInteger prime = BigInteger.probablePrime(31,  new Random());
		return prime.longValue();
	}
	
	public int search(String txt) {
		int N = txt.length();
		if(N < M) {
			return N;
		}
		long txtHash = hash(txt, M);
		
		if(txtHash == patHash && check(txt,0))
			return 0;
		
		for(int i = M; i < N; i++) {
			txtHash = (txtHash + Q - RM * txt.charAt(i-M) % Q) % Q;
			txtHash = (txtHash * R + txt.charAt(i) % Q);
			if(patHash == txtHash && check(txt, i-M+1))
				return i - M + 1;
		}
		return N;
	}

	private boolean check(String txt, int i) {
		// TODO Auto-generated method stub
		for(int j = 0; j < M; j++)
			if(pat.charAt(j) != txt.charAt(i + j))
				return false;
		return true;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
