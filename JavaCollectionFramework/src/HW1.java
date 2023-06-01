import java.io.*;
import java.util.*;
import java.util.regex.*;

public class HW1{
		
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		String fileName = sc.next(); //파일 이름
		int targetUser = sc.nextInt(); //대상 사용자의 ID 0,1,2...
		int topN = sc.nextInt(); //유사도가 높은 상위n명
		int topK = sc.nextInt(); //추천 컨텐츠 상위k개
		
		//예외처리
		try {
			File file = new File(fileName); //fileName으로 부터 입력받은 flie을 읽어온다.(일치할 시)
			Scanner fileScanner = new Scanner(file); //해당 file을 스캔 //32L부터

			ArrayList<String> contents = new ArrayList<>(); 
			//file의 콘텐츠를 임시 저장, 정규화된 점수 계산 및 출력에 사용
			
			//HashMap 사용 이유 빠른 접근 시간, 중복 데이터 처리(콘텐츠나 사용자ID), 데이터 추적 및 분석(콘텐츠의 등장 횟수, 사용자별 콘텐츠 점수)에 용이함
			
			HashMap<String, Integer> contentScores = new HashMap<>();
			//콘텐츠와 그에 대한 점수(등장횟수 + 평가점수)를 저장 하는 맵
			
			HashMap<String, Integer> contentCounts = new HashMap<>();
			//콘텐츠의 등장횟수를 추척해 맵에 저장하고 콘텐츠 평균 점수를 구하는데 사용
			
			HashMap<String, Integer> targetUserContentScores = new HashMap<>();
			//대상 사용자의 콘텐츠와 점수를 저장하는 맵, 대상 사용자가 평가한 콘텐츠와 그에 대한 점수를 저장
			
			HashMap<Integer, HashMap<String, Integer>> userContentScores = new HashMap<>();
			//사용자별 콘텐츠와 점수를 저장하는 맵,
			//사용자ID를 key로 사용하여 해당 사용자가 평가한 콘텐츠와 그에 대한 점수를 저장 유사도 계산에 활용
			

			
			if(fileScanner.hasNextLine()) {//첫 번째 줄을 건너뛰고 두 번째 줄부터 읽음
				fileScanner.nextLine();
			}
			
			int userContentSum = 0;//대상 사용자의 콘텐츠 점수 총합을 저장
			
			//file의 각 줄의 데이터를처리
			while(fileScanner.hasNextLine()) {//다음 줄 확인
				String line = fileScanner.nextLine();//읽어온 데이터를 line에 저장
				String[] parts = line.split(" ");//line을 공백을 기준으로 나눠서 parts에 저장 
				int user = Integer.parseInt(parts[0]);//사용자
				String content = parts[1];//content
				int score = Integer.parseInt(parts[2]);//score를 저장
				
				//현재user가 targetUser라면(targetUser의 콘텐츠와 점수를 찾고 컨텐츠 점수 누적합)
				if(user == targetUser) {
					//targetUser의 콘텐츠를 arrayList contents에 추가
					contents.add(content);
					
					//HashMap에 콘텐츠와 점수 저장
					targetUserContentScores.put(content, score);
					userContentSum += score; //누적합
				}
				
				//getOrDefault를 사용하여 맵에서 해당 콘텐츠를 키로 가진 값을 가져온다. 만약 해당 콘텐츠가 존재하지 않는다면, 기본값0으로 반환
				//가져온 점수 합계와 현재 점수(score)를 더하여 새로운 점수 합계를 계산(각 콘텐츠의 등장 횟수와, 점수 합계를 저장, 평균 점수계산에 사용)
				contentScores.put(content, contentScores.getOrDefault(content, 0) + score);//해당 콘텐츠가 등장한 모든 사용자의 점수 합계를 저장
				
				contentCounts.put(content, contentCounts.getOrDefault(content, 0) + 1 );//(사용자들에 의해 평가된 횟수를 추적함)콘텐츠의 등장횟수를 저장
				
				//있으면 user의 데이터가 있는 맵을 가져오고, 없으면 새로운 맵 생성
				//target user는 이미 위에서 만들어졌으니 userContentScores에 들어가지 않는다.
				userContentScores.putIfAbsent(user, new HashMap<>());
				
				//user들이 평가한 콘텐츠를 읽어 content와 점수를 저장 user = key
				//만약 해당 사용자의 데이터가 존재하지 않으면 (null) putIfAbsent함수를 이용하여 새로운 맵을 만들고
				//이후에 put실행
				userContentScores.get(user).put(content, score);
				
				//※ part로 분리한 데이터들을 puIfAbsent를 활용해 user별 맵을 만들고 거기에 content와 socres를 집어넣는다.
			}
			fileScanner.close();
			//file을 다 읽었으면 닫는다.(작업완료)
			
			
			//문제1.정규화점수 정렬할 대상인 contents들만 가져와서 오름차순 정렬
			Collections.sort(contents, new Comparator<String>() {
				//o1과 o2두개의 문자열을 인자로 받는 compare함수
				public int compare(String o1, String o2) {
					//content의 문자와 숫자를 구분하기 위해 정규 표현식 사용 ex) 'A'와'0' 
					Pattern pattern = Pattern.compile("(\\D+)(\\d+)");
					Matcher m1 = pattern.matcher(o1);
					Matcher m2 = pattern.matcher(o2);
					
					if(m1.find() && m2.find()) {
						//m1과 m2를 찾아서 
						int charCompare = m1.group(1).compareTo(m2.group(1));
						//matcher그룹의(1번 문자열) 문자열이 다른 경우 m1그룹과 m2그룹을 비교
						if(charCompare != 0) {
							//m1(문자열)과 m2(문자열)의 비교값 반환 (문자가 다르므로 숫자까지 비교할필요x)
							return charCompare;// compare에 비교값 반환
						}else {
							//문자열이 같은 경우 m1(숫자)와 m2(숫자)를 정수형으로 변환하여 비교
							//pattern.compile로 정규 표현식에 맞는것을 찾았을 뿐 정수형으로 변한건 아님
							int num1 = Integer.parseInt(m1.group(2));
							int num2 = Integer.parseInt(m2.group(2));
							return Integer.compare(num1, num2);// compare에 비교값 반환
						}
					}
					return 0;
				}
			});
			
			
			System.out.println("1.사용자" + targetUser + "의 콘텐츠와 정규화 점수: ");
			System.out.print("[");
			
			//ArrayList contents에 저장된 각 콘텐츠를 반복적으로 가져와 content에 할당
			//contents = file데이터를 저장한 arrayList, content = for문으로 현재 가져오고있는 contents의 데이터
			for(String content: contents) {
				
				//정규화 계산식
				//현재 콘텐츠(content)에 대한 대상 사용자(targetUser)의 점수를 가져온다.
				//targetUserContentScores.get(content) = 현재 콘텐츠에 대한 targetuser의 contents정보
				double targetUserScore = targetUserContentScores.get(content);
				
				//사용자의 콘텐츠 평균 점수 5 + 4 + 1이라면 평균점수는 10/3()
				//평균 = 콘텐츠점수의 누적합 / 평가한 콘텐츠의 개수
				double userContentAverage = (double) userContentSum / contents.size();
				
				//점수의 정규화 = 대상사용자의 점수 - 평균
				double average = targetUserScore - userContentAverage;
		        
				//출력: 오름차순 정렬을한 content와, 해당 content의 정규화 점수
				System.out.printf("(%s, %.3f)", content, average);
				
				//arrayList contents의 마지막 요소라면 ,출력 (ex: A0의 마지막이라면 ,출력 )
				//contents.size()에서 -1을 한 값이 contents의 마지막 요소
				if(contents.indexOf(content) != contents.size() - 1) {
					System.out.print(", ");
				}
			}
			System.out.print("]\n\n\n");
			
			//(사용자ID, 평균 점수)를 가지는 userAverages 맵 생성
			HashMap<Integer, Double> userAverages = new HashMap<>();
			
			//userContentScores의 사용자ID(key)를 하나씩 user에 할당하여 반복문 실행
			for(Integer user : userContentScores.keySet()) {
				//각 사용자의 content, score를 userScores에 복사 
				HashMap<String, Integer> userScores = userContentScores.get(user);
				
				int userSum = 0;
				//위에서 받은 userScores 각 사용자에 대한 콘텐츠의 점수를 score에 할당
				for(int score : userScores.values()) {
					userSum += score;//모든 콘텐츠 점수의 합을 구하고
				}
				//모든 사용자의 콘텐츠 점수 평균을 구한뒤 처음애 만든 userAverages 맵에 저장
				userAverages.put(user, (double) userSum / userScores.size());
				//사용자별 콘텐츠 점수의 평균
			}
			
			//유사도 리스트라는 arrayList 선언
			ArrayList<Map.Entry<Integer, Double>> similarityList = new ArrayList<>();
			
			//각 사용자 ID를 user에 할당 반복문 실행
			for(Integer user : userContentScores.keySet()) {
				//타겟 사용자와 다른 사용자들 간의
				if(user != targetUser) {
					//코사인 유사도로 계산한 함수를 similarity에 저장
					//대상 사용자의 콘텐츠 점수, 다른 사용자들의 콘텐츠 점수, 모든 사용자의 콘텐츠 점수 평균, 대상 사용자, 다른 사용자
					double similarity = calculateCosineSimilarity(targetUserContentScores, userContentScores.get(user), userAverages, targetUser, user);
					//targetUser의 콘텐츠 점수 맵과 user의 콘텐츠 점수 맵을 가져와 userAverage와 유사도 계산을 실시
					
					
					//사용자 id와 유사도를 arrayList에 저장 (Key(정수), Value(실수))
					similarityList.add(new AbstractMap.SimpleEntry<>(user, similarity));
				}
			}
			
			
			//유사도 내림차순 정렬(2번은 유사도의 크기별로 정렬)
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
				//similarityList에서 key와 value값을 가져온다.(map.entry로 안가져온이유는 각각의 변수에 할당해 각각 다른 용도로 사용하기 위함)
				//map.entry는 주로 출력이 목적인 경우나, key와 value의 관계를 명확하게 표현하고자 하기 위함임
				
				int similarUser = similarityList.get(i).getKey();//key = (유사도가 유사한 사용자ID) + 점수
				double similarity = similarityList.get(i).getValue();
				//value = targetUser와 유사한 otherUser간의 유사도
				
				//similarUserScores에 similarUser의 값을 저장
				HashMap<String, Integer> similarUserScores = userContentScores.get(similarUser);
				
				//유사도가 유사한 사용자의 ID만큼 content에 할당
				for(String content : similarUserScores.keySet()) {
					//targetUser가 content를 key로 가지는 값이 없다면(평가하지 않았다면)
					if(!targetUserContentScores.containsKey(content)) {
						//참
						//otherUserScore에 유사한사용자가 평가한 해당 콘텐츠의 점수 저장
						int otherUserScore = similarUserScores.get(content);
						//유사한 사용자의 콘텐츠 평가점수를 가져온 후, 사용자의 콘텐츠 평균 점수로 정규화하여 차이를 계산
						double otherUserNormalizedScore = otherUserScore - userAverages.get(similarUser);
						
						//추천콘텐츠 선정: 유사도 * 유사한 사용자의 정규화 점수를 곱함
						double recommendationScore = similarity * otherUserNormalizedScore;
						//맵recommendationScores에 content의 key값을 가져와 content가 추천점수 업데이트
						//targetUserContentScores에 content가 존재한다면 content 반환 없다면 0.0반환 둘 다 recommendationScore을 더한다
						recommendationScores.put(content, recommendationScores.getOrDefault(content, 0.0) + recommendationScore);
					}
				}
			}
			//추천 점수가 가장 높은 상위 N개의 콘텐츠를 recommendations안에 저장
			
