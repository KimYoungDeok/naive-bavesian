package morphemes;

import kr.ac.kaist.swrc.jhannanum.comm.Eojeol;
import kr.ac.kaist.swrc.jhannanum.comm.Sentence;
import kr.ac.kaist.swrc.jhannanum.exception.ResultTypeException;
import kr.ac.kaist.swrc.jhannanum.hannanum.Workflow;
import kr.ac.kaist.swrc.jhannanum.plugin.MajorPlugin.MorphAnalyzer.ChartMorphAnalyzer.ChartMorphAnalyzer;
import kr.ac.kaist.swrc.jhannanum.plugin.MajorPlugin.PosTagger.HmmPosTagger.HMMTagger;
import kr.ac.kaist.swrc.jhannanum.plugin.SupplementPlugin.MorphemeProcessor.UnknownMorphProcessor.UnknownProcessor;
import kr.ac.kaist.swrc.jhannanum.plugin.SupplementPlugin.PlainTextProcessor.InformalSentenceFilter.InformalSentenceFilter;
import kr.ac.kaist.swrc.jhannanum.plugin.SupplementPlugin.PlainTextProcessor.SentenceSegmentor.SentenceSegmentor;
import kr.ac.kaist.swrc.jhannanum.plugin.SupplementPlugin.PosProcessor.NounExtractor.NounExtractor;
import org.apache.log4j.Logger;

import java.util.LinkedList;

public class MorphemesAnalystHannaum implements MorphemesAnalyst {
	private static final Logger log = Logger.getLogger(MorphemesAnalystHannaum.class);
	private Workflow workflow = new Workflow();

	public MorphemesAnalystHannaum() throws Exception {
		workflow.appendPlainTextProcessor(new SentenceSegmentor(), null);
		workflow.appendPlainTextProcessor(new InformalSentenceFilter(), null);

		workflow.setMorphAnalyzer(new ChartMorphAnalyzer(), "conf/plugin/MajorPlugin/MorphAnalyzer/ChartMorphAnalyzer.json");
		workflow.appendMorphemeProcessor(new UnknownProcessor(), null);

		workflow.setPosTagger(new HMMTagger(), "conf/plugin/MajorPlugin/PosTagger/HmmPosTagger.json");
		workflow.appendPosProcessor(new NounExtractor(), null);
		workflow.activateWorkflow(true);
	}

	public LinkedList<Sentence> analyze(String document) throws ResultTypeException {
		workflow.analyze(document);
		return workflow.getResultOfDocument(new Sentence(0, 0, false));
	}

	@Override
	public void analyze(String category, String document, LearnWords learnWords) {
		workflow.analyze(document);
		try {
			LinkedList<Sentence> resultList = workflow.getResultOfDocument(new Sentence(0, 0, false));
			for (Sentence sentence : resultList) {
				Eojeol[] eojeols = sentence.getEojeols();
				for (Eojeol eojeol : eojeols) {
					if (eojeol.length > 0) {
						String[] morphemes = eojeol.getMorphemes();
						for (String morpheme : morphemes) {
							learnWords.learn(category, morpheme);
						}
					}
				}
			}
		} catch (ResultTypeException e) {
			log.error(e);
		}
	}

	@Override
	public void analyze(String category, String document,LearnWords learnWords, int weight) {
		workflow.analyze(document);
		try {
			LinkedList<Sentence> resultList = workflow.getResultOfDocument(new Sentence(0, 0, false));
			for (Sentence sentence : resultList) {
				Eojeol[] eojeols = sentence.getEojeols();
				for (Eojeol eojeol : eojeols) {
					if (eojeol.length > 0) {
						String[] morphemes = eojeol.getMorphemes();
						for (String morpheme : morphemes) {
							learnWords.learnWeightedWord(category, morpheme, weight);
						}
					}
				}
			}
		} catch (ResultTypeException e) {
			log.error(e);
		}
	}

	public void close() {
		workflow.close();
	}

	public void clear() {
		workflow.clear();
	}


}
