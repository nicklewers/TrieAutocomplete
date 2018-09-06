package dsa.cw.autocomplete;

import java.util.*;
import java.util.stream.Collectors;

public class Trie {

    private TrieNode root;
    private String wordFlag;

    public Trie() {
        root = new TrieNode();
        this.wordFlag = "";
    }

    /*
    * add():
    * @param word String we wish to add
    * adds a new word into the trie
    * */
    public void add(String word) {

        TrieNode r = root; // placeholder for the root

        for(int i=0; i<word.length(); i++){ // loop through each letter in the word
            char c = word.charAt(i);
//            format the current letter
            int index = c-'a';
            if(r.letters[index]==null){ // if this child of the root is free
                TrieNode temp = new TrieNode(); // make a new trienode
                r.letters[index]=temp; // and store in the correct index
                r = temp;
            }else{
                r=r.letters[index]; // otherwise overwrite
            }
        }
        r.isWord=true; // mark as a full word
    }

    /*
    * contains():
    * @param key String we are checking for
    * searches for a full word within the trie
    * returns true if it is found*/
    public boolean contains(String key) {
        TrieNode p = searchNode(key);
        if(p==null){
            return false;
        }else{
            if(p.isWord)
                return true;
        }

        return false;
    }

//    perform a breadth first search on the trie
    /*
    * outputBreadthFirstSearch():
    * performs a breadth first search traversal on the trie
    * returns String bfs with the concatenated traversal results
    * */
    public String outputBreadthFirstSearch(){

       if (root == null){ // double check to see if a trie actually exists yet
           return null;
       }
       Queue<TrieNode> queue = new LinkedList<TrieNode>(); // initate an empty queue

//       variables for formatting
       String bfs = "";
       char c;
       int index;

       TrieNode currentNode; // keep track of the current node

        queue.add(root); // push root into the queue

        while (queue.size() > 0){
            currentNode = queue.remove(); // remove the first node in queue and store it

            for(int i = 0; i < currentNode.letters.length; i++){ // find valid children of this node
                if (currentNode.letters[i] != null){ // via means of checking for null "letters"

                    index = i+97;
                    c = (char)index; // get the alphabet index for this character

//                    add current node to queue for use in recursion
                    queue.add(currentNode.letters[i]); // add current node to queue for recursion
                    bfs+=c; // append this character to the main string for use in retrn
                }

            }
        }

        return bfs;

    }


    public String outputDepthFirstSearch(){

        return "";
    }


    /*
    * getSubTrie():
    * @param prefix The new prefix to base this trie off
    * anchors a new prefix as the root of a subtrie
    * returns Trie subTrie
    * */
    public Trie getSubTrie(String prefix){

        Trie subTrie = new Trie(); // initiate an empty trie as a placeholder
        subTrie.root = root; // set the root as the anchor for the new trie


        String[] characters = prefix.split(""); // split the prefix into an array
        for (int i =0; i<characters.length; i++){ // for each character in the prefix
            if (subTrie.searchNode(characters[i])!=null){ // if this character exists in trie
                subTrie.root = subTrie.searchNode(characters[i]); // set it as the new root
            }else{
                return null;
            }
        }

        subTrie.wordFlag = prefix; // store the flag of the prefix for use in getAllWords()
        return subTrie;


    }

    /*
    *   getAllWords():
    *   retrieves a list of all words in Trie, uses recursive helper function getAllWordsRecursive()
    *   returns List allWords
    *   */
    public List<String> getAllWords(){
        List<String> allWords = new ArrayList<String>();

        for (int i=0; i<root.letters.length;i++){
            if(root.letters[i]!=null){
                getAllWordsRecursive(allWords, getValue(i), root.letters[i]);
            }
        }
        return allWords;
    }

    /*
    *   getAllWordsRecursive():
    *   @param words List of words passed from parent function getAllWords()
    *   @param letter The letter value of the current node
    *   @param n The current TrieNode to be processed
    *   */
    public void getAllWordsRecursive(List<String> words, String letter, TrieNode n){

        if (n.isWord){ // if n is a word
            words.add(this.wordFlag+letter); // append the wordFlag (if exists)
        }
        for (int t=0; t<n.letters.length; t++){ // recursively loop through the trieNodes
            if(n.letters[t]!=null){
                getAllWordsRecursive(words, letter + getValue(t), n.letters[t]);
            }
        }
    }


    /*
    *   getValue():
    *   @param charIndex
    *   convert index of current character to string format
    *   returns String val
    *   */

    public String getValue(int charIndex){
        int index = charIndex+97;
        char c = (char)index;
        String val = "";
        val+=c;
        return val;
    }


    /*
    * searchNode():
    * @param s String to search for
    * searches this Trie for a specific string, not necessarily a word
    * returns TrieNode where s is found
    * or null if not found*/
    public TrieNode searchNode(String s){
        TrieNode p = root;
        for(int i=0; i<s.length(); i++){ // for each letter in string
            char c= s.charAt(i); // calculate its char val
            int index = c-'a'; // and its index val
            if(p.letters[index]!=null){ // if a valid TrieNode exists at index
                p = p.letters[index]; // assign that as the root
            }else{
                return null; // else return null
            }
        }

        if(p==root) // if p is still the root, return null
            return null;

        return p;
    }



    public static void main(String[] args) {
        Trie Dictionary = new Trie();
        String words[] = new String[5];
        words[0] = "cheers";
        words[1] = "cheese";
        words[2] = "chat";
        words[3] = "cat";
        words[4] = "bat";
        for (String w : words){
            Dictionary.add(w);
        }
        System.out.println("done");
        System.out.println(Dictionary.contains("oranges"));
        System.out.println(Dictionary.outputBreadthFirstSearch());

        Trie subTrie = Dictionary.getSubTrie("ch");
        List<String> allWords = Dictionary.getAllWords();
        System.out.println(subTrie.getAllWords());
    }

}
