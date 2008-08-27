//
// Generated by JTB 1.3.2
//

package jp.go.aist.rtm.rtctemplate.corba.idl.parser.visitor;
import jp.go.aist.rtm.rtctemplate.corba.idl.parser.syntaxtree.*;
import java.util.*;

/**
 * Provides default methods which visit each node in the tree in depth-first
 * order.  Your visitors may extend this class.
 */
public class GJVoidDepthFirst<A> implements GJVoidVisitor<A> {
   //
   // Auto class visitors--probably don't need to be overridden.
   //
   public void visit(NodeList n, A argu) {
      int _count=0;
      for ( Enumeration<Node> e = n.elements(); e.hasMoreElements(); ) {
         e.nextElement().accept(this,argu);
         _count++;
      }
   }

   public void visit(NodeListOptional n, A argu) {
      if ( n.present() ) {
         int _count=0;
         for ( Enumeration<Node> e = n.elements(); e.hasMoreElements(); ) {
            e.nextElement().accept(this,argu);
            _count++;
         }
      }
   }

   public void visit(NodeOptional n, A argu) {
      if ( n.present() )
         n.node.accept(this,argu);
   }

   public void visit(NodeSequence n, A argu) {
      int _count=0;
      for ( Enumeration<Node> e = n.elements(); e.hasMoreElements(); ) {
         e.nextElement().accept(this,argu);
         _count++;
      }
   }

   public void visit(NodeToken n, A argu) {}

   //
   // User-generated visitor methods below
   //

   /**
    * <PRE>
    * nodeList -> ( definition() )+
    * </PRE>
    */
   public void visit(specification n, A argu) {
      n.nodeList.accept(this, argu);
   }

   /**
    * <PRE>
    * nodeChoice -> type_dcl() ";"
    *       | const_dcl() ";"
    *       | except_dcl() ";"
    *       | interfacex() ";"
    *       | module() ";"
    * </PRE>
    */
   public void visit(definition n, A argu) {
      n.nodeChoice.accept(this, argu);
   }

   /**
    * <PRE>
    * nodeToken -> "module"
    * identifier -> identifier()
    * nodeToken1 -> "{"
    * nodeList -> ( definition() )+
    * nodeToken2 -> "}"
    * </PRE>
    */
   public void visit(module n, A argu) {
      n.nodeToken.accept(this, argu);
      n.identifier.accept(this, argu);
      n.nodeToken1.accept(this, argu);
      n.nodeList.accept(this, argu);
      n.nodeToken2.accept(this, argu);
   }

   /**
    * <PRE>
    * nodeChoice -> interface_dcl()
    *       | forward_dcl()
    * </PRE>
    */
   public void visit(interfacex n, A argu) {
      n.nodeChoice.accept(this, argu);
   }

   /**
    * <PRE>
    * interface_header -> interface_header()
    * nodeToken -> "{"
    * interface_body -> interface_body()
    * nodeToken1 -> "}"
    * </PRE>
    */
   public void visit(interface_dcl n, A argu) {
      n.interface_header.accept(this, argu);
      n.nodeToken.accept(this, argu);
      n.interface_body.accept(this, argu);
      n.nodeToken1.accept(this, argu);
   }

   /**
    * <PRE>
    * nodeToken -> "interface"
    * identifier -> identifier()
    * </PRE>
    */
   public void visit(forward_dcl n, A argu) {
      n.nodeToken.accept(this, argu);
      n.identifier.accept(this, argu);
   }

   /**
    * <PRE>
    * nodeToken -> "interface"
    * identifier -> identifier()
    * nodeOptional -> [ inheritance_spec() ]
    * </PRE>
    */
   public void visit(interface_header n, A argu) {
      n.nodeToken.accept(this, argu);
      n.identifier.accept(this, argu);
      n.nodeOptional.accept(this, argu);
   }

   /**
    * <PRE>
    * nodeListOptional -> ( export() )*
    * </PRE>
    */
   public void visit(interface_body n, A argu) {
      n.nodeListOptional.accept(this, argu);
   }

