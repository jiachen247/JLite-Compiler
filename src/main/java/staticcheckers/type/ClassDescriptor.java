package main.java.staticcheckers.type;

import java.util.HashMap;

import main.java.parsetree.MdSignature;
import main.java.parsetree.shared.Id;
import main.java.parsetree.shared.Type;

public class ClassDescriptor {
   private BasicType cname;
   private HashMap<Id, BasicType> fields;

   public BasicType getCname() {
      return cname;
   }

   public HashMap<Id, BasicType> getFields() {
      return fields;
   }

   public HashMap<MdSignature, FunctionType> getMethods() {
      return methods;
   }

   private HashMap<MdSignature, FunctionType> methods;

   public ClassDescriptor(String cname, HashMap<Id, BasicType> fields, HashMap<MdSignature, FunctionType> methods) {
      this.cname = new BasicType(cname);
      this.fields = fields;
      this.methods = methods;
   }

   public ClassDescriptor(BasicType type) {
      this.cname = type;
      this.fields = new HashMap<>();
      this.methods = new HashMap<>();
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
