package com.github.lotty;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
  @Test
  public void addition_isCorrect() {
    assertEquals(4, 2 + 2);
  }

  @Test
  public void box() {
    Integer a = 1;
    Integer b = 2;
    Integer c = 3;

    Integer d = 3;

    Long l = 128L;
    Long ll = 128L;

    Float f1 = 127f;
    Float f2 = 127f;

    Integer s1 = 128;
    Integer s2 = 128;
    Integer s3 = 129;

    Integer x = 128;
    Integer y = 129;

    String ss1 = "this is str";
    String ss2 = "this is str";
    String ss3 = new String("this is str");
    String ss4 = new String("this is str").intern();

    System.out.println("ss2 == ss1 ? " + (ss2 == ss1));
    System.out.println("ss3 == ss1 ? " + (ss3 == ss1));
    System.out.println("ss4 == ss1 ? " + (ss4 == ss1));


    //System.out.println("a==b ? " + (a == b));
    //System.out.println("(x + a) ==y ? " + ((x + a) == y));


    //System.out.println("f1==f2 ? " + (f1 == f2));

    //
    //System.out.println(c == d);
    //System.out.println(c.equals(d));
    //
    //System.out.println(c == (a + b));
    //System.out.println(c.equals(a + b));
    //
    //System.out.println((a + b) == l);
    //
    //
    //System.out.println(c.equals(l));
    //
    //System.out.println(s1 == s2);
    //System.out.println(s3 == (a + s1));
    //
    //System.out.println(ss2 == ss1);
    //System.out.println(ss3 == ss1);
    //System.out.println(ss4 == ss1);
    //System.out.println(l == ll);
    //System.out.println(f == ff);


  }
}