			//recommendations ArrayList 선언
			//entery.set을 이용해 맵의 값들을 가져온다
			ArrayList<Map.Entry<String, Double>> recommendations = new ArrayList<>(recommendationScores.entrySet());
			//우선 추천점수를 비교해 크면 앞으로 작으면 뒤로 보낸다.
			//추천점수의 내림차순 정렬
			recommendations.sort((o1, o2) -> {
			    if (o1.getValue() > o2.getValue()) {
			        return -1; //o1 o2
			    } else if (o1.getValue() < o2.getValue()) {
			        return 1; //o2 o1
			    } else { 
			    	//value값이 같다면
			    	//key의 오름차순으로 출력
			    	Pattern pattern = Pattern.compile("(\\D+)(\\d+)");
			    	Matcher m1 = pattern.matcher(o1.getKey());
			    	Matcher m2 = pattern.matcher(o2.getKey());
			    	if(m1.find() && m2.find()) {
			    		//문자먼저 비교
			    		int charCompare = m1.group(1).compareTo(m2.group(1));
			    		//문자가 다르다면
			    		if(charCompare != 0) {
			    			//compareTo로 정렬한걸 반환
			    			return charCompare;
			    			//문자가 같다면 숫자부분 비교
			    		}else {
			    			int num1 = Integer.parseInt(m1.group(2));
			    			int num2 = Integer.parseInt(m2.group(2));
			    			return Integer.compare(num1, num2);
			    		}
			    	}
			    	//value값이 같다면 key값으로 비교해 작은 수 부터 
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

	//유사도 두 벡터 간의 내적 / 벡터의 크기의 곱
	private static double calculateCosineSimilarity(HashMap<String, Integer> targetUserScores,
			//targetUser, otherUser 메서드, targetUserScore, otherUserScores 사용자의 평가 점수 맵, userAverages 평균 평점 맵을 인수로 받음 
	HashMap<String, Integer> otherUserScores, HashMap<Integer, Double> userAverages, int targetUser, int otherUser) {
		//Cosine유사도를 계산하기 위해 변수들을 초기화한다.
		double dotProduct = 0.0;//분자
		double targetUserSquaredSum = 0.0;//targetUser 분모
		double otherUserSquaredSum = 0.0;//targetUser 분모
		
		//각 사용자의 평균 평점을 가져온다.
		double targetUserAverage = userAverages.get(targetUser);
		double otherUserAverage = userAverages.get(otherUser);
		
		
		for(String content : targetUserScores.keySet()) {
			
			//targetUser의 ID를 받아 해당ID의 targetUser의 콘텐츠 점수를 가져옴
			int targetUserScore = targetUserScores.get(content);
			
			//targetUser의 점수에서 평균값을 뺀 값
			double targetUserNoramlizedScore = targetUserScore - targetUserAverage;
			
			//targetUser 벡터의 크기(Normalized의 합):분모
			targetUserSquaredSum += targetUserNoramlizedScore * targetUserNoramlizedScore;
			
			
			//다른 사용자들의 content가 존재하는지 확인
			if(otherUserScores.containsKey(content)) {
				//참
				//콘텐츠 점수를 가져온뒤
				int otherUserScore = otherUserScores.get(content);
				//정규화된 점수를 계산
				double otherUserNormalizedScore = otherUserScore - otherUserAverage;
				//각 벡터의 크기를 구했으면 분자도 구한다.
				dotProduct += targetUserNoramlizedScore  * otherUserNormalizedScore;
			}
		}
		
		//otherUserScores의 모든 점수를
		for(int otherUserScore : otherUserScores.values()) {
			//otherUserScore의 정규화된 점수들을 전부 계산하고,
			double otherUserNormalizedScore = otherUserScore - otherUserAverage;
			//otheruser 벡터의 크기
			otherUserSquaredSum += otherUserNormalizedScore * otherUserNormalizedScore;
		}
		
		//target와 otherUser의 제곱근을 계산
		targetUserSquaredSum = Math.sqrt(targetUserSquaredSum);
		otherUserSquaredSum = Math.sqrt(otherUserSquaredSum);
		
		//제곱근을마치고 분모가 0이라면
		if(targetUserSquaredSum == 0.0 || otherUserSquaredSum == 0.0) {
			return 0.0;//0출력
		}else {
			//아니라면,
			//계산한 코사인 유사도 반환
			return dotProduct / (targetUserSquaredSum * otherUserSquaredSum);
		}
	}
}