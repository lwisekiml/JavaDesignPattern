package strategy.test;
/*
https://gmlwjd9405.github.io/2018/07/06/strategy-pattern.html
1. Strategy
: 행위를 클래스로 캡슐화해 동적으로 행위를 자유롭게 바꿀 수 있게 해주는 패턴
 - 같은 문제를 해결하는 여러 알고리즘이 클래스별로 캡슐화되어 있고 이들이 필요할 때
   교체할 수 있도록 함으로써 동일한 문제를 다른 알고리즘으로 해결할 수 있게 하는 디자인 패턴

: 전략을 쉽게 바꿀 수 있도록 해주는 디자인 패턴이다.
전략이란
어떤 목적을 달성하기 위해 일을 수행하는 방식, 비즈니스 규칙, 문제를 해결하는 알고리즘 등
특히 게임 프로그래밍에서 게임 캐릭터가 자신이 처한 상황에 따라 공격이나 행동하는 방식을 바꾸고 싶을 때 스트래티지 패턴은 매우 유용하다.

: 행동/전략 등 동일계열의 알고리즘들을 인터페이스-캡슐화하고, 알고리즘들을 컴포지션(위임 형태로) 가지는 패턴

(https://dailyheumsi.tistory.com/209?category=855210)
: 동/전략 등 동일계열의 알고리즘들을 인터페이스-캡슐화하고, 알고리즘들을 컴포지션(위임 형태로) 가지는 패턴

2. 역할이 수행하는 작업
Strategy
 - 인터페이스나 추상 클래스로 외부에서 동일한 방식으로 알고리즘을 호출하는 방법을 명시

ConcreteStrategy
 - 스트래티지 패턴에서 명시한 알고리즘을 실제로 구현한 클래스

Context
 - 스트래티지 패턴을 이용하는 역할을 수행한다.
필요에 따라 동적으로 구체적인 전략을 바꿀 수 있도록 setter 메서드(‘집약 관계’)를 제공한다.

(참고)
 - 행위(Behavioral) 패턴
객체나 클래스 사이의 알고리즘이나 책임 분배에 관련된 패턴
한 객체가 혼자 수행할 수 없는 작업을 여러 개의 객체로 어떻게 분배하는지, 또 그렇게 하면서도 객체 사이의 결합도를 최소화하는 것에 중점을 둔다.

 - 집약 관계
참조값을 인자로 받아 필드를 세팅하는 경우
전체 객체의 라이프타임과 부분 객체의 라이프 타임은 독립적이다.
즉, 전체 객체가 메모리에서 사라진다 해도 부분 객체는 사라지지 않는다.

3. 문제점
1) 기존 로봇의 공격과 이동 방법을 수정하는 경우
 - Atom이 날 수는 없고 오진 걷게만 만들고 싶다면?
 - TaekwonV를 날게 하려면?
public class Atom extends Robot {
  public Atom(String name) { super(name); }
  public void attack() { System.out.println("I have strong punch."); }
  public void move() { System.out.println("I can only walk."); } // 수정
}

새로운 기능으로 변경하려고 기존 코드의 내용을 수정해야 하므로 OCP에 위배된다.
또한 TaekwonV와 Atom의 move() 메서드의 내용이 중복된다. 이런 중복 상황은 많은 문제를 야기하는 원인이 된다.
만약 걷는 방식에 문제가 있거나 새로운 방식으로 수정하려면 모든 중복 코드를 일관성있게 변경해야만 한다.

2) 새로운 로봇을 만들어 기존의 공격(attack) 또는 이동 방법(move)을 추가/수정하는 경우
새로운 로봇으로 Sungard를 만들어 TaekwonV의 미사일 공격 기능을 추가하려면?
public class Sungard extends Robot {
  public Sungard(String name) { super(name); }
  public void attack() { System.out.println("I have Missile."); } // 중복
  public void move() { System.out.println("I can only walk."); }
}
TaekwonV와 Sungard 클래스의 attack() 메서드의 내용이 중복된다.

현재 시스템의 캡슐화의 단위가 ‘Robot’ 자체이므로 로봇을 추가하기는 매우 쉽다.
그러나 새로운 로봇인 ‘Sungard’에 기존의 공격 또는 이동 방법을 추가하거나 변경하려고 하면 문제가 발생한다.

4. 해결책
무엇이 변화되었는지 찾은 후 이를 클래스로 캡슐화
- 원인 : 로봇의 이동방식과 공격 방식의 변화
- 캡슐화하려면 외분에서 구체적인 이동 방식과 공격 방식을 담은 구체적인 클래스들을 은닉해야 한다.
  -> 공격과 이동을 위한 인터페이스를 각가 만들고 이들을 실현한 클래스를 만들어야 함

- Robot 클래스가 이동 기능과 공격 기능을 이용하는 클라이언트 역할을 수행
  -> 구체적인 이동, 공격 방식이 MovingStrategy와 AttackStrategy 인터페이스에 의해 캡슐화되어 있다.
  -> 이 인터페이스들이 일종의 방화벽 역할을 수행해 Robot 클래스의 변경을 차단

- 스트래티지 패턴을 이용하면 새로운 기능의 추가(새로운 이동, 공격 기능)가 기존의 코드에 영향을 미치지 못하게 하므로 OCP를 만족 하는 설계가 된다.
  -> 외부에서 로봇 객체의 이동, 공격 방식을 임의대로 바꾸도록 해주는 setter 메서드가 필요
  -> setMovingStrategy, setAttackStrategy
  -> 상속 대신 ‘집약 관계’를 이용했기 때문에 변경 가능

(참고)
https://blog.naver.com/PostView.nhn?blogId=1ilsang&logNo=221119257326&parentCategoryNo=&categoryNo=88&viewDate=&isShowPopularPosts=false&from=postList
*/

abstract class Robot {
    private String name;
    public Robot(String name) { this.name = name; }
    public String getName() { return name; }
    // 추상 메서드
    public abstract void attack();
    public abstract void move();
}

class TaekwonV extends Robot {
    public TaekwonV(String name) { super(name); }
    public void attack() { System.out.println("I have Missile."); }
    public void move() { System.out.println("I can only walk."); }
}

class Atom extends Robot {
    public Atom(String name) { super(name); }
    public void attack() { System.out.println("I have strong punch."); }
    public void move() { System.out.println("I can fly."); }
}

class Sungard extends Robot {
    public Sungard(String name) { super(name); }
    public void attack() { System.out.println("I have Missile."); } // 중복
    public void move() { System.out.println("I can only walk."); }
}

public class StrategyTest {
    public static void main(String[] args) {
        Robot taekwonV = new TaekwonV("TaekwonV");
        Robot atom = new Atom("Atom");

        System.out.println("My name is " + taekwonV.getName());
        taekwonV.move();
        taekwonV.attack();

        System.out.println();
        System.out.println("My name is " + atom.getName());
        atom.move();
        atom.attack();
    }
}