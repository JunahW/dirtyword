package top.junah.inspector;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;


public abstract class AbstractDirtyWordInspector {


  /**
   * 读取词
   */
  public List<String> readWords() {
    ArrayList<String> words = new ArrayList<>();
    FileReader fileReader = null;
    BufferedReader reader = null;
    try {
      fileReader = new FileReader("resource/dirtywords.txt");
      reader = new BufferedReader(fileReader);
      String word;
      while ((word = reader.readLine()) != null) {
        if (word == "" || word.length() <= 1) {
          continue;
        }
        words.add(word);
      }
    } catch (Exception e) {
      e.printStackTrace();
      throw new RuntimeException("初始化失败");
    } finally {
      if (fileReader != null) {
        try {
          fileReader.close();
        } catch (Exception e) {
          e.printStackTrace();
        }

      }

      if (reader != null) {
        try {
          reader.close();
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    }

    return words;
  }


  /**
   * 初始化
   */
  public abstract void initWords();

  /**
   * 检查词
   *
   * @param source
   * @return
   */
  public abstract List<? extends Object> checkWords(String source);

}
