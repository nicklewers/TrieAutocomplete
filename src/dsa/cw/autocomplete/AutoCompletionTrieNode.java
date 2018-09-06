package dsa.cw.autocomplete;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AutoCompletionTrieNode {

    AutoCompletionTrieNode[] letters;
    Boolean isWord;
    Boolean visited;
    int frequency;

    public AutoCompletionTrieNode(){
        this.letters = new AutoCompletionTrieNode[26];
        this.visited = false;
        this.isWord = false;
        this.frequency = 0;
    };

    public List<AutoCompletionTrieNode> getChildren(){

        AutoCompletionTrieNode left = null;
        AutoCompletionTrieNode right = null;
        int childNo = 0;
        for (int i=0; i<letters.length; i++){

            if (letters[i]!=null){
                if (childNo == 0){
                    left = letters[i];
                }else if(childNo == 1){
                    right = letters[i];

                }
                childNo++;
            }
        }
        return Stream.of(left, right)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

    }





}


