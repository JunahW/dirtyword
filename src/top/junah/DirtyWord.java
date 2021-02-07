package top.junah;

import java.util.List;
import top.junah.inspector.AbstractDirtyWordInspector;
import top.junah.inspector.impl.DfaInspector;

public class DirtyWord {

  public static void main(String[] args) throws Exception {
    String source = "小明是个大坏蛋，坏蛋就是爱说脏话";
//    AbstractDirtyWordInspector inspector = new CrossWordInspector();
    AbstractDirtyWordInspector inspector = new DfaInspector();
//    AbstractDirtyWordInspector inspector = new ViolenceInspector();

    inspector.initWords();

    long start = System.currentTimeMillis();
    List<? extends Object> results = inspector.checkWords(source);
    long end = System.currentTimeMillis();
    System.out.println("花费时间为：" + (end - start));
    System.out.println("脏词的数量为：" + results.size());
    System.out.println(results);
  }
}
