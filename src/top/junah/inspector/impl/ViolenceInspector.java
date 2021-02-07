package top.junah.inspector.impl;

import java.util.ArrayList;
import java.util.List;
import top.junah.inspector.AbstractDirtyWordInspector;

/**
 * 暴力脏词检查器
 *
 * @author junah
 */
public class ViolenceInspector extends AbstractDirtyWordInspector {

  private List<String> wordList;

  /**
   * 初始化
   */
  @Override
  public void initWords() {
    List<String> strings = readWords();
    this.wordList = strings;
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

      if (wordList == null || wordList.size() <= 0) {
        continue;
      }
      for (String string : wordList) {
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
