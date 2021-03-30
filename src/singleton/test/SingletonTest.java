package singleton.test;
/*
https://gmlwjd9405.github.io/2018/07/06/singleton-pattern.html
1. Singleton
: 전역 변수를 사용하지 않고 객체를 하나만 생성 하도록 하며,
  생성된 객체를 어디에서든지 참조할 수 있도록 하는 패턴
: 하나의 인스턴스만을 생성하는 책임이 있으면 getInstance 메서드를 통해
  모든 클라이언트에게 동일한 인스턴스를 반환하는 작업을 수행

: 싱글톤 패턴은 단 하나의 인스턴스를 생성해 사용하는 디자인 패턴
  (https://dailyheumsi.tistory.com/149?category=855210)

2. 문제점
다중 스레드에서 Printer 클래스를 이용할 때 인스턴스가 1개 이상 생성되는 경우가 발생할 수 있다.

경합 조건(Race Condition)(메모리와 같은 동일한 자원을 2개 이상의 스레드가 이용하려고 경합하는 현상) 을 발생시키는 경우
Printer 인스턴스가 아직 생성되지 않았을 때 스레드 1이 getPrinter 메서드의 if문을 실행해 이미 인스턴스가 생성되었는지 확인한다. 현재 printer 변수는 null인 상태다.
만약 스레드 1이 생성자를 호출해 인스턴스를 만들기 전 스레드 2가 if문을 실행해 printer 변수가 null인지 확인한다. 현재 printer 변수는 null이므로 인스턴스를 생성하는 생성자를 호출하는 코드를 실행하게 된다.
스레드 1도 스레드 2와 마찬가지로 인스턴스를 생성하는 코드를 실행하게 되면 결과적으로 Printer 클래스의 인스턴스가 2개 생성된다.

3. 해결책
다중 스레드 애플리케이션이 아닌 경우에는 문제 없음
1) 정적 변수에 인스턴스를 만들어 바로 초기화
class Printer{
    // static 변수에 외부에 제공할 자기 자신의 인스턴스를 만들어 초기화
    private static Printer printer = new Printer();
    private Printer(){ }
    // 자기 자신의 인스턴스를 외부에 제공
    public static Printer getPrinter(){
        return printer;
    }
    public void print(String str){
        System.out.println(str);
    }
}
static 변수
객체가 생성되기 전 클래스가 메모리에 로딩될 때 만들어져 초기화가 한 번만 실행 됨
프로그램 시작~종료까지 없어지지 않고 메모리에 계속 상주하며 클래스에서 생성된 모든 객체에서 참조할 수 있다.

2) 인스턴스를 만드는 메서드에 동기화(synchronized)
class Printer{
    // 외부에 제공할 자기 자신의 인스턴스
    private static Printer printer = null;
    private int counter = 0;
    private Printer(){ }
    // 인스턴스를 만드는 메서드 동기화(임계 구역)
    public synchronized static Printer getPrinter(){
        if(printer == null){
            printer = new Printer();
        }
        return printer;
    }
    public void print(String str){
        // 오직 하나의 스레드만 접근을 허용함(임계 구역)
        // 성능을 위해 필요한 부분만을 임계 구역으로 설정
        synchronized(this){
            counter++;
            System.out.println(str + counter);
        }
    }
}
인스턴스를 만드는 메서드를 임계 구역으로 변경
- 다중 스레드 환경에서 동시에 여러 스레드가 getPrinter메서드를 소유하는 객체에 접근하는 것을 방지
공유 변수에 접근하는 부분을 임계 구역으로 변경
- 여러 개의 스레드가 하나뿐인 counter변수 값에 동시에 접근해 갱신하는 것을 방지
getInstance()에 Lock을 하는 방식이라 속도 느림

(https://jeong-pro.tistory.com/86)
3) Initialization on demand holder idiom (holder에 의한 초기화)
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
    // 외부에 제공할 자기 자신의 인스턴스
    private static Printer printer = null;
    // 자기 자신의 인스턴스를 외부에 제공
    private Printer(){ }
    public static Printer getPrinter(){
        // static 메서드/ 변수
        // 구체적인 인스턴스에 속하는 영역이 아니고 클래스 자체에 속한다.
        // 클래스의 인스턴스를 통하지 않고서도 메서드를 실행할 수 있고 변수 참조 가능
        if(printer == null){
//            try{
//                Thread.sleep(1);
//            }catch (InterruptedException e){}
            System.out.println("Printer 생성");
            printer = new Printer();
        }
        return printer;
    }
    public void print(String str){
        System.out.println(str);
    }
}

class User {
    private String name;
    public User(String name){this.name = name;}
    public void print(){
        Printer printer = Printer.getPrinter();
        printer.print(this.name + " print using " + printer.toString());
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

public class SingletonTest {
    private static final int USER_NUM = 5;
    public static void main(String[] args) {
        User[] user = new User[USER_NUM];
        for(int i = 0; i < USER_NUM; i++){
            user[i] = new User((i+"1"));
            user[i].print();
        }
//        아래 코드 테스트시에 위 코드는 주석처리(생성자에 있는 try문 주석 풀기)
//        UserThread[] userThreads = new UserThread[USER_NUM];
//        for(int i = 0; i < USER_NUM; i++){
//            userThreads[i] = new UserThread((i+1));
//            userThreads[i].start();
//        }
    }
}
