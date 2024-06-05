package frequency_Analyzer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class File_Reader_Runnable implements Runnable 
{
    private String file_path;

    public File_Reader_Runnable(String file_path) 
    {
        this.file_path = file_path;
    }

    @Override
    public void run() 
    { 
        try (BufferedReader br = new BufferedReader(new FileReader(file_path))) 
        {
            String line;
            Map<String, Integer> wordCountMap = new HashMap<>();
            while ((line = br.readLine()) != null) 
            {
                // Breaking each line into words, converting to lower case, and updating the map
                line = line.toLowerCase();
                String[] words = line.split("\\W+");
                for (String word : words) 
                {
                    if (!word.isEmpty()) 
                    {
                        wordCountMap.put(word, wordCountMap.getOrDefault(word, 0) + 1);
                    }
                }
            }

            // Storing the map entries in an ArrayList
            List<Entry<String, Integer>> entryList = new ArrayList<>(wordCountMap.entrySet());

            // Sorting the list in ascending order of values
            entryList.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));

            // Printing the top 10 keywords in reverse order
            System.out.println("Top 10 Keywords in reverse order:");
            int count = 1;
            for (Map.Entry<String, Integer> entry : entryList) 
            {
                if (count >= 10) break; // Print top 9 keywords
                System.out.println(entry.getValue() + " " + entry.getKey());
                count++;
            }
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }
        
    }

    public static void main(String[] args) 
    {
        String file_path = "C:/Users/lenovo/Documents/workspace-spring-tool-suite-4-4.19.0.RELEASE/Word_Frequency_Analyser/src/main/java/frequency_Analyzer/National_Anthem.txt";
        ExecutorService executor = Executors.newSingleThreadExecutor();
        File_Reader_Runnable runnable1 = new File_Reader_Runnable(file_path);
        executor.submit(runnable1);
        executor.shutdown();
    }
}
