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
import morphemes.LearnWords;
import morphemes.MorphemesAnalyst;
import org.apache.log4j.Logger;
import org.junit.Test;

import java.util.LinkedList;

public class MorphemesAnalystHannaum {
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
	@Test
	public void analyze() {
		String document = "아주 재밋서서 죽겟어... 알바들아 고생한다";
		workflow.analyze(document);
		try {
			LinkedList<Sentence> resultList = workflow.getResultOfDocument(new Sentence(0, 0, false));
			for (Sentence sentence : resultList) {
				Eojeol[] eojeols = sentence.getEojeols();
				for (Eojeol eojeol : eojeols) {
					if (eojeol.length > 0) {
						String[] morphemes = eojeol.getMorphemes();
						for (String morpheme : morphemes) {
							System.out.println(morpheme);
						}
					}
				}
			}
		} catch (ResultTypeException e) {
			log.error(e);
		}
	}
}
