import java.util.*;

public class TST<Value> {
	private Node<Value> root;
	private int N;
	
	private class Node<Value>{
		char c; //����
		Value val; //��
		Node<Value> left, mid, right; //��ũ
	}
	
	//�˻�
	private Node<Value> get(Node<Value> x, String key, int d){
		if(x == null) {
			return null;
		}
		char c = key.charAt(d);
		//��庸�� Ű ���� �۴ٸ� ��������
		if(c < x.c) {
			return get(x.left, key, d);
			//��庸�� Ű ���� ũ�ٸ� ����������
		}else if( c > x.c) {
			return get(x.right, key, d);
		}else if(d < key.length() -1) {
			//���� Ű ���� ���ٸ� �Ʒ���
			return get(x.mid, key, d+1);
		}else {
			return x;
		}
	}
	
	//����
	private Node<Value> put(Node<Value> x, String key, Value val, int d){
		char c = key.charAt(d);
		if(x == null) {
			x = new Node<Value>();
			x.c = c;
		}
		if(c < x.c) {
			x.left = put(x.left, key, val, d);
		}else if(c > x.c) {
			x.right = put(x.right, key, val, d);
		}else if(d < key.length() - 1) {
			x.mid = put(x.mid, key, val, d+1);
		}else {
			x.val = val;
		}
		return x;
	}
	
	
	public int size() {
		return N;
	}
	
	public boolean contains(String key) {
		return get(key) != null;
	}

	//�˻�
	public Value get(String key) {
		Node<Value> x = get(root, key, 0);
		if(x == null) {
			return null;
		}
		return x.val;
	}
	
	//����
	public void put(String key, Value val) {
		if(!contains(key))N++;
	}

	public static void main(String[] args) {
		
	}
}