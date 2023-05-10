package SymbolTable;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class FerquencyCounter {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		//int minlen = Integer.parseInt(args[0]); //minlen = 첫번째 정수형 arguments를 받아
		
		BST<String, Integer> st = new BST<String, Integer>();
		File file;
		final JFileChooser fc = new JFileChooser();
		if(fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
			file = fc.getSelectedFile();
		else {
			JOptionPane.showMessageDialog(null, "파일을 선태하세요.", "0오류", JOptionPane.ERROR_MESSAGE);
			return;
		}

	
		Scanner sc = null;
		try{
			sc = new Scanner(file);
			long start = System.currentTimeMillis(); //실행 시간 측정
			//key: word, value : word의 빈도 수 symbol table에 저장
			while(sc.hasNext()) {
				String word = sc.next();
				if(word.length() < 0)
					continue;
				if(!st.contains(word))
					st.put(word, 1);
				else
					st.put(word, st.get(word) + 1);
			}
			//value가 가장 높은 단어를 조사
			String maxKey = "";
			int maxValue = 0;
			for(String word : st.keys())
				if(st.get(word) > maxValue) {
					maxValue = st.get(word); maxKey = word;
				}
			long end = System.currentTimeMillis();
			System.out.println(maxKey + " " + maxValue);//가장 많이 나온 단어
			System.out.println("소요 시간 = " + (end-start) + "ms");//소요 시간
		}catch(FileNotFoundException e) {
			e.printStackTrace();
		}
		if(sc != null)
			sc.close();
	}

}
