package SymbolTable;

import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class TestClient {
	public static void main(String[] args) {
		
		BinarySearchST<String, Integer> st = new BinarySearchST<String, Integer>();
		File file;
		final JFileChooser fc = new JFileChooser();
		if(fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
			file = fc.getSelectedFile();
		else {
			JOptionPane.showMessageDialog(null, "파일을 선태하세요.", "오류", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		 //file에 있는 단어들을 key로 해서 table에 차례대로 저장
		//이후, 테이블에 저장된 모든(키,값)의 쌍들을 출력
		Scanner sc = null;
		try {
			sc = new Scanner(file);
			for(int i =0; sc.hasNext(); i++) {
				String key = sc.next();
				st.put(key, i);
			}
			for(String s : st.keys())
				System.out.println("key : " + s + "  " + "value : " + st.get(s));
		}catch(FileNotFoundException e) {
			e.printStackTrace();
		}
		if(sc != null)
			sc.close();
	}
}
