package com.vanillasource.oscar.runtime;

import org.testng.annotations.Test;
import com.oracle.truffle.api.Truffle;
import static org.testng.Assert.*;

@Test
public final class IntLiteralNodeTests {
   public void testReturnsTheLiteral() {
      int result = (int) execute(new IntLiteralNode(12));

      assertEquals(result, 12);
   }

   private Object execute(IntLiteralNode node) {
      return Truffle.getRuntime().createDirectCallNode(new OscarRootNode(node).getCallTarget()).call();
   }
}
