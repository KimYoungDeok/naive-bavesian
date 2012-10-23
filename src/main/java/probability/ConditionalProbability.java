package probability;

import memory.Memory;

import java.util.Set;

/**
 * Description.
 *
 * @author Youngdeok Kim
 * @since 1.0
 */
public class ConditionalProbability {
	private Memory memory;
	private final float weight= 1.0f;
	private final float ap= 0.5f;

	public ConditionalProbability(Memory memory) {
		this.memory = memory;
	}

	public float probability(String category, String word) {
		float categoryCount = memory.getCategoryCount(category);
		float wordCount = memory.getWordCountForCategory(category, word);
		if(categoryCount==0 || wordCount==0)
			return 0;
		return wordCount / categoryCount;
	}

	public float weightProbility(String category, String word){
		float basicProb = probability(category, word);
		float total = 0f;
		Set<String> categorys = memory.getkeysOfCategory();
		for (String cat : categorys) {
			total += memory.getWordCountForCategory(cat, word);
		}
		return ((weight*ap)+(total*basicProb)) / (weight+total);
	}
}
