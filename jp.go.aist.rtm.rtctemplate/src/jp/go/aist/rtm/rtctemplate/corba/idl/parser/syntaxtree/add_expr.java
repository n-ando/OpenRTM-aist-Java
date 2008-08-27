//
// Generated by JTB 1.3.2
//

package jp.go.aist.rtm.rtctemplate.corba.idl.parser.syntaxtree;

/**
 * Grammar production:
 * <PRE>
 * mult_expr -> mult_expr()
 * nodeListOptional -> ( ( "+" | "-" ) mult_expr() )*
 * </PRE>
 */
public class add_expr implements Node {
   private Node parent;
   public mult_expr mult_expr;
   public NodeListOptional nodeListOptional;

   public add_expr(mult_expr n0, NodeListOptional n1) {
      mult_expr = n0;
      if ( mult_expr != null ) mult_expr.setParent(this);
      nodeListOptional = n1;
      if ( nodeListOptional != null ) nodeListOptional.setParent(this);
   }

   public void accept(jp.go.aist.rtm.rtctemplate.corba.idl.parser.visitor.Visitor v) {
      v.visit(this);
   }
   public <R,A> R accept(jp.go.aist.rtm.rtctemplate.corba.idl.parser.visitor.GJVisitor<R,A> v, A argu) {
      return v.visit(this,argu);
   }
   public <R> R accept(jp.go.aist.rtm.rtctemplate.corba.idl.parser.visitor.GJNoArguVisitor<R> v) {
      return v.visit(this);
   }
   public <A> void accept(jp.go.aist.rtm.rtctemplate.corba.idl.parser.visitor.GJVoidVisitor<A> v, A argu) {
      v.visit(this,argu);
   }
   public void setParent(Node n) { parent = n; }
   public Node getParent()       { return parent; }
}

