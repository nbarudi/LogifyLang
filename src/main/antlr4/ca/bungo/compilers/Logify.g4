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
    | aa=arrayAssignment {$expr = $aa.expr;}
    | el=eachLoop {$expr = $el.expr;}
    | f=forLoop {$expr = $f.expr;}
    | wl=whileBlock {$expr = $wl.expr;}
    | fd=functionDef {$expr = $fd.expr;}
    | ifs=ifBlock {$expr = $ifs.expr;}
    | ex=expression SEMI? {$expr = $ex.expr;}
    | pr=print SEMI? {$expr = $pr.expr;}
    | fc=functionCall SEMI? {$expr = $fc.expr;}
    ;

forLoop returns [Expression expr]
    :  {List<Expression> loopStmts = new ArrayList<>();}
    FOR LPARAN a=assignment SPLIT cond=expression RPARAN OBLOCK (stmt=statement {loopStmts.add($stmt.expr);})* EBLOCK
      {
          $expr = new ForLoopExpr($a.expr, $cond.expr, new BlockExpr(loopStmts));
      }
    ;

eachLoop returns [Expression expr]
    :  {List<Expression> loopStmts = new ArrayList<>();}
    EACH LPARAN ID IN cond=expression RPARAN OBLOCK (stmt=statement {loopStmts.add($stmt.expr);})* EBLOCK
      {
          $expr = new EachLoopExpr($ID.text, $cond.expr, new BlockExpr(loopStmts));
      }
    ;

params returns [List<String> ids]
    : {$ids = new ArrayList<>();}
      ID { $ids.add($ID.text); } (COMMA ID { $ids.add($ID.text); })*
    ;

assignment returns [Expression expr]
    : ID EQL expression SEMI? {$expr = new AssignExpr($ID.text, $expression.expr);}
    ;

arrayAssignment returns [Expression expr]
    : ID OARRAY idx=expression EARRAY EQL e1=expression SEMI? {$expr = new ArrayAssignExpr($ID.text, $idx.expr, $e1.expr);}
    ;

arrayRef returns [Expression expr]
    : ID OARRAY exp=expression EARRAY SEMI? {$expr = new ArrayVarExpr($ID.text, $exp.expr);}
    ;

expression returns [Expression expr]
    : STRING {$expr = new StringExpr($STRING.text.substring(1, $STRING.text.length() - 1));}
    //| s=INT RANGE e=INT {$expr = new RangeExpr(Integer.parseInt($s.text), Integer.parseInt($e.text));}
    | INT {$expr = new IntExpr(Integer.parseInt($INT.text));}
    | DECIMAL {$expr = new DecimalExpr(Double.parseDouble($DECIMAL.text));}
    | ar=arrayRef {$expr = $ar.expr;}
    | sum SEMI? {$expr = $sum.expr;}
    | sort SEMI? {$expr = $sort.expr;}
    | reverse SEMI? {$expr = $reverse.expr;}
    | mean SEMI? {$expr = $mean.expr;}
    | length SEMI? {$expr = $length.expr;}
    | fc=functionCall {$expr = $fc.expr;}
    | ID {$expr = new VarExpr($ID.text);}
    | TRUE {$expr = new BoolExpr(true);}
    | FALSE {$expr = new BoolExpr(false);}
    | BREAK {$expr = new BreakExpr(); }
    | ab=arrayBlock {$expr = new ArrayExpr($ab.exprList);}
    | el=expression PLUS er=expression {$expr = new BinaryExpr($el.expr, "+", $er.expr);}
    | el=expression MINUS er=expression {$expr = new BinaryExpr($el.expr, "-", $er.expr);}
    | el=expression MULT er=expression {$expr = new BinaryExpr($el.expr, "*", $er.expr);}
    | el=expression DIV er=expression {$expr = new BinaryExpr($el.expr, "/", $er.expr);}
    | el=expression CONCAT er=expression {$expr = new ConcatExpr($el.expr, $er.expr);}
    | el=expression LT er=expression {$expr = new ConditionalExpr($el.expr, "<", $er.expr);}
    | el=expression LTE er=expression {$expr = new ConditionalExpr($el.expr, "<=", $er.expr);}
    | el=expression GT er=expression {$expr = new ConditionalExpr($el.expr, ">", $er.expr);}
    | el=expression GTE er=expression {$expr = new ConditionalExpr($el.expr, ">=", $er.expr);}
    | el=expression EQ er=expression {$expr = new ConditionalExpr($el.expr, "==", $er.expr);}
    | LPARAN expression RPARAN {$expr = $expression.expr;}
    ;

