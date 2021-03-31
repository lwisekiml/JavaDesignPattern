package templateMethod.Inheritance;
/*
4. 해결책
1) 상속 1
Motor 클래스를 상위 클래스로 정의함으로써 중복 코드 제거
- Door 클래스와의 연관관계
- motorStatus 필드
- getMotorStatus, setMotorStatus 메서드
그러나 HyundaiMotor와 LGMotor의 move 메서드는 대부분이 비슷하다.
즉, move 메서드에는 여전히 코드 중복 문제가 있다.
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
}

class HyundaiMotor extends Motor {
    public HyundaiMotor(Door door) {
        super(door);
    }

    private void moveHyundaiMotor(Direction direction){
       // Hyundai Motor를 구동시킴
    }

    public void move(Direction direction) {
        MotorStatus motorStatus = getMotorStatus();
        // 이미 이동 중이면 아무 작업을 하지 않음
        if(motorStatus == MotorStatus.MOVING) return;

        DoorStatus doorStatus = door.getDoorStatus();
        if(doorStatus == DoorStatus.OPENED) door.close();

        // Hyyndai 모터를 주어진 방향으로 이동
        moveHyundaiMotor(direction);
        // 모터 상태를 이동 중으로 변경
        setMotorStatus(MotorStatus.MOVING);
    }
}

class LGMotor extends Motor{
    public LGMotor(Door door) {
        super(door);
    }

    private void moveLGMotor(Direction direction) {
        // LG Motor를 구동시킴
    }

    public void move(Direction direction) {
        MotorStatus motorStatus = getMotorStatus();
        // 이미 이동 중이면 아무 작업을 하지 않음
        if (motorStatus == MotorStatus.MOVING) return;

        DoorStatus doorStatus = door.getDoorStatus();
        // 만약 문이 열려 있으면 우선 문을 닫음
        if (doorStatus == DoorStatus.OPENED) door.close();

        // LG 모터를 주어진 방향으로 이동시킴
        moveLGMotor(direction); // (이 부분을 제외하면 HyundaiMotor의 move 메서드와 동일)
        // 모터 상태를 이동 중으로 변경함
        setMotorStatus(MotorStatus.MOVING);
    }
}

public class TemplateMethodInheritance {
    public static void main(String[] args) {
        Door door = new Door();
        HyundaiMotor hyundaiMotor = new HyundaiMotor(door);
        hyundaiMotor.move(Direction.UP);
    }
}
