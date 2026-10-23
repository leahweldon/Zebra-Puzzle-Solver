# Zebra Puzzle Solver

A Java constraint-programming implementation of the **Zebra Puzzle (Einstein's Riddle)**, developed as coursework for **CSU22012 – Algorithms and Data Structures II** at Trinity College Dublin (2022–2023).

## Overview

The solver reads a set of variables and constraints from `data.txt`, narrows possible values through constraint propagation, and uses recursive backtracking to resolve remaining possibilities. It outputs assignments in the `Sol-<variable>-<position>` format and can be checked against `sol.txt`.

## Concepts

- Constraint satisfaction and domain reduction
- Equality, inequality, ordering and adjacency constraints
- Recursive backtracking and state restoration
- Java classes and automated testing with JUnit 4

## Project structure

| File or directory | Purpose |
|---|---|
| [`src/main/java/`](src/main/java/) | Solver, domains, variables and constraint implementations |
| [`src/test_files/java/ZebraTest.java`](src/test_files/java/ZebraTest.java) | Original JUnit test for the expected solution |
| [`data.txt`](data.txt) | Puzzle variables and constraints |
| [`sol.txt`](sol.txt) | Expected solution |

## Run

From the repository root, with Java installed:

```bash
mkdir -p out
javac -d out src/main/java/*.java
java -cp out main.java.ConstraintSolver
```

The solver reads `data.txt` from the current directory by default. You can pass an alternative input file as the first command-line argument.

To run the JUnit test, place **JUnit 4.13.2** and **Hamcrest Core 1.3** jars on your local classpath:

```bash
javac -cp 'out:junit-4.13.2.jar:hamcrest-core-1.3.jar' -d out src/test_files/java/ZebraTest.java
java -cp 'out:junit-4.13.2.jar:hamcrest-core-1.3.jar' org.junit.runner.JUnitCore test_files.java.ZebraTest
```

The example test commands use Unix-style `:` classpath separators; Windows uses `;`.

## Verification

The Java sources compile; the solver's output for the supplied puzzle matches `sol.txt`, and the original JUnit test passed locally with JUnit 4.13.2. This does not establish correctness for arbitrary constraint datasets.

## Academic context

**University:** Trinity College Dublin  
**Course:** Computer Science  
**Module:** CSU22012 – Algorithms and Data Structures II  
**Academic year:** 2022–2023  

## Archive note

This is a maintained public copy of coursework originally retained in a private GitHub Classroom repository. The private Classroom repository retains the historical commit record and contributor attribution.
