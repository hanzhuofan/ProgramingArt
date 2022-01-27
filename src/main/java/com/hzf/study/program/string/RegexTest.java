package com.hzf.study.program.string;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author zhuofan.han
 * @date 2022/1/26
 */
public class RegexTest {
    public static void main(String[] args) {
        String fangcheng = "x+5-3+x=6+x-2";
        p(fangcheng);
        p(Arrays.toString(fangcheng.split("(?=\\+)|(?=-)|(?==)")));
        execP1();
        execP2();
        execP3();
        exec();
    }

    private static void exec() {
        p("exec***********************************");
        //true 说名\s 空白字符：[ \t\n\x0B\f\r]
        p(" \n\r\t".matches("\\s{4}"));
        //false 说明\S 非空白字符：[^\s] 所以是false
        p(" ".matches("\\S"));
        //true
        p("a_8".matches("\\w{3}"));
        //true 说明[a-z]{1,3}代表字符出现到三次，\\d+数字出现多个[&^#%]+中括号里面的四者之一出现一次或者多次
        p("\"abc888&^%\".matches(\"[a-z]{1,3}\\\\d+[&^#%]+\"):" + "abc888&^%".matches("[a-z]{1,3}\\d+[&^#%]+"));
        //或者这样写，true
        p("abc888&^%".matches("[a-z]{3}\\d{3}[&^%]+"));
        //true注意\\前面的指的是一个\
        p("\\".matches("\\\\"));
        // 注意\\p{Upper}或\\p{Lower}只能判断一个字符，而不是字符串//true 说明\p{Lower} 小写字母字符：[a-z]
        p("a".matches("\\p{Lower}"));
        //true
        p("dafdsfad".matches("\\w{2,}"));
        //false
        p("dafdsAfad".matches("[a-z]+"));
        //true
        p("dafdsfad".matches("[a-z]+"));
        ////true可以用下面方法匹配小写字符串，当然这样写多此一举了，因为[a-z]+本身就代表着多个小写字母
        p("dafdsfaddfdsf".matches("[a-z&&\\p{Lower}]+"));
        //false
        p("A".matches("\\p{Lower}"));
        //false
        p("aA".matches("\\p{Lower}"));
        //true
        p("aA".matches("\\p{Lower}\\p{Upper}"));
        //true 说明\p{Upper} 大写字母字符：[A-Z]
        p("A".matches("\\p{Upper}"));
        //false
        p("Ab".matches("\\p{Upper}"));
        //true
        p("Ab".matches("\\p{Upper}\\p{Lower}"));
        //true \p{Space} 空白字符：[ \t\n\x0B\f\r]
        p(" ".matches("\\p{Space}"));
    }

    public static final Pattern P1 = Pattern.compile(".{3}(?=a6)");

    private static void execP1() {
    /*
    .{3}(?=a)代表着这样的功能：
    查找给出的字符串中符合a前面有三个字母的这样的子串，当然取得的子串不包括（？=a)
     */
        p("execP1***********************************");
        Matcher m = P1.matcher("444a66a6");
        while (m.find()) {
            p(m.group());
        }
    }

    public static final Pattern P2 = Pattern.compile("\\d{3}(?=a)");

    private static void execP2() {
        /*
        同理\\d{3}(?=a)代表着这样的功能：
        查找给出的字符串中符合a前面有三个数字的这样的子串，当然取得的子串不包括（？=a)
        本例给出的444a66b是匹配的，得出的group是444
        而 "44d4a66b";是不匹配的，因为没有在a之前没连续的三个数字
        在例如.{3}(?=b)这样的匹配，如果用来匹配444a66b得到的字符串是a66
         */
        p("execP2***********************************");
        Matcher m = P2.matcher("444a66b");
        while (m.find()) {
            p(m.group());
        }
    }

    public static final Pattern P3 = Pattern.compile("\\d{3}(?!a)");

    private static void execP3() {
        /*
        通过上面的（？=X）下面来测试和设想一下(?!a)，api解释为
        (?！X) X，通过零宽度的负lookahead，所以对比一下很容易想到
        \\d{3}(?!a)代表着连续三个数字的后面出现的字符不是a的匹配，
        所以字符串444a666b只有一个匹配子串666
        字符串444b666b两个匹配444 666
        字符串444a666a都不匹配
         */
        p("execP3***********************************");
        Matcher m = P3.matcher("444b666c");
        while (m.find()) {
            p(m.group());
        }
    }

    public static void p(Object s) {
        System.out.println(s);
    }
}
