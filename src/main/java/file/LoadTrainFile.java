package file;

import memory.Memory;

import java.io.File;
import java.util.Scanner;

/**
 * Description.
 *
 * @author Youngdeok Kim
 * @since 1.0
 */
public class LoadTrainFile {
    private File root;
    private Memory memory;
	private RowProcess row;

    public LoadTrainFile(String directory, Memory memory, RowProcess row) {
        this.memory = memory;
	    this.row = row;
	    this.root = new File(directory);
    }

    public void load() throws Exception {
        File[] files = root.listFiles();
        for (File file : files) {
            if(file.isDirectory())
                loadDirectory(file);
            if(file.isFile())
                loadFile(file.getName(), file);
        }
    }

    private void loadDirectory(File parent) throws Exception {
        File[] childFiles = parent.listFiles();
        for (File childFile : childFiles) {
            loadFile(parent.getName(), childFile);
        }
    }

    private void loadFile(String fileName, File file) throws Exception {
        Scanner scanner = new Scanner(file);
        while(scanner.hasNextLine()){
	        row.process(memory, scanner.nextLine());
        }
	    scanner.close();
    }
}
