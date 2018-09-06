package dsa.cw.autocomplete;

import java.util.*;
import java.util.stream.Collectors;

/*
* for more detailed comments, reference Trie class
*
* */

public class AutoCompletionTrie {

    private AutoCompletionTrieNode root;
    public String wordFlag;

    public AutoCompletionTrie() {
        root = new AutoCompletionTrieNode();
        wordFlag = "";
    }

    // add a word into the AutoCompletionTrie
    public void add(String w) {

        String[] wordArray = w.split(":");

        int frequency = Integer.parseInt(wordArray[1]);
        String word = wordArray[0];

        AutoCompletionTrieNode r = root;
//        loop through each letter in the word
        for(int i=0; i<word.length(); i++){
            char c = word.charAt(i);
//            subtract 97 from the current char to get the value back within range
            int index = c-'a';
            if(r.letters[index]==null){
                AutoCompletionTrieNode temp = new AutoCompletionTrieNode();
                r.letters[index]=temp;
                r = temp;
            }else{
                r=r.letters[index];
            }
        }
        r.frequency = frequency;
        r.isWord=true;
    }

    // Returns if the word is in the AutoCompletionTrie.
    public boolean contains(String key) {
        AutoCompletionTrieNode p = searchNode(key);
        if(p==null){
            return false;
        }else{
            if(p.isWord)
                return true;
        }

        return false;
    }

    //    perform a breadth first search on the AutoCompletionTrie
    public String outputBreadthFirstSearch(){

//        check to see if AutoCompletionTrie actually exists
        if (root == null){
            return null;
        }
//       initiate an empty queue
        Queue<AutoCompletionTrieNode> queue = new LinkedList<AutoCompletionTrieNode>();

//       variables for formatting
        String bfs = "";
        char c;
        int index;

//      keep track of the current node
        AutoCompletionTrieNode currentNode;

//       push root into queue
        queue.add(root);

        while (queue.size() > 0){
//            remove the first node in queue and store it
            currentNode = queue.remove();

//            find the children of this node
            for(int i = 0; i < currentNode.letters.length; i++){
                if (currentNode.letters[i] != null){
//                    convert the index to a character
                    index = i+97;
                    c = (char)index;

//                    add current node to queue for use in recursion
                    queue.add(currentNode.letters[i]);
//                    append this character to the returned string
                    bfs+=c;
                }

            }
        }

        return bfs;

    }

    public void depthFirstSearch(AutoCompletionTrieNode node){

//        AutoCompletionTrieNode n = node;

    }
    public String outputDepthFirstSearch(){
        List<AutoCompletionTrieNode> visitedNodes = new LinkedList<>();
        List<AutoCompletionTrieNode> unvisitedNodes = new LinkedList<>();

        unvisitedNodes.add(root);

        while (!unvisitedNodes.isEmpty()){
            AutoCompletionTrieNode currNode = unvisitedNodes.remove(0);

            List<AutoCompletionTrieNode> newNodes = currNode.getChildren()
                    .stream()
                    .filter(node -> !visitedNodes.contains(node))
                    .collect(Collectors.toList());

            unvisitedNodes.addAll(0, newNodes);
            visitedNodes.add(currNode);
        }
        return "";
    }



    public AutoCompletionTrie getSubAutoCompletionTrie(String prefix){
        AutoCompletionTrie subAutoCompletionTrie = new AutoCompletionTrie();
        subAutoCompletionTrie.root = root;
        AutoCompletionTrie newTrie = new AutoCompletionTrie();

        String[] characters = prefix.split("");
        for (int i =0; i<characters.length; i++){
            if (subAutoCompletionTrie.searchNode(characters[i])!=null){
                subAutoCompletionTrie.root = subAutoCompletionTrie.searchNode(characters[i]);
            }else{
                return null;
            }
        }
        newTrie = subAutoCompletionTrie;
        newTrie.wordFlag = prefix;
        return newTrie;
    }


    /*
     *   getAllWords():
     *   reAutoCompletionTrieves a list of all words in AutoCompletionTrie, uses recursive helper function getAllWordsRecursive()
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
     *   @param n The current AutoCompletionTrieNode to be processed
     *   */
    public void getAllWordsRecursive(List<String> words, String letter, AutoCompletionTrieNode n){

        if (n.isWord){
            words.add(this.wordFlag+letter+":"+n.frequency);
        }
        for (int t=0; t<n.letters.length; t++){
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
    public boolean startsWith(String prefix) {
        AutoCompletionTrieNode p = searchNode(prefix);
        if(p==null){
            return false;
        }else{
            return true;
        }
    }

    public AutoCompletionTrieNode searchNode(String s){
        AutoCompletionTrieNode p = root;
        for(int i=0; i<s.length(); i++){
            char c= s.charAt(i);
            int index = c-'a';
            if(p.letters[index]!=null){
                p = p.letters[index];
            }else{
                return null;
            }
        }

        if(p==root)
            return null;

        return p;
    }



    public static void main(String[] args) {
        AutoCompletionTrie Dictionary = new AutoCompletionTrie();
        String words[] = new String[5];
        words[0] = "cheers:2";
        words[1] = "cheese:4";
        words[2] = "chat:1";
        words[3] = "cat:4";
        words[4] = "bat:2";
        for (String w : words){
            Dictionary.add(w);
        }
        System.out.println("done");
        System.out.println(Dictionary.contains("oranges"));
        System.out.println(Dictionary.outputBreadthFirstSearch());

        AutoCompletionTrie subAutoCompletionTrie = Dictionary.getSubAutoCompletionTrie("ch");
        List<String> allWords = Dictionary.getAllWords();
        List<String> subWords = subAutoCompletionTrie.getAllWords();
        System.out.println("test");
    }

}
