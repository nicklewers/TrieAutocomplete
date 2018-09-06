
package dsa.cw.autocomplete;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StreamTokenizer;
import java.util.*;

/**
 *
 * @author ajb
 */
public class DictionaryMaker {

    ArrayList<String> dictionary;
    String outputFile;


    public DictionaryMaker(String outputFileName){

        this.dictionary = new ArrayList<>();
        this.outputFile = outputFileName;


    }
    /**
     * Reads all the words in a comma separated text document into an Array
     * @param f
     */
    public static ArrayList<String> readWordsFromCSV(String file) throws FileNotFoundException {
        Scanner sc=new Scanner(new File(file));
        sc.useDelimiter(",|\\n");
        ArrayList<String> words=new ArrayList<>();
        String str;
        while(sc.hasNext()){
            str=sc.next();
            str=str.trim();
            str=str.toLowerCase();
            words.add(str);
        }
        return words;
    }

//    form a dictionary from the arraylist generated by the csv file
    public void formDictionary(ArrayList<String> list){

//        create a hashset out of the list to get each unique item
        Set<String> listHashSet = new HashSet<>(list);
//        define the blank dictionary list
        ArrayList<String> dictionary = new ArrayList<>();

//        for each item in the hash set
        for (String key : listHashSet){
//            add a new item to the dictionary list, with the word appended by the frequency
            dictionary.add(key + ":" + Collections.frequency(list, key));
        }

//        sort the new dictionary alphabetically
        Collections.sort(dictionary);
//        save it to the body of the class
        this.dictionary = dictionary;

    }

//   save the dictionary + occurrences to file
    public void saveToFile() throws IOException {

        FileWriter fileWriter = new FileWriter(this.outputFile);
        PrintWriter printWriter = new PrintWriter(fileWriter);
        for(String word: this.dictionary){
            printWriter.println(word);
        }
        printWriter.close();

    }

    public static void main(String[] args) throws Exception {
        DictionaryMaker df=new DictionaryMaker("outputFileTest.txt");
        ArrayList<String> in=readWordsFromCSV("testDocument.txt");
        //DO STUFF TO df HERE in countFrequencies
        df.formDictionary(in);
        df.saveToFile();



    }

}


//        sort alphabetically
//        Collections.sort(listOfStrings);
//
//
////        count occurrences
//                Set<String> stringHashSet = new HashSet<>(listOfStrings);
//        for (String key : stringHashSet){
//        System.out.println(key+ " : "+ Collections.frequency(listOfStrings, key));
//        }