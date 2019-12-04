class TestObjectInObjectMain {

	Void main () {

		Object o;
		o = new Object();
		o.i = 555;
		o.b = true;
		println(o.i); // should be 555
		println(o.b); // should be true
		o.initO(); // inits o, with o.i = 5 and o.b to false
		println(o.i); // should be 5
        println(o.b); // should be false

        o.o = new Object(); // objects can be fields of other objects
        o.o.i = 10;
        o.o.b = true;
        println(o.getO().i); // should be 10
        println(o.getO().b); // should be true


	}

}


class Object {
    Object o;
    Int i;
    Bool b;

    Object getO() {
        return o;
    }

    Void initO() {

        i = 5;
        b = false;
    }
}

