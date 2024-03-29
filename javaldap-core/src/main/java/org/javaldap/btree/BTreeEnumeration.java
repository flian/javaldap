package org.javaldap.btree;

import java.util.Enumeration;
import java.util.Vector;

public class BTreeEnumeration implements Enumeration {
	private BTree bTree = null;
	private int btnodeLevel=0;

	private BTNode currentNode = null;
	private int currentItem = 0;
	private boolean enumKeys = false;
	private boolean moreElements = true;
	private Vector nodesSeen = null;
	private Object nextElem = null;

	public BTreeEnumeration(BTree btree, boolean getKeys) {
		bTree = btree;
		currentNode = btree.root;
		enumKeys = getKeys;
		nodesSeen = new Vector();
		nextElem = getNext();
	}
	public Object getNext() {

		Object element = null;
		boolean viewNode = false;

		if (!moreElements) return null;

		while (!currentNode.isLeaf || currentItem >= currentNode.nKey) {
			boolean newChild = false;
			if (!currentNode.isLeaf) {
				for (int i = 0; i <= currentNode.nKey; i++) {
					if (!nodesSeen.contains(currentNode.getBTNode(i))) {
						if (i > 0) {
							if (enumKeys == true) {
								element = currentNode.getKeyNode(i-1).getKey();
							} else {
								element = currentNode.getKeyNode(i-1).getObj();
							}
						}
						currentNode = currentNode.getBTNode(i);
						currentItem = 0;
						if (i > 0) return element;
						newChild = true;
						break;
					}
				}
			}
			if (!newChild) {
				nodesSeen.addElement(currentNode);
				currentNode = currentNode.parent;
				if (currentNode == null) {
					moreElements = false;
					return null;
				}
				currentItem = 0;
			}
		}

		if (currentItem < currentNode.nKey) {
			if (enumKeys == true) {
				element = currentNode.getKeyNode(currentItem).getKey();
			} else {
				element = currentNode.getKeyNode(currentItem).getObj();
			}
			currentItem++;
			if (currentNode.parent == null && currentItem >= currentNode.nKey) {
				moreElements = false;
			}
			return element;
		}

		System.out.println("Odd...we shouldn't get here...");
		moreElements = false;
		return null;
	}
	public boolean hasMoreElements() {
		if (nextElem == null) {
			return false;
		}
		return true;
	}
	public Object nextElement() {
		Object retElem = nextElem;
		nextElem = getNext();
		return retElem;
	}
	public Object nextElementold() {

		Object element = null;
		if (currentItem < currentNode.nKey) {
			if (enumKeys == true) {
				element = currentNode.getKeyNode(currentItem).getKey();
			} else {
				element = currentNode.getKeyNode(currentItem).getObj();
			}
			currentItem++;
			return element;
		} else {
			if (!currentNode.isLeaf) {
				//			currentNode
				//			btnodeLevel++;
				//			for (i = 0; i <= btnode.nKey; i++)
				//				showBTNode(btnode.getBTNode(i));
				//			btnodeLevel--;
			}
			else {
				moreElements = false;
			}
		}
		return null;
	}
	public Object nextElementold2() {

		Object element = null;
		boolean viewNode = false;
		while (!viewNode && moreElements && (currentItem == 0 || currentItem >= currentNode.nKey) ) {
			boolean newChild = false;
			if (currentNode.isLeaf && currentItem < currentNode.nKey) {
				viewNode = true;
			} else {
				if (currentItem >= currentNode.nKey) {
					nodesSeen.addElement(currentNode);
				}
				if (!currentNode.isLeaf) {
					for (int i = 0; i <= currentNode.nKey;i++) {
						System.out.println("child# " + i);
						if (!nodesSeen.contains(currentNode.getBTNode(i))) {
							currentNode = currentNode.getBTNode(i);
							currentItem = 0;
							newChild = true;
							//viewNode = true;
							break;
						}
					}
					if (!newChild && currentItem < currentNode.nKey) {
						viewNode = true;
					}
				}
			}
			if (!viewNode && !newChild) {
				System.out.println("Getting Parent");
				currentNode = currentNode.parent;
				if (currentNode == null) {
					moreElements = false;
					return null;
				}
				currentItem = 0;
			}
		}

		if (currentItem < currentNode.nKey) {
			if (enumKeys == true) {
				element = currentNode.getKeyNode(currentItem).getKey();
			} else {
				element = currentNode.getKeyNode(currentItem).getObj();
			}
			currentItem++;
			if (currentNode.parent == null && currentItem >= currentNode.nKey) {
				moreElements = false;
			}
			return element;
		}
		//	else {
		//		if (!currentNode.isLeaf) {
		//			currentNode
		//			btnodeLevel++;
		//			for (i = 0; i <= btnode.nKey; i++)
		//				showBTNode(btnode.getBTNode(i));
		//			btnodeLevel--;
		//		} else {
		//			moreElements = false;
		//		}
		//	}
		System.out.println("Odd...we shouldn't get here...");
		moreElements = false;
		return null;
	}
	void showBTNode(BTNode btnode) {
		//System.out.println("\nNODE:" + btnode + " Level" + btnodeLevel);
		int i = 0;
		for (i = 0; i < btnode.nKey; i++) {
			//System.out.println("Obj=" + btnode.getKeyNode(i).getObj() + " | i=" + i + " | refBTNode:" + btnode.getBTNode(i));
		}
		//System.out.println("                   | refBTNode:" + btnode.getBTNode(i));

		if (!btnode.isLeaf) {
			btnodeLevel++;
			for (i = 0; i <= btnode.nKey; i++)

				//check next sub node
				showBTNode(btnode.getBTNode(i));
			btnodeLevel--;
		}
	}
	void showBTree(BTree bTree) {
		showBTNode(bTree.root);
	}
}
