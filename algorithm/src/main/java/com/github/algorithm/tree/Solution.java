package com.github.algorithm.tree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

class Solution {

  /**
   * 给你一个二叉树，请你返回其按 层序遍历 得到的节点值。 （即逐层地，从左到右访问所有节点）。
   * 示例：
   * 二叉树：[3,9,20,null,null,15,7],
   *
   * 3
   * / \
   * 9  20
   * /  \
   * 15   7
   *
   * 返回其层次遍历结果：
   *
   * [
   * [3],
   * [9,20],
   * [15,7]
   * ]
   */
  public List<List<Integer>> levelOrder(TreeNode root) {
    List<List<Integer>> ans = new ArrayList<>();
    Queue<TreeNode> queue = new LinkedList<>();
    Queue<TreeNode> leafQueue = new LinkedList<>();
    if (root != null) {
      List<Integer> temp = new ArrayList<>();
      temp.add(root.val);
      ans.add(temp);
      queue.add(root);
      while (!queue.isEmpty()) {
        TreeNode poll = queue.poll();
        if (poll.left != null) {
          leafQueue.add(poll.left);
        }
        if (poll.right != null) {
          leafQueue.add(poll.right);
        }
        if (queue.isEmpty() && !leafQueue.isEmpty()) {
          List<Integer> temp1 = new ArrayList<>();
          while (!leafQueue.isEmpty()) {
            TreeNode node = leafQueue.poll();
            temp1.add(node.val);
            queue.add(node);
          }
          ans.add(temp1);
          leafQueue.clear();
        }
      }
    }
    return ans;
  }

  public boolean hasPathSum(TreeNode root, int sum) {
    if (root == null) {
      return false;
    }
    Stack<TreeNode> stack = new Stack<>();
    Set used = new HashSet();
    int total = root.val;
    stack.add(root);
    used.add(root);
    while (!stack.isEmpty() && stack.peek() != null) {
      TreeNode peek = stack.peek();
      if (peek.left != null && !used.contains(peek.left)) {
        stack.add(peek.left);
        used.add(peek.left);
        total += peek.left.val;
      } else if (peek.right != null && !used.contains(peek.right)) {
        stack.add(peek.right);
        used.add(peek.right);
        total += peek.right.val;
      } else if (total == sum && peek.left == null && peek.right == null) {
        return true;
      } else {
        stack.pop();
        total -= peek.val;
      }
    }
    return false;
  }

  /**
   * 从中序与后序遍历序列构造二叉树
   *
   * @param inorder 中序
   * @param postorder 后序
   */
  public TreeNode buildTree1(int[] inorder, int[] postorder) {

    int orderLen = inorder.length;
    int postLen = postorder.length;
    if (inorder.length==0){
      return null;
    }
    // 根节点
    int rootValue = postorder[postLen - 1];
    TreeNode root = new TreeNode(rootValue);
    if (postLen==1){
      return root;
    }
    int index = -1;
    for (int i = 0; i < orderLen; i++) {
      if (inorder[i] == rootValue){
        index = i;
      }
    }
    if (index<0){
      return root;
    }
    int[] inorderLeft = Arrays.copyOfRange(inorder, 0, index);
    int[] inorderRight = Arrays.copyOfRange(inorder, index+1, orderLen);

    int[] postLeft = Arrays.copyOfRange(postorder, 0, index);
    int[] postRight = Arrays.copyOfRange(postorder, index, orderLen-1);

    root.left = buildTree1(inorderLeft, postLeft);
    root.right = buildTree1(inorderRight, postRight);
    return root;
  }

  /**
   * 前序遍历 preorder = [3,9,20,15,7]
   * 中序遍历 inorder = [9,3,15,20,7]
   * 返回如下的二叉树：
   *
   * 3
   * / \
   * 9  20
   * /  \
   * 15   7
   */
  public TreeNode buildTree(int[] preorder, int[] inorder) {
    int len = preorder.length;
    if (inorder.length == 0) {
      return null;
    }
    // 根节点
    int rootValue = preorder[0];
    TreeNode root = new TreeNode(rootValue);
    if (len == 1) {
      return root;
    }
    int index = -1;
    for (int i = 0; i < len; i++) {
      if (inorder[i] == rootValue) {
        index = i;
      }
    }
    if (index < 0) {
      return root;
    }
    int[] inorderLeft = Arrays.copyOfRange(inorder, 0, index);
    int[] inorderRight = Arrays.copyOfRange(inorder, index + 1, len);

    int[] preLeft = Arrays.copyOfRange(preorder, 1, index+1);
    int[] preRight = Arrays.copyOfRange(preorder, index+1, len);

    root.left = buildTree(preLeft,inorderLeft);
    root.right = buildTree(preRight,inorderRight);
    return root;
  }

  public static class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    TreeNode(int x) {
      val = x;
    }
  }
}