package templateMethod.complete;
/*
2) 상속 2
move 메서드 같이 부분 중복되는 경우 상속을 활용해 코드 중복을 피할 수 있다.

- move 메서드에서 moveHyundaiMotor 메서드와 moveLGMotor 메서드를 호출하는 부분만 다름
  -> moveHyundaiMotor 메서드와 moveLGMotor 메서드는 기능(모터 구동을 실제로 구현) 면에서는 동일

방법) move 메서드를 상위 Motor 클래스로 이동 -> moveHyundaiMotor 메서드와 moveLGMotor 메서드의 호출 부분을 하위 클래스에서 오버라이드

- Motor 클래스의 move 메서드는 HyundaiMotor와 LGMotor에서 동일한 기능을 구현하면서 각 하위 클래스에서 구체적으로 정의할 필요가 있는 부분, 즉 moveMotor메서드 부분만 각 하위 클래스에서 오버라이드되도록 한다.
- Template Method 패턴을 이용하면 전체적으로는 동일하면서 부분적으로는 다른 구문으로 구성된 메서드의 코드 중복을 최소화할 수 있다.

“AbstractClass”: Motor 클래스
“ConcreteClass”: HyundaiMotor 클래스와 LGMotor 클래스
“템플릿 메서드”: Motor 클래스의 move 메서드
“primitive 또는 hook 메서드”: move 메서드에서 호출되면서 하위 클래스에서 오버라이드될 필요가 있는 moveMotor 메서드
*/
enum DoorStatus { CLOSED, OPENED }
enum MotorStatus {MOVING, STOPPED }
enum Direction {UP, DOWN }

class Door{
    private DoorStatus doorStatus;
    public Door(){ doorStatus = DoorStatus.CLOSED; }
    public DoorStatus getDoorStatus(){ return doorStatus; }
    public void close(){ doorStatus = DoorStatus.CLOSED; }
    public void open(){ doorStatus = DoorStatus.OPENED; }
}

abstract class Motor {
    protected Door door;
    private MotorStatus motorStatus;

    public Motor(Door door) {
        this.door = door;
        motorStatus = MotorStatus.STOPPED;
    }

    public MotorStatus getMotorStatus() { return motorStatus; }
    protected void setMotorStatus(MotorStatus motorStatus) { this.motorStatus = motorStatus; }

    public void move(Direction direction) {
        MotorStatus motorStatus = getMotorStatus();
        // 이미 이동 중이면 아무 작업을 하지 않음
        if(motorStatus == MotorStatus.MOVING) return;

        DoorStatus doorStatus = door.getDoorStatus();
        if(doorStatus == DoorStatus.OPENED) door.close();

        // Hyyndai 모터를 주어진 방향으로 이동
        moveMotor(direction);
        // 모터 상태를 이동 중으로 변경
        setMotorStatus(MotorStatus.MOVING);
    }

    protected abstract void moveMotor(Direction direction);
}

class HyundaiMotor extends Motor {
    public HyundaiMotor(Door door) { super(door); }

    protected void moveMotor(Direction direction){
        // Hyundai Motor를 구동시킴
    }
}

class LGMotor extends Motor{
    public LGMotor(Door door) {
        super(door);
    }

    protected void moveMotor(Direction direction) {
        // LG Motor를 구동시킴
    }
}

public class TemplateMethodComplete {
    public static void main(String[] args) {
        Door door = new Door();
        HyundaiMotor hyundaiMotor = new HyundaiMotor(door);
        hyundaiMotor.move(Direction.UP);
    }
}