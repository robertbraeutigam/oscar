package com.vanillasource.oscar.compiler.parser;

import com.vanillasource.oscar.compiler.tokenizer.Token;
import static com.vanillasource.oscar.compiler.tokenizer.Token.*;
import org.typemeta.funcj.parser.Parser;
import java.io.DataOutput;
import java.io.IOException;

public final class OscarMethod {
   public static final Parser<Token, OscarMethod> PARSER =
      token("def")
      .andR(anyToken())
      .andL(token("(")).andL(token(")")).andL(token("="))
      .and(anyNumber())
      .map(OscarMethod::new);

   private final String methodName;
   private final int body;

   public OscarMethod(String methodName, int body) {
      this.methodName = methodName;
      this.body = body;
   }

   public void compileTo(DataOutput out) throws IOException {
      out.writeUTF(methodName);
      out.writeInt(body);
   }

   @Override
   public String toString() {
      return "Method \""+methodName+"\": "+body;
   }

   @Override
   public boolean equals(Object o) {
      if ((o==null)||(!(o instanceof OscarMethod))) {
         return false;
      }
      OscarMethod other = (OscarMethod) o;
      return methodName.equals(other.methodName) && body == other.body;
   }
}
