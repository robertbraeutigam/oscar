package com.vanillasource.oscar;

import com.oracle.truffle.api.Truffle;
import org.junit.Test;
import static org.junit.Assert.*;

public final class IntLiteralNodeTests {
   @Test
   public void testReturnsTheLiteral() {
      int result = (int) execute(new IntLiteralNode(12));

      assertEquals(12, result);
   }

   private Object execute(IntLiteralNode node) {
      return Truffle.getRuntime().createDirectCallNode(new OscarRootNode(node).getCallTarget()).call();
   }
}
