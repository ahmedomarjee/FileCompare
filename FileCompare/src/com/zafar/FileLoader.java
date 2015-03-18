package com.zafar;

public class FileLoader {

	private FileResource file1, file2;

	public FileLoader() {
		file1 = new FileResource();
		file2 = new FileResource();
	}

	public static void main(String[] args) {

		FileLoader loader = new FileLoader();
		if (args.length == 2) {
			if (true == loader.file1.setFile(args[0])
					&& true == loader.file2.setFile(args[1])) {
				loader.file1.buildTree();
				loader.file2.buildTree();
				loader.file1.computeCrc();
				loader.file2.computeCrc();
				System.out.println("Comparing now");
				System.out.println("Matches: " + loader.compareFiles());
			}
		}
		else
			System.out.println("Give only two argments as file names, no less no more.");

	}

	private boolean compareFiles() {

		if (file1.getSize() <= file2.getSize())
			return file1.compareTrees(file2);
		else
			return file2.compareTrees(file1);
	}

}
