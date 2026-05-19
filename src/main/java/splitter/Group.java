package splitter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Group {

    public List<Person> people;
    public List<Expense> expenses;

    public Group() {
        this.people = new ArrayList<>();
        this.expenses = new ArrayList<>();
    }

    // --- people ---

    public void addPerson(String name) {
        for (Person p : people) {
            if (p.name.equalsIgnoreCase(name)) {
                System.out.println(name + " is already in the group.");
                return;
            }
        }
        people.add(new Person(name));
        System.out.println("Added " + name + " to the group.");
    }

    public void removePerson(String name) {
        boolean removed = people.removeIf(p -> p.name.equalsIgnoreCase(name));
        if (removed) {
            System.out.println("Removed " + name + " from the group.");
        } else {
            System.out.println("Person not found: " + name);
        }
    }

    public void listPeople() {
        if (people.isEmpty()) {
            System.out.println("No people in the group yet.");
            return;
        }
        System.out.println("Group members:");
        for (Person p : people) {
            System.out.println("  - " + p.name);
        }
    }

    // --- expenses ---

    public void addExpense(String payerName, double amount, String description) {
        // check payer exists
        boolean found = false;
        for (Person p : people) {
            if (p.name.equalsIgnoreCase(payerName)) {
                p.totalPaid += amount;
                found = true;
                break;
            }
        }

        if (!found) {
            System.out.println("Person not found: " + payerName + ". Add them first.");
            return;
        }

        expenses.add(new Expense(payerName, amount, description));
        System.out.println("Added: " + payerName + " paid ₹" + amount + " for " + description);
    }

    public void listExpenses() {
        if (expenses.isEmpty()) {
            System.out.println("No expenses yet.");
            return;
        }
        System.out.println("\nAll expenses:");
        System.out.println("------------------------------------------");
        for (Expense e : expenses) {
            System.out.printf("  %-15s paid ₹%-8.2f for %s%n", e.paidBy, e.amount, e.description);
        }
        System.out.println("------------------------------------------");
    }

    // --- settlement ---

    public void settle() {
        if (people.isEmpty()) {
            System.out.println("No people in the group.");
            return;
        }

        if (expenses.isEmpty()) {
            System.out.println("No expenses recorded.");
            return;
        }

        int count = people.size();

        // total money spent by everyone
        double total = 0;
        for (Expense e : expenses) {
            total += e.amount;
        }

        // each person's fair share
        double share = total / count;

        System.out.println("\n==========================================");
        System.out.printf("  Total spent: ₹%.2f%n", total);
        System.out.printf("  Each person's share: ₹%.2f%n", share);
        System.out.println("==========================================");

        // calculate net balance for each person
        // positive = they are owed money
        // negative = they owe money
        Map<String, Double> balance = new HashMap<>();
        for (Person p : people) {
            balance.put(p.name, p.totalPaid - share);
        }

        System.out.println("\nIndividual summary:");
        for (Person p : people) {
            double bal = balance.get(p.name);
            if (bal > 0.01) {
                System.out.printf("  %-15s is owed ₹%.2f%n", p.name, bal);
            } else if (bal < -0.01) {
                System.out.printf("  %-15s owes ₹%.2f%n", p.name, Math.abs(bal));
            } else {
                System.out.printf("  %-15s is settled%n", p.name);
            }
        }

        // find minimum transactions to settle everyone
        // split into two lists: who owes, who is owed
        List<String> owes = new ArrayList<>();      // negative balance
        List<String> isOwed = new ArrayList<>();    // positive balance

        for (Map.Entry<String, Double> entry : balance.entrySet()) {
            if (entry.getValue() < -0.01) owes.add(entry.getKey());
            else if (entry.getValue() > 0.01) isOwed.add(entry.getKey());
        }

        System.out.println("\nSettlements (minimum transactions):");
        System.out.println("------------------------------------------");

        // greedily match debtors with creditors
        int i = 0, j = 0;
        while (i < owes.size() && j < isOwed.size()) {
            String debtor = owes.get(i);
            String creditor = isOwed.get(j);

            double debtorOwes = Math.abs(balance.get(debtor));
            double creditorOwed = balance.get(creditor);

            double payment = Math.min(debtorOwes, creditorOwed);

            System.out.printf("  %s → %s: ₹%.2f%n", debtor, creditor, payment);

            balance.put(debtor, balance.get(debtor) + payment);
            balance.put(creditor, balance.get(creditor) - payment);

            if (Math.abs(balance.get(debtor)) < 0.01) i++;
            if (Math.abs(balance.get(creditor)) < 0.01) j++;
        }

        System.out.println("------------------------------------------");
    }
}
