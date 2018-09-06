package dsa.cw.autocomplete;


public class TrieNode {

    TrieNode[] letters; // array for children
    Boolean isWord;
    Boolean visited;

    public TrieNode(){
        this.letters = new TrieNode[26]; // set to alphabet size of 26
        this.visited = false; // mark unvisited by default
        this.isWord = false; // mark as not word by default
    };

}


