package com.vanillasource.oscar.compiler.parser;

import java.io.DataOutput;
import org.typemeta.funcj.data.IList;
import static org.typemeta.funcj.parser.Text.*;
import org.typemeta.funcj.data.Chr;
import org.typemeta.funcj.parser.Parser;
import java.io.IOException;

public final class OscarObject {
   public static final Parser<Chr, OscarObject> PARSER = ws.many().andR(string("object")).andR(ws.many())
      .andR(alpha.many1().map(Chr::listToString))
      .and(OscarMethod.PARSER.many()
            .between(string("{").between(ws.many(), ws.many()), string("}").between(ws.many(), ws.many())))
      .map(OscarObject::new);

   private final String objectName;
   private final IList<OscarMethod> methods;

   public OscarObject(String objectName, IList<OscarMethod> methods) {
      this.objectName = objectName;
      this.methods = methods;
   }

   public void compileTo(DataOutput out) throws IOException {
      out.writeUTF(objectName);
      out.writeInt(methods.size());
      for (OscarMethod method: methods) {
         method.compileTo(out);
      }
   }
}
