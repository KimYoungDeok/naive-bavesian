package file;

import memory.Memory;

/**
 * Description.
 *
 * @author Youngdeok Kim
 * @since 1.0
 */
public interface RowProcess {
	public void process(Memory memory, String row) throws Exception;
}
