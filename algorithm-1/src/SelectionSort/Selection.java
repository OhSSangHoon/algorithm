package SelectionSort;

import java.util.*;

public class Selection extends Abstraction{
//	
//public class Selection extends Abstraction{
//	public static boolean less(Comparable a, Comparable b) {
//		return a.compareTo(b) < 0;
//	}
//	
//	public static void exch(Comparable[] a,int i, int j) {
//		Comparable t = a[i];
//		a[i] = a[j];
//		a[j] = t;
//	}
//	
//	public static void show(Comparable[] a) {
//		for(int i =0; i < a.length; i++) {
//			System.out.println(a[i] + " ");
//		}
//		System.out.println();
//	}
//	
//	public static boolean isSorted(Comparable[] a) {
//		for(int i =1; i < a.length; i++) {
//			if(less(a[i], a[i-1])) {
//				return false;
//			}
//		}
//		return true;
//	}
//	
	public static void sort(Comparable[] a) {
		int N = a.length;
		for(int i =0; i < a.length; i++) {
			int min = i;
			for(int j = i+1; j < a.length; j++) {
				if(less(a[j],a[min])) {
				min = j;
				}
			}
			exch(a, i, min);
		}
		assert isSorted(a);
	}
	
	
	public static void main(String[] args) {
		Integer[] a= {10, 5, 4, 3, 2, 6, 8, 9, 1};
		sort(a);
		show(a);
	}
}