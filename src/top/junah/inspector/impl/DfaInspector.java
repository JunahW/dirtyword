package top.junah.inspector.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import top.junah.inspector.AbstractDirtyWordInspector;

/**
 * DFA检查器
 *
 * @author junah
 */
public class DfaInspector extends AbstractDirtyWordInspector {

  /**
   * 根节点
   */
  private TreeNode rootNode = new TreeNode();


  @Override
  public void initWords() {
    List<String> strings = readWords();
    for (String words : strings) {
      TreeNode tempNode = rootNode;
      for (int i = 0; i < words.length(); i++) {
        Character c = words.charAt(i);
        TreeNode node = tempNode.getSubNode(c);
        if (node == null) {
          node = new TreeNode();
          tempNode.addSubNode(c, node);
        }
        tempNode = node;
        //到最后一个字符
        if (i == words.length() - 1) {
          tempNode.setEnd(true);
        }
      }
    }
  }

  @Override
  public List<? extends Object> checkWords(String source) {
    if (source == null || source.length() <= 0) {
      return Collections.emptyList();
    }
    List<String> dirtyWord = new ArrayList<>();

    TreeNode tempNode = rootNode;
    int begin = 0;
    int position = 0;

    while (position < source.length()) {
      Character c = source.charAt(position);
      tempNode = tempNode.getSubNode(c);

      //如果匹配失败
      if (tempNode == null) {
        //说明以begin起头的那一段不存在非法词汇
        begin++;
        position = begin;
        tempNode = rootNode;
        continue;
      } else if (tempNode.isEnd()) {
        position++;
        dirtyWord.add(source.substring(begin, position));
        begin = position;
        tempNode = rootNode;
      } else {
        position++;
      }
    }

    return dirtyWord;
  }

  /**
   * 树节点
   */
  private class TreeNode {

    //是否最后一个字
    private boolean isEnd = false;

    //子节点
    private Map<Character, TreeNode> subNodes = new HashMap<>();

    public void addSubNode(Character key, TreeNode node) {
      subNodes.put(key, node);
    }

    public TreeNode getSubNode(Character key) {
      return subNodes.get(key);
    }

    public boolean isEnd() {
      return isEnd;
    }

    public void setEnd(boolean end) {
      isEnd = end;
    }
  }
}
