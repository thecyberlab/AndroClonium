package usask.cyberlab.smalisimulator;

import java.util.ArrayList;
import java.util.Stack;

public class TestClass {
    public static int si = 20;
    public static int[] spa = {11,12,13};
    public static Object so = new ArrayList<>();
    public static Object[] soa = {new Object(), new Stack<>()};
    public static char sch = '#';
    public static String ss = "Hola";
    public static short ssh = 23;
    public static boolean sb = true;
    public static float sf = 23.454f;
    public static Object sn = null;

    public char c = '@';
    public Class cls = this.getClass();
    public Class[] clsArray = {cls, cls};
    public Object nullObj = null;


    public int test0(){
        return 55;
    }

    public String test1(String s){
        return s;
    }

    public Object test2(){
        return new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
    }

    public void test3(){
        System.out.println("Testing ,,,,,,");
    }

    public Object test4(){
        return null;
    }

    static public int stest0(){
        return 111;
    }

    static public String stest1(String s){
        return "hello " + s;
    }

    static public Object stest2(){
        return new int[]{11, 12, 13, 14, 15};
    }

    static public void stest3(){
        System.out.println("Static Testing.....");
    }

    static public Object stest4(){
        return null;
    }

}
