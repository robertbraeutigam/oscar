package com.vanillasource.oscar.compiler.parser;

import com.vanillasource.oscar.compiler.tokenizer.Token;
import static com.vanillasource.oscar.compiler.tokenizer.Token.*;
import java.io.DataOutput;
import org.typemeta.funcj.data.IList;
import org.typemeta.funcj.parser.Parser;
import java.io.IOException;

public final class OscarObject {
   public static final Parser<Token, String> OBJECT_NAME = matchingToken("object name",
         name -> Character.isUpperCase(name.charAt(0))); // Note: all other characters are from category identifier
   public static final Parser<Token, OscarObject> PARSER =
      token("object")
      .andR(OBJECT_NAME)
      .and(OscarMethod.PARSER.many().between(token("{"), token("}")))
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
