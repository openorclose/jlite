class Main {
    Void main() {

        Overloaded o;
        o = new Overloaded();
        o.run();
    }
}

class Overloaded {
    Void run() {
        println(sum(1));
        println(sum(2, 4)); // should be 6
        println(sum(true, false)); // should be true;
    }
    Int sum(Int x) {
        return x;
    }

    Int sum(Int x, Int y) {
        return x + y;
    }

    Bool sum(Bool x, Bool y) { // same no of args, different type;
        return x || y;
    }
}