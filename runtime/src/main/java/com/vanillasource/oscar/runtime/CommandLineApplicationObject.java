package com.vanillasource.oscar.runtime;

import java.io.IOException;
import java.io.DataInput;
import com.oracle.truffle.api.CallTarget;

public final class CommandLineApplicationObject {
   public CallTarget parse(DataInput input) throws IOException {
      input.readUTF(); // Don't need object name
      int methodCount = input.readInt();
      for (int methodIndex=0; methodIndex < methodCount; methodIndex++) {
         String methodName = input.readUTF();
         int methodBody = input.readInt();
         if (methodName.equals("main")) {
            return new OscarRootNode(new IntLiteralNode(methodBody)).getCallTarget();
         }
      }
      throw new IllegalStateException("main() not found in object");
   }
}
