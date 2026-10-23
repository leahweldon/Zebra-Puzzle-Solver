package main.java;

public class ConstraintEqualityVarCons extends Constraint {

    Variable v;
    int cons;

    public ConstraintEqualityVarCons(Variable v, int cons) {
        this.v = v;
        this.cons = cons;
    }

    public String toString() {
        return this.v + " = " + this.cons;
    }

    protected boolean isSatisfied() {
        return v.d.contains(cons);
    }

    protected boolean reduce() {
        for(int i = 0; i < this.v.d.vals.length; i++) {
            if(this.v.d.vals[i] != this.cons) {
                v.d.delete(i);
                i--;
            }
        }
        if (v.d.isEmpty() == true) {
            return false;
        }
        else {
            return true;
        }
    }



}
