package main.java;

public class ConstraintDifferenceVarVar extends Constraint {

    Variable v1, v2;

    public ConstraintDifferenceVarVar(Variable v1, Variable v2){
        this.v1 = v1;
        this.v2 = v2;
    }

    public String toString() {
        return this.v1 + " != " + this.v2;
    }

    protected boolean isSatisfied() {
        if(v1.d.size() == 1 && v2.d.size() == 1 && v1.d.vals[0] == v2.d.vals[0])
            return false;
        return true;
    }

    protected boolean reduce() {
        if(v2.d.size() == 1) {
            for(int i = 0; i < v1.d.size(); i++) {
                if(v1.d.vals[i] == v2.d.vals[0]) {
                    v1.d.delete(i);
                    i--;
                }
            }
        }

        if(v1.d.size() == 1) {
            for(int i = 0; i < v2.d.size(); i++) {
                if(v2.d.vals[i] == v1.d.vals[0]) {
                    v2.d.delete(i);
                    i--;
                }
            }
        }
        if ((v1.d.isEmpty() == true) || (v2.d.isEmpty() == true))  {
            return false;
        }
        else {
            return true;
         }
    }

}
