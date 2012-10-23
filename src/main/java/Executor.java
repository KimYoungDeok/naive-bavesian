import file.LoadTrainFile;
import file.RowProcess;
import memory.Memory;
import morphemes.MorphemesAnalyst;
import morphemes.MorphemesAnalystHannaum;
import probability.NaiveBayesian;

/**
 * Description.
 *
 * @author Youngdeok Kim
 * @since 1.0
 */
public class Executor {

	public static void main(String[] args) throws Exception {
		MorphemesAnalyst morphemesAnalyst = new MorphemesAnalystHannaum();
		Memory memory = new Memory(morphemesAnalyst);

		new LoadTrainFile("documents/train", memory, new RowProcess() {
			@Override
			public void process(Memory memory, String row) throws Exception {
				String[] values = row.split("::");
				int grade = Integer.valueOf(values[0]);

				if (grade >= 8)
					memory.learning("good", values[1]);
				if (6 < grade && grade < 8)
					memory.learning("normal", values[1]);
				if (grade <= 6)
					memory.learning("bad", values[1]);
			}
		}).load();

//		new LoadTrainFile("documents/swearword", memory,
//			new RowProcess() {
//			@Override
//			public void process(Memory memory, String row) throws Exception {
//				memory.learning("bad", row, 100);
//			}
//		}).load();


		NaiveBayesian naiveBayesian = new NaiveBayesian(memory, morphemesAnalyst);
		//good
		System.out.println(naiveBayesian.classify("시간 가는줄도 모르고 정말 눈물 흘리면서 봤다."));
		System.out.println(naiveBayesian.classify("정말 재밌어요   우리나라 최초의 재난영화 인데 이민기 ㅠ 아너무 불쌍해 ㅠ ...  "));
		System.out.println(naiveBayesian.classify("CG가 좋아요"));


		//bad
		System.out.println(naiveBayesian.classify("이거는 그냥 쓰레기 졸작 영화임"));
		System.out.println(naiveBayesian.classify("너무 지루하고 억지감동 연출... 보고 후회했습니다"));
		System.out.println(naiveBayesian.classify("알바 쩐다"));

		//형태소 인식 안되는 케이스
		System.out.println(naiveBayesian.classify("야 ㅅㅂ 이걸 영화라고 만들었냐 우리나라가 챙피하다"));
		System.out.println(naiveBayesian.classify("개 같은 영화 이걸 영화라고 만들었다니 ㅉㅉ"));

		memory.close();
	}
}
