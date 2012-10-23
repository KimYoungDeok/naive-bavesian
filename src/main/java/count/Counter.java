package count;

import java.util.HashMap;
import java.util.Set;

/**
 * Description.
 *
 * @author Youngdeok Kim
 * @since 1.0
 */
public class Counter {
	private HashMap<String, Count> countMap;
	private int total;

	public Counter() {
		this.countMap = new HashMap<String, Count>();
	}

	public void count(String category) {
		if (!countMap.containsKey(category)) {
			Count count = new Count(category);
			countMap.put(count.getName(), count);
		}
		countMap.get(category).increment();
		total++;
	}

	public void count(String category, int weight) {
		if (!countMap.containsKey(category)) {
			Count count = new Count(category);
			countMap.put(count.getName(), count);
		}
		countMap.get(category).increment(weight);
		total += weight;
	}

	public int sizeForCategory(String category) {
		if (!countMap.containsKey(category))
			return 0;
		return countMap.get(category).getCount();
	}

	public int size() {
		return total;
	}

	public Set<String> getkeysOfCategory() {
		return countMap.keySet();
	}


}
