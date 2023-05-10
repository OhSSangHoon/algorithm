package mergeSort;

import java.util.*;

public class mergeBT extends Abstraction{
	
	public static void BTsort(Comparable[] a) {
		Comparable[] src = a, dst = new Comparable[a.length], temp;
		int N = a.length;
		
		//병합할 부분 배열의 크기 2배수씩 증가 1, 2, 4, 8...
		for(int n = 1; n < N; n *= 2) {
			for(int i = 0; i < N; i += 2*n)
				//각 부분배열은 i부터 i+n-1까지와 i+n부터 i+2*n-1까지 구성
				mergeBU(src, dst, i, i+n-1, Math.min(i+2*n-1, N-1));
			
			temp = src;
			src = dst;
			dst = temp;
		}
	    // src와 a가 같지 않으면 src를 dst로 복사
		if(src != a)
			System.arraycopy(src, 0, dst, 0, N);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Comparable[] arr = {"m","e","r","g","e","s","o","r","t","e","x","a","m","p","l","e"};
		System.out.println(Arrays.toString(arr));
		BTsort(arr);
		System.out.println(Arrays.toString(arr));
	}

}
