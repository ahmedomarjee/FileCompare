package com.zafar;

public class FileLoader {

	private FileResource file1, file2; 
	public FileLoader() {
		file1=new FileResource();
		file2=new FileResource();
	}

	/**
	 * @param args
	 */
	public boolean buildTree(){
		
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		FileLoader loader=new FileLoader();
		if(args.length==2)
		{
			loader.file1.setFile(args[0]);
			loader.file2.setFile(args[1]);
			loader.file1.buildTree();
			loader.file2.buildTree();
		}

	}

}
