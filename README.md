# jlite
A compiler for jLite, a subset of Java

This was done as 3 programming assignments in CS4212.

Jlite is a subset of Java, and the the compiler parses the language, typechecks it, and then generates ARM assembly.

You can view the sample JLite programs inside `/tests`.

# Compiling

1. Ensure Java 11 or above is installed.
2. Run `make`
3. Run `make compile file=/path/to/file`. The generated assembly will be located in `/path/to/file.s`.
