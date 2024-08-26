package org.firstinspires.ftc.teamcode.util.math;

import androidx.annotation.NonNull;

import org.jetbrains.annotations.Contract;

public class Algebra {
    public static class QuadraticFormulaResult {
        public final int answerCount;
        public final double root1;
        public final double root2;

        public QuadraticFormulaResult(double root1, double root2, int answerCount) {
            this.answerCount = answerCount;
            this.root1 = root1;
            this.root2 = root2;
        }
    }

    public static int sign(double num) {
        if (num >= 0) {
            return 1;
        } else {
            return -1;
        }
    }

    /**
     * Solve for all real solutions to a quadratic.
     * @param a The coefficient of the x^2 term.
     * @param b The coefficient of the x term.
     * @param c The final term.
     * @return All real solutions to the quadratic.
     */
    @NonNull
    @Contract(value = "_, _, _ -> new", pure = true)
    public static QuadraticFormulaResult quadraticFormula(double a, double b, double c) {
        double discriminant = b*b - 4*a*c;
        if (discriminant < 0) { // no real slns
            return new QuadraticFormulaResult(0, 0, 0);
        }

        double ans1 = (-b + Math.sqrt(discriminant)) / (2*a);
        if (discriminant == 0) { // one real sln
            return new QuadraticFormulaResult(ans1, ans1, 1);
        }

        double ans2 = (-b - Math.sqrt(discriminant)) / (2*a);
        return new QuadraticFormulaResult(ans1, ans2, 2);
    }
}
