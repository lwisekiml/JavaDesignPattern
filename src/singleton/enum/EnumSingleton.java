import singleton.test.SingletonTest;

/* 확인 필요
Thread-safety와 Serialization이 보장된다.
Reflection을 통한 공격에도 안전하다.
따라서 Enum을 이용해서 Singleton을 구현하는 것이 가장 좋은 방법이다.
https://gmlwjd9405.github.io/2018/07/06/singleton-pattern.html
*/
public enum EnumSingleton {
    INSTANCE;

    public static SingletonTest getInstance() {
        return INSTANCE;
    }
}
