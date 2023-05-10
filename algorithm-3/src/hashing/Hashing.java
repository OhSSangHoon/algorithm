package hashing;

//Java String의 해시 함수
public class Hashing {
	public static class MyString{
		private int hash = 0;
		private final char[] s;
		
		public MyString(String input) {
			this.s = input.toCharArray();
		}
		
		public int length() {
			return s.length;
		}
		
		public int hashCode() {
			int h = hash;
			if(h != 0) return h;
			for(int i =0; i < length(); i++)
				h = s[i] + (31 * h);
			//honor's method l + 31 *(l + 31*(a + 31*(c)))
			// 108 + 31*(108 + 31*(97 + 31*(99)))
			hash = h;
			return h;
		}
	}

	
	public static void main(String[] args) {
		MyString s = new MyString("call");
		int code = s.hashCode();
		System.out.println("Hash code: " + code);
		//Hash code: 3045982
	}
}
