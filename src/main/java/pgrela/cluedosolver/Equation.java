package pgrela.cluedosolver;

public class Equation {
    String name;
    int length;
    double[] params;
    double solution;

    public Equation(String name, int length) {
        this.name = name;
        this.length = length;
        params = new double[length];
    }

    public double getParam(int index) {
        return params[index];
    }

    public void setParam(int index, double param) {
        this.params[index] = param;
    }

    public double getSolution() {
        return solution;
    }

    public void setSolution(double solution) {
        this.solution = solution;
    }

    @Override
    public String toString() {
        return name;
    }
}
