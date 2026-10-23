package main.java;

public abstract class Constraint {

    //abstract String toString();

    abstract boolean isSatisfied();

    abstract boolean reduce();

}