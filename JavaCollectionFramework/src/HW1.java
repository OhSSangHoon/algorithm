import java.io.*;
import java.util.*;
import java.util.regex.*;

public class HW1{
		
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		String fileName = sc.next();
		int targetUser = sc.nextInt(); //��� ������� id
		int topN = sc.nextInt(); // ���絵 ���� n���� ��
		int topK = sc.nextInt(); // ���� k���� ��õ ������ ��
		
		try {
			File file = new File(fileName);
			Scanner fileScanner = new Scanner(file);
			ArrayList<String> contents = new ArrayList<>();
			HashMap<String, Integer> contentScores = new HashMap<>(); //�������� �� �����͸� �����ϴ� HashMap
			HashMap<String, Integer> contentCounts = new HashMap<>(); //�������� �� �����͸� �����ϴ� HashMap
			HashMap<String, Integer> targetUserContentScores = new HashMap<>(); //�������� �� �����͸� �����ϴ� HashMap
			HashMap<Integer, HashMap<String, Integer>> userContentScores = new HashMap<>(); //�������� �� �����͸� �����ϴ� HashMap
			

			
			if(fileScanner.hasNextLine()) {
				fileScanner.nextLine();
			}
			
			int userContentSum = 0;
			
			//�ؽ�Ʈ ���Ͽ��� �����͸� �о��(txt���Ͽ��� �о�� ���� ������ ��,  )
			while(fileScanner.hasNextLine()) {
				String line = fileScanner.nextLine();
				//������ �����͸� �������� �����Ͽ� �迭�� ����
				String[] parts = line.split(" ");				
				int user = Integer.parseInt(parts[0]);//user
				String content = parts[1];//content
				int score = Integer.parseInt(parts[2]);//score�� ����
				
				//����user�� targetUser���
				if(user == targetUser) {
					//targetUser�� content������ contents������ �߰�
					contents.add(content);
					//HashMap�� �������� ���� ����
					targetUserContentScores.put(content, score);
					userContentSum += score; //������
				}
				
				//HashMap�� content��, contentScores(����Ƚ�� + score)�� ����
				contentScores.put(content, contentScores.getOrDefault(content, 0) + score);
				contentCounts.put(content, contentCounts.getOrDefault(content, 0) + 1);
				//���������� ���� �հ�� ���� Ƚ���� �����ϰ�, �� ������ ���߿� ��� ���� ����̳� �ٸ� �м��� ����� �� �ִ�.
				
				//user���� �����ϴ� ��� user���� ��ȯ�ϰ� ���� ��� ���� hashMap�� ����� null�� ��ȯ
				userContentScores.putIfAbsent(user, new HashMap<>());
				
				//user�� Ű���� ���� userContentScores�� content�� score�� �ִ´�.
				//�� ����� ���� �� �������� ���� ������ ����
				userContentScores.get(user).put(content, score);
			}
			fileScanner.close();
			
			
			//����ȭ���� content �������� ����
			Collections.sort(contents, new Comparator<String>() {
				public int compare(String o1, String o2) {
					//content�� ���ڿ� ���ڸ� �����ϱ� ���� ���� ǥ���� ���
					Pattern pattern = Pattern.compile("(\\D+)(\\d+)");
					Matcher m1 = pattern.matcher(o1);
					Matcher m2 = pattern.matcher(o2);
					
					if(m1.find() && m2.find()) {
						//
						int charCompare = m1.group(1).compareTo(m2.group(1));
						//���ڿ��� �ٸ� ���
						if(charCompare != 0) {
							//m1(1)�� m2(1)�� �񱳰� ��ȯ
							return charCompare;
						}else {
							//���ڿ��� ���� ��� m1(2)�� m2(2)�� ���������� ��ȯ�Ͽ� ��
							int num1 = Integer.parseInt(m1.group(2));
							int num2 = Integer.parseInt(m2.group(2));
							return Integer.compare(num1, num2);//�񱳰� ��ȯ
						}
					}
					//��ȯ���� �������� Collections.sort�� ���� ����
					return o1.compareTo(o2);
				}
			});
			
			
			System.out.println("1.�����" + targetUser + "�� �������� ����ȭ ����: ");
			System.out.print("[");
			for(String content: contents) {
				//����ȭ ����
				double targetUserScore = targetUserContentScores.get(content);
		        double userContentAverage = (double) userContentSum / contents.size();
		        double average = targetUserScore - userContentAverage;
		        
		        double totalContentScore = (double) contentScores.get(content) / contentCounts.get(content);
		        
				System.out.printf("(%s, %.3f)", content, average);
				if(contents.indexOf(content) != contents.size() - 1) {
					System.out.print(", ");
				}
			}
			System.out.print("]\n\n\n");
			
			//userAverages �� ����
			HashMap<Integer, Double> userAverages = new HashMap<>();
			//����ں������������� ���� �ݺ����� ����
			for(Integer user : userContentScores.keySet()) {
				//�� ������� ������ ������ ������ �� 
				HashMap<String, Integer> userScores = userContentScores.get(user);
				int userSum = 0;
				for(int score : userScores.values()) {
					userSum += score;//��� ������ ������ ���� ���ϰ�
				}
				//��� ������ ������ �� / ������ũ��� ����� ���ѵ� ó���� ���� userAverages �ʿ� ����
				userAverages.put(user, (double) userSum / userScores.size());
				//����ں� ������ ������ ���
			}
			
			//���絵 ����Ʈ��� arrayList ����
			ArrayList<Map.Entry<Integer, Double>> similarityList = new ArrayList<>();
			for(Integer user : userContentScores.keySet()) {
				//Ÿ�� ����ڿ� �ٸ� ����ڵ� ����
				if(user != targetUser) {
					//�ڻ��� ���絵�� ����� �Լ��� similarity�� ����
					double similarity = calculateCosineSimilarity(targetUserContentScores, userContentScores.get(user), userAverages, targetUser, user);
					//����� id�� ���絵�� arrayList�� ���� (Key(����), Value(�Ǽ�))
					similarityList.add(new AbstractMap.SimpleEntry<>(user, similarity));
				}
			}
			
			
			//���絵 �������� ����
			Collections.sort(similarityList, (o1, o2) -> {
				if(o1.getValue() > o2.getValue()) {
					return -1; //o1 o2
				}else if(o1.getValue() < o2.getValue()) {
					return 1; //o2 o1
				}else {
					return 0; //���� ����
				}
			});
			
			
			System.out.println("2.������ ����� id�� ���絵 ����Ʈ");
			for(int i =0; i < topN; i++) {
				//Map entry�� ���絵�� ���� ������ �� ���� similarityList�� ���� ���
				// -> similarityList���� �������� ������ �߱� ������
				Map.Entry<Integer, Double> entry = similarityList.get(i);
				System.out.printf("����� id: %d, ���絵: %.6f\n", entry.getKey(), entry.getValue());
			}
			System.out.print("\n\n");
			
			
			System.out.println("3. �����" + targetUser +"���� ��õ�� �������� ��õ ����");
			
			//hashmap ����
			HashMap<String, Double> recommendationScores = new HashMap<>();
			
			//���絵�� ������ topN���� ����Ʈ��
			for(int i =0; i < topN; i++) {
				//similarityList���� key�� value���� �����´�.
				int similarUser = similarityList.get(i).getKey();//key = user id
				double similarity = similarityList.get(i).getValue();//value = user ���絵 ����
				//similarUserScores�� ����
				HashMap<String, Integer> similarUserScores = userContentScores.get(similarUser);
				
				//similarUserScores�� ��ü ����ŭ for�� ����
				for(String content : similarUserScores.keySet()) {
					//targetUser�� ������ content
					if(!targetUserContentScores.containsKey(content)) {
						//otherUserScore�� �����ѻ������ �ش� �������� ����
						int otherUserScore = similarUserScores.get(content);
						//�ش� �������� ���� ������ ������� ����ȭ�� ���� ���(�ش� ������� ������ ���� - �ش� ������� ��� ����)
						double otherUserNormalizedScore = otherUserScore - userAverages.get(similarUser);
						//��õ ���� = ���絵 * ������ ������� ����ȭ������ ������ ��
						double recommendationScore = similarity * otherUserNormalizedScore;
						//recommendationsScores�� �ش� content��, content�� ����Ƚ�� + ��õ ������ ���Ѱ��� ����
						recommendationScores.put(content, recommendationScores.getOrDefault(content, 0.0) + recommendationScore);
						//��õ ������ ���� ���� ���� N���� �������� �����Ѵ�.
					}
				}
			}
			//��õ ������ ���� ���� ���� N���� �������� recommendations�ȿ� ����
			//recommendations ArrayList ����
			List<Map.Entry<String, Double>> recommendations = new ArrayList<>(recommendationScores.entrySet());
			recommendations.sort((o1, o2) -> {
			    if (o1.getValue() > o2.getValue()) {
			        return -1; //o1 o2
			    } else if (o1.getValue() < o2.getValue()) {
			        return 1; //o2 o1
			    } else { //value���� ���ٸ�
			    	//key�� ������������ ���
			    	Pattern pattern = Pattern.compile("(\\D+)(\\d+)");
			    	Matcher m1 = pattern.matcher(o1.getKey());
			    	Matcher m2 = pattern.matcher(o2.getKey());
			    	if(m1.find() && m2.find()) {
			    		int charCompare = m1.group(1).compareTo(m2.group(1));
			    		if(charCompare != 0) {
			    			return charCompare;
			    		}else {
			    			int num1 = Integer.parseInt(m1.group(2));
			    			int num2 = Integer.parseInt(m2.group(2));
			    			return Integer.compare(num1, num2);
			    		}
			    	}
			    	return o1.getKey().compareTo(o2.getKey());
			    }
			});
			
			System.out.print("[");
			//topK�� ���
			for(int i =0; i < topK; i++) {
				//entry�� recommendations�� ���� ������ ��
				Map.Entry<String, Double> entry = recommendations.get(i);
				//���
				System.out.printf("(%s, %.3f)", entry.getKey(), entry.getValue());
				
				//�߰� ���� ���
				if(i != topK - 1) {
					System.out.print(" ");
				}
			}
			System.out.print("]");
			
		} catch(FileNotFoundException e) {
			System.out.println("File not found.");
		}
	}

	//���絵 ���
	private static double calculateCosineSimilarity(HashMap<String, Integer> targetUserScores,
			//targetUser, otherUser �޼���, targetUserScore, otherUserScores ������� �� ���� ��, userAverages ��� ���� ���� �μ��� ���� 
	HashMap<String, Integer> otherUserScores, HashMap<Integer, Double> userAverages, int targetUser, int otherUser) {
		//Cosine���絵�� ����ϱ� ���� �������� �ʱ�ȭ�Ѵ�.
		double dotProduct = 0.0;
		double targetUserSquaredSum = 0.0;
		double otherUserSquaredSum = 0.0;
		//�� ������� ��� ������ �����´�.
		double targetUserAverage = userAverages.get(targetUser);
		double otherUserAverage = userAverages.get(otherUser);
		
		
		for(String content : targetUserScores.keySet()) {
			//targetUser�� ����ȭ ������ �����´�
			int targetUserScore = targetUserScores.get(content);
			//targetUser�� ����ȭ�� ������ ����ϰ� ,SquaredSum�� �ش� ������ ������ ������Ų��.
			double targetUserNoramlizedScore = targetUserScore - targetUserAverage;
			targetUserSquaredSum += targetUserNoramlizedScore * targetUserNoramlizedScore;
			
			//otherUserScores�� �ش� content�׸��� �ִ��� Ȯ���� �� ������
			if(otherUserScores.containsKey(content)) {
				//����ȭ�� ������ ������ ��
				int otherUserScore = otherUserScores.get(content);
				//����ȭ�� ������ ���
				double otherUserNormalizedScore = otherUserScore - otherUserAverage;
				//dotProduct�� targetUser�� otherUser�� ����ȭ�� ������ ���� �����Ѵ�.
				dotProduct += targetUserNoramlizedScore  * otherUserNormalizedScore;
			}
		}
		
		//otherUserScores�� ��� ������
		for(int otherUserScore : otherUserScores.values()) {
			//otherUserScore�� ����ȭ�� ������ ���� ����ϰ�,
			double otherUserNormalizedScore = otherUserScore - otherUserAverage;
			//�ش� ������ ������ ����
			otherUserSquaredSum += otherUserNormalizedScore * otherUserNormalizedScore;
		}
		
		//targetUserSquaredSum�� otherUserSquaredSum�� �������� ���
		targetUserSquaredSum = Math.sqrt(targetUserSquaredSum);
		otherUserSquaredSum = Math.sqrt(otherUserSquaredSum);
		
		//targetUser�� otherUser���� �ڻ��� ���絵�� ����ϰ� ��ȯ
		if(targetUserSquaredSum == 0.0 || otherUserSquaredSum == 0.0) {
			//�и� 0�ΰ�� ���絵�� 0���� ��ȯ
			return 0.0;
		}else {
			//�ڻ��� ���絵 ��ȯ
			return dotProduct / (targetUserSquaredSum * otherUserSquaredSum);
		}
	}
}