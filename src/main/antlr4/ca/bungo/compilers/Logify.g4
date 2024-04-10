grammar Logify;

@header {
import ca.bungo.compilers.logic.data.*;
import ca.bungo.compilers.logic.expressions.*;
}

@members {

}

program returns [Expression expr]
    : {List<Expression> stmts = new ArrayList<>();}
      (s=statement {stmts.add($s.expr);})* EOF
      {$expr = new BlockExpr(stmts);}
    ;

statement returns [Expression expr]
    : a=assignment {$expr = $a.expr;}
    | f=forLoop {$expr = $f.expr;}
    | fd=functionDef {$expr = $fd.expr;}
    | ifs=ifBlock {$expr = $ifs.expr;}
    | ex=expression SEMI {$expr = $ex.expr;}
    | pr=print SEMI? {$expr = $pr.expr;}
    | fc=functionCall SEMI? {$expr = $fc.expr;}
    ;

forLoop returns [Expression expr]
    :  {List<Expression> loopStmts = new ArrayList<>();}
    FOR LPARAN ID IN e=expression RPARAN OBLOCK (stmt=statement {loopStmts.add($stmt.expr);})* EBLOCK
      {
          $expr = new ForLoopExpr($ID.text, $e.expr, loopStmts);
      }
    ;

params returns [List<String> ids]
    : {$ids = new ArrayList<>();}
      ID { $ids.add($ID.text); } (COMMA ID { $ids.add($ID.text); })*
    ;

assignment returns [Expression expr]
    : ID EQL expression SEMI? {$expr = new AssignExpr($ID.text, $expression.expr);}
    ;

expression returns [Expression expr]
    : STRING {$expr = new StringExpr($STRING.text.substring(1, $STRING.text.length() - 1));}
    | s=INT RANGE e=INT {$expr = new RangeExpr(Integer.parseInt($s.text), Integer.parseInt($e.text));}
    | INT {$expr = new IntExpr(Integer.parseInt($INT.text));}
    | ID {$expr = new VarExpr($ID.text);}
    | fc=functionCall {$expr = $fc.expr;}
    | el=expression PLUS er=expression {$expr = new BinaryExpr($el.expr, "+", $er.expr);}
    | el=expression MINUS er=expression {$expr = new BinaryExpr($el.expr, "-", $er.expr);}
    | el=expression MULT er=expression {$expr = new BinaryExpr($el.expr, "*", $er.expr);}
    | el=expression DIV er=expression {$expr = new BinaryExpr($el.expr, "/", $er.expr);}
    | el=expression CONCAT er=expression {$expr = new ConcatExpr($el.expr, $er.expr);}
    | el=expression LT er=expression {$expr = new BinaryExpr($el.expr, "<", $er.expr);}
    | LPARAN expression RPARAN {$expr = $expression.expr;}
    ;

functionCall returns [Expression expr]
    : ID LPARAN arg=args RPARAN {$expr = new FuncCallExpr($ID.text, $arg.exprList);}
    ;

functionDef returns [Expression expr]
    : {List<Expression> funcStmts = new ArrayList<>();}
    FUNC ID LPARAN p=params RPARAN OBLOCK (stmt=statement {funcStmts.add($stmt.expr);})* EBLOCK
      {
          $expr = new FuncDefExpr($ID.text, $p.ids, new BlockExpr(funcStmts));
      }
    ;

ifBlock returns [Expression expr]
    :
    {
        List<Expression> thenExprs = new ArrayList<>();
        List<Expression> elseExprs = new ArrayList<>();
    }
    IF LPARAN cond=expression RPARAN OBLOCK (stmt=statement {thenExprs.add($stmt.expr);})* EBLOCK
      (ELSE OBLOCK (stmt=statement {elseExprs.add($stmt.expr);})* EBLOCK)?
      {
          Expression thenExpr = new BlockExpr(thenExprs);

          Expression elseExpr = null;
          if (!elseExprs.isEmpty()) {
              elseExpr = new BlockExpr(elseExprs);
          }

          $expr = new IfExpr($cond.expr, thenExpr, elseExpr);
      }
    ;

args returns [List<Expression> exprList]
    : {$exprList = new ArrayList<>();}
      expression {$exprList.add($expression.expr);}
      (COMMA expression {$exprList.add($expression.expr);})*
    ;

print returns [Expression expr]
    : PRINT LPARAN e=expression RPARAN {$expr = new PrintExpr($e.expr);}
    ;

COMMA : ',' ;
LPARAN : '(' ;
RPARAN : ')';
EQL : '=' ;
SEMI : ';' ;

OBLOCK : '{' ;
EBLOCK : '}' ;

PLUS : '+' ;
MINUS : '-' ;
MULT : '*' ;
DIV : '/' ;
LT : '<' ;

CONCAT : '++';

FOR : 'for' ;
IN : 'in' ;
RANGE : '..';
FUNC : 'function' ;
IF : 'if' ;
ELSE : 'else' ;

PRINT : 'print' ;

STRING : '"' ( ~["\r\n] | ' ')+ '"' ;
INT : [0-9]+ ;
ID: [a-zA-Z_][a-zA-Z_0-9]* ;
WHITESPACE : [ \t\r\n] -> skip;