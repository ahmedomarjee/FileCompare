package com.zafar;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.zip.CRC32;

public class FileResource {

	private int numberOfLeaves = 0;
	private static String FILE_NAME;
	private MerkleTree tree;
	private long sizeInBytes;
	private int depth = 0;// considering a tree with a single node has a depth
							// of 1

	public void setFile(String string) {
		FILE_NAME = string;
		sizeInBytes = (new File(string)).length();
		numberOfLeaves = (int) (sizeInBytes / Constants.BLOCK_SIZE);
		if ((sizeInBytes % Constants.BLOCK_SIZE) > 0)
			numberOfLeaves++;
		depth = (int) (Math.ceil(Math.log10(numberOfLeaves) / Math.log10(2)) + 1);
		tree = new MerkleTree(depth);
		tree.createEmptyTree();
	}

	public void buildTree() {
		FileInputStream in = null;
		FileOutputStream out = null;
		int c;
		int bytesRead = 0;
		int index = 0;
		ArrayList<Node> leafQueue=tree.getLeafQueue();
		Node leaf = leafQueue.get(index);
		CRC32 crc = leaf.getCrc();
		byte[] bytes = new byte[1024];
		try {
			in = new FileInputStream(FILE_NAME);

			while (((c = in.read())) != -1) {
				bytes[bytesRead] = (byte) c;
				bytesRead++;

				if (bytesRead == Constants.BLOCK_SIZE) {
					crc.update(bytes);
					index++;
					leaf = leafQueue.get(index);
					crc = leaf.getCrc();
					bytesRead = 0;
					bytes = new byte[1024];
				}
			}
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

	}
}
