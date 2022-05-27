package com.vanillasource.oscar.compiler;

import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileOutputStream;
import java.io.DataOutputStream;

public final class Compiler {
   private final File sourceFile;

   public Compiler(File sourceFile) {
      this.sourceFile = sourceFile;
   }

   public static final void main(String[] args) {
      if (args.length < 1) {
         error("USAGE: osc <filename>");
      } else {
         File sourceFile = new File(args[0]);
         if (!sourceFile.isFile()) {
            error("osc: can't find file '"+args[0]+"'");
         }
         new Compiler(sourceFile).compile();
      }
   }

   private static void error(String errorMessage) {
      System.err.println(errorMessage);
      System.exit(1);
   }

   public void compile() {
      try {
         try (
            BufferedReader in = new BufferedReader(new FileReader(sourceFile));
            DataOutputStream out = new DataOutputStream(new FileOutputStream(sourceFile.getName()+"c"));
         ) {
            String content = in.readLine();
            out.writeInt(Integer.valueOf(content));
         }
         
      } catch (IOException e) {
         error("osc: can't read file '"+sourceFile.getName()+"': "+e.getMessage());
      } catch (Exception e) {
         error("osc: error parsing file '"+sourceFile.getName()+"': "+e.getMessage());
      }
   }
}
