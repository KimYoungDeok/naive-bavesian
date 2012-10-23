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
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.kr.KoreanAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

import java.io.IOException;
import java.io.StringReader;
import java.util.LinkedList;

public class MorphemesAnalystLucene implements MorphemesAnalyst {
	private static final Logger log = Logger.getLogger(MorphemesAnalystLucene.class);
	private KoreanAnalyzer analyzer;

	public MorphemesAnalystLucene() throws Exception {
		analyzer = new KoreanAnalyzer();
		analyzer.setBigrammable(true);
		analyzer.setHasOrigin(true);
	}

	@Override
	public void analyze(String category, String document, LearnWords learnWords) {
		try {
			TokenStream tokenStream = analyzer.tokenStream("content", new StringReader(document));
		    CharTermAttribute termAttribute = tokenStream.getAttribute(CharTermAttribute.class);
			while (tokenStream.incrementToken()) {
				learnWords.learn(category, termAttribute.toString());
			}
		} catch (IOException e) {
			log.error(e);
		}
	}

	@Override
	public void analyze(String category, String document, LearnWords learnWords, int weight) {
		try {
			TokenStream tokenStream = analyzer.tokenStream("content", new StringReader(document));
		    CharTermAttribute termAttribute = tokenStream.getAttribute(CharTermAttribute.class);
			while (tokenStream.incrementToken()) {
				learnWords.learnWeightedWord(category, termAttribute.toString(), weight);
			}
		} catch (IOException e) {
			log.error(e);
		}
	}

	public void close() {
		analyzer.close();
	}
}
