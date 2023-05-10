import java.io.*;
import java.util.*;
import java.util.regex.*;

public class HW1{
		
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		String fileName = sc.next();
		int targetUser = sc.nextInt(); //대상 사용자의 id
		int topN = sc.nextInt(); // 유사도 상위 n명의 수
		int topK = sc.nextInt(); // 상위 k개의 추천 콘텐츠 수
		
		try {
			File file = new File(fileName);
			Scanner fileScanner = new Scanner(file);
			ArrayList<String> contents = new ArrayList<>();
			HashMap<String, Integer> contentScores = new HashMap<>(); //콘텐츠를 평가 데이터를 저장하는 HashMap
			HashMap<String, Integer> contentCounts = new HashMap<>(); //콘텐츠를 평가 데이터를 저장하는 HashMap
			HashMap<String, Integer> targetUserContentScores = new HashMap<>(); //콘텐츠를 평가 데이터를 저장하는 HashMap
			HashMap<Integer, HashMap<String, Integer>> userContentScores = new HashMap<>(); //콘텐츠를 평가 데이터를 저장하는 HashMap
			

			
			if(fileScanner.hasNextLine()) {
				fileScanner.nextLine();
			}
			
			int userContentSum = 0;
			
			//텍스트 파일에서 데이터를 읽어옴(txt파일에서 읽어온 값을 저장한 후,  )
			while(fileScanner.hasNextLine()) {
				String line = fileScanner.nextLine();
				//각줄의 데이터를 공백으로 구분하여 배열에 저장
				String[] parts = line.split(" ");				
				int user = Integer.parseInt(parts[0]);//user
				String content = parts[1];//content
				int score = Integer.parseInt(parts[2]);//score를 저장
				
				//현재user가 targetUser라면
				if(user == targetUser) {
					//targetUser의 content점수를 contents변수에 추가
					contents.add(content);
					//HashMap에 콘텐츠와 점수 저장
					targetUserContentScores.put(content, score);
					userContentSum += score; //누적합
				}
				
				//HashMap에 content와, contentScores(등장횟수 + score)를 저장
				contentScores.put(content, contentScores.getOrDefault(content, 0) + score);
				contentCounts.put(content, contentCounts.getOrDefault(content, 0) + 1);
				//콘텐츠별로 점수 합계와 등장 횟수를 추적하고, 이 정보를 나중에 평균 점수 계산이나 다른 분석에 사용할 수 있다.
				
				//user값이 존재하는 경우 user값을 반환하고 없을 경우 새로 hashMap을 만들고 null값 반환
				userContentScores.putIfAbsent(user, new HashMap<>());
				
				//user의 키값을 받은 userContentScores에 content와 score를 넣는다.
				//각 사용자 별로 각 콘텐츠에 대한 점수가 저장
				userContentScores.get(user).put(content, score);
			}
			fileScanner.close();
			
			
			//정규화점수 content 오름차순 정렬
			Collections.sort(contents, new Comparator<String>() {
				public int compare(String o1, String o2) {
					//content의 문자와 숫자를 구분하기 위해 정규 표현식 사용
					Pattern pattern = Pattern.compile("(\\D+)(\\d+)");
					Matcher m1 = pattern.matcher(o1);
					Matcher m2 = pattern.matcher(o2);
					
					if(m1.find() && m2.find()) {
						//
						int charCompare = m1.group(1).compareTo(m2.group(1));
						//문자열이 다른 경우
						if(charCompare != 0) {
							//m1(1)과 m2(1)의 비교값 반환
							return charCompare;
						}else {
							//문자열이 같은 경우 m1(2)와 m2(2)를 정수형으로 변환하여 비교
							int num1 = Integer.parseInt(m1.group(2));
							int num2 = Integer.parseInt(m2.group(2));
							return Integer.compare(num1, num2);//비교값 반환
						}
					}
					//반환값을 기준으로 Collections.sort를 통해 정렬
					return o1.compareTo(o2);
				}
			});
			
			
			System.out.println("1.사용자" + targetUser + "의 콘텐츠와 정규화 점수: ");
			System.out.print("[");
			for(String content: contents) {
				//정규화 계산식
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
			
			//userAverages 맵 생성
			HashMap<Integer, Double> userAverages = new HashMap<>();
			//사용자별콘텐츠점수에 대해 반복문을 실행
			for(Integer user : userContentScores.keySet()) {
				//각 사용자의 콘텐츠 점수를 가져온 후 
				HashMap<String, Integer> userScores = userContentScores.get(user);
				int userSum = 0;
				for(int score : userScores.values()) {
					userSum += score;//모든 콘텐츠 점수의 합을 구하고
				}
				//모든 콘텐츠 점수의 합 / 콘텐츠크기로 평균을 구한뒤 처음애 만든 userAverages 맵에 저장
				userAverages.put(user, (double) userSum / userScores.size());
				//사용자별 콘텐츠 점수의 평균
			}
			
			//유사도 리스트라는 arrayList 선언
			ArrayList<Map.Entry<Integer, Double>> similarityList = new ArrayList<>();
			for(Integer user : userContentScores.keySet()) {
				//타겟 사용자와 다른 사용자들 간의
				if(user != targetUser) {
					//코사인 유사도로 계산한 함수를 similarity에 저장
					double similarity = calculateCosineSimilarity(targetUserContentScores, userContentScores.get(user), userAverages, targetUser, user);
					//사용자 id와 유사도를 arrayList에 저장 (Key(정수), Value(실수))
					similarityList.add(new AbstractMap.SimpleEntry<>(user, similarity));
				}
			}
			
			
			//유사도 내림차순 정렬
			Collections.sort(similarityList, (o1, o2) -> {
				if(o1.getValue() > o2.getValue()) {
					return -1; //o1 o2
				}else if(o1.getValue() < o2.getValue()) {
					return 1; //o2 o1
				}else {
					return 0; //순서 유지
				}
			});
			
			
			System.out.println("2.유사한 사용자 id와 유사도 리스트");
			for(int i =0; i < topN; i++) {
				//Map entry에 유사도가 가장 유사한 값 부터 similarityList값 저장 출력
				// -> similarityList에서 내림차순 정렬을 했기 때문에
				Map.Entry<Integer, Double> entry = similarityList.get(i);
				System.out.printf("사용자 id: %d, 유사도: %.6f\n", entry.getKey(), entry.getValue());
			}
			System.out.print("\n\n");
			
			
			System.out.println("3. 사용자" + targetUser +"에게 추천할 콘텐츠와 추천 점수");
			
			//hashmap 생성
			HashMap<String, Double> recommendationScores = new HashMap<>();
			
			//유사도가 유사한 topN개의 리스트를
			for(int i =0; i < topN; i++) {
				//similarityList에서 key와 value값을 가져온다.
				int similarUser = similarityList.get(i).getKey();//key = user id
				double similarity = similarityList.get(i).getValue();//value = user 유사도 점수
				//similarUserScores에 저장
				HashMap<String, Integer> similarUserScores = userContentScores.get(similarUser);
				
				//similarUserScores의 전체 값만큼 for문 실행
				for(String content : similarUserScores.keySet()) {
					//targetUser가 평가하지 content
					if(!targetUserContentScores.containsKey(content)) {
						//otherUserScore에 유사한사용자의 해당 콘텐츠를 저장
						int otherUserScore = similarUserScores.get(content);
						//해당 콘텐츠에 대한 유사한 사용자의 정규화된 점수 계산(해당 사용자의 콘테츠 점수 - 해당 사용자의 평균 점수)
						double otherUserNormalizedScore = otherUserScore - userAverages.get(similarUser);
						//추천 점수 = 유사도 * 유사한 사용자의 정규화콘텐츠 점수의 곱
						double recommendationScore = similarity * otherUserNormalizedScore;
						//recommendationsScores에 해당 content와, content의 등장횟수 + 추천 점수를 더한값을 저장
						recommendationScores.put(content, recommendationScores.getOrDefault(content, 0.0) + recommendationScore);
						//추천 점수가 가장 높은 상위 N개의 콘텐츠를 선택한다.
					}
				}
			}
			//추천 점수가 가장 높은 상위 N개의 콘텐츠를 recommendations안에 저장
			//recommendations ArrayList 선언
			List<Map.Entry<String, Double>> recommendations = new ArrayList<>(recommendationScores.entrySet());
			recommendations.sort((o1, o2) -> {
			    if (o1.getValue() > o2.getValue()) {
			        return -1; //o1 o2
			    } else if (o1.getValue() < o2.getValue()) {
			        return 1; //o2 o1
			    } else { //value값이 같다면
			    	//key의 오름차순으로 출력
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
			//topK개 출력
			for(int i =0; i < topK; i++) {
				//entry에 recommendations의 값을 저장한 뒤
				Map.Entry<String, Double> entry = recommendations.get(i);
				//출력
				System.out.printf("(%s, %.3f)", entry.getKey(), entry.getValue());
				
				//중간 공백 출력
				if(i != topK - 1) {
					System.out.print(" ");
				}
			}
			System.out.print("]");
			
		} catch(FileNotFoundException e) {
			System.out.println("File not found.");
		}
	}

	//유사도 계산
	private static double calculateCosineSimilarity(HashMap<String, Integer> targetUserScores,
			//targetUser, otherUser 메서드, targetUserScore, otherUserScores 사용자의 평가 점수 맵, userAverages 평균 평점 맵을 인수로 받음 
	HashMap<String, Integer> otherUserScores, HashMap<Integer, Double> userAverages, int targetUser, int otherUser) {
		//Cosine유사도를 계산하기 위해 변수들을 초기화한다.
		double dotProduct = 0.0;
		double targetUserSquaredSum = 0.0;
		double otherUserSquaredSum = 0.0;
		//각 사용자의 평균 평점을 가져온다.
		double targetUserAverage = userAverages.get(targetUser);
		double otherUserAverage = userAverages.get(otherUser);
		
		
		for(String content : targetUserScores.keySet()) {
			//targetUser의 정규화 점수를 가져온다
			int targetUserScore = targetUserScores.get(content);
			//targetUser의 정규화된 점수를 계산하고 ,SquaredSum에 해당 점수의 제곱을 누적시킨다.
			double targetUserNoramlizedScore = targetUserScore - targetUserAverage;
			targetUserSquaredSum += targetUserNoramlizedScore * targetUserNoramlizedScore;
			
			//otherUserScores에 해당 content항목이 있는지 확인한 후 있으면
			if(otherUserScores.containsKey(content)) {
				//정규화된 점수를 가져온 뒤
				int otherUserScore = otherUserScores.get(content);
				//정규화된 점수를 계산
				double otherUserNormalizedScore = otherUserScore - otherUserAverage;
				//dotProduct에 targetUser와 otherUser의 정규화된 점수의 곱을 누적한다.
				dotProduct += targetUserNoramlizedScore  * otherUserNormalizedScore;
			}
		}
		
		//otherUserScores의 모든 점수를
		for(int otherUserScore : otherUserScores.values()) {
			//otherUserScore의 정규화된 점수를 전부 계산하고,
			double otherUserNormalizedScore = otherUserScore - otherUserAverage;
			//해당 점수의 제곱을 누적
			otherUserSquaredSum += otherUserNormalizedScore * otherUserNormalizedScore;
		}
		
		//targetUserSquaredSum과 otherUserSquaredSum의 제곱근을 계산
		targetUserSquaredSum = Math.sqrt(targetUserSquaredSum);
		otherUserSquaredSum = Math.sqrt(otherUserSquaredSum);
		
		//targetUser와 otherUser간의 코사인 유사도를 계산하고 반환
		if(targetUserSquaredSum == 0.0 || otherUserSquaredSum == 0.0) {
			//분모가 0인경우 유사도를 0으로 반환
			return 0.0;
		}else {
			//코사인 유사도 반환
			return dotProduct / (targetUserSquaredSum * otherUserSquaredSum);
		}
	}
}