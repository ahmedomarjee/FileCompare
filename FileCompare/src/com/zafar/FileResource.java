package com.zafar;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.zip.CRC32;

public class FileResource {

	private int numberOfLeaves = 0;
	private String FILE_NAME;
	private MerkleTree tree;
	private long sizeInBytes;
	private int depth = 0;// considering a tree with a single node has a depth
							// of 1
	public long getSize(){
		return sizeInBytes;
	}
	public boolean setFile(String string) {
		FILE_NAME = string;
		sizeInBytes = (new File(string)).length();
		if(sizeInBytes==0)
		{
			System.out.println("Either the given path is incorrect or file is empty.");
			return false;
		}
		numberOfLeaves = (int) (sizeInBytes / Constants.BLOCK_SIZE);
		if ((sizeInBytes % Constants.BLOCK_SIZE) > 0)
			numberOfLeaves++;
		depth = (int) (Math.ceil(Math.log10(numberOfLeaves) / Math.log10(2)) + 1);
		if(depth==1)
			depth++; //minimum depth is 2
		tree = new MerkleTree(depth);
		tree.createEmptyTree();
		System.out.println("Empty tree created for "+FILE_NAME);
		return true;
	}

	public void buildTree() {
		FileInputStream in = null;
		FileOutputStream out = null;
		int c;
		int bytesRead = 0;
		int index = 0;
		ArrayList<LeafNode> leafQueue=tree.getLeafQueue();
		LeafNode leaf = leafQueue.get(index);
		CRC32 crc = leaf.getCrc();
		leaf.setStartByteIndex(0);
		byte[] bytes = new byte[Constants.BLOCK_SIZE];
		try {
			in = new FileInputStream(FILE_NAME);

			while (((c = in.read())) != -1) {
				bytes[bytesRead] = (byte) c;
				bytesRead++;

				if (bytesRead == Constants.BLOCK_SIZE) {
					crc.update(bytes);
					leaf.setEndByteIndex((index+1)*Constants.BLOCK_SIZE);
					index++;
					leaf = leafQueue.get(index);
					leaf.setStartByteIndex(index*Constants.BLOCK_SIZE +1);
					crc = leaf.getCrc();
					bytesRead = 0;
					bytes = new byte[Constants.BLOCK_SIZE];
				}
			}
			leaf.setEndByteIndex(index*Constants.BLOCK_SIZE + bytesRead);
			crc.update(bytes, 0, bytesRead);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			try {
				if (in != null) {
					in.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		System.out.println("Tree filled for "+FILE_NAME);

	}

	public void computeCrc() {
		tree.computeCrc();
		
	}
	public MerkleTree getTree(){
		return tree;
	}
	public boolean compareTrees(FileResource file2) {
		MerkleTree tree2=file2.getTree();
		return tree.compareTo(tree2, FILE_NAME, file2.FILE_NAME);
	}
}
