class Main {
    Void main() {
    Functions f;
    Int counter;
        println(1); // prints 1
        println(true); // prints true
        println("Hello world");
        
        
        println(1 + 1); // addition
        println(2 * 4); //multiply
        println(1000 - 300); //sub

        println(---------5);
        println(!!!!!!!!!!!!true);
        
        println ( 1 < 2 );
        println(2 < 1);
        println( 2 <= 2);
        println(3 <= 2);
        
        println( 3 == 3);
        println(3 == 4);
        println(3 != 3);
        println(10000 != 9999);
        
         println ( 1 > 2 );
                println(2 > 1);
                println( 2 >= 2);
                println(3 >= 2);
                
         
         println(false || false);
         println(false || true);
         println(true || false);
         println(true || true);
                
      println(false && false);
      println(false && true);
      println(true && false);
      println(true && true);

      if (true) {
        println("true part of if statemenent. should be printed.");
      } else {println("false part of if statement, should not be printed");}

      if (false) {
        println("should not be printed");
      } else {
        println("should be printed");
      }

      counter = 0;

      while (counter < 10) {
        println(counter);
        //test if in while
        if (counter < 5) {
            println("counter less than 5!");
        } else {
            println("counter more than or equal 5!");
        }
        counter = counter + 1;
      }


        println("test more than 4 argument functions");
        f = new Functions();
        f.field = 1;
        println(f.sum6(1, 2, 3 ,4 ,5, 6)); // should be 21

        f.shadow(10000);
        f.mutateField();
    }
}

class Functions {

    Int field;
    Int sum6(Int a, Int b, Int c, Int d, Int e, Int f) {
        return a + b + c + d + e + f;
    }

    Void shadow(Int field) {
        Int field;
        field = 100;
        println(field);// should be 100
        println(this.field);//should be 1, not changed
    }

    Void mutateField() {
        field = 9999; // not a variable, so mutates field
        println(this.field); // should be 9999
    }
}