/* Generated By:JJTree: Do not edit this line. ASTOr.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=location.query.BaseNode,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package location.query.parser;

public
class ASTOr extends SimpleNode {
  public ASTOr(int id) {
    super(id);
  }

  public ASTOr(QueryParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(QueryParserVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=e3a81be5fe617fba4dfd1040b0811db8 (do not edit this line) */
