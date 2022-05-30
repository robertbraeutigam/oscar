package com.vanillasource.oscar.runtime;

import java.io.File;
import java.io.FileInputStream;
import java.io.DataInputStream;
import com.oracle.truffle.api.Truffle;
import java.io.IOException;

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
         try (
            DataInputStream in = new DataInputStream(new FileInputStream(objectFile));
         ) {
            Object value = Truffle.getRuntime().createDirectCallNode(
                  new CommandLineApplicationObject().parse(in)).call();
            System.exit((Integer) value);
         }
      } catch (IOException e) {
         error("oc: error while executing '"+objectName+"': "+e.getMessage());
      }
   }

   private static void error(String errorMessage) {
      System.err.println(errorMessage);
      System.exit(1);
   }
}
