package morphemes;

/**
 * Description.
 *
 * @author Youngdeok Kim
 * @since 1.0
 */
public interface LearnWords {
	public void learn(String category, String morpheme);
	public void learnWeightedWord(String category, String morpheme, int weight);
}
