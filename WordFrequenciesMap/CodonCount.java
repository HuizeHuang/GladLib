import java.util.*;
import edu.duke.*;
/**
 * Write a description of CodonCount here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class CodonCount {
    private HashMap<String,Integer> map;
    
    public CodonCount(){
        map = new HashMap<String,Integer>();
    }
    
    public void buildCodonMap(int start, String dna){
        map.clear();
        dna.toUpperCase();
        for(int i = start; i < dna.length()-2; i +=3){
            String currCodon = dna.substring(i,i+3);
            if(Character.isLetter(currCodon.charAt(2))){
                if(!map.containsKey(currCodon)){
                    map.put(currCodon,1);
                }
                else{
                    map.put(currCodon,map.get(currCodon)+1);
                }
            }
        }
    }
    
    public String getMostCommonCodon(){
        int maxCount = 0;
        String maxCodon = "";
        for(String s : map.keySet()){
            if(map.get(s) > maxCount){
                maxCount = map.get(s);
                maxCodon = s;
            }
        }
        return maxCodon;
    }
    
    public void printCodonCounts(int start, int end){
        for(String s: map.keySet()){
            if(map.get(s) >= start && map.get(s) <= end){
                System.out.println(s + " " + map.get(s));
            }
        }
    }
    
    public void codonTester(){
        FileResource fr = new FileResource();
        String dna = fr.asString();
        for(int i = 0; i<3; i++){
            buildCodonMap(i,dna);
            String maxCodon = getMostCommonCodon();
            System.out.println("Reading frame starting from " + i + " results in " + map.size()+" unique condons");
            System.out.println("and most common codon is "+ maxCodon+" with count "+map.get(maxCodon));
            System.out.println("Counts of codons between 1 and 5 inclusive are:");;
            printCodonCounts(6,10);
        }
    }
}
