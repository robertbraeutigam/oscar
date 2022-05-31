package com.vanillasource.oscar.compiler.compiler;

import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileOutputStream;
import java.io.DataOutputStream;
import org.typemeta.funcj.parser.Input;
import org.typemeta.funcj.parser.Result.FailureOnExpected;
import org.typemeta.funcj.parser.Result.FailureMessage;
import java.io.DataOutput;
import java.io.UncheckedIOException;
import com.vanillasource.oscar.compiler.chars.OscarInput;
import com.vanillasource.oscar.compiler.parser.OscarObject;
import com.vanillasource.oscar.compiler.tokenizer.Token;
import com.vanillasource.oscar.compiler.tokenizer.OscarTokenizer;

public final class Compiler {
   public static final void main(String[] args) {
      if (args.length < 1) {
         mainError("USAGE: osc <filename>");
      } else {
         File sourceFile = new File(args[0]);
         if (!sourceFile.isFile()) {
            mainError("osc: can't find file '"+args[0]+"'");
         }
         try {
            new Compiler().compile(sourceFile);
         } catch (IOException|UncheckedIOException e) {
            mainError("osc: i/o error: "+e.getMessage());
         }
      }
   }

   private static void mainError(String errorMessage) {
      System.err.println(errorMessage);
      System.exit(1);
   }

   public void compile(File sourceFile) throws IOException {
      StringBuilder sourceCode = new StringBuilder();
      try (BufferedReader in = new BufferedReader(new FileReader(sourceFile))) {
         String content;
         while ((content = in.readLine()) != null) {
            sourceCode.append(content+"\n");
         }
      }
      compile(OscarTokenizer.of(OscarInput.of(sourceFile.getName(), sourceCode.toString())),
            new DataOutputStream(new FileOutputStream(sourceFile.getName()+"c")),
            new StdOutMessages());
   }

   public void compile(String source, DataOutput output, CompilerMessages messages) {
      compile(OscarTokenizer.of(OscarInput.of(source)), output, messages);
   }

   private void compile(Input<Token> input, DataOutput output, CompilerMessages messages) {
      OscarObject.PARSER.parse(input)
         .handle(
               success -> {
                  try {
                     success.value().compileTo(output);
                  } catch (IOException e) {
                     throw new UncheckedIOException(e);
                  }
               },
               failure -> {
                  if (failure instanceof FailureOnExpected) {
                     messages.addError(failure.input().get().printStartPosition(), "expected "+((FailureOnExpected)failure).expected(), "");
                  } else if (failure instanceof FailureMessage) {
                     messages.addError(failure.input().get().printStartPosition(), ((FailureMessage)failure).expected(), "");
                  } else {
                     messages.addError(failure.input().get().printStartPosition(), failure.toString(), "");
                  }
               });
   }

   private static final class StdOutMessages implements CompilerMessages {
      @Override
      public void addError(String position, String message, String description) {
         System.out.println(position+":error:"+message+((description.isEmpty())?(""):("\n"+description)));
      }
   }
}
