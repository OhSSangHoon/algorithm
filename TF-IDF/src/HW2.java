import java.io.*;
import java.util.*;

public class HW2 {
    
    public static void main(String[] args) {
        Map<String, List<String>> dataMap = new HashMap<>();

        Scanner sc = new Scanner(System.in);

        String filename = sc.next();
        int similarity = sc.nextInt();
        String targetTitle = sc.next().toLowerCase();

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            String title = null;

            while ((line = reader.readLine()) != null) {
                if (title == null) {
                    title = line.toLowerCase();
                    dataMap.put(title, new ArrayList<>());
                } else {
                    String content = line.toLowerCase();
                    dataMap.get(title).add(content);
                    title = null;
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("File을 찾을 수 없음");
        } catch (IOException e) {
            System.out.println("Error reading the file");
        }

        if (dataMap.containsKey(targetTitle)) {
            List<String> contents = dataMap.get(targetTitle);
            int numDocuments = dataMap.size();

            for (String content : contents) {
                String[] words = content.split("[,.?!:\"\\s]+");
                Map<Integer, Double> termFrequencies = new HashMap<>();

                List<List<String>> wordLists = new ArrayList<>();
                for (List<String> docContents : dataMap.values()) {
                    for (String docContent : docContents) {
                        wordLists.add(Arrays.asList(docContent.split("[,.?!:\"\\s]+")));
                    }
                }
                System.out.println("결과 1. " + targetTitle + "의 TF-IDF 벡터");
                for (String word : words) {
                    termFrequencies.put(word.hashCode(), tf(Arrays.asList(words), word) * idf(wordLists, word));
                }
                System.out.print("[");
                termFrequencies.entrySet().stream()
                        .sorted(Map.Entry.comparingByKey())
                        .forEach(entry -> System.out.print("(" + entry.getKey() + ": " + String.format("%.3f", entry.getValue()) + ")" ));
                System.out.print("]");
            }
        } else {
            System.out.println("제목을 찾을 수 없습니다.");
        }
    }

    private static double tf(List<String> list, String word) {
        double result = 0;
        for (String targetWord : list)
            if (word.equalsIgnoreCase(targetWord)) result++;
        return result / list.size();
    }

    private static double idf(List<List<String>> lists, String word) {
        double n = 0;
        for (List<String> list : lists) {
            for (String targetWord : list) {
                if (word.equalsIgnoreCase(targetWord)) {
                    n++;
                    break;
                }
            }
        }
        return Math.log(lists.size() / n);
    }
}