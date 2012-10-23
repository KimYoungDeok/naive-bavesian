package morphemes;

/**
 * Description.
 *
 * @author Youngdeok Kim
 * @since 1.0
 */
public interface MorphemesAnalyst {
	void analyze(String category, String document, LearnWords learnWords);
	void analyze(String category, String document, LearnWords learnWords, int weight);
	void close();
}
