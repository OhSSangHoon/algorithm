package SymbolTable;

import java.util.ArrayList;

public class BinarySearchST <K extends Comparable<K>, V>{
	private static final int INIT_CAPACITY = 10;
	private K[] keys; //key의 배열
	private V[] vals; //value의 배열
	private int N;
	
	public BinarySearchST() {
		keys = (K[]) new Comparable[INIT_CAPACITY];
		vals = (V[]) new Object[INIT_CAPACITY];
	}
	
	public BinarySearchST(int capacity) {
		keys = (K[]) new Comparable[capacity];
		vals = (V[]) new Object[capacity];
	}
	
	public boolean contains(K key) {
		return get(key) != null;
	}
	
	public boolean isEmpty() {
		return N == 0;
	}
	
	public int size() {
		return N;
	}
	
	private void resize(int capacity) {
		K[] tempk = (K[]) new Comparable[capacity];
		V[] tempv = (V[]) new Object[capacity];
		for(int i =0; i < N; i++) {
			tempk[i] = keys[i];
			tempv[i] = vals[i];
 		}
		vals = tempv;
		keys = tempk;
	}
	
	private int search(K key) {//Binary Search
		int lo = 0;//시작
		int hi = N-1;//끝
		while(lo <= hi) {//반복문 
			int mid = (hi+lo) / 2; //중앙값
			 //key값을 비교하여 key값이 keys[mid]보다 크다면 cmp = 1
			//값이 같으면 0, key값이 keys[mid]보다 작으면 -1
			int cmp = key.compareTo(keys[mid]);
			
			if(cmp < 0)
				hi = mid - 1;
			else if(cmp > 0)
				lo = mid + 1;
			else
				return mid;//mid 반환
		}
		return lo;//key가 없을 경우, -1이 아닌 lo를 반환
		
	}

	public V get(K key) {
		// TODO Auto-generated method stub
		if(isEmpty())
			return null;
		int i = search(key);
		 //i가 N보다 작고, keys[i]값이 key값과 같다면
		if(i < N && keys[i].compareTo(key) == 0)
			return vals[i];//vals[i]값 반환
		else
			return null;//key가 없으면 null반환
	}
	
	public void put(K key, V value) {
		int i = search(key);
		if(i < N && keys[i].compareTo(key) == 0) {
			vals[i] = value;
			return;
		}
		if(N == keys.length)
			resize(2*keys.length);
		
		for(int j = N; j > i; j--) {
			//뒤로 한칸씩 당겨서 key값이 추가될 공간 확보
			keys[j] = keys[j-1]; vals[j] = vals[j-1];
		}
		keys[i] = key;
		vals[i] = value;
		N++;
	}

	
	public Iterable<K> keys(){
		ArrayList<K> keyList = new ArrayList<K>(N);//ArrayList생성
		//반복문을 실행해 keyList(ArrayList)에 keys[i]값을 추가해준다.
		for(int i =0; i < N; i++)
			keyList.add(keys[i]);
		return keyList; //keyList반환
	}
	
	
}
