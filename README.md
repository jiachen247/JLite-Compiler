# JLite Compiler

This program compiles JLite code to arm assembly code.

## Implementation
- lexing done using JFlex via regular grammar rules
- parsing done using Cup via context free grammar rules
- semantic checks (Name and type checking)
- IR generation
- Code generation
- Register allocation done via constructing the interference graph 
- and then running graph coloring (simplify and select)
- post optimization on generated arm assemble code

## Getting started

### Build
````bash
$ make
````

## Compiling a source file
```bash
$ java -cp ./bin/java-cup-11b.jar:out Main <jlite source file here>
```
## Testcases
*Test cases source files can be found at* `src/test/pa3/`.

## Flags

### Debug Flag
When run with the `-d` or `--debug` flag, information such as those listed below will be printed to stdout.
- parse tree
- ir3 tree
- offset table (offset for spilled objects)
- variable webs
- interference graph
- register allocation

```bash
$ java -cp ./bin/java-cup-11b.jar:out Main src/test/pa3/pass/0.in -d
```

### Optimization Flag
With this flag, assembly code will go thorough optimization passes to produce more efficiently code.
Might take a little longer but WILL preserve correctness.
```bash
$ java -cp ./bin/java-cup-11b.jar:out Main src/test/pa3/pass/0.in -o
```

## Running with Gem 5
You can run the generated arm assembly code on an emulator like Gem5!

```bash
$ java -cp ./bin/java-cup-11b.jar:out Main sample.in > sample.s
$ arm-linux-gnueabi-gcc -c sample.s -o sample.o
$ arm-linux-gnueabi-gcc -o sample.bin sample.o --static
$ ./gem5/build/ARM/gem5.opt gem5/configs/example/se.py -c ./sample.o
```


## Example (Fact .s)
To see how our compiler works, we will be compiling a simple factorial function.

fact.s
```java
class Main {
    Void main() {
        Factorial factorial;
        factorial = new Factorial();
        println(factorial.recursive(5));
    }
}

class Factorial {
    Int recursive(Int n) {
        if (n == 1) {
            return 1;
        } else {
            return n * recursive(n - 1);
        }
    }
}
```

```$arm
    .data
_int:
    .asciz "%d"
_string:
    .asciz "%s"
_true:
    .asciz "true"
_false:
    .asciz "false"
_null:
    .asciz "null"
_empty:
    .asciz ""
_newline:
    .asciz "\\n"
S1:
    .asciz "
"

    .text
    .global main
    .type main, %function

Factorial_0:
    stmfd sp!, {fp, lr, v1, v2, v3, v4, v5}
    add fp, sp, #24
    sub sp, fp, #28
    mov v4, a1
    mov v2, a2
    ldr a1, =#1
    mov v1, #0
    cmp a1, v2
    moveq v1, #1
    cmp v1, #1
    beq l1
    ldr a1, =#1
    sub v3, v2, a1
    mov a2, v3
    mov a1, v4
    bl Factorial_0(PLT)
    mov v1, a1
    mul a1, v1, v2
    b Factorial_0_exit
    b l2
l1:
    ldr a1, =#1
    b Factorial_0_exit
l2:

Factorial_0_exit:
    sub sp, fp, #24
    ldmfd sp!, {fp, pc, v1, v2, v3, v4, v5}

Factorial_1:
    stmfd sp!, {fp, lr, v1, v2, v3, v4, v5}
    add fp, sp, #24
    sub sp, fp, #28
    mov v3, a1
    mov v1, a2
    ldr v4, =#1
    b l4
l3:
    mul v4, v1, v4
    ldr a1, =#1
    sub v1, v1, a1
l4:
    ldr a1, =#0
    mov v2, #0
    cmp a1, v1
    movlt v2, #1
    cmp v2, #1
    beq l3
    mov a1, v4
    b Factorial_1_exit

Factorial_1_exit:
    sub sp, fp, #24
    ldmfd sp!, {fp, pc, v1, v2, v3, v4, v5}

main:
    stmfd sp!, {fp, lr, v1, v2, v3, v4, v5}
    add fp, sp, #24
    sub sp, fp, #28
    mov a1, #4
    bl malloc(PLT)
    mov v1, a1
    ldr a2, =#5
    mov a1, v1
    bl Factorial_0(PLT)
    mov v2, a1
    mov a2, v2
    ldr a1, =_int
    bl printf(PLT)
    ldr a2, =S1
    ldr a1, =_string
    bl printf(PLT)
    ldr a2, =#5
    mov a1, v1
    bl Factorial_1(PLT)
    mov v2, a1
    mov a2, v2
    ldr a1, =_int
    bl printf(PLT)
    b Main_0_exit

Main_0_exit:
    sub sp, fp, #24
    ldmfd sp!, {fp, pc, v1, v2, v3, v4, v5}

    .end

```
![](https://i.imgur.com/3thH3pg.png)




