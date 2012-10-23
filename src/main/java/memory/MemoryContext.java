package memory;

import count.Count;
import count.Counter;

import java.util.*;

/**
 * Description.
 *
 * @author Youngdeok Kim
 * @since 1.0
 */
class MemoryContext {
    private Map<String, HashMap<String, Count>> categoryMap;

    public MemoryContext() {
        this.categoryMap = new HashMap<String, HashMap<String, Count>>();
    }

    public void learningForDocument(String category, String word) {
        if (!categoryMap.containsKey(category)){
	        HashMap<String, Count> wordMap = new HashMap<String, Count>();
            categoryMap.put(category, wordMap);
        }

	    HashMap<String, Count> wordMap = categoryMap.get(category);
        if(!wordMap.containsKey(word)){
	        wordMap.put(word, new Count());
        }
	    wordMap.get(word).increment();
    }

	public float getWordForCategory(String category, String word){
		if (categoryMap.containsKey(category)){
			HashMap<String, Count> wordMap = categoryMap.get(category);
			if(wordMap.containsKey(word))
				return (float) wordMap.get(word).getCount();
		}
		return 0;
	}

	public void learningForDocument(String category, String word, int weight) {
		if (!categoryMap.containsKey(category)) {
			HashMap<String, Count> wordMap = new HashMap<String, Count>();
			categoryMap.put(category, wordMap);
		}

		HashMap<String, Count> wordMap = categoryMap.get(category);
		if (!wordMap.containsKey(word)) {
			wordMap.put(word, new Count());
		}

		wordMap.get(word).increment(weight);
	}
}
