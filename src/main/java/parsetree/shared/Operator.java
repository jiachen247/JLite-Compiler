package main.java.ast.common;

public class Operator {

    public enum Unary {
        NEGATE {
            @Override
            public String toString() {
                return "-";
            }
        }, NOT {
            @Override
            public String toString() {
                return "!";
            }
        }
    }

    public enum Binary {
        AND {
            @Override
            public String toString() {
                return "&&";
            }
        }, OR {
            @Override
            public String toString() {
                return "||";
            }
        },
        LT {
            @Override
            public String toString() {
                return "<";
            }
        }, GT {
            @Override
            public String toString() {
                return ">";
            }
        }, LTE {
            @Override
            public String toString() {
                return "<=";
            }
        }, GTE {
            @Override
            public String toString() {
                return ">=";
            }
        }, EQ {
            @Override
            public String toString() {
                return "==";
            }
        }, NEQ {
            @Override
            public String toString() {
                return "!=";
            }
        },
        PLUS {
            @Override
            public String toString() {
                return "+";
            }
        }, MINUS {
            @Override
            public String toString() {
                return "-";
            }
        }, TIMES {
            @Override
            public String toString() {
                return "*";
            }
        }, DIVIDE {
            @Override
            public String toString() {
                return "/";
            }
        }
    }
}
