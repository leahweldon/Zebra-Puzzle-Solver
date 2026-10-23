package main.java;

public class ConstraintEqualityVarVar extends Constraint {
    
    Variable v1, v2;

    public ConstraintEqualityVarVar(Variable v1, Variable v2){
        this.v1 = v1;
        this.v2 = v2;
    }

    public String toString() {
        return this.v1 + " = " + this.v2;
    }

    protected boolean isSatisfied() {
        for(int i = 0; i < this.v1.d.vals.length; i++) {
            for(int j = 0; j < this.v2.d.vals.length; j++) { 
                if(this.v1.d.vals[i] == this.v2.d.vals[j])
                    return true;
            }
        }
        return false;
    }

    protected boolean reduce() {
        //from d1
        for(int i = 0; i < this.v1.d.vals.length; i++) {
            Boolean flag = false;
            for(int j = 0; j < this.v2.d.vals.length; j++) { 
                if(this.v1.d.vals[i] == this.v2.d.vals[j])
                    flag = true;
            }
            if (!flag) {
                v1.d.delete(i); 
                i--;
            }
        }

        //from d2
        for(int i = 0; i < this.v2.d.vals.length; i++) {
            Boolean flag = false;
            for(int j = 0; j < this.v1.d.vals.length; j++) { 
                if(this.v2.d.vals[i] == this.v1.d.vals[j])
                    flag = true;
            }
            if (!flag) {
                v2.d.delete(i); 
                i--;
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
