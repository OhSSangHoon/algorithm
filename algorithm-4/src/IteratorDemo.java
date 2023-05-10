import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;

public class IteratorDemo {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ArrayList<String> al = new ArrayList<String>();
		
		al.add("T"); al.add("A"); al.add("B"); al.add("L"); al.add("E");
		ListIterator<String> litr = al.listIterator();
		while(litr.hasNext()) {
			String element = litr.next();
			litr.set(element + "+");
		}
		//litr = al.iterator();
		while(litr.hasNext()) {
			String element = litr.next();
			System.out.print(element + "$");
		}
		System.out.println();
	}

}
