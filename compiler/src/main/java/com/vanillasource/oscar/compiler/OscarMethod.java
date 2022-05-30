package com.vanillasource.oscar.compiler;

import org.typemeta.funcj.parser.Parser;
import static org.typemeta.funcj.parser.Text.*;
import org.typemeta.funcj.data.Chr;
import java.io.DataOutput;
import java.io.IOException;

public final class OscarMethod {
   public static final Parser<Chr, OscarMethod> PARSER =
      string("def").andL(ws.many1())
      .andR(alpha.many1().map(Chr::listToString))
      .andL(string("(").between(ws.many(), ws.many()))
      .andL(string(")").between(ws.many(), ws.many()))
      .andL(string("=").between(ws.many(), ws.many()))
      .and(intr)
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
