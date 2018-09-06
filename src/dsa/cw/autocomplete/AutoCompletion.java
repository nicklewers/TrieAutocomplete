package dsa.cw.autocomplete;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class AutoCompletion {


    DictionaryMaker dictionary;
    AutoCompletionTrie trie;

    FileWriter fileWriter = new FileWriter("lotrMatches.csv");
    PrintWriter printWriter = new PrintWriter(fileWriter);

    /*
    * wordFrequency{}:
    * simple class to enable effective sorting on the word frequency
    * */
    public class wordFrequency implements Comparable<wordFrequency>{
        String word;
        int frequency;
        public wordFrequency(String word, String frequency){
            this.word = word;
            this.frequency = Integer.parseInt(frequency);
        }
        @Override
        public int compareTo(wordFrequency x){
            return (x.frequency - frequency);
        }

    }

    public AutoCompletion() throws IOException {
//
        this.dictionary = new DictionaryMaker("lotrDictionary.txt");
        ArrayList<String> dictionaryIn= DictionaryMaker.readWordsFromCSV("lotr.csv");

        dictionary.formDictionary(dictionaryIn); // lotr.csv -> dictionary-> lotrDictionary.txt
        dictionary.saveToFile(); // save to file

        this.trie = new AutoCompletionTrie(); // create a new empty trie
        for (String word : this.dictionary.dictionary){
            this.trie.add(word); // for each word in dictionary, add into trie
        }


        ArrayList<String> queriesIn = DictionaryMaker.readWordsFromCSV("lotrQueries.csv"); // load prefixes

        AutoCompletionTrie subTrie = new AutoCompletionTrie(); // new empty trie for subtrie
        List<String> subTrieWords = new ArrayList<String>(); // List to store subTrie words

        for (String prefix: queriesIn){ // for each prefix

            subTrie = this.trie.getSubAutoCompletionTrie(prefix); // recover all words matching prefix and store in the clean subtrie

            subTrieWords = subTrie.getAllWords(); // extract each word in string format from subTrie


            String[] wordFreq; // array to store word and frequency seperately
            List<wordFrequency> wordFrequencies = new ArrayList<wordFrequency>(); // custom List to store word details
            wordFrequency thisWord;
            int sumOfFrequencies = 0; // set sum of frequencies to 0
           for (String word : subTrieWords){ // for each word in the subTrie
               wordFreq = null;
               wordFreq = word.split(":"); // split the words via the ":" delimiter


               thisWord = new wordFrequency(wordFreq[0], wordFreq[1]); // create a new wordFrequency obj {word, freq}
               wordFrequencies.add(thisWord); // add to the custom List

               sumOfFrequencies+=Integer.parseInt(wordFreq[1]); // increase the sumOfFrequencies

           }

            Collections.sort(wordFrequencies); // sort them in descending order

            String fileLine = subTrie.wordFlag+","; // prepend the wordFlag to the fileLine
           if (wordFrequencies.size()>=3){ // if there are more than 3 words matching
               for (int i =0; i <3; i++){ // process exactly 3

                   float probability = (float)wordFrequencies.get(i).frequency/sumOfFrequencies; // format the probability
                   System.out.println(wordFrequencies.get(i).word+" (probability "+probability+")"); // print to sysout
//                   store results in a single string to be written to file
//                   extra bit of formatting to ensure correct comma placement
                   if (i==2){
                       fileLine+=wordFrequencies.get(i).word+","+wordFrequencies.get(i).frequency+","+probability;
                   }else{
                       fileLine+=wordFrequencies.get(i).word+","+wordFrequencies.get(i).frequency+","+probability+",";
                   }

               }
           }

           else{ // if less than 3, go for as many as possible
               for (int i =0; i <wordFrequencies.size(); i++){

                   float probability = (float)wordFrequencies.get(i).frequency/sumOfFrequencies;

                   System.out.println(wordFrequencies.get(i).word+" (probability "+probability+")");
//                   store results in a single string to be written to file
//                   extra bit of formatting to ensure correct comma placement
                   if (i==wordFrequencies.size()-1){
                       fileLine+=wordFrequencies.get(i).word+","+wordFrequencies.get(i).frequency+","+probability;
                   }else{
                       fileLine+=wordFrequencies.get(i).word+","+wordFrequencies.get(i).frequency+","+probability+",";
                   }
               }
           }

            printWriter.println(fileLine); // print line to file

        }

        printWriter.close();


    }




    public static void main(String[] args) throws IOException {

        AutoCompletion lotrAutoComplete = new AutoCompletion(); // this runs the main functionality described in spec


//        TESTS
        System.out.println("\nAll words in main trie");
        System.out.println(lotrAutoComplete.trie.getAllWords());

        AutoCompletionTrie subTrie = lotrAutoComplete.trie.getSubAutoCompletionTrie("hi");
        System.out.println("\nAll words in sub trie");
        System.out.println(subTrie.getAllWords());

    }

}
