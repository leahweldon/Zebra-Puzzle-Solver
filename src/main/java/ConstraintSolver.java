package main.java;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Stack;
import java.util.ArrayList;
import java.util.Arrays;

public class ConstraintSolver {

    private Domain dom;
    private List<Variable> variableSet;
    private List<Constraint> constraintSet;

    public ConstraintSolver() {
        this.variableSet = new ArrayList<Variable>();
        this.constraintSet = new ArrayList<Constraint>();
    }

    public String toString() {
        //print variable
        for(int i = 0; i < variableSet.size(); i++)
            System.out.println(variableSet.get(i));
        System.out.println("");
        //print constraints
        for(int i = 0; i < constraintSet.size(); i++)
            System.out.println(constraintSet.get(i));
        return "";
        //TODO
        //needs to print constraints as well
    }

    private void parse(String fileName) {
        try {
            File inputFile = new File(fileName);
            Scanner scanner = new Scanner(inputFile);

            while (scanner.hasNextLine()) {
                String currentLine = scanner.nextLine();

                if(currentLine.startsWith("Domain-")) {
                    //this is our domain - i.e. a datastructure that contains values and can be updated, played with etc.
                    String s = currentLine.replace("Domain-","");
                    String[] array = s.split(","); 
                    int[] vals = new int[array.length];
                    for(int i = 0; i < array.length; i++) {
                        vals[i] = Integer.parseInt(array[i]);
                    }
                    dom = new Domain(vals);
                } else if (currentLine.startsWith("Var-")) {
                    //this is the code for every variable (a name and a domain)
                    String s = currentLine.replace("Var-","");
                    Variable var = new Variable(s, dom); 
                    variableSet.add(var);
                } else if (currentLine.startsWith("Cons-")) {
                
                    if (currentLine.startsWith("Cons-eqVPC")) {
                        String regexPattern = "\\(|\\)|\\ ";
                        String s = currentLine.replace("Cons-eqVPC(","").replaceAll(regexPattern, "");
                        String[] values = s.split("="); 
                        String[] values2 = values[1].split("\\+");
                        String val1Name = values[0];
                        String val2Name = values2[0];
                        Variable v1 = null;
                        Variable v2 = null;
                        for (Variable element : variableSet) {
                            if (element.hasThisName(val1Name)) {
                                v1 = element;
                            } else if(element.hasThisName(val2Name)) {
                                v2 = element; 
                            }
                        }
                        ConstraintEqualityVarPlusCons eq = new ConstraintEqualityVarPlusCons(v1, v2, Integer.parseInt(values2[1]), false);
                        constraintSet.add(eq);
                    }
                    else if (currentLine.startsWith("Cons-eqVV")) {
                        String s = currentLine.replace("Cons-eqVV(","").replace(")", "");
                        String[] values = s.split(" = "); 
                        String val1Name = values[0];
                        String val2Name = values[1];
                        Variable v1 = null;
                        Variable v2 = null;
                        for (Variable element : variableSet) {
                            if (element.hasThisName(val1Name)) {
                                v1 = element;
                            } else if(element.hasThisName(val2Name)) {
                                v2 = element; 
                            }
                        }
                        ConstraintEqualityVarVar eq = new ConstraintEqualityVarVar(v1, v2);
                        constraintSet.add(eq);
                    }
                    //ConstraintEqualityVarCons:
                    else if (currentLine.startsWith("Cons-eqVC")) {
                        String s = currentLine.replace("Cons-eqVC(", "").replace(")", "");
                        String[] values = s.split(" = ");
                        String val1Name = values[0];
                        String val2 = values[1];
                        Variable v1 = null;
                        for (Variable element : variableSet) {
                            if (element.hasThisName(val1Name)) {
                                v1 = element;
                                break;
                            }
                        }
                        ConstraintEqualityVarCons eq = new ConstraintEqualityVarCons(v1, Integer.parseInt(val2));
                        constraintSet.add(eq);

                    }
                    //ConstraintDifferenceVarVar:
                    else if (currentLine.startsWith("Cons-diff")) {
                        String s = currentLine.replace("Cons-diff(","").replace(")", "");
                        String[] values = s.split(","); 
                        String val1Name = values[0];
                        String val2Name = values[1];
                        Variable v1 = null;
                        Variable v2 = null;
                        for (Variable element : variableSet) {
                            if (element.hasThisName(val1Name)) {
                                v1 = element;
                            } else if(element.hasThisName(val2Name)) {
                                v2 = element; 
                            }
                        }
                        ConstraintDifferenceVarVar diff = new ConstraintDifferenceVarVar(v1,v2);
                        constraintSet.add(diff);
                    }
                    // Constraint Abs
                    else if (currentLine.startsWith("Cons-abs")) {
                        String s = currentLine.replace("Cons-abs(","").replace(" = 1)", "");
                        String[] values = s.split(" - "); 
                        String val1Name = values[0];
                        String val2Name = values[1];
                        Variable v1 = null;
                        Variable v2 = null;
                        for (Variable element : variableSet) {
                            if (element.hasThisName(val1Name)) {
                                v1 = element;
                            } else if(element.hasThisName(val2Name)) {
                                v2 = element; 
                            }
                        }
                        ConstraintEqualityVarPlusCons eq = new ConstraintEqualityVarPlusCons(v1, v2, 1, true);
                        constraintSet.add(eq);
                    }

                //ConstraintEqualityVarVar:
                } 
            }

            scanner.close();
            
        } catch (FileNotFoundException e) {
            System.out.println("Error.");
            e.printStackTrace();
        }
    }