   /**
    * <PRE>
    * nodeChoice -> type_dcl() ";"
    *       | const_dcl() ";"
    *       | except_dcl() ";"
    *       | attr_dcl() ";"
    *       | op_dcl() ";"
    * </PRE>
    */
   public void visit(export n, A argu) {
      n.nodeChoice.accept(this, argu);
   }

   /**
    * <PRE>
    * nodeToken -> ":"
    * scoped_name -> scoped_name()
    * nodeListOptional -> ( "," scoped_name() )*
    * </PRE>
    */
   public void visit(inheritance_spec n, A argu) {
      n.nodeToken.accept(this, argu);
      n.scoped_name.accept(this, argu);
      n.nodeListOptional.accept(this, argu);
   }

   /**
    * <PRE>
    * nodeOptional -> [ "::" ]
    * identifier -> identifier()
    * nodeListOptional -> ( "::" identifier() )*
    * </PRE>
    */
   public void visit(scoped_name n, A argu) {
      n.nodeOptional.accept(this, argu);
      n.identifier.accept(this, argu);
      n.nodeListOptional.accept(this, argu);
   }

   /**
    * <PRE>
    * nodeToken -> "const"
    * const_type -> const_type()
    * identifier -> identifier()
    * nodeToken1 -> "="
    * const_exp -> const_exp()
    * </PRE>
    */
   public void visit(const_dcl n, A argu) {
      n.nodeToken.accept(this, argu);
      n.const_type.accept(this, argu);
      n.identifier.accept(this, argu);
      n.nodeToken1.accept(this, argu);
      n.const_exp.accept(this, argu);
   }

   /**
    * <PRE>
    * nodeChoice -> integer_type()
    *       | char_type()
    *       | boolean_type()
    *       | floating_pt_type()
    *       | string_type()
    *       | scoped_name()
    * </PRE>
    */
   public void visit(const_type n, A argu) {
      n.nodeChoice.accept(this, argu);
   }

   /**
    * <PRE>
    * or_expr -> or_expr()
    * </PRE>
    */
   public void visit(const_exp n, A argu) {
      n.or_expr.accept(this, argu);
   }

   /**
    * <PRE>
    * xor_expr -> xor_expr()
    * nodeListOptional -> ( "|" xor_expr() )*
    * </PRE>
    */
   public void visit(or_expr n, A argu) {
      n.xor_expr.accept(this, argu);
      n.nodeListOptional.accept(this, argu);
   }

   /**
    * <PRE>
    * and_expr -> and_expr()
    * nodeListOptional -> ( "^" and_expr() )*
    * </PRE>
    */
   public void visit(xor_expr n, A argu) {
      n.and_expr.accept(this, argu);
      n.nodeListOptional.accept(this, argu);
   }

   /**
    * <PRE>
    * shift_expr -> shift_expr()
    * nodeListOptional -> ( "&" shift_expr() )*
    * </PRE>
    */
   public void visit(and_expr n, A argu) {
      n.shift_expr.accept(this, argu);
      n.nodeListOptional.accept(this, argu);
   }

   /**
    * <PRE>
    * add_expr -> add_expr()
    * nodeListOptional -> ( ( "&gt;&gt;" | "&lt;&lt;" ) add_expr() )*
    * </PRE>
    */
   public void visit(shift_expr n, A argu) {
      n.add_expr.accept(this, argu);
      n.nodeListOptional.accept(this, argu);
   }

   /**
    * <PRE>
    * mult_expr -> mult_expr()
    * nodeListOptional -> ( ( "+" | "-" ) mult_expr() )*
    * </PRE>
    */
   public void visit(add_expr n, A argu) {
      n.mult_expr.accept(this, argu);
      n.nodeListOptional.accept(this, argu);
   }

