class TestRecursionMain {

	Void main () {
        Functions f;

		println(f.fib(15));

		println(f.isEven(100)); //true
		println(f.isOdd(133)); // true

		println(f.isEven(201)); //false
        		println(f.isOdd(332)); // false

        println(f.ack(3, 4)); // 125
	}

}


class Functions {
    Int fib(Int n) {
        if (n <= 1) {
            return 1;
        } else {
            return fib(n-1) + fib(n-2);
        }
    }


    // test mutual recursion
    Bool isOdd(Int n) {
        if (n == 0) {
        return false;
        } else {

        return n == 1 || isEven(n-1);
        }
    }

    Bool isEven(Int n) {
        if (n == 0) {
        return true;
        } else {
          return isOdd(n - 1);
        }
    }

    Int ack(Int m, Int n) {
        if (m == 0) {
         return n + 1;
        } else {
            if (n == 0) {
                return ack(m - 1, 1);
            } else {
               return ack(m - 1, ack(m, n - 1));
            }
        }

     }
}

