import java.io.*;
import java.util.*;

public class HW2 {
    public static void main(String[] args) {
        Map<String, List<String>> dataMap = new HashMap<>();

        Scanner sc = new Scanner(System.in);

        String filename = sc.next();
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
            System.out.println("File not found");
        } catch (IOException e) {
            System.out.println("Error reading the file");
        }

        if (dataMap.containsKey(targetTitle)) {
            List<String> contents = dataMap.get(targetTitle);
            int numDocuments = dataMap.size();
            int totalContents = 0;

            for (List<String> contentList : dataMap.values()) {
                totalContents += contentList.size();
            }

            for (String content : contents) {
                Map<Integer, Integer> termFrequencies = calculateTermFrequencies(content);
                System.out.println("TF Vector:");

                for (Map.Entry<Integer, Integer> entry : termFrequencies.entrySet()) {
                    int termHash = entry.getKey();
                    int tf = entry.getValue();
                    double tfNormalized = (double) tf / totalContents;
                    System.out.println(termHash + ": " + tfNormalized);
                }
            }
        } else {
            System.out.println("Title not found.");
        }
    }

    private static Map<Integer, Integer> calculateTermFrequencies(String content) {
        Map<Integer, Integer> termFrequencies = new HashMap<>();
        String[] words = content.split("[,.?!:\"\\s]+");

        for (String word : words) {
            int termHash = word.hashCode();
            termFrequencies.put(termHash, termFrequencies.getOrDefault(termHash, 0) + 1);
        }

        return termFrequencies;
    }
}