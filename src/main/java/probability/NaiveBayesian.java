package probability;

import kr.ac.kaist.swrc.jhannanum.comm.Eojeol;
import kr.ac.kaist.swrc.jhannanum.comm.Sentence;
import kr.ac.kaist.swrc.jhannanum.exception.ResultTypeException;
import memory.Memory;
import morphemes.LearnWords;
import morphemes.MorphemesAnalyst;
import morphemes.MorphemesAnalystHannaum;
import org.apache.log4j.Logger;

import java.util.LinkedList;
import java.util.Set;

/**
 * Description.
 *
 * @author Youngdeok Kim
 * @since 1.0
 */
public class NaiveBayesian {
	private static final Logger log = Logger.getLogger(NaiveBayesian.class);
	private ConditionalProbability conditionalProbability;
	private final MorphemesAnalyst morphemesAnalyst;
	private final Memory memory;

	public NaiveBayesian(Memory memory, MorphemesAnalyst morphemesAnalyst) {
		this.conditionalProbability = new ConditionalProbability(memory);
		this.morphemesAnalyst = morphemesAnalyst;
		this.memory = memory;
	}

	public float documentProbability(String category, String document) {
		final float[] probaility = {1f};

		morphemesAnalyst.analyze(category, document, new LearnWords() {
			@Override
			public void learn(String category, String morpheme) {
				probaility[0] *= conditionalProbability.weightProbility(category, morpheme);
			}

			@Override
			public void learnWeightedWord(String category, String morpheme, int weight) {
			}
		});

		return probaility[0];
	}

	public float probaility(String category, String document) {
		float categoryProbaility = memory.getCategoryCount(category) / memory.getTotalCount();
		float documentProbaility = documentProbability(category, document);
		return documentProbaility * categoryProbaility;
	}

	public String classify(String document) {
		float max = 0f;
		float probaility = 0f;
		String best = "unknown";
		Set<String> categorys = memory.getkeysOfCategory();

		for (String category : categorys) {
			probaility = probaility(category, document);
			if (probaility > max) {
				max = probaility;
				best = category;
			}
		}
		return best;
	}
}