arrayBlock returns [List<Expression> exprList]
    : {$exprList = new ArrayList<>(); }
    OARRAY (e1=expression {$exprList.add($e1.expr);}
     (COMMA e2=expression {$exprList.add($e2.expr);})*)? EARRAY
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
        List<Expression> elseIfExprs = new ArrayList<>();
        List<Expression> elseExprs = new ArrayList<>();
        List<Expression> elseStmnts = new ArrayList<>();
    }
    IF LPARAN cond=expression RPARAN OBLOCK
        (thenStmt=statement { thenExprs.add($thenStmt.expr); })*
      EBLOCK
      (ELSEIF LPARAN elseifCond=expression RPARAN OBLOCK
        (elseifStmt=statement { elseStmnts.add($elseifStmt.expr); })*
      EBLOCK
      {
        if(!elseStmnts.isEmpty()) {
            elseIfExprs.add(new IfElseExpr($elseifCond.expr, new BlockExpr(new ArrayList<>(elseStmnts))));
            elseStmnts.clear();
        }
      })*
      (ELSE OBLOCK
        (elseStmt=statement { elseExprs.add($elseStmt.expr); })*
      EBLOCK)?
      {
          Expression thenExpr = new BlockExpr(thenExprs);
          Expression elseExpr = null;
          if (!elseExprs.isEmpty()) {
              elseExpr = new BlockExpr(elseExprs);
          }

          $expr = new IfExpr($cond.expr, thenExpr, elseIfExprs, elseExpr);
      }
    ;

whileBlock returns [Expression expr]
    :
    {
        List<Expression> whileBody = new ArrayList<Expression>();
    }
    WHILE LPARAN cond=expression RPARAN OBLOCK (stmt=statement {whileBody.add($stmt.expr);})* EBLOCK
    {
        $expr = new WhileExpr($cond.expr, new BlockExpr(whileBody));
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

sum returns [Expression expr]
    : SUM LPARAN e=expression RPARAN {$expr = new SumExpr($e.expr);}
    ;

sort returns [Expression expr]
    : SORT LPARAN e=expression RPARAN {$expr = new SortExpr($e.expr);}
    ;

reverse returns [Expression expr]
    : REVERSE LPARAN e=expression RPARAN {$expr = new ReverseExpr($e.expr);}
    ;

mean returns [Expression expr]
    : MEAN LPARAN e=expression RPARAN {$expr = new MeanExpr($e.expr);}
    ;

length returns [Expression expr]
    : LENGTH LPARAN e=expression RPARAN {$expr = new LengthExpr($e.expr);}
    ;

COMMA : ',' ;
LPARAN : '(' ;
RPARAN : ')';
EQ : '==' ;
EQL : '=' ;
SEMI : ';' ;

OBLOCK : '{' ;
EBLOCK : '}' ;

OARRAY : '[' ;
EARRAY : ']' ;

PLUS : '+' ;
MINUS : '-' ;
MULT : '*' ;
DIV : '/' ;
LT : '<' ;
GT : '>' ;
LTE : '<=' ;
GTE : '>=' ;

CONCAT : '++';

FOR : 'for' ;
EACH : 'each' ;
IN : 'in' ;
//RANGE : '..';
SPLIT : ' | ' ;
FUNC : 'function' ;
IF : 'if' ;
ELSE : 'else' ;
ELSEIF : 'else if' ;
WHILE : 'while' ;
BREAK : 'break';

TRUE : 'true'|'True';
FALSE : 'false'|'False';

PRINT : 'print' ;
SUM : 'sum' ;
MEAN : 'mean' ;
SORT : 'sort' ;
REVERSE : 'reverse' ;
LENGTH : 'length' ;

STRING : '"' ( ~["\r\n] | ' ')+ '"' ;
INT : [0-9]+ ;
DECIMAL : [0-9]+ '.' [0-9]+;
ID: [a-zA-Z_][a-zA-Z_0-9]* ;
WHITESPACE : [ \t\r\n] -> skip;