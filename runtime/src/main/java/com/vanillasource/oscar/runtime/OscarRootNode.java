package com.vanillasource.oscar.runtime;

import com.oracle.truffle.api.nodes.RootNode;
import com.oracle.truffle.api.frame.VirtualFrame;

public final class OscarRootNode extends RootNode {
   @Child
   private IntLiteralNode node;

   public OscarRootNode(IntLiteralNode node) {
      super(null);
      this.node = node;
   }

   @Override
   public Object execute(VirtualFrame frame) {
      return node.executeEvaluation(frame);
   }
}
