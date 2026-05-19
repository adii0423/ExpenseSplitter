package splitter;

public class Person {

    public String name;
    public double totalPaid;   // how much this person paid in total

    // needed by Gson
    public Person() {}

    public Person(String name) {
        this.name = name;
        this.totalPaid = 0;
    }

    @Override
    public String toString() {
        return name;
    }
}
