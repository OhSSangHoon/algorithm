package hashing;

import java.util.ArrayList;


//순차 연결 리스트
class Node<K,V>{//하나의 노드를 표현한다
	//key, value, next node를 가리키는 참조의 쌍으로 구성
	K key; V value; Node<K,V> next;
	
	public Node(K key, V value, Node<K,V> next) {
		this.key = key; this.value = value; this.next = next;
	}
}

//연결리스트를 이용한 순차 검색
public class SequentialSearchST<K,V> {
	
	private Node<K,V> first; //첫 번째 노드에 대한 참조를 유지. 초기값 = null
	int N;
	
	
	public V get(K key) {
		for(Node<K,V> x = first; x !=null; x = x.next) //연결리스트를 스캔
			if(key.equals(x.key))//key값과 x에 key값을 비교 (형,값 비교)
				return x.value; //true라면(x에 key가 있다) x.value를 반환
		return null;//false라면(x에 key가 없다) null반환
	}
	
	
	public void put(K key, V value) {
		for(Node<K,V> x= first; x != null; x = x.next)
			if(key.equals(x.key)) {
				x.value = value; //value값을 x.value에 저장(x.value == null -> value)
				return;
			}
		first = new Node<K,V>(key, value, first); //key가 없으면, 맨 앞에 node추가
		N++;//다음으로
	}
	
	
	public Iterable<K> keys(){
		//ArrayList는 사이즈의 제한이없다.(동적 할당)
		ArrayList<K> keyList = new ArrayList<K>(N);
		for(Node<K,V> x = first; x != null; x = x.next)
			keyList.add(x.key);//ArrayList에 x.key값 추가
		return keyList;
	}
	
	
	public void delete(K key) {
		if(key.equals(first.key)) {
			first = first.next; N--;
			return;
		}
		for(Node<K,V> x = first; x.next != null; x = x.next) {
			if(key.equals(x.next.key)) {
				x.next = x.next.next; N--;
				return;
			}
		}
	}
	
	public boolean contains(K key) {
		return get(key) != null;
	}

}
