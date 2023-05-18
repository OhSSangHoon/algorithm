public class Tries<Value> {
	private static int R = 256; //ASCII 기수
	private Node root;
	private int N;

	private class Node{
		private Object val;
		private Node[] next = new Node[R];
	}
	
	public Value get(String key) {
		Node x = get(root, key, 0);
		if(x == null) {
			return null;
		}
		return (Value)x.val;
	}
	
	private Node get(Node x, String key, int d) {
		// TODO Auto-generated method stub
		if(x == null) {
			return null;
		}
		if(d == key.length()) {
			return x;
		}
		char c = key.charAt(d);
		return get(x.next[c], key, d+1);
	}
	
	public void put(String key, Value val) {
		if(val == null) {
			delete(key);
		}else {
			root = put(root, key, val, 0);
		}
	}
	
	private Node put(Node x, String key, Value val, int d) {
		if(x == null) {
			x = new Node();
		}
		if(d == key.length()) {
			if(x.val == null) {
				N++;
			}
			x.val = val;
			return x;
		}
		char c = key.charAt(d);
		x.next[c] = put(x.next[c], key, val, d+1);
		return x;
	}

	public void delete(String key) {
		root = delete(root, key, 0);
	}
	
	
	private Node delete(Node x, String key, int d) {
		// TODO Auto-generated method stub
		if(x == null) {
			return null;
		}
		if(d == key.length()) {
			if(x.val != null) N--;
			x.val = null;
		}
		else {
			char c = key.charAt(d);
			x.next[c] = delete(x.next[c], key, d+1);
		}
		
		if(x.val != null)
			return x;
		for(char c = 0; c < R; c++)
			if(x.next[c] != null)
				return x;
		return null;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//값 검색 해서 성공하면 문자의 마지막 값 출력 , 실패시 null return 

	}

}
