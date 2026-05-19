package splitter;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Group group = Storage.load();
        Scanner scanner = new Scanner(System.in);

        System.out.println("==============================");
        System.out.println("      Expense Splitter");
        System.out.println("==============================");
        System.out.println("Commands:");
        System.out.println("  add-person <name>                        - add a person");
        System.out.println("  remove-person <name>                     - remove a person");
        System.out.println("  people                                   - list all people");
        System.out.println("  add-expense <name> <amount> <description>- add an expense");
        System.out.println("  expenses                                 - list all expenses");
        System.out.println("  settle                                   - show who pays whom");
        System.out.println("  reset                                    - clear everything");
        System.out.println("  quit                                     - exit");
        System.out.println("==============================\n");

        while (true) {
            System.out.print("> ");
            String input = scanner.nextLine().trim();

            if (input.isEmpty()) continue;

            if (input.startsWith("add-person ")) {
                String name = input.substring(11).trim();
                group.addPerson(name);
                Storage.save(group);

            } else if (input.startsWith("remove-person ")) {
                String name = input.substring(14).trim();
                group.removePerson(name);
                Storage.save(group);

            } else if (input.equals("people")) {
                group.listPeople();

            } else if (input.startsWith("add-expense ")) {
                // format: add-expense Rahul 600 Dinner
                String[] parts = input.substring(12).trim().split(" ", 3);
                if (parts.length < 3) {
                    System.out.println("Usage: add-expense <name> <amount> <description>");
                    continue;
                }
                String name = parts[0];
                double amount;
                try {
                    amount = Double.parseDouble(parts[1]);
                } catch (NumberFormatException e) {
                    System.out.println("Amount must be a number. Example: add-expense Rahul 600 Dinner");
                    continue;
                }
                String description = parts[2];
                group.addExpense(name, amount, description);
                Storage.save(group);

            } else if (input.equals("expenses")) {
                group.listExpenses();

            } else if (input.equals("settle")) {
                group.settle();

            } else if (input.equals("reset")) {
                group = new Group();
                Storage.save(group);
                System.out.println("Everything cleared.");

            } else if (input.equals("quit")) {
                System.out.println("Bye!");
                break;

            } else {
                System.out.println("Unknown command. Type one of: add-person, remove-person, people, add-expense, expenses, settle, reset, quit");
            }
        }

        scanner.close();
    }
}