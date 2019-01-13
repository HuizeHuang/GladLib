import edu.duke.*;
import java.util.ArrayList;
/**
 * Write a description of CharactersInPlay here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class CharactersInPlay {
    private ArrayList<String> charName;
    private ArrayList<Integer> charFreqs;
    
    public CharactersInPlay(){
        charName = new ArrayList<String>();
        charFreqs = new ArrayList<Integer>();
    }
    
    public void update(String person){
        int index = charName.indexOf(person);
        if(index == -1){
            charName.add(person);
            charFreqs.add(1);
        }
        else{
            int count = charFreqs.get(index);
            charFreqs.set(index,count+1);
        }
    }
    
    public void findAllCharacters(){
        charName.clear();
        charFreqs.clear();
        FileResource fr = new FileResource();
        for(String s : fr.lines()){
            int index = s.indexOf(".");
            if(index != -1){
                String name = s.substring(0,index);
                update(name);
            }
        }
    }
    
    public void tester(){
        findAllCharacters();
        int maxFreq = 0;
        int secondFreq = 0;
        int thirdFreq = 0;
        String thirdChar = "";
        String mainChar = "";
        for(int i = 0; i < charName.size(); i++){
            int currFreq = charFreqs.get(i);
            if( currFreq > maxFreq){
                secondFreq = maxFreq;
                maxFreq = currFreq;
                mainChar = charName.get(i);
            }
            if(currFreq > secondFreq && maxFreq != currFreq){
                thirdFreq = secondFreq;
                secondFreq = currFreq;
                thirdChar = charName.get(i);
                if (thirdFreq < currFreq && secondFreq != currFreq){
                    thirdFreq = currFreq;
                    thirdChar = charName.get(i);
                }
            }
        }
        
        charactersWithNumParts(10,15);
        System.out.println("Main character: " + mainChar + " " + "number: " + maxFreq);
        System.out.println("Third character: " + thirdChar + " " + thirdFreq);
    }
    
    public void charactersWithNumParts(int num1, int num2){
        for(int i = 0; i < charName.size(); i++){
            if(charFreqs.get(i) >= num1 && charFreqs.get(i) <= num2){
                System.out.println(charName.get(i));
            }
        }
    }
}
