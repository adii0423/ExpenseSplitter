# Expense Splitter

A simple CLI app to split expenses between friends and figure out who owes whom.

---

## The problem it solves

You go on a trip with friends. Different people pay for different things. At the end nobody knows who owes what. This app tracks all of that and tells you the simplest way to settle up.

---

## How to run

```
.\compile_and_run.bat
```

---

## Commands

| Command | Example |
|---|---|
| `add-person <name>` | `add-person Rahul` |
| `add-expense <name> <amount> <description>` | `add-expense Rahul 600 Dinner` |
| `expenses` | shows all expenses |
| `settle` | shows who pays whom |
| `people` | shows all members |
| `remove-person <name>` | removes someone |
| `reset` | clears everything |
| `quit` | exits the app |

---

## Example

```
> add-person Rahul
> add-person Priya
> add-person Arjun
> add-expense Rahul 600 Dinner
> add-expense Priya 300 Auto
> add-expense Arjun 900 Movies
> settle

Total spent: ₹1800.00
Each person's share: ₹600.00

Settlements:
  Priya → Arjun: ₹300.00
```

Priya pays Arjun ₹300 and everyone is settled. That's it.

---

## Files

```
ExpenseSplitter/
├── src/main/java/splitter/
│   ├── Main.java        — CLI interface
│   ├── Person.java      — stores person data
│   ├── Expense.java     — stores one expense
│   ├── Group.java       — manages everyone + settlement logic
│   └── Storage.java     — saves data to JSON
├── lib/
│   └── gson-2.10.1.jar
└── data/
    └── group.json       — auto created when you run
```

---

Built to solve a real problem every student faces on trips and outings.
