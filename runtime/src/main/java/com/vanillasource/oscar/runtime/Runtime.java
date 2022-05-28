package com.vanillasource.oscar.runtime;

import java.io.File;
import java.io.IOException;
import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Value;
import org.graalvm.polyglot.Source;

public final class Runtime {
   private final String objectName;

   public Runtime(String objectName) {
      this.objectName = objectName;
   }

   public static final void main(String[] args) {
      if (args.length < 1) {
         error("USAGE: os <object>");
      } else {
         new Runtime(args[0]).run();
      }
   }

   public void run() {
      File objectFile = new File(objectName+".osc");
      if (!objectFile.isFile()) {
         error("os: can't find object '"+objectName+"'");
      }
      try {
         Value value = Context.create().eval(Source.newBuilder("osc", objectFile).build());
         System.exit(value.asInt());
      } catch (IOException e) {
         error("oc: error while executing '"+objectName+"': "+e.getMessage());
      }
   }

   private static void error(String errorMessage) {
      System.err.println(errorMessage);
      System.exit(1);
   }
}
