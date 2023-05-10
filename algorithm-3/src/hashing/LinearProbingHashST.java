package hashing;

import java.util.ArrayList;
import java.util.Scanner;

public class LinearProbingHashST<K,V>{
	
	private int N; //원소의 수
	private int M; //테이블의 크기
	private K[] keys; //key를 저장하는 배열
	private V[] vals; //values를 저장하는 배열
	private int[] hashIndices; //hash Index를 저장하는 배열
	
	public LinearProbingHashST() {
		this(31);
	}
	public LinearProbingHashST(int M) {
		keys = (K[]) new Object[M];
		vals = (V[]) new Object[M];
		hashIndices = new int[M];
		this.M = M;
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
	
	//hash함수
	private int hash(K key) {
		return (key.hashCode() & 0x7fffffff) % M;
	}

	private V get(K key) {
		// TODO Auto-generated method stub
		for(int i = hash(key); keys[i] != null; i = (i + 1) % M)
			if(keys[i].equals(key)) //search hit
				return vals[i];
		return null; //search miss
	}
	
	
	//Linear Probing 예(3)을 풀기 위해 put함수 변경
//	private void put(K key, V value, int hashIndex) {
//		if (N >= M / 2)
//			resize(2*M + 1);
//		int i ;
//		for(i = hash(key); keys[i] != null; i = (i + 1) % M) //테이블 재구성
//			if(keys[i].equals(key)) { //기존에 존재하는 키
//				vals[i] = value; 
//				return;
//			}
//		keys[i] = key;  //새로(Key,value)의 쌍을 추가
// 		vals[i] = value;
// 		hashIndices[i] = hashIndex;
//		N++;
//	}
	

//	public void put(K key, V value, int hashIndex) {
//	    if (hashIndex < 0 || hashIndex >= M) {
//	        throw new IllegalArgumentException("Hash index out of bounds");
//	    }
//
//	    if (keys[hashIndex] == null) {
//	        keys[hashIndex] = key;
//	        vals[hashIndex] = value;
//	        N++;
//	    } else {
//	        // 선형 탐사 시작
//	        int i = hashIndex;
//	        do {
//	            i = (i + 1) % M;
//	            if (keys[i] == null) {
//	                keys[i] = key;
//	                vals[i] = value;
//	                N++;
//	                break;
//	            }
//	        } while (i != hashIndex); // 해시 인덱스가 처음으로 돌아오면 종료
//	    }
//	}

	public void put(K key, V value, int hashIndex) {
	    int i;
	    for (i = hashIndex; keys[i] != null; i = (i + 1) % M) {
	        if (keys[i].equals(key)) {
	            vals[i] = value;
	            return;
	        }
	    }
	    keys[i] = key;
	    vals[i] = value;
	    N++;
	}
	
	//예(3)을 풀기 위해 직접 해시 테이블에 인덱스를 입력하기 때문에 keys() 함수는 사용하지 않는다.
	public Iterable<K> keys(){
		ArrayList<K> keyList = new ArrayList<>();
		for(int i =0; i < M; i++) {
			if(keys[i] != null) {
				keyList.add(keys[i]);
			}
		}
		return keyList;
	}
	
	//hashTable key와 val을 반환할 수 있도록 함수 선언
	public K getKeyAt(int index) {
	    if (index < 0 || index >= M) {
	        throw new IllegalArgumentException("Index out of bounds");
	    }
	    return keys[index];
	}

	public V getValueAt(int index) {
	    if (index < 0 || index >= M) {
	        throw new IllegalArgumentException("Index out of bounds");
	    }
	    return vals[index];
	}
	
	private void resize(int capacity) {
		// TODO Auto-generated method stub
		K[] tempk = (K[]) new Comparable[capacity];
		V[] tempv = (V[]) new Object[capacity];
		for(int i =0; i < N; i++) {
			tempk[i] = keys[i];
			tempv[i] = vals[i];
 		}
		vals = tempv;
		keys = tempk;
		
	}
	
	//hash table의 재구성
	//packing density를 적정한 수준으로 유지
	//동적 해싱
//	private void resize(int capacity) {
//		LinearProbingHashST<K,V> t;
//		t = new LinearProbingHashST<>(capacity);
//		for(int i =0; i < M; i++) {
//			if(keys[i] != null)
//				t.put(keys[i], vals[i], hashIndices[i]); //새로운 테이블에 모든 원소 추가
//			keys = t.keys;
//			vals = t.vals;
//			hashIndices = t.hashIndices;
//			M = t.M;
//		}
//	}
//	
	
	//delete local reorganization
	public void delete(K key) {
	    if (!contains(key))
	        return;

	    int i = hash(key);
	    while (!key.equals(keys[i]))
	        i = (i + 1) % M;

	    keys[i] = null;
	    vals[i] = null;
	    N--;

	    i = (i + 1) % M;
	    while (keys[i] != null) {
	        K keyToRehash = keys[i];
	        V valToRehash = vals[i];
	        keys[i] = null;
	        vals[i] = null;

	        put(keyToRehash, valToRehash, hash(keyToRehash));

	        i = (i + 1) % M;
	    }
	}

	
	
	public static void main(String[] args) {
	    Scanner scanner = new Scanner(System.in);
	    System.out.print("Enter the size of the hash table: ");
	    int hashTableSize = scanner.nextInt();
	    scanner.nextLine(); // Consume newline character
	    
	    LinearProbingHashST<String, Integer> hashTable = new LinearProbingHashST<>(hashTableSize);

	 // Adding keys and values to the hash table
	    while (true) {
	        System.out.print("Enter key:");
	        String key = scanner.nextLine();

	        if (key.equalsIgnoreCase("exit")) {
	            break;
	        }

	        System.out.print("Enter value:");
	        int value = Integer.parseInt(scanner.nextLine());

	        System.out.print("Enter hash index:");
	        int hashIndex = Integer.parseInt(scanner.nextLine());

	        hashTable.put(key, value, hashIndex);
	    }

	    System.out.println("Keys and values in the hash table (before deletion):");
	    for (int i = 0; i < hashTableSize; i++) {
	        String currentKey = hashTable.getKeyAt(i);
	        Integer currentValue = hashTable.getValueAt(i);
	        if (currentKey != null) {
	            System.out.println("hash index[" + i + "]: " + "Key: " + currentKey + ", Value: " + currentValue);
	        } else {
	            System.out.println("Hash index[" + i + "]: null");
	        }
	    }

	    // Deleting keys from the hash table
	    while (true) {
	        System.out.print("Enter key to delete (or type 'exit' to finish):");
	        String keyToDelete = scanner.nextLine();

	        if (keyToDelete.equalsIgnoreCase("exit")) {
	            break;
	        }

	        hashTable.delete(keyToDelete);

	        System.out.println("Keys and values in the hash table (after deletion):");
	        for (int i = 0; i < hashTableSize; i++) {
	            String currentKey = hashTable.getKeyAt(i);
	            Integer currentValue = hashTable.getValueAt(i);
	            if (currentKey != null) {
	                System.out.println("hash index[" + i + "]: " + "Key: " + currentKey + ", Value: " + currentValue);
	            } else {
	                System.out.println("Hash index[" + i + "]: null");
	            }
	        }
	    }

	    scanner.close();

	}


}