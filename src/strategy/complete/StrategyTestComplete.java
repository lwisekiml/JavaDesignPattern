package strategy.complete;

abstract class Robot {
    private String name;
    private AttackStrategy attackStrategy;
    private  MovingStrategy movingStrategy;

    public Robot(String name) { this.name = name; }
    public String getName() { return name; }
    public void attack(){ attackStrategy.attack(); };
    public void move(){ movingStrategy.move(); };

    // 집약 관계, 전체 객체가 메모리에서 사라진다 해도 부분 객체는 사라지지 않는다.
    // setter 메서드
    public void setAttackStrategy(AttackStrategy attackStrategy){
        this.attackStrategy = attackStrategy;
    }

    public void setMovingStrategy(MovingStrategy movingStrategy){
        this.movingStrategy = movingStrategy;
    }
}

class TaekwonV extends Robot {
    public TaekwonV(String name) { super(name); }
}

class Atom extends Robot {
    public Atom(String name) { super(name); }
}

interface AttackStrategy { public void attack(); }
class MissileStrategy implements AttackStrategy {
    public void attack() {
        System.out.println("I have Missile.");
    }
}
class PunchStrategy implements AttackStrategy {
    public void attack() {
        System.out.println("I have strong punch.");
    }
}

interface MovingStrategy { public void move(); }
class FlyingStrategy implements MovingStrategy {
    public void move() {
        System.out.println("I can fly.");
    }
}
class WalkingStrategy implements MovingStrategy {
    public void move() {
        System.out.println("I can only walk.");
    }
}

public class StrategyTestComplete {
    public static void main(String[] args) {
        Robot taekwonV = new TaekwonV("TaekwonV");
        Robot atom = new Atom("Atom");

        taekwonV.setMovingStrategy(new WalkingStrategy());
        taekwonV.setAttackStrategy(new MissileStrategy());
        atom.setMovingStrategy(new FlyingStrategy());
        atom.setAttackStrategy(new PunchStrategy());

        System.out.println("My name is " + taekwonV.getName());
        taekwonV.move();
        taekwonV.attack();

        System.out.println();
        System.out.println("My name is " + atom.getName());
        atom.move();
        atom.attack();
    }
}