   /**
    * <PRE>
    * unary_expr -> unary_expr()
    * nodeListOptional -> ( ( "*" | "/" | "%" ) unary_expr() )*
    * </PRE>
    */
   public void visit(mult_expr n, A argu) {
      n.unary_expr.accept(this, argu);
      n.nodeListOptional.accept(this, argu);
   }

   /**
    * <PRE>
    * nodeOptional -> [ unary_operator() ]
    * primary_expr -> primary_expr()
    * </PRE>
    */
   public void visit(unary_expr n, A argu) {
      n.nodeOptional.accept(this, argu);
      n.primary_expr.accept(this, argu);
   }

   /**
    * <PRE>
    * nodeChoice -> "-"
    *       | "+"
    *       | "~"
    * </PRE>
    */
   public void visit(unary_operator n, A argu) {
      n.nodeChoice.accept(this, argu);
   }

   /**
    * <PRE>
    * nodeChoice -> scoped_name()
    *       | literal()
    *       | "(" const_exp() ")"
    * </PRE>
    */
   public void visit(primary_expr n, A argu) {
      n.nodeChoice.accept(this, argu);
   }

   /**
    * <PRE>
    * nodeChoice -> integer_literal()
    *       | string_literal()
    *       | character_literal()
    *       | floating_pt_literal()
    *       | boolean_literal()
    * </PRE>
    */
   public void visit(literal n, A argu) {
      n.nodeChoice.accept(this, argu);
   }

   /**
    * <PRE>
    * nodeChoice -> "TRUE"
    *       | "FALSE"
    * </PRE>
    */
   public void visit(boolean_literal n, A argu) {
      n.nodeChoice.accept(this, argu);
   }

   /**
    * <PRE>
    * const_exp -> const_exp()
    * </PRE>
    */
   public void visit(positive_int_const n, A argu) {
      n.const_exp.accept(this, argu);
   }

   /**
    * <PRE>
    * nodeChoice -> "typedef" type_declarator()
    *       | struct_type()
    *       | union_type()
    *       | enum_type()
    * </PRE>
    */
   public void visit(type_dcl n, A argu) {
      n.nodeChoice.accept(this, argu);
   }

   /**
    * <PRE>
    * type_spec -> type_spec()
    * declarators -> declarators()
    * </PRE>
    */
   public void visit(type_declarator n, A argu) {
      n.type_spec.accept(this, argu);
      n.declarators.accept(this, argu);
   }

   /**
    * <PRE>
    * nodeChoice -> simple_type_spec()
    *       | constr_type_spec()
    * </PRE>
    */
   public void visit(type_spec n, A argu) {
      n.nodeChoice.accept(this, argu);
   }

   /**
    * <PRE>
    * nodeChoice -> base_type_spec()
    *       | template_type_spec()
    *       | scoped_name()
    * </PRE>
    */
   public void visit(simple_type_spec n, A argu) {
      n.nodeChoice.accept(this, argu);
   }

   /**
    * <PRE>
    * nodeChoice -> floating_pt_type()
    *       | integer_type()
    *       | char_type()
    *       | boolean_type()
    *       | octet_type()
    *       | any_type()
    * </PRE>
    */
   public void visit(base_type_spec n, A argu) {
      n.nodeChoice.accept(this, argu);
   }

   /**
    * <PRE>
    * nodeChoice -> sequence_type()
    *       | string_type()
    * </PRE>
    */
   public void visit(template_type_spec n, A argu) {
      n.nodeChoice.accept(this, argu);
   }

   /**
    * <PRE>
    * nodeChoice -> struct_type()
    *       | union_type()
    *       | enum_type()
    * </PRE>
    */
   public void visit(constr_type_spec n, A argu) {
      n.nodeChoice.accept(this, argu);
   }

   /**
    * <PRE>
    * declarator -> declarator()
    * nodeListOptional -> ( "," declarator() )*
    * </PRE>
    */
   public void visit(declarators n, A argu) {
      n.declarator.accept(this, argu);
      n.nodeListOptional.accept(this, argu);
   }

