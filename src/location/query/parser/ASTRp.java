/* Generated By:JJTree: Do not edit this line. ASTRp.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=location.query.BaseNode,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package location.query.parser;

public
class ASTRp extends SimpleNode {
  public ASTRp(int id) {
    super(id);
  }

  public ASTRp(QueryParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(QueryParserVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=5784716d273d42f8f39f7c11197c4cac (do not edit this line) */
