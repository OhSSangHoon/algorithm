package BucketSort;

import java.util.*;

//public class Bucket {
//
//	public static void BucketSort(int[] arr, int maxVal) {
//		int n = arr.length;
//		
//		//배열에서 최대값과 최소값 찾기
//		for(int i =0; i < n; i++) {
//			maxVal = Math.max(maxVal, arr[i]); //배열값 중 가장 큰 숫자를 반환함
//		}
//		
//		//버킷 만들기
//		int[] bucketSize = new int[maxVal + 1];//입력값의 범위를 기준으로 버킷의 크기 결정
//		
//		//버킷 초기화
//		for(int i =0; i < bucketSize.length; i++){
//			bucketSize[i] = 0; //모든 버킷을 0으로 초기화
//		}
//		
//		//각 값이 속하는 버킷에 담아주기
//		for(int i =0; i < n; i++) {
//			bucketSize[arr[i]]++; //입력값을 각 버킷에 맞게 분배
//		} 
//		
//		int idx=0;
//		
//		//각 버킷에 들어있는 값들을 정렬해서 원래 배열에 합치기
//		for(int i =0; i < bucketSize.length; i++) {
//			for(int j =0; j < bucketSize[i]; j++) {
//				arr[idx++] = i; //각 버킷에 있는 값들을 순서대로 배열에 담기
//			}
//		}
//		
//	}
//		
//	public static void main(String[] args) {
//		// TODO Auto-generated method stub
//		int maxVal = Integer.MIN_VALUE; //정수 범위의 최대값
//		
//		int[] arr = {29,25,3,49,9,37,21,43};
//		
//		System.out.println("정렬 전: " + Arrays.toString(arr));
//		
//		BucketSort(arr, maxVal);
//		
//		System.out.println("정렬 후: " + Arrays.toString(arr));
//	}
//
//}


public class Bucket {
	public static void main(String[] args){
		Scanner scanner = new Scanner(System.in);
		StringBuilder sb = new StringBuilder();
		
		int N = scanner.nextInt();
		
		ArrayList<Integer> list = new ArrayList<>();
		
		//n개의 정수를 입력받아 list에 추가
		for(int i =0; i < N; i++) {
			list.add(scanner.nextInt());
		}
		
		//리스트 오름차순 정렬
		Collections.sort(list);
		
		for(int value : list) {
			//정렬된 리스트를 반복문을 통해 sb에 리스트값 추가
			sb.append(value).append('\n');
		}
		System.out.println(sb);
	}
}
