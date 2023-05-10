package LineSort;

public class CountingSort {
	
	public static int[] sort(int[] A, int K) {
		int i,N = A.length;
		int[] C = new int[K]; //임시 배열 C
		int[] B = new int[N]; //결과 배열 B
		
		//배열A에 들어있는 숫자의 개수 배열C에 자리수별로 비교하여 정렬
		//Ex) A에 {0,0,0}, C[0] == 3
		for(i =0; i < N; i++)
			C[A[i]]++;
		//C에 있는 수를 누적합해서 저장한다
		//누적합을 계산하면 C[i]는 배열에서 i보다 작거나 같은 값의 개수를 나타낸다.
		for(i = 1; i < K; i++)
			C[i] += C[i-1];
		//뒤에서부터 C배열을 순회하며, 각 원소의 값을 인덱스로 사용하여 B배열에 저장
		//이때 C'배열에서 해당 원소의 값을 하나 감소시키고, B배열의 인덱스는 C'배열의 값을 사용
		//마지막으로 다시 A배열에 B배열값을 복사
		for(i = N-1; i >= 0; i--)
			B[--C[A[i]]] = A[i];
		
		return B;
	}

	public static void main(String[] args) {
		int[] A = {10, 4, 5, 8, 1, 8, 3, 6}, B; //입력 배열 A
		B = CountingSort.sort(A, 11);
		
		for (int i = 0; i < B.length; i++) 
		System.out.print(B[i] + " ");
	}
}
