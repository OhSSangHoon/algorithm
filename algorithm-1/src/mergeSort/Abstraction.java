package mergeSort;

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
	
	
	//병합 정렬(제자리 병합) - UpDown
	protected static void merge(Comparable[] arr, Comparable[] aux, int lo, int mid, int hi) {
		
		//배열을 치음부터 끝까지 순환
		for(int k = lo; k <= hi; k++) {
			 aux[k] = arr[k]; //복사
		}
		
		//aux[] 배열을 비교하여 병합된 결과를 a[] 배열에 다시 저장
		int i = lo, j = mid+1;
		
		//배열을 치음부터 끝까지 순환
		for(int k = lo; k <= hi; k++) {
			 // 만약 lo가 mid보다 크다면, 왼쪽 절반의 배열은 이미 병합이 끝났기 때문에
		    //오른쪽 하위 배열의 나머지 요소를 병합
			if(i > mid)
				arr[k] = aux[j++];
			// 만약 hi가 mid보다 작다면, 오른쪽 절반의 배열은 이미 병합이 끝났기 때문에
		    // 왼쪽 하위 배열의 나머지 요소를 병합
			else if(j > hi)
				arr[k] = aux[i++];
			//aux[j] < aux[i]일 때, 두 번째 하위 배열의 요소를 병합
			else if(less(aux[j], aux[i]))
				arr[k] = aux[j++];
			//아니라면, 첫 번째 하위 배열의 요소를 병합
			else
				arr[k] = aux[i++];
		}
	}
	
	//BottomTop
	
	//@param in 입력 배열
	//@param out 출력 배열
	//@param lo 첫 번째 하위 배열의 시작 인덱스
	//@param mid 첫 번째 하위 배열의 끝 인덱스 및 두 번째 하위 배열의 시작 인덱스
	//@param hi 두 번째 하위 배열의 끝 인덱스
	protected static void mergeBU(Comparable[] in, Comparable[] out, int lo, int mid, int hi) {
		
		int i = lo, j = mid+1;
		
		for(int k = lo; k <= hi; k++) {
			if(i > mid)//i가 끝까지 다돌면
				out[k] = in[j++];
			// 첫 번째 하위 배열의 모든 요소가 병합되었으므로, 두 번째 하위 배열의 나머지 요소를 병합
			else if(j > hi)//j가 끝까지 다 돌면
				out[k] = in[i++];
			// 두 번째 하위 배열의 모든 요소가 병합되었으므로, 첫 번째 하위 배열의 나머지 요소를 병합
			else if(less(in[j],in[i]))//in[j](두 번째 하위배열)가
				//in[i](첫 번째 하위배열) 보다 작다면
				out[k] = in[j++];//두 번째 하위배열의 요소를 병합
			else//첫 번째 하위 배열의 요소가 두 번째 하위 배열의 요소보다 작거나 같은 경우,
				out[k] = in[i++];//첫 번째 하위 배열의 요소를 병합
		}
	}
	
}
