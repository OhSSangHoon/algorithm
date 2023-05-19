public class StrBuilder {
	
//	public static String reverse(String s) {
//		String rev = "";
//		
//		for(int i = s.length()-1; i >= 0; i--) {
//			rev += s.charAt(i);
//		}
//		return rev;
//	}
	
	public static String reverse(String s) {
		StringBuilder rev = new StringBuilder();
		
		for(int i = s.length() - 1; i >= 0; i--) {
			rev.append(s.charAt(i));
		}
		
		return rev.toString();
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String a = "hello";

		System.out.println(reverse(a));
		//o
		//ol
		//oll
		//olle
		//garbage data ����
		//olleh
		
		//�ذ� ���
		//String Builder ���
	}

}
