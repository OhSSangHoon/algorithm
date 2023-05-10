package mergeSort;

import java.util.*;

public class mergeTD extends Abstraction{
	
	
	public static void TDsort(Comparable[] arr) {
		Comparable[] aux = new Comparable[arr.length];
		TDsort(arr, aux, 0, arr.length-1);
		//0 = lo, arr.lenght = hi
	}
	
	private static void TDsort(Comparable[] arr, Comparable[] aux, int lo, int hi) {
		if(hi <= lo)
			return;
		
		int mid = lo + (hi-lo) / 2; //중앙값 구하기
		
		TDsort(arr, aux, lo, mid); //lo부터 mid까지 정렬
		TDsort(arr, aux, mid+1, hi);//mid+1부터 hi까지 정렬
		
		//mid가 mid+1보다 작다면 이미 정렬이 완료된 상태이기에 값을 병합하지않고 값을 반환해준다
		if(less(arr[mid], arr[mid+1]))
			return;
		
		merge(arr, aux, lo, mid, hi); //전부 병합
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Comparable[] arr = {"m","e","r","g","e","s","o","r","t","e","x","a","m","p","l","e"};
		System.out.println(Arrays.toString(arr));
		TDsort(arr);
		System.out.println(Arrays.toString(arr));
	}
}
