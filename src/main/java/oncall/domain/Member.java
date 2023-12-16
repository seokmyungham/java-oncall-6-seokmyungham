package oncall.domain;

public class Member {
    private final Name name;
    private final int turnNum;

    public Member(Name name, int turnNum) {
        this.name = name;
        this.turnNum = turnNum;
    }
}
