package main.java.staticcheckers.type;

import java.util.HashMap;

import main.java.parsetree.shared.Type;

public class ClassDescriptor {
   private String cname;
   private HashMap<String, BasicType> fields;
   private HashMap<String, FunctionType> methods;

   public ClassDescriptor(String cname, HashMap<String, BasicType> fields, HashMap<String, FunctionType> methods) {
      this.cname = cname;
      this.fields = fields;
      this.methods = methods;
   }

   @Override
   public String toString() {
      return "ClassDescriptor{" +
          "cname='" + cname + '\'' +
          ", fields=" + fields +
          ", methods=" + methods +
          '}';
   }
}
