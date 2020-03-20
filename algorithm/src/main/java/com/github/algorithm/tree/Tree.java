package com.github.algorithm.tree;

import java.util.ArrayList;
import java.util.List;

/**
 * 二叉树的前序遍历：
 *
 * 递归实现
 */
public class Tree {

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
