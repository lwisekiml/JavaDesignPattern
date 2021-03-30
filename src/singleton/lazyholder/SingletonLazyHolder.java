package singleton.lazyholder;
/*
https://jeong-pro.tistory.com/86
3. Initialization on demand holder idiom (holder에 의한 초기화)
클래스안에 클래스(Holder)를 두어 JVM의 Class loader 매커니즘과 Class가 로드되는 시점을 이용한 방법

public class Something {
    private Something() {
    }

    private static class LazyHolder {
        public static final Something INSTANCE = new Something();
    }

    public static Something getInstance() {
        return LazyHolder.INSTANCE;
    }
}

이 방법은 JVM의 클래스 초기화 과정에서 보장되는 원자적 특성을 이용하여 싱글턴의 초기화 문제에 대한 책임을 JVM에 떠넘긴다.
holder안에 선언된 instance가 static이기 때문에 클래스 로딩시점에 한번만 호출될 것이며 final을 사용해 다시 값이 할당되지 않도록 만든 방법.
* 가장 많이 사용하고 일반적인 Singleton 클래스 사용 방법이다.
*/
class Printer{
    public static class LazyHolder{
        public static final Printer printer = new Printer();
    }
    public static Printer getPrinter(){
        return LazyHolder.printer;
    }
    public void print(String str){
        System.out.println(str);
    }
}

class UserThread extends Thread {
    private int name;
    public UserThread(int name){super(String.valueOf(name));}
    public void run(){
        Printer printer = Printer.getPrinter();
        printer.print(Thread.currentThread().getName() + " print using " + printer.toString());
    }
}

public class SingletonLazyHolder {
    private static final int USER_NUM = 5;
    public static void main(String[] args) {
        UserThread[] userThreads = new UserThread[USER_NUM];
        for(int i = 0; i < USER_NUM; i++){
            userThreads[i] = new UserThread((i+1));
            userThreads[i].start();
        }
    }
}