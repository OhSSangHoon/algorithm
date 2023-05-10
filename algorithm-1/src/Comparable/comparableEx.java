package Comparable;
//comparable�� ���� ���� ��ü���� ����(��)�� ���� ������ �����ִ� �������̽�
//comparable "�ڱ� �ڽŰ� �Ű����� ��ü�� ��"
//comparator "�� �Ű����� ��ü�� ��"

import java.util.*;


public class comparableEx {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ArrayList<Book> list = new ArrayList<Book>();
			list.add(new Book ("�ѱռ�", "������ ���̾Ƹ��", "���л��", 2005));
			list.add(new Book ("�ѱռ�", "������ ���̾Ƹ��", "���л��", 2000));
			list.add(new Book ("��Ÿ��Ͼ�, �ĵ��� ĥ ���� ������", "�̺� ������", "�������Ͽ콺", 2020));
			list.add(new Book ("��Ÿ��Ͼ�, �ĵ��� ĥ ���� ������", "�̺� ������", "�������Ͽ콺", 2010));
			list.add(new Book ("�ڽ���", "Į ���̰�", "���̾𽺺Ͻ�", 2010));
			list.add(new Book ("�ڽ���", "Į ���̰�", "���̾𽺺Ͻ�", 2001));
			list.add(new Book ("���� ���� �� û��", "���", "�迵��", 2020));
			list.add(new Book ("��������", "���� ����", "������", 2007));
			list.add(new Book ("��������", "���� ����", "������", 1998));
			list.add(new Book ("ħ���� ��", "����ÿ ī��", "���ڸ��긣", 2011));
			list.add(new Book ("�Ҿ�", "�˷� �� ����", "���೪��", 2012));
			list.add(new Book ("�Ҿ�", "�˷� �� ����", "���೪��", 2018));
			list.add(new Book ("�˴ٸ�Ÿ", "�츣�� �켼", "������", 2002));
			list.add(new Book ("�˴ٸ�Ÿ", "�츣�� �켼", "������", 2005));
			list.add(new Book ("ȣ�𵥿콺", "���� �϶�", "�迵��", 2017));
			
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
//	//���ǳ⵵(year)�� Ȱ���� �������� ����
//	public int compareTo(Book o) {
//		// TODO Auto-generated method stub
//		//���� ��ä == �Ķ���ͷ� �Ѿ�� ��ü => 0 ����
//		if(this.year == o.year) {
//			return 0;
//			//���� ��ä < �Ķ���ͷ� �Ѿ�� ��ü => ���� ����(�ڸ� �ٲ�������, ��������)
//			//�տ��ִ¼��ڰ� ���� ���ں��� �۴ٸ� ����x
//		}else if(this.year < o.year) {
//			return -1;
//			//���� ��ä > �Ķ���ͷ� �Ѿ�� ��ü => ��� ����(�ڸ� �ٲ�, ��������)
//			//�տ��ִ� ���ڰ� ���� ���ں��� ũ�ٸ� ����
//		}else {
//			return 1;
//		}
//	}
	
	//������ Ȱ���� �������� ����
//	@Override
//	public int compareTo(Book o) {
//		int res = this.getTitle().compareTo(o.getTitle());
//		return res;
//
//	}
	
	//���� ����������, ������ ���� ��� �Ⱓ�⵵ ������������
	@Override
	public int compareTo(Book o) {
		int res = this.getTitle().compareTo(o.getTitle());
		if(res == 0) {
//			//���� ��ü�� �⵵ > �Ķ���ͷ� �Ѿ�� ��ü�� �⵵
//			if(this.year > o.year) {
//				//1 ���� �ڸ�����
//				return 1;
//				//�ƴ� ��
//			}else {
//				//�ڸ� ����x
//				return -1;
//			}
			//���� ���� ����
			res = this.year - o.year;
		}
		return res;
	}
	
}