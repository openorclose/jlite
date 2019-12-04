JFLAGS = -d out -cp cup.jar:runtime.jar:
JAVAC=javac
sources = $(wildcard *.java)
classes = $(sources:.java=.class)

all:
	java -jar jflex.jar jlite.flex && java -jar cup.jar < jlite.cup && $(JAVAC) $(JFLAGS) *.java

compile:
	java -cp out:runtime.jar: Main $(file) 0 > $(file).s

compileOpt:
	java -cp out:runtime.jar: Main $(file) 1 > $(file).s

clean:
	rm -f *.class
	