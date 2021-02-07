package top.junah.inspector.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import top.junah.inspector.AbstractDirtyWordInspector;

/**
 * 自持扩词脏词检查器
 *
 * @author junah
 */
public class CrossWordInspector extends AbstractDirtyWordInspector {

  private Map<Character, List<String>> wordListMap;


  /**
   * 初始化
   */
  @Override
  public void initWords() {
    Map<Character, List<String>> wordMap = new HashMap<>();
    List<String> wordList = readWords();
    for (String string : wordList) {
      if (string == null || string.length() <= 0) {
        continue;
      }
      char first = string.charAt(0);
      List<String> strings = wordMap.computeIfAbsent(first, k -> new ArrayList<>());
      strings.add(string);
    }
    wordListMap = wordMap;
  }

  /**
   * 检查词
   *
   * @param source
   * @return
   */
  @Override
  public List<String> checkWords(String source) {
    List<String> dirtyWord = new ArrayList<>();
    char[] chars = source.toCharArray();
    int sourceLength = chars.length;
    for (int i = 0; i < chars.length; i++) {
      char c = chars[i];
      List<String> strings = wordListMap.get(c);

      if (strings == null || strings.size() <= 0) {
        continue;
      }
      for (String string : strings) {
        char[] dirtyChars = string.toCharArray();
        if (string.length() > sourceLength - i) {
          continue;
        }
        boolean success = true;
        for (int j = 0; j < dirtyChars.length; j++) {
          char dirtyChar = dirtyChars[j];
          if (dirtyChar != chars[i + j]) {
            success = false;
            break;
          }
        }
        if (success) {
          i = i + dirtyChars.length;
          dirtyWord.add(string);
        }
      }
    }
    return dirtyWord;
  }

}