   /**
    * <PRE>
    * nodeChoice -> complex_declarator()
    *       | simple_declarator()
    * </PRE>
    */
   public void visit(declarator n, A argu) {
      n.nodeChoice.accept(this, argu);
   }

   /**
    * <PRE>
    * identifier -> identifier()
    * </PRE>
    */
   public void visit(simple_declarator n, A argu) {
      n.identifier.accept(this, argu);
   }

   /**
    * <PRE>
    * array_declarator -> array_declarator()
    * </PRE>
    */
   public void visit(complex_declarator n, A argu) {
      n.array_declarator.accept(this, argu);
   }

   /**
    * <PRE>
    * nodeChoice -> "float"
    *       | "double"
    * </PRE>
    */
   public void visit(floating_pt_type n, A argu) {
      n.nodeChoice.accept(this, argu);
   }

   /**
    * <PRE>
    * nodeChoice -> signed_int()
    *       | unsigned_int()
    * </PRE>
    */
   public void visit(integer_type n, A argu) {
      n.nodeChoice.accept(this, argu);
   }

   /**
    * <PRE>
    * nodeChoice -> signed_long_long_int()
    *       | signed_long_double_int()
    *       | signed_long_int()
    *       | signed_short_int()
    * </PRE>
    */
   public void visit(signed_int n, A argu) {
      n.nodeChoice.accept(this, argu);
   }

   /**
    * <PRE>
    * nodeToken -> "long"
    * </PRE>
    */
   public void visit(signed_long_int n, A argu) {
      n.nodeToken.accept(this, argu);
   }

   /**
    * <PRE>
    * nodeToken -> "short"
    * </PRE>
    */
   public void visit(signed_short_int n, A argu) {
      n.nodeToken.accept(this, argu);
   }

   /**
    * <PRE>
    * nodeToken -> "long"
    * nodeToken1 -> "long"
    * </PRE>
    */
   public void visit(signed_long_long_int n, A argu) {
      n.nodeToken.accept(this, argu);
      n.nodeToken1.accept(this, argu);
   }

   /**
    * <PRE>
    * nodeToken -> "long"
    * nodeToken1 -> "double"
    * </PRE>
    */
   public void visit(signed_long_double_int n, A argu) {
      n.nodeToken.accept(this, argu);
      n.nodeToken1.accept(this, argu);
   }

   /**
    * <PRE>
    * nodeChoice -> unsigned_long_long_int()
    *       | unsigned_long_int()
    *       | unsigned_short_int()
    * </PRE>
    */
   public void visit(unsigned_int n, A argu) {
      n.nodeChoice.accept(this, argu);
   }

   /**
    * <PRE>
    * nodeToken -> "unsigned"
    * nodeToken1 -> "long"
    * </PRE>
    */
   public void visit(unsigned_long_int n, A argu) {
      n.nodeToken.accept(this, argu);
      n.nodeToken1.accept(this, argu);
   }

   /**
    * <PRE>
    * nodeToken -> "unsigned"
    * nodeToken1 -> "short"
    * </PRE>
    */
   public void visit(unsigned_short_int n, A argu) {
      n.nodeToken.accept(this, argu);
      n.nodeToken1.accept(this, argu);
   }

   /**
    * <PRE>
    * nodeToken -> "unsigned"
    * nodeToken1 -> "long"
    * nodeToken2 -> "long"
    * </PRE>
    */
   public void visit(unsigned_long_long_int n, A argu) {
      n.nodeToken.accept(this, argu);
      n.nodeToken1.accept(this, argu);
      n.nodeToken2.accept(this, argu);
   }

   /**
    * <PRE>
    * nodeToken -> "char"
    * </PRE>
    */
   public void visit(char_type n, A argu) {
      n.nodeToken.accept(this, argu);
   }

   /**
    * <PRE>
    * nodeToken -> "boolean"
    * </PRE>
    */
   public void visit(boolean_type n, A argu) {
      n.nodeToken.accept(this, argu);
   }

