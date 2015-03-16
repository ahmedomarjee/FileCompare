package com.zafar;

import java.util.zip.CRC32;

public class Node {
	private Node leftChild, rightChild;
	private CRC32 crc=new CRC32();
	public void  setLeftChild(Node n){
		leftChild=n;
	}
	public void setRightChild(Node n) {
		rightChild=n;
		
	}
	public Node getLeftChild() {
		return leftChild;
	}
	public Node getRightChild() {
		return rightChild;
	}
	public CRC32 getCrc() {
		return crc;
	}
	public void setCrc(CRC32 crc) {
		this.crc = crc;
	}
	
	
}
