package com.vanillasource.oscar.runtime;

import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.frame.VirtualFrame;

public final class IntLiteralNode extends Node {
   private final int value;

   public IntLiteralNode(int value) {
      this.value = value;
   }

   public int executeEvaluation(VirtualFrame frame) {
      return value;
   }
}
