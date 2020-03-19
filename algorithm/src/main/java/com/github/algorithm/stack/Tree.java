package com.github.algorithm.stack;

import java.util.ArrayList;
import java.util.List;

public class Tree {

  public static void main(String[] args) {
    Tree tree = new Tree();
  }

  public List<Integer> inorderTraversal(TreeNode root) {
    List<Integer> list = new ArrayList<>();
    if (root != null) {
      if (root.left != null) {
        list.addAll(inorderTraversal(root.left));
      }
      list.add(root.val);
      if (root.right != null) {
        list.addAll(inorderTraversal(root.right));
      }
    }
    return list;
  }

  public class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    TreeNode(int x) {
      val = x;
    }
  }
}
