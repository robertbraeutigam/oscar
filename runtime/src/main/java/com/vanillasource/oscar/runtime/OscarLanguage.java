package com.vanillasource.oscar.runtime;

import com.oracle.truffle.api.TruffleLanguage;
import com.oracle.truffle.api.CallTarget;
import java.io.DataInputStream;

@TruffleLanguage.Registration(id="osc", name="Oscar Compiled")
public final class OscarLanguage extends TruffleLanguage<Void> {
   @Override
   protected CallTarget parse(ParsingRequest request) throws Exception {
      try (
            DataInputStream in = new DataInputStream(request.getSource().getURL().openStream());
      ) {
         IntLiteralNode node = new IntLiteralNode(in.readInt());
         return new OscarRootNode(node).getCallTarget();
      }
   }

   @Override
   protected Void createContext(Env env) {
      return null;
   }
}
