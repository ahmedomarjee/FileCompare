package com.zafar;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.zip.CRC32;

public class MerkleTree {
	private Node root;
	private int depth = 0;
	private ArrayList<Node> leafQueue;// This is an ordered list having all
										// leaves of the tree from left to right

	public MerkleTree(int d) {
		depth = d;
		leafQueue = new ArrayList<Node>();
	}

	public CRC32 computeCrc(Node n){
		if(n instanceof LeafNode){
			return n.getCrc();
		}
		else
		{
			CRC32 c1=computeCrc(n.getLeftChild());
			CRC32 c2=computeCrc(n.getRightChild());
			CRC32 c3=n.getCrc();
			byte[] bytes = ByteBuffer.allocate(8).putLong(c1.getValue()).array();
			c3.update(bytes);
			bytes = ByteBuffer.allocate(8).putLong(c2.getValue()).array();
			c3.update(bytes);
			return c3;
		}
	}
	public void createEmptyTree() {
		createRecursively(root, 1);
	}
	public ArrayList<Node> getLeafQueue(){
		return leafQueue;
	}
	public void createRecursively(Node n, int level) {
		if (level == depth)
			return;
		else {
			if (level < depth - 1) {
				n.setLeftChild(new InternalNode());
				createRecursively(n.getLeftChild(), level + 1);
				n.setRightChild(new InternalNode());
				createRecursively(n.getRightChild(), level + 1);
			} else {

				n.setLeftChild(new LeafNode());
				leafQueue.add(n.getLeftChild());
				n.setRightChild(new LeafNode());
				leafQueue.add(n.getRightChild());
			}
		}
	}

}
