package InsertionSort;

public class Insertion extends Abstraction{

	public static void sort(Comparable[] a) {
		int N = a.length;
		for(int i =1; i < N; i++) {
			//j =i ���� ���� j �� 0���� ũ�� less�� true��� ���� �ƴϸ� ���� �ڿ������� ���� 
			//�������� = [j-1] [j]
			for(int j = i; j > 0 && less(a[j], a[j-1]); j--) {
				exch(a, j, j-1);
			}
		}
		assert isSorted(a);
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Integer[] a = {10, 4, 5, 2, 1, 8, 3, 6};
		sort(a);
		show(a);
	}

}