   /**
    * <PRE>
    * nodeToken -> "octet"
    * </PRE>
    */
   public void visit(octet_type n, A argu) {
      n.nodeToken.accept(this, argu);
   }

   /**
    * <PRE>
    * nodeToken -> "any"
    * </PRE>
    */
   public void visit(any_type n, A argu) {
      n.nodeToken.accept(this, argu);
   }

   /**
    * <PRE>
    * nodeToken -> "struct"
    * identifier -> identifier()
    * nodeToken1 -> "{"
    * member_list -> member_list()
    * nodeToken2 -> "}"
    * </PRE>
    */
   public void visit(struct_type n, A argu) {
      n.nodeToken.accept(this, argu);
      n.identifier.accept(this, argu);
      n.nodeToken1.accept(this, argu);
      n.member_list.accept(this, argu);
      n.nodeToken2.accept(this, argu);
   }

   /**
    * <PRE>
    * nodeList -> ( member() )+
    * </PRE>
    */
   public void visit(member_list n, A argu) {
      n.nodeList.accept(this, argu);
   }

   /**
    * <PRE>
    * type_spec -> type_spec()
    * declarators -> declarators()
    * nodeToken -> ";"
    * </PRE>
    */
   public void visit(member n, A argu) {
      n.type_spec.accept(this, argu);
      n.declarators.accept(this, argu);
      n.nodeToken.accept(this, argu);
   }

   /**
    * <PRE>
    * nodeToken -> "union"
    * identifier -> identifier()
    * nodeToken1 -> "switch"
    * nodeToken2 -> "("
    * switch_type_spec -> switch_type_spec()
    * nodeToken3 -> ")"
    * nodeToken4 -> "{"
    * switch_body -> switch_body()
    * nodeToken5 -> "}"
    * </PRE>
    */
   public void visit(union_type n, A argu) {
      n.nodeToken.accept(this, argu);
      n.identifier.accept(this, argu);
      n.nodeToken1.accept(this, argu);
      n.nodeToken2.accept(this, argu);
      n.switch_type_spec.accept(this, argu);
      n.nodeToken3.accept(this, argu);
      n.nodeToken4.accept(this, argu);
      n.switch_body.accept(this, argu);
      n.nodeToken5.accept(this, argu);
   }

   /**
    * <PRE>
    * nodeChoice -> integer_type()
    *       | char_type()
    *       | boolean_type()
    *       | enum_type()
    *       | scoped_name()
    * </PRE>
    */
   public void visit(switch_type_spec n, A argu) {
      n.nodeChoice.accept(this, argu);
   }

   /**
    * <PRE>
    * nodeList -> ( casex() )+
    * </PRE>
    */
   public void visit(switch_body n, A argu) {
      n.nodeList.accept(this, argu);
   }

   /**
    * <PRE>
    * nodeList -> ( case_label() )+
    * element_spec -> element_spec()
    * nodeToken -> ";"
    * </PRE>
    */
   public void visit(casex n, A argu) {
      n.nodeList.accept(this, argu);
      n.element_spec.accept(this, argu);
      n.nodeToken.accept(this, argu);
   }

   /**
    * <PRE>
    * nodeChoice -> "case" const_exp() ":"
    *       | "default" ":"
    * </PRE>
    */
   public void visit(case_label n, A argu) {
      n.nodeChoice.accept(this, argu);
   }

   /**
    * <PRE>
    * type_spec -> type_spec()
    * declarator -> declarator()
    * </PRE>
    */
   public void visit(element_spec n, A argu) {
      n.type_spec.accept(this, argu);
      n.declarator.accept(this, argu);
   }

   /**
    * <PRE>
    * nodeToken -> "enum"
    * identifier -> identifier()
    * nodeToken1 -> "{"
    * enumerator -> enumerator()
    * nodeListOptional -> ( "," enumerator() )*
    * nodeToken2 -> "}"
    * </PRE>
    */
   public void visit(enum_type n, A argu) {
      n.nodeToken.accept(this, argu);
      n.identifier.accept(this, argu);
      n.nodeToken1.accept(this, argu);
      n.enumerator.accept(this, argu);
      n.nodeListOptional.accept(this, argu);
      n.nodeToken2.accept(this, argu);
   }

