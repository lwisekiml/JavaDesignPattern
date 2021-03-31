package templateMethod.test;
/*
https://gmlwjd9405.github.io/2018/07/13/template-method-pattern.html
1. Template method
: 전체 일을 수행하는 구조는 바꾸지 않으면서 특정 단계에서 수행하는 내역(어떤 작업을 처리하는 일부분을 서브 클래스로 캡슐화)을 바꾸는 패턴
  -> 즉, 전체적으로는 동일, 부분적으로는 다른 구문으로 구성된 메서드의 코드 중복을 최소화
  -> 상위 클래스에서 동일한 기능을 정의하면 확장/변화가 필요한 부분만 서브 클래스에서 구현
  -> 상위 클래스에서 전체적인 알고리즘 구현, 하위 클래스에서 다른 부분 구현, 전체적인 알고리즘 코드를 재사용하는 데 유용하도록 함

: 상위 클래스가 뼈대가 되는 로직을 구성하고, 하위 클래스들이 이 로직의 요소들을 각각 구현하는 패턴

2. 역할이 수행하는 작업
AbstractClass
- 템플릿 메서드를 정의하는 클래스
- 상위 클래스에 공통 알고리즘을 정의, 하위 클래스에서 구현될 기능을 (primitive 메서드 또는 hook 메서드로) 정의하는 클래스

ConcreteClass
- 물려받은 (primitive 메서드 또는 hook) 메서드를 구현하는 클래스
- 상위 클래스에 구현된 템플릿 메서드를 하위 클래스에 적합하게 (primitive 메서드나 hook 메서드를) 오버라이드하는 클래스

(참고)
행위(Behavioral) 패턴
- 객체나 클래스 사이의 알고리즘이나 책임 분배에 관련된 패턴
- 한 객체가 혼자 수행할 수 없는 작업을 여러 개의 객체로 어떻게 분배하는지, 또 그렇게 하면서도 객체 사이의 결합도를 최소화하는 것에 중점을 둔다.

엘리베이터 제어 시스템에서 모터를 구동시키는 기능
예를 들어 현대 모터를 이용하는 제어 시스템이라면 HyundaiMotor 클래스에 move 메서드를 정의할 수 있다.
HyundaiMotor Class ?연관 관계?> Door 클래스
move 메서드를 실행할 때 안전을 위해 Door가 닫혀 있는지 확인하기 위해 연관 관계를 정의한다.

Enumeration Interface
- 모터의 상태 (정지 중, 이동 중)
- 문의 상태 (닫혀 있는 중, 열려 있는 중)
- 이동 방향 (위로, 아래로)

3. 문제점
다른 회사의 모터를 제어해야 하는 경우
-> HyundaiMotor 클래스는 현대 모터를 구동시킨다. 만약 LG 모터를 구동시키려면?

-> HyundaiMotor와 LGMotor에는 여러 개의 메서드가 동일하게 구현되어 있다.
-> 즉, 2개의 클래스는 많은 중복 코드 를 가진다.
-> Door 클래스와의 연관관계
-> motorStatus 필드
-> getMotorStatus, setMotorStatus 메서드
-> 중복 코드는 유지보수성을 악화 시키므로 바람직하지 않다.

4. 해결책
1) 상속 1
Motor 클래스를 상위 클래스로 정의함으로써 중복 코드 제거
- Door 클래스와의 연관관계
- motorStatus 필드
- getMotorStatus, setMotorStatus 메서드
그러나 HyundaiMotor와 LGMotor의 move 메서드는 대부분이 비슷하다.
즉, move 메서드에는 여전히 코드 중복 문제가 있다.

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

class HyundaiMotor {
    private Door door;
    private MotorStatus motorStatus;

    public HyundaiMotor(Door door) {
        this.door = door;
        motorStatus = MotorStatus.STOPPED;
    }

    private void moveHyundaiMotor(Direction direction){
        // Hyundai Motor를 구동시킴
    }

    public MotorStatus getMotorStatus() { return motorStatus; }
    private void setMotorStatus(MotorStatus motorStatus) { this.motorStatus = motorStatus; }
    // 엘리베이터 제어
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

// LG 모터와 Hyundai모터 클래스는 비슷
class LGMotor {
    private Door door;
    private MotorStatus motorStatus;

    public LGMotor() {
        this.door = door;
        motorStatus = MotorStatus.STOPPED; // 초기: 멈춘 상태
    }
    private void moveLGMotor(Direction direction) {
        // LG Motor를 구동시킴
    }
    public MotorStatus getMotorStatus() { return motorStatus; }
    private void setMotorStatus(MotorStatus motorStatus) { this.motorStatus = motorStatus; }

    /* 엘리베이터 제어 */
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

public class TemplateMethodTest {
    public static void main(String[] args) {
        Door door = new Door();
        HyundaiMotor hyundaiMotor = new HyundaiMotor(door);
        hyundaiMotor.move(Direction.UP);
    }
}