import java.io.*;
import java.util.*;
import java.util.regex.*;

public class HW1{
		
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		String fileName = sc.next(); //���� �̸�
		int targetUser = sc.nextInt(); //��� ������� ID 0,1,2...
		int topN = sc.nextInt(); //���絵�� ���� ����n��
		int topK = sc.nextInt(); //��õ ������ ����k��
		
		try {
			File file = new File(fileName); //fileName���� ���� �Է¹��� flie�� �о�´�.(��ġ�� ��)
			Scanner fileScanner = new Scanner(file); //�ش� file�� ��ĵ //32L����

			ArrayList<String> contents = new ArrayList<>(); 
			//file�� �������� �ӽ� ����, ����ȭ�� ���� ��� �� ��¿� ���
			
			//HashMap ��� ���� ���� ���� �ð�, �ߺ� ������ ó��(�������� �����ID), ������ ���� �� �м�(�������� ���� Ƚ��, ����ں� ������ ����)�� ������
			
			HashMap<String, Integer> contentScores = new HashMap<>();
			//�������� �׿� ���� ����(����Ƚ�� + ������)�� ���� �ϴ� ��
			
			HashMap<String, Integer> contentCounts = new HashMap<>();
			//�������� ����Ƚ���� ��ô�� �ʿ� �����ϰ� ������ ��� ������ ���ϴµ� ���
			
			HashMap<String, Integer> targetUserContentScores = new HashMap<>();
			//��� ������� �������� ������ �����ϴ� ��, ��� ����ڰ� ���� �������� �׿� ���� ������ ����
			
			HashMap<Integer, HashMap<String, Integer>> userContentScores = new HashMap<>();
			//����ں� �������� ������ �����ϴ� ��,
			//�����ID�� key�� ����Ͽ� �ش� ����ڰ� ���� �������� �׿� ���� ������ ���� ���絵 ��꿡 Ȱ��
			

			
			if(fileScanner.hasNextLine()) {//ù ��° ���� �ǳʶٰ� �� ��° �ٺ��� ����
				fileScanner.nextLine();
			}
			
			int userContentSum = 0;//��� ������� ������ ���� ������ ����
			
			//file�� �� ���� �����͸�ó��
			while(fileScanner.hasNextLine()) {//���� �� Ȯ��
				String line = fileScanner.nextLine();//�о�� �����͸� line�� ����
				String[] parts = line.split(" ");//line�� ������ �������� ������ parts�� ���� 
				int user = Integer.parseInt(parts[0]);//�����
				String content = parts[1];//content
				int score = Integer.parseInt(parts[2]);//score�� ����
				
				//����user�� targetUser���(targetUser�� �������� ������ ã�� ������ ���� ������)
				if(user == targetUser) {
					//targetUser�� �������� arrayList contents�� �߰�
					contents.add(content);
					
					//HashMap�� �������� ���� ����
					targetUserContentScores.put(content, score);
					userContentSum += score; //������
				}
				
				//getOrDefault�� ����Ͽ� �ʿ��� �ش� �������� Ű�� ���� ���� �����´�. ���� �ش� �������� �������� �ʴ´ٸ�, �⺻��0���� ��ȯ
				//������ ���� �հ�� ���� ����(score)�� ���Ͽ� ���ο� ���� �հ踦 ���(�� �������� ���� Ƚ����, ���� �հ踦 ����, ��� ������꿡 ���)
				contentScores.put(content, contentScores.getOrDefault(content, 0) + score);//�ش� �������� ������ ��� ������� ���� �հ踦 ����
				
				contentCounts.put(content, contentCounts.getOrDefault(content, 0) + 1 );//(����ڵ鿡 ���� �򰡵� Ƚ���� ������)�������� ����Ƚ���� ����
				
				//������ user�� �����Ͱ� �ִ� ���� ��������, ������ ���ο� �� ����
				//target user�� �̹� ������ ����������� userContentScores�� ���� �ʴ´�.
				userContentScores.putIfAbsent(user, new HashMap<>());
				
				//user���� ���� �������� �о� content�� ������ ���� user = key
				//���� �ش� ������� �����Ͱ� �������� ������ (null) putIfAbsent�Լ��� �̿��Ͽ� ���ο� ���� �����
				//���Ŀ� put����
				userContentScores.get(user).put(content, score);
				
				//�� part�� �и��� �����͵��� puIfAbsent�� Ȱ���� user�� ���� ����� �ű⿡ content�� socres�� ����ִ´�.
			}
			fileScanner.close();
			//file�� �� �о����� �ݴ´�.(�۾��Ϸ�)
			
			
			//����1.����ȭ���� ������ ����� contents�鸸 �����ͼ� �������� ����
			Collections.sort(contents, new Comparator<String>() {
				//o1�� o2�ΰ��� ���ڿ��� ���ڷ� �޴� compare�Լ�
				public int compare(String o1, String o2) {
					//content�� ���ڿ� ���ڸ� �����ϱ� ���� ���� ǥ���� ��� ex) 'A'��'0' 
					Pattern pattern = Pattern.compile("(\\D+)(\\d+)");
					Matcher m1 = pattern.matcher(o1);
					Matcher m2 = pattern.matcher(o2);
					
					if(m1.find() && m2.find()) {
						//m1�� m2�� ã�Ƽ� 
						int charCompare = m1.group(1).compareTo(m2.group(1));
						//matcher�׷���(1�� ���ڿ�) ���ڿ��� �ٸ� ��� m1�׷�� m2�׷��� ��
						if(charCompare != 0) {
							//m1(���ڿ�)�� m2(���ڿ�)�� �񱳰� ��ȯ (���ڰ� �ٸ��Ƿ� ���ڱ��� �����ʿ�x)
							return charCompare;// compare�� �񱳰� ��ȯ
						}else {
							//���ڿ��� ���� ��� m1(����)�� m2(����)�� ���������� ��ȯ�Ͽ� ��
							//pattern.compile�� ���� ǥ���Ŀ� �´°��� ã���� �� ���������� ���Ѱ� �ƴ�
							int num1 = Integer.parseInt(m1.group(2));
							int num2 = Integer.parseInt(m2.group(2));
							return Integer.compare(num1, num2);// compare�� �񱳰� ��ȯ
						}
					}
					return 0;
				}
			});
			
			
			System.out.println("1.�����" + targetUser + "�� �������� ����ȭ ����: ");
			System.out.print("[");
			
			//ArrayList contents�� ����� �� �������� �ݺ������� ������ content�� �Ҵ�
			//contents = file�����͸� ������ arrayList, content = for������ ���� ���������ִ� contents�� ������
			for(String content: contents) {
				
				//����ȭ ����
				//���� ������(content)�� ���� ��� �����(targetUser)�� ������ �����´�.
				//targetUserContentScores.get(content) = ���� �������� ���� targetuser�� contents����
				double targetUserScore = targetUserContentScores.get(content);
				
				//������� ������ ��� ���� 5 + 4 + 1�̶�� ��������� 10/3()
				//��� = ������������ ������ / ���� �������� ����
				double userContentAverage = (double) userContentSum / contents.size();
				
				//������ ����ȭ = ��������� ���� - ���
				double average = targetUserScore - userContentAverage;
		        
				//���: �������� �������� content��, �ش� content�� ����ȭ ����
				System.out.printf("(%s, %.3f)", content, average);
				
				//arrayList contents�� ������ ��Ҷ�� ,��� (ex: A0�� �������̶�� ,��� )
				//contents.size()���� -1�� �� ���� contents�� ������ ���
				if(contents.indexOf(content) != contents.size() - 1) {
					System.out.print(", ");
				}
			}
			System.out.print("]\n\n\n");
			
			//(�����ID, ��� ����)�� ������ userAverages �� ����
			HashMap<Integer, Double> userAverages = new HashMap<>();
			
			//userContentScores�� �����ID(key)�� �ϳ��� user�� �Ҵ��Ͽ� �ݺ��� ����
			for(Integer user : userContentScores.keySet()) {
				//�� ������� content, score�� userScores�� ���� 
				HashMap<String, Integer> userScores = userContentScores.get(user);
				
				int userSum = 0;
				//������ ���� userScores �� ����ڿ� ���� �������� ������ score�� �Ҵ�
				for(int score : userScores.values()) {
					userSum += score;//��� ������ ������ ���� ���ϰ�
				}
				//��� ������� ������ ���� ����� ���ѵ� ó���� ���� userAverages �ʿ� ����
				userAverages.put(user, (double) userSum / userScores.size());
				//����ں� ������ ������ ���
			}
			
			//���絵 ����Ʈ��� arrayList ����
			ArrayList<Map.Entry<Integer, Double>> similarityList = new ArrayList<>();
			
			//�� ����� ID�� user�� �Ҵ� �ݺ��� ����
			for(Integer user : userContentScores.keySet()) {
				//Ÿ�� ����ڿ� �ٸ� ����ڵ� ����
				if(user != targetUser) {
					//�ڻ��� ���絵�� ����� �Լ��� similarity�� ����
					//��� ������� ������ ����, �ٸ� ����ڵ��� ������ ����, ��� ������� ������ ���� ���, ��� �����, �ٸ� �����
					double similarity = calculateCosineSimilarity(targetUserContentScores, userContentScores.get(user), userAverages, targetUser, user);
					//targetUser�� ������ ���� �ʰ� user�� ������ ���� ���� ������ userAverage�� ���絵 ����� �ǽ�
					
					
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
			//targetUser�� ID�� �޾� �ش�ID�� targetUser�� ����ȭ�� ������ ������
			int targetUserScore = targetUserScores.get(content);
			
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
			//otherUserScore�� ����ȭ�� �������� ���� ����ϰ�,
			double otherUserNormalizedScore = otherUserScore - otherUserAverage;
			//�ش� ������ ������ ����
			otherUserSquaredSum += otherUserNormalizedScore * otherUserNormalizedScore;
		}
		
		//targetUserSquaredSum�� otherUserSquaredSum�� �������� ���
		//targetUser������ ũ��
		targetUserSquaredSum = Math.sqrt(targetUserSquaredSum);
		//otherUser������ ũ��
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