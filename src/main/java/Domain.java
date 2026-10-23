package main.java;

import java.util.Arrays;

public class Domain {

    int[] vals;


    public Domain(int[] vals) {
        this.vals = vals;
    }


    public Domain(Domain d2) {
        //TODO make a copy of the domain from what d2 contains
        vals = new int[d2.vals.length];
        for(int i = 0; i < vals.length; i++)
            this.vals[i] = d2.vals[i];
    }

    public void delete(int index) {
        int[] newVals = new int[vals.length - 1]; // new array with one less element

        int j = 0; // index for new array
        for (int i = 0; i < vals.length; i++) {
            if (i != index) {
                newVals[j] = vals[i]; // copy element to new array
                j++; // increment index for new array
            }
        }

        this.vals = newVals;
    }

    /**
     * @return
     */
    public String toString() {
        String result  = "{";
        for (int i = 0; i < vals.length; i++)
            result += vals[i];
        result += "}";
        return result;
    }

    /**
     * Splits the domain into two sub-domains.
     * @return an array containing the two sub-domains.
     */
    public Domain[] split() {
       // return (new Domain[2]);
       int mid = vals.length / 2;
    int[] firstHalf = Arrays.copyOfRange(vals, 0, mid);
    int[] secondHalf = Arrays.copyOfRange(vals, mid, vals.length);
    Domain[] result = new Domain[2];
    result[0] = new Domain(firstHalf);
    result[1] = new Domain(secondHalf);
    return result;
    }

    /**
     * Checks if the domain is empty.
     * @return true if the domain is empty, false otherwise.
     */
    public boolean isEmpty() {
        if (vals.length == 0){
        return true;
        }
        return false;

    }

    /**
     * Checks if the domain is equal to another domain.
     * @param d2 the domain to compare to.
     * @return true if the domains are equal, false otherwise.
     */
    public boolean equals(Domain d2) {
        if (vals.length != d2.vals.length) {
            return false;
        }
        for (int i = 0; i < vals.length; i++) {
            if (vals[i] != d2.vals[i]) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks if the domain is reduced to only one value.
     * @return true if the domain is reduced to only one value, false otherwise.
     */
    public boolean  isReducedToOnlyOneValue() {
        if (vals.length == 1) {
            return true;
        }
        return false;
    }

    public int size() {
        return vals.length;
    }


    public boolean contains(int val) {
        for (int i = 0; i < vals.length; i++) {
            if (vals[i] == val) {
                return true;
            }
        }
        return false;
    }
}


