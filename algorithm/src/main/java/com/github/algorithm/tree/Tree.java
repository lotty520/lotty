package com.github.algorithm.tree;

import java.util.HashSet;
import java.util.Set;

/**
 * 二叉树的前序遍历：
 *
 * 递归实现
 */
public class Tree {

  private Set<TreeNode> set = new HashSet<>();

  public Set<TreeNode> inorderTraversal(TreeNode root) {
    if (root != null) {
      if (root.left != null) {
        set.addAll(inorderTraversal(root.left));
      }
      set.add(root);
      if (root.right != null) {
        set.addAll(inorderTraversal(root.right));
      }
    }
    return set;
  }

  public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
    if (root == null) {
      return null;
    }
    Set<TreeNode> all = inorderTraversal(root);
    TreeNode ans = root;
    Set<TreeNode> left = inorderTraversal(root.left);
    Set<TreeNode> right = inorderTraversal(root.right);
    if (left.contains(p) && left.contains(q)) {
      ans = lowestCommonAncestor(root.left, p, q);
    } else if (right.contains(p) && right.contains(q)) {
      ans = lowestCommonAncestor(root.right, p, q);
    }
    return ans;
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