   /**
    * <PRE>
    * identifier -> identifier()
    * </PRE>
    */
   public void visit(enumerator n, A argu) {
      n.identifier.accept(this, argu);
   }

   /**
    * <PRE>
    * nodeToken -> "sequence"
    * nodeToken1 -> "&lt;"
    * simple_type_spec -> simple_type_spec()
    * nodeOptional -> [ "," positive_int_const() ]
    * nodeToken2 -> "&gt;"
    * </PRE>
    */
   public void visit(sequence_type n, A argu) {
      n.nodeToken.accept(this, argu);
      n.nodeToken1.accept(this, argu);
      n.simple_type_spec.accept(this, argu);
      n.nodeOptional.accept(this, argu);
      n.nodeToken2.accept(this, argu);
   }

   /**
    * <PRE>
    * nodeToken -> "string"
    * nodeOptional -> [ "&lt;" positive_int_const() "&gt;" ]
    * </PRE>
    */
   public void visit(string_type n, A argu) {
      n.nodeToken.accept(this, argu);
      n.nodeOptional.accept(this, argu);
   }

   /**
    * <PRE>
    * identifier -> identifier()
    * nodeList -> ( fixed_array_size() )+
    * </PRE>
    */
   public void visit(array_declarator n, A argu) {
      n.identifier.accept(this, argu);
      n.nodeList.accept(this, argu);
   }

   /**
    * <PRE>
    * nodeToken -> "["
    * positive_int_const -> positive_int_const()
    * nodeToken1 -> "]"
    * </PRE>
    */
   public void visit(fixed_array_size n, A argu) {
      n.nodeToken.accept(this, argu);
      n.positive_int_const.accept(this, argu);
      n.nodeToken1.accept(this, argu);
   }

   /**
    * <PRE>
    * nodeOptional -> [ "readonly" ]
    * nodeToken -> "attribute"
    * param_type_spec -> param_type_spec()
    * simple_declarator -> simple_declarator()
    * nodeListOptional -> ( "," simple_declarator() )*
    * </PRE>
    */
   public void visit(attr_dcl n, A argu) {
      n.nodeOptional.accept(this, argu);
      n.nodeToken.accept(this, argu);
      n.param_type_spec.accept(this, argu);
      n.simple_declarator.accept(this, argu);
      n.nodeListOptional.accept(this, argu);
   }

   /**
    * <PRE>
    * nodeToken -> "exception"
    * identifier -> identifier()
    * nodeToken1 -> "{"
    * nodeListOptional -> ( member() )*
    * nodeToken2 -> "}"
    * </PRE>
    */
   public void visit(except_dcl n, A argu) {
      n.nodeToken.accept(this, argu);
      n.identifier.accept(this, argu);
      n.nodeToken1.accept(this, argu);
      n.nodeListOptional.accept(this, argu);
      n.nodeToken2.accept(this, argu);
   }

   /**
    * <PRE>
    * nodeOptional -> [ op_attribute() ]
    * op_type_spec -> op_type_spec()
    * identifier -> identifier()
    * parameter_dcls -> parameter_dcls()
    * nodeOptional1 -> [ raises_expr() ]
    * nodeOptional2 -> [ context_expr() ]
    * </PRE>
    */
   public void visit(op_dcl n, A argu) {
      n.nodeOptional.accept(this, argu);
      n.op_type_spec.accept(this, argu);
      n.identifier.accept(this, argu);
      n.parameter_dcls.accept(this, argu);
      n.nodeOptional1.accept(this, argu);
      n.nodeOptional2.accept(this, argu);
   }

   /**
    * <PRE>
    * nodeToken -> "oneway"
    * </PRE>
    */
   public void visit(op_attribute n, A argu) {
      n.nodeToken.accept(this, argu);
   }

