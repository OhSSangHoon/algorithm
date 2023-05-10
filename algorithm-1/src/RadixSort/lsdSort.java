package RadixSort;

import java.util.Arrays;

public class lsdSort {
	
	public static void LSD(int[] arr) {
		int i ,m = arr[0], exp = 1, n = arr.length;
		int[] b = new int[n]; 
		
		for(i = 0; i < n; i++) {
			if(arr[i] > m)
				m = arr[i];
		}
		
		while(m / exp > 0) {
			int[] c = new int[10];
			
			for(i = 0; i < n; i++)
				c[(arr[i] / exp) % 10]++; 
			
			
			for(i =1; i < 10; i++)
				c[i] += c[i-1];
			
			for(i = n-1; i >=0; i--)
				
				b[--c[(arr[i] / exp) % 10]] = arr[i];
			
			
			for(i = 0; i < n; i++)
				arr[i] = b[i];
			
		
			System.out.println(exp + "의 자리 정렬: " + Arrays.toString(arr));
			System.out.println(' ');
			
		
			exp *= 10;
		}
		
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] arr = {179, 208, 306, 93, 859, 984, 55, 9, 271, 33};
		
		LSD(arr);
		
	}
}
