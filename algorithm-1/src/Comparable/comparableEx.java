package Comparable;
//comparable을 쓰는 이유 객체간의 정렬(비교)을 위해 기준을 정해주는 인터페이스
//comparable "자기 자신과 매개변수 객체를 비교"
//comparator "두 매개변수 객체를 비교"

import java.util.*;


public class comparableEx {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ArrayList<Book> list = new ArrayList<Book>();
			list.add(new Book ("총균쇄", "제레미 다이아몬드", "문학사상", 2005));
			list.add(new Book ("총균쇄", "제레미 다이아몬드", "문학사상", 2000));
			list.add(new Book ("파타고니아, 파도가 칠 떄는 서핑을", "이본 쉬나드", "라이팅하우스", 2020));
			list.add(new Book ("파타고니아, 파도가 칠 떄는 서핑을", "이본 쉬나드", "라이팅하우스", 2010));
			list.add(new Book ("코스모스", "칼 세이건", "사이언스북스", 2010));
			list.add(new Book ("코스모스", "칼 세이건", "사이언스북스", 2001));
			list.add(new Book ("죽은 자의 집 청소", "김완", "김영사", 2020));
			list.add(new Book ("동물농장", "조지 오웰", "민음사", 2007));
			list.add(new Book ("동물농장", "조지 오웰", "민음사", 1998));
			list.add(new Book ("침묵의 봄", "레이첼 카슨", "에코리브르", 2011));
			list.add(new Book ("불안", "알랭 드 보통", "은행나무", 2012));
			list.add(new Book ("불안", "알랭 드 보통", "은행나무", 2018));
			list.add(new Book ("싯다르타", "헤르만 헤세", "민음사", 2002));
			list.add(new Book ("싯다르타", "헤르만 헤세", "민음사", 2005));
			list.add(new Book ("호모데우스", "유발 하라리", "김영사", 2017));
			
			Collections.sort(list);
			for(int i =0; i < list.size(); i++) {
				System.out.println(list.get(i).getYear()+", "+list.get(i).getTitle());
			}
	}

}


class Book implements Comparable<Book>{
	private String title;
	private String author;
	private String company;
	private int year;
	

	public Book(String a, String b, String c, int d) {
		// TODO Auto-generated constructor stub
		this.title = a;
		this.author = b;
		this.company = c;
		this.year = d;
	}

	public int getYear() {
		// TODO Auto-generated method stub
		return year;
	}

	public String getTitle() {
		// TODO Auto-generated method stub
		return title;
	}

//	@Override
//	//출판년도(year)를 활용한 오름차순 정렬
//	public int compareTo(Book o) {
//		// TODO Auto-generated method stub
//		//현재 객채 == 파라미터로 넘어온 객체 => 0 리턴
//		if(this.year == o.year) {
//			return 0;
//			//현재 객채 < 파라미터로 넘어온 객체 => 음수 리턴(자리 바뀌지않음, 오름차순)
//			//앞에있는숫자가 뒤의 숫자보다 작다면 변경x
//		}else if(this.year < o.year) {
//			return -1;
//			//현재 객채 > 파라미터로 넘어온 객체 => 양수 리턴(자리 바뀜, 오름차순)
//			//앞에있는 숫자가 뒤의 숫자보다 크다면 변경
//		}else {
//			return 1;
//		}
//	}
	
	//제목을 활용한 오름차순 정렬
//	@Override
//	public int compareTo(Book o) {
//		int res = this.getTitle().compareTo(o.getTitle());
//		return res;
//
//	}
	
	//제목 사전순으로, 제목이 같을 경우 출간년도 오름차순으로
	@Override
	public int compareTo(Book o) {
		int res = this.getTitle().compareTo(o.getTitle());
		if(res == 0) {
//			//현재 객체의 년도 > 파라미터로 넘어온 객체의 년도
//			if(this.year > o.year) {
//				//1 리턴 자리변경
//				return 1;
//				//아닐 시
//			}else {
//				//자리 변경x
//				return -1;
//			}
			//위와 같은 동작
			res = this.year - o.year;
		}
		return res;
	}
	
}