   /**
    * <PRE>
    * nodeChoice -> param_type_spec()
    *       | "void"
    * </PRE>
    */
   public void visit(op_type_spec n, A argu) {
      n.nodeChoice.accept(this, argu);
   }

   /**
    * <PRE>
    * nodeToken -> "("
    * nodeOptional -> [ param_dcl() ( "," param_dcl() )* ]
    * nodeToken1 -> ")"
    * </PRE>
    */
   public void visit(parameter_dcls n, A argu) {
      n.nodeToken.accept(this, argu);
      n.nodeOptional.accept(this, argu);
      n.nodeToken1.accept(this, argu);
   }

   /**
    * <PRE>
    * param_attribute -> param_attribute()
    * param_type_spec -> param_type_spec()
    * simple_declarator -> simple_declarator()
    * </PRE>
    */
   public void visit(param_dcl n, A argu) {
      n.param_attribute.accept(this, argu);
      n.param_type_spec.accept(this, argu);
      n.simple_declarator.accept(this, argu);
   }

   /**
    * <PRE>
    * nodeChoice -> "in"
    *       | "out"
    *       | "inout"
    * </PRE>
    */
   public void visit(param_attribute n, A argu) {
      n.nodeChoice.accept(this, argu);
   }

   /**
    * <PRE>
    * nodeToken -> "raises"
    * nodeToken1 -> "("
    * scoped_name -> scoped_name()
    * nodeListOptional -> ( "," scoped_name() )*
    * nodeToken2 -> ")"
    * </PRE>
    */
   public void visit(raises_expr n, A argu) {
      n.nodeToken.accept(this, argu);
      n.nodeToken1.accept(this, argu);
      n.scoped_name.accept(this, argu);
      n.nodeListOptional.accept(this, argu);
      n.nodeToken2.accept(this, argu);
   }

   /**
    * <PRE>
    * nodeToken -> "context"
    * nodeToken1 -> "("
    * string_literal -> string_literal()
    * nodeListOptional -> ( "," string_literal() )*
    * nodeToken2 -> ")"
    * </PRE>
    */
   public void visit(context_expr n, A argu) {
      n.nodeToken.accept(this, argu);
      n.nodeToken1.accept(this, argu);
      n.string_literal.accept(this, argu);
      n.nodeListOptional.accept(this, argu);
      n.nodeToken2.accept(this, argu);
   }

   /**
    * <PRE>
    * nodeChoice -> base_type_spec()
    *       | string_type()
    *       | scoped_name()
    * </PRE>
    */
   public void visit(param_type_spec n, A argu) {
      n.nodeChoice.accept(this, argu);
   }

   /**
    * <PRE>
    * nodeToken -> &lt;ID&gt;
    * </PRE>
    */
   public void visit(identifier n, A argu) {
      n.nodeToken.accept(this, argu);
   }

   /**
    * <PRE>
    * nodeChoice -> &lt;OCTALINT&gt;
    *       | &lt;DECIMALINT&gt;
    *       | &lt;HEXADECIMALINT&gt;
    * </PRE>
    */
   public void visit(integer_literal n, A argu) {
      n.nodeChoice.accept(this, argu);
   }

   /**
    * <PRE>
    * nodeToken -> &lt;STRING&gt;
    * </PRE>
    */
   public void visit(string_literal n, A argu) {
      n.nodeToken.accept(this, argu);
   }

   /**
    * <PRE>
    * nodeToken -> &lt;CHARACTER&gt;
    * </PRE>
    */
   public void visit(character_literal n, A argu) {
      n.nodeToken.accept(this, argu);
   }

   /**
    * <PRE>
    * nodeChoice -> &lt;FLOATONE&gt;
    *       | &lt;FLOATTWO&gt;
    * </PRE>
    */
   public void visit(floating_pt_literal n, A argu) {
      n.nodeChoice.accept(this, argu);
   }

}
