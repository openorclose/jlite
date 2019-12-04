class Main {
    Void main() {
        Loop l;
        l = new Loop();
        l.loop();
    }
}

class Loop {
    Void loop() {
        println("loop"); // should print loop forever, until run out of stack space
        loop();
    }
}