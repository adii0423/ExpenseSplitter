package splitter;

public class Expense {

    public String paidBy;       // name of person who paid
    public double amount;
    public String description;

    // needed by Gson
    public Expense() {}

    public Expense(String paidBy, double amount, String description) {
        this.paidBy = paidBy;
        this.amount = amount;
        this.description = description;
    }

    @Override
    public String toString() {
        return paidBy + " paid ₹" + amount + " for " + description;
    }
}
