//
//package dsa.cw.autocomplete;
//
//import java.io.BufferedReader;
//import java.io.File;
//import java.io.FileNotFoundException;
//import java.io.FileReader;
//import java.io.FileWriter;
//import java.io.IOException;
//import java.io.PrintWriter;
//import java.io.StreamTokenizer;
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.Collections;
//import java.util.HashMap;
//import java.util.Scanner;
//
///**
// *
// * @author ajb
// */
//public class DictionaryFinder {
//
//    public DictionaryFinder(){
//    }
///**
// * Reads all the words in a comma separated text document into an Array
// * @param f
// */
//    public static ArrayList<String> readWordsFromCSV(String file) throws FileNotFoundException {
//        Scanner sc=new Scanner(new File(file));
//        sc.useDelimiter(" |,");
//        ArrayList<String> words=new ArrayList<>();
//        String str;
//        while(sc.hasNext()){
//            str=sc.next();
//            str=str.trim();
//            str=str.toLowerCase();
//            words.add(str);
//        }
//        return words;
//    }
//     public static void saveCollectionToFile(Collection<?> c,String file) throws IOException {
//        FileWriter fileWriter = new FileWriter(file);
//        PrintWriter printWriter = new PrintWriter(fileWriter);
//         for(Object w: c){
//            printWriter.println(w.toString());
//         }
//        printWriter.close();
//     }
//     public void formDictionary(){
//     }
//     public void saveToFile(){
//     }
//
//    public static void main(String[] args) throws Exception {
//        DictionaryFinder df=new DictionaryFinder();
//        ArrayList<String> in=readWordsFromCSV("C:\\Teaching\\2017-2018\\Data Structures and Algorithms\\Coursework 2\\test.txt");
//        //DO STUFF TO df HERE in countFrequencies
//        df.formDictionary();
//        df.saveToFile();
//
//
//
//    }
//
//}
