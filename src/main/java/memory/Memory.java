package memory;

import count.Counter;
import morphemes.LearnWords;
import morphemes.MorphemesAnalyst;
import java.util.Set;

/**
 * Description.
 *
 * @author Youngdeok Kim
 * @since 1.0
 */
public class Memory {
	private MorphemesAnalyst morphemesAnalyst;
	private MemoryContext memoryContext;
	private final Counter categoryCounter;

	public Memory(MorphemesAnalyst morphemesAnalyst) {
		this.morphemesAnalyst = morphemesAnalyst;
		this.memoryContext = new MemoryContext();
		this.categoryCounter = new Counter();
	}

	private final LearnWords learnWords = new LearnWords() {
		@Override
		public void learn(String category, String morpheme) {
			memoryContext.learningForDocument(category, morpheme);
		}

		@Override
		public void learnWeightedWord(String category, String morpheme, int weight) {
			memoryContext.learningForDocument(category, morpheme, weight);
		}
	};

	public void learning(String category, String document) throws Exception {
		categoryCounter.count(category);
		morphemesAnalyst.analyze(category, document, learnWords);
	}


	public void learning(String category, String document, int weight) throws Exception {
		categoryCounter.count(category, weight);
		morphemesAnalyst.analyze(category, document, learnWords, weight);
	}

	public float getCategoryCount(String category) {
		return (float) categoryCounter.sizeForCategory(category);
	}

	public float getWordCountForCategory(String category, String word) {
		return memoryContext.getWordForCategory(category, word);
	}

	public Set<String> getkeysOfCategory() {
		return categoryCounter.getkeysOfCategory();
	}

	public float getTotalCount() {
		return categoryCounter.size();
	}

	public void close() {
		morphemesAnalyst.close();
	}
}