    /** Repeatedly propagate constraints, then search remaining possibilities. */
    public ArrayList<String> printAnswer(String fileName) {
        variableSet.clear();
        constraintSet.clear();
        parse(fileName);
        if (!solve()) {
            throw new IllegalStateException("No solution found for " + fileName);
        }
        ArrayList<String> answer = new ArrayList<>();
        for (Variable v : variableSet) {
            answer.add("Sol-" + v.name + "-" + v.d.vals[0]);
        }
        return answer;
    }

    private boolean solve() {
        // Propagate until no domain changes; the original reduce() only
        // processed an initial prefix of constraints.
        boolean changed;
        do {
            int[][] before = snapshot();
            for (Constraint constraint : constraintSet) {
                constraint.reduce();
                if (!constraint.isSatisfied()) return false;
            }
            for (Variable v : variableSet) if (v.d.isEmpty()) return false;
            changed = !same(before);
        } while (changed);

        Variable branch = null;
        for (Variable v : variableSet) {
            if (v.d.size() > 1 && (branch == null || v.d.size() < branch.d.size())) {
                branch = v;
            }
        }
        if (branch == null) return true;

        int[][] state = snapshot();
        int index = variableSet.indexOf(branch);
        for (int candidate : state[index]) {
            restore(state);
            variableSet.get(index).d.vals = new int[]{candidate};
            if (solve()) return true;
        }
        restore(state);
        return false;
    }

    private int[][] snapshot() {
        int[][] domains = new int[variableSet.size()][];
        for (int i = 0; i < variableSet.size(); i++) {
            domains[i] = variableSet.get(i).d.vals.clone();
        }
        return domains;
    }

    private boolean same(int[][] previous) {
        for (int i = 0; i < previous.length; i++) {
            if (!Arrays.equals(previous[i], variableSet.get(i).d.vals)) return false;
        }
        return true;
    }

    private void restore(int[][] previous) {
        for (int i = 0; i < previous.length; i++) {
            variableSet.get(i).d.vals = previous[i].clone();
        }
    }

    public static void main(String[] args) {
        String filename = args.length > 0 ? args[0] : "data.txt";
        ConstraintSolver solver = new ConstraintSolver();
        for (String answer : solver.printAnswer(filename)) System.out.println(answer);
    }
}
