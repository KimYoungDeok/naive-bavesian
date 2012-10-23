/**
 * Description.
 *
 * @author Youngdeok Kim
 * @since 1.0
 */

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.kr.KoreanAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import java.io.IOException;
import java.io.StringReader;

/**
 * Description.
 *
 * @author Edward KIM
 * @since 1.0
 */
public class LuceneTest {
	public static void main(String[] args) throws IOException {
		KoreanAnalyzer analyzer = new KoreanAnalyzer();
		analyzer.setBigrammable(true);
		analyzer.setHasOrigin(true);


		TokenStream tokenStream = analyzer.tokenStream("content", new StringReader("아주 재밋서서 죽겟어... 알바들아 고생한다"));
		CharTermAttribute termAttribute = tokenStream.getAttribute(CharTermAttribute.class);

		while (tokenStream.incrementToken()) {
			String term = termAttribute.toString();
			System.out.println(term);
		}
	}
}
