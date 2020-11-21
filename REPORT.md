# CS4212 Assignment 3 Report

For a getting started or how to run the compiler refer to the readme instead.

## Introduction
This report will detail some of the underlying design decisions when implementing the JLite compiler.

## Phase 1: Lexing and Parsing
For lexing and parsing we used the JFlex and Cup respectively to generate java code via grammar rules.

We also introduced some error production to catch predictable errors such as missing semi colons.

```cup
VarDecl
::= Type:type ID:id SEMI
  {:
    RESULT = new VarDecl(typeleft, typeright, type, new Id(idleft, idright, id));
  :}
| Type:type CNAME:cname SEMI
    {:
      report_error(cnameleft, cnameright, ILLEGAL_CLASSNAME);
      RESULT = new VarDecl(typeleft, typeright, type, new Id(cnameleft, cnameright, cname));
        :}
| Type:type ID:id
    {:
      report_error(idleft, idright, MISSING_SEMI);
      RESULT = new VarDecl(typeleft, typeright, type, new Id(idleft, idright, id));
    :}
```

I had to relax the grammar rules a little to handle string concatenation. For example I allowed `1 + "2"` through. We would flag these errors though type checking in the next phase.

## Phase 2: Semantic Analysis
During this phase, we would go over the parse tree and flag any semantic errors. If we observed an errors we would quit and not proceed with compilation.

### Distinct Name Checking
In this semantic check, we ensured that variable and class have unique names to prevent shadowing.

#### Method Overloading
Interesting portion here was defining "uniqueness" for overloaded methods. I decided on using a method signature consisting of method name and arg types as per java.
I implemented a mechanism for allowing method overloading by introducing a method signature which includes the method name and the argument type
We allow overloading iff method signature are different.

*Method signatures are equal iff method name are the same and arg types are the identical (order matters)*

This is derived and consistent from overloading rules in modern languages eg Java.
See implementation of ClassDecl::checkUniqueMethodSignature for impl details.

- Overloaded methods MUST change the argument type list
- Overloaded methods CAN change the return type
- Overloaded methods CAN change the access modifier (not applicable to jlite)
- Overloaded methods CAN declare new or broader checked exceptions (not applicable to jlite)
- A method can be overloaded in the same class or in a subclass (not applicable to jlite)
https://stackoverflow.com/questions/2807840/is-it-possible-to-have-different-return-types-for-a-overloaded-method

#### Method Call Ambiguity
While coding, i noticed an edge case (see fail test case 6.in) where a method call could potentially be ambigeous when passing nulls as arguments;
Since it could potentially match many methods. What Java does is try to infer uniqueness by checking how many methods it could possibly be
if its 1 -> carry on and assume that as the method signature
else if its more than one -> throw an error!

```java
class Test {
     Int method(Dummy d) {
         return 1;
     }
 
     Boolean method(Dummy2 d) {
         return true;
     }
 
     Void test() {
         // throw error, ambiguous method call could be method(Dummy) or method(Dummy2)
         // return type is not used 
         int i = method(null);
     }
 }
```

### Type Checking
For type checking there was nothing to complex here since we did not have to deal with generics and inheritance!

A nice side effect of this is that for IR and code gen we always have the type information that is useful when creating a new instance of the class via `new` later on!

## Phase 3: IR Generation
For IR generation, 

## Phase 4: Code / Arm Generation

### Initial

### Lowering IR

### Register Allocation

### New

### String Concat

### Function Calls



## Phase 5: Post Optimizations

## Conclusion