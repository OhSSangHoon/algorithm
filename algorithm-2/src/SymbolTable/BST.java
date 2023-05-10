package SymbolTable;

import java.util.ArrayList;
import java.util.Collection;

class TNode<K,V>{
	K key;
	V value;
	TNode<K,V> left, right;
	int N; //자손 노드 + 1(ordered연산에 사용)
	int aux; //AVL트리나 RB트리에 사용
	public TNode<K, V> parent;
	
	public TNode(K key, V val) {
		this.key = key;
		this.value = val;
		this.N = 1;
	}
	
	public int getAux() {
		return aux;
	}
	public void setAux(int value) {
		aux = value;
	}
}
//BST class
public class BST <K extends Comparable<K>, V>{
	protected TNode<K,V> root;
	private int N;
	
	public int size() {
		return(root != null) ? root.N : 0;
	}
	

	//비교 횟수 = node level(root의 level = 1)
	protected TNode<K,V> treeSearch(K key){
		TNode<K,V> x = root; //BST에 대한 모든 연산은 root부터 시작한다.
		
		while(true){
			int cmp = key.compareTo(x.key);
			if(cmp == 0) //key값과 같은 x.key값을 찾았다면
				return x;
			//key값이 x.key값 보다 작을 경우 왼쪽으로
			else if(cmp < 0) { 
				if(x.left == null)
					return x; //left에 값이 없다면 x를 반환하고 반복문 종료
				else
					x = x.left;
			}
			//클 경우 오른쪽으로
			else {
				if(x.right == null)//right에 값이 없다면 x를 반환하고 반복문 종료
					return x;
				else
					x = x.right;
			}
		}
	}
	
	//전체 tree를 확인하는 함수들은 처음에 root의 값을 확인하고 시작한다.
	
	//가장 작은 키를 반환
	public K min() {
		if(root == null) //root가 null이라면,
			return null;
		TNode<K,V> x = root; //새 노드 생성
		while(x.left != null)//제일 왼쪽에 있는 노드가 나올 때 까지 반복문 실행
			x = x.left; //왼쪽 노드 찾기
		return x.key; //가장 왼쪽 노드의 key값을 반환
	}
	
	//가장 큰 키를 반환
	public K max() {
		if(root == null)
			return null;
		TNode<K,V> x = root;
		while(x.right != null) //제일 오른쪽에 있는 노드가 나올 때 까지 반복문 실행
			x = x.right;
		return x.key;
	}
	
	public V get(K key) {
		if(root == null)
			return null;
		TNode<K,V> x = treeSearch(key); //node x의 값은 treeSearch의 key값
		
		if(key.equals(x.key)) //검색 키를 가진 노드가 반환된 경우
			return x.value;
		else                  //검색 키를 가진 노드가 없는 경우
			return null;
	}
	
	public void put(K key, V val) {
		if(root == null) {
			root = new TNode<K,V>(key,val); return;
		}
		TNode<K,V> x = treeSearch(key);
		
		int cmp = key.compareTo(x.key);
		
		if(cmp == 0) //key값과 x.key값이 같다면
			x.value = val; //키가 존재하므로 값 reset
		else {//키가 없다면 새로운 node를 생성해 x의 자식으로 추가
			TNode<K,V> newNode = new TNode<K,V>(key,val);
			if(cmp < 0)
				x.left = newNode; //key값이 x.key값보다 작으면 node x의 왼쪽에 추가
			else
				x.right = newNode;//크면 오른쪽에 추가
			newNode.parent = x; 
		}
	}
	
	public Iterable<K> keys(){

		if(root == null)//root == null(트리가 없다)
			return null;
		ArrayList<K> keyList = new ArrayList<K>(size());
		inorder(root, keyList);
		return keyList;
	}
	
	
	private void inorder(TNode<K, V> x, ArrayList<K> keyList) {
		// TODO Auto-generated method stub
		if(x != null) {
			inorder(x.left, keyList);//keyList를 x.left값 부터 중위순회한다 
			keyList.add(x.key);
			inorder(x.right, keyList);//keyList를 x.right값 부터 중위순회한다
		}
		
	}
	
