import java.util.*;
import edu.duke.*;
import java.io.*;
/**
 * Write a description of WordsInFiles here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class WordsInFiles {
    private HashMap<String,ArrayList<String>> map;
    
    public WordsInFiles(){
        map = new HashMap<String,ArrayList<String>>();
    }
    
    //If the current word (let's call it currentWord) is not in the HashMap 
    //(which you can check using the containsKey method), you will want to 
    //create a new instance of ArrayList<String> to store file names associated 
    //with the word (let's call it newFileList). After adding the current file 
    //to the newFileList, you can add the word and the ArrayList to the HashMap 
    //using put(currentWord, newFileList).Even though your newFileList variable 
    //will go away, remember that it was just a pointer to the instance of 
    //ArrayList<String> that you created. By putting the newFileList in the HashMap, 
    //the HashMap continues to point to the ArrayList<String>.

    private void addWordsFromFile(File f){
        FileResource fr = new FileResource(f);
        for(String currWord : fr.words()){
            if(!map.containsKey(currWord)){
                ArrayList<String> newFileList = new ArrayList<String>();
                newFileList.add(f.getName());
                map.put(currWord, newFileList);
            }
            else{
                if(!(map.get(currWord)).contains(f.getName())){
                    ArrayList<String> currList = map.get(currWord);
                    currList.add(f.getName());
                    map.put(currWord, currList);
                }
            }
        }
    }
    
    public void buildWordFileMap(){
        map.clear();
        DirectoryResource dr = new DirectoryResource();
        for(File f : dr.selectedFiles()){
            addWordsFromFile(f);
        }
    }
    
    public int maxNumber(){
        int maxNum = 0;
        for(String s: map.keySet()){
            int currNum = map.get(s).size();
            if(currNum > maxNum){
                maxNum = currNum;
            }
        }
        return maxNum;
    }
    
    public ArrayList<String> wordsInNumFiles(int number){
        ArrayList<String> words = new ArrayList<String>();
        for(String s: map.keySet()){
            int currNum = map.get(s).size();
            if(currNum == number){
                words.add(s);
            }
        }
        return words;
    }
    
    public void printFilesIn(String word){
        if(map.containsKey(word)){
            for(int i = 0; i<(map.get(word)).size();i++){
                System.out.println(word + " " + (map.get(word)).get(i));
            }
        }
    }
    
    public void tester(){
        buildWordFileMap();
        //System.out.println(map);
        int maxNum = maxNumber();
        System.out.println("max number of files: " + maxNum);
        ArrayList<String> words = wordsInNumFiles(4);
        //for(String word : words){
            //printFilesIn(word);
        //}
        System.out.println("words in 7 files: " + words.size());
        System.out.println("files in which \"sad\" shows: " + map.get("sad"));
        System.out.println("files in which \"tree\" shows: " + map.get("tree"));
        System.out.println("files in which \"laid\" shows: " + map.get("laid"));
    }
}
