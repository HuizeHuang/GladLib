import edu.duke.*;
import java.util.*;

public class GladLibMap {
    private HashMap<String, ArrayList<String>> myMap;   
    private ArrayList<String> usedWords;
    private ArrayList<String> usedLabels;
    private int numOfReplace;
    private Random myRandom;
    private static String dataSourceURL = "http://dukelearntoprogram.com/course3/data";
    private static String dataSourceDirectory = "datalong";
    
    public GladLibMap(){
        myMap = new HashMap<String,ArrayList<String>>();
        initializeFromSource(dataSourceDirectory);
        myRandom = new Random();
        usedWords = new ArrayList<String>();
        usedLabels = new ArrayList<String>();
        numOfReplace = 0;
    }
    
    public GladLibMap(String source){
        initializeFromSource(source);
        myRandom = new Random();
        usedWords = new ArrayList<String>();
        numOfReplace = 0;
    }
    
    private void initializeFromSource(String source) {    
        String[] labels = {"adjective","noun","color","country","name","animal","timeframe","verb","fruit"};
        for(String s : labels){
            ArrayList<String> list = readIt(source+"/"+s+".txt");
            myMap.put(s,list);
        }
    }
    
    private String randomFrom(ArrayList<String> source){
        int index = myRandom.nextInt(source.size());
        return source.get(index);
    }
    
    private String getSubstitute(String label) {
        if (label.equals("number")){
            return ""+myRandom.nextInt(50)+5;
        }
        return randomFrom(myMap.get(label));
    }
    
    private String processWord(String w){
        int first = w.indexOf("<");
        int last = w.indexOf(">",first);
        if (first == -1 || last == -1){
            return w;
        }
        String prefix = w.substring(0,first);
        String suffix = w.substring(last+1);
        String sub = getSubstitute(w.substring(first+1,last));
        //check if the label is used
        int indexLabel = usedLabels.indexOf(w.substring(first+1,last));
        if(indexLabel ==-1){
            usedLabels.add(w.substring(first+1,last));
        }
        //check if the word is already used in this label
        int index = usedWords.indexOf(sub);
        if(index ==-1){
            usedWords.add(sub);
        }
        else{
            sub = getSubstitute(w.substring(first+1,last));
        }
        numOfReplace += 1;
        return prefix+sub+suffix;
    }
    
    private void printOut(String s, int lineWidth){
        int charsWritten = 0;
        for(String w : s.split("\\s+")){
            if (charsWritten + w.length() > lineWidth){
                System.out.println();
                charsWritten = 0;
            }
            System.out.print(w+" ");
            charsWritten += w.length() + 1;
        }
        System.out.println("\n# Replace: " + numOfReplace);
    }
    
    private String fromTemplate(String source){
        String story = "";
        if (source.startsWith("http")) {
            URLResource resource = new URLResource(source);
            for(String word : resource.words()){
                story = story + processWord(word) + " ";
            }
        }
        else {
            FileResource resource = new FileResource(source);
            for(String word : resource.words()){
                story = story + processWord(word) + " ";
            }
        }
        return story;
    }
    
    private ArrayList<String> readIt(String source){
        ArrayList<String> list = new ArrayList<String>();
        if (source.startsWith("http")) {
            URLResource resource = new URLResource(source);
            for(String line : resource.lines()){
                list.add(line);
            }
        }
        else {
            FileResource resource = new FileResource(source);
            for(String line : resource.lines()){
                list.add(line);
            }
        }
        return list;
    }
    
    public int totalWordsInMap(){
        int total = 0;
        for(String s : myMap.keySet()){
            total += myMap.get(s).size();
        }
        return total;
    }
    
    public int totalWordsConsidered(){
        int total = 0;
        for(String s : usedLabels){
            System.out.println(s);
            if(!s.equals("number")){
                total += myMap.get(s).size();
            }
        }
        return total;
    }
    
    public void makeStory(){
        usedWords.clear();
        System.out.println("\n");
        String story = fromTemplate("data/madtemplate2.txt");
        printOut(story, 60);
        System.out.println("\ntotal words in map: " + totalWordsInMap());
        System.out.println("\ntotal used words in map: " + totalWordsConsidered());
    }

}