	public void delete (K key) {
		if (root == null) return;
		TNode<K, V> x, y, p;
		x = treeSearch(key);
		
		//key가 없는 경우
		if(!key.equals(x.key))
			return; //if문 종료
		
		//x가 루트노드이고 자식이 두개라면
		if(x == root || isTwoNode(x)) {
			if(isLeaf(x)) {//루트를 리프노드로 바꿔준다.
				root = null;
				return;
			}else if(!isTwoNode(x)) {//자식 노드가 없다면
				root = (x.right == null) ?
					x.left : x.right; //자식을 루트로
				root.parent = null;
				return;
			}else { //자식 노드가 둘이라면(루트 포함)
				y = min(x.right); //inorder successor 왼 ← 오
				x.key = y.key; //y키를 x에 복사
				x.value = y.value; //y의 값을 x에 복사
				p = y.parent; //p는 y의 부모 노드를 저장
				//p와 y.right를 연결, y가 p의 왼쪽 자식 노드인지를 나타내는 bool 값인
				//y == p.left를 전달한다
				relink(p, y.right, y == p.left);
				//y의 조상 노드들의 size를 감소
				rebalanceDelete(p, y);
			}
		}else { //자식노드가 1이하이고, 루트가 아닐 때
			p = x.parent; //x의 부모 노드를 p에 저장
			if(x.right == null)
				//p와 x.left를 연결, x가 p의 왼쪽 자식 노드인지를 나타내닌 bool 값인
				//x == p.left를 전달
				relink(p, x.left, x == p.left);
			else if(x.left == null)
				relink(p,x.right, x == p.right);
			rebalanceDelete(p,x);
		}
	}
	
	//floor function

	// TNode<K,V> 타입의 변수 x를 선언하고,
	// root 노드를 루트로 하는 이진 탐색 트리에서 key값과 가장 가까운
	// (key보다 작거나 같은) 노드를 찾아 x에 할당한다.
	// 만약 key보다 작거나 같은 노드가 존재하지 않으면 x에 null을 할당한다
	
	//key보다 작거나 같은 키들 중에서 제일 큰 키
	public K floor(K key) {
		if(root == null || key == null) //root와 key가 null이라면
			return null; //null 반환
		TNode<K,V> x = floor(root, key); //새로운 노드 생성
		if(x == null)
			return null;
		else
		return x.key;
	}
	
	private TNode<K, V> floor(TNode<K, V> x, K key) {
		if(x == null)
			return null;
		
		int cmp = key.compareTo(x.key);
		
		if(cmp == 0)
			return x; //key와 동일한 키를 가진 노드
		if(cmp < 0) //key보다 크다면 계속 왼쪽으로
			return floor(x.left, key);
		
		TNode<K,V> t = floor(x.right, key); //key가 클 경우, 오른쪽으로
		
		if(t != null)
			return t; //오른쪽에 작은 키가 있을 경우
		else
			return x; //오른쪽에 작은 키가 없을 경우
	}


	//rank
	public int rank(K key) {
		if(root == null || key == null)
			return 0;
		TNode<K,V> x= root;
		int num = 0;
		while(x != null) { //루트부터 비교하면서 key보다 작은 키의 수를 합산
			int cmp = key.compareTo(x.key);
			if(cmp < 0) x = x.left;
			else if(cmp > 0) { //key보다 작은 키를 갖는 노드를 발견
				num += 1 + size(x.left); //오른쪽 subtree도 계속 검사
				x = x.right;
			}else { //key값을 갖는 노드: 왼쪽 subtree만 합산
				num += size(x.left);
				break;
			}
		}
		return num;
	}
	
	private int size(TNode<K, V> left) {
		// TODO Auto-generated method stub
		return 0;
	}

	
	public K select(int rank) {
		if(root == null || rank < 0 || rank >= size())
			return null;
		TNode<K,V> x = root;
		while(true) {
			int t = size(x.left);
			if(rank < t)
				x = x.left;
			else if(rank > t) {
				rank = rank - t- 1;
				x = x.right;
			}else {
				return x.key;
			}
		}
	}

	//inline & protected functions
	public boolean contains(K key) {
		return get(key) != null;
	}
	
	public boolean isEmpty() {
		return root == null;
	}
	
	protected boolean isLeaf(TNode<K, V> x) {
		// TODO Auto-generated method stub
		//x.left와 right에 아무런 노드가 없다면, null이라면
		return x.left == null && x.right == null;
	}

	protected boolean isTwoNode(TNode<K, V> x) {
		// TODO Auto-generated method stub
		//x.left와 right에 노드가 있다면, null이 아니라면
		return x.left != null && x.right != null;
	}
	
	protected void relink(TNode<K,V> parent, TNode<K,V> child, boolean makeLeft) {
		if(child != null) //child 노드가 있다면,
			child.parent = parent; //child를 parent의 자식으로
		if(makeLeft)
			parent.left = child; //왼쪽 자식 또는,
		else
			parent.right = child; //오른쪽 자식으로
		
	}

	protected TNode<K,V> min(TNode<K,V> x){
		while(x.left != null)
			x = x.left;
		return x;
	}
	
	
	//rebalanceInsert
	protected void rebalanceInsert(TNode<K,V> x) {
		resetSize(x.parent, 1); //root까지 조상 노드들의 size를 1 증가
	}
	
	protected void rebalanceDelete(TNode<K,V> p, TNode<K,V> deleted) {
		resetSize(p, -1);
	}
	
	private void resetSize(TNode<K,V> x, int value) {
		for(; x != null; x= x.parent)
			x.N += value;
	}
}


