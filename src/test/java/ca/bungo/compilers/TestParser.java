package ca.bungo.compilers;

import ca.bungo.compilers.logic.Runtime;
import org.antlr.v4.runtime.*;
import org.junit.Test;

import java.io.IOException;
import java.util.Objects;

public class TestParser {

    @Test
    public void helloWorld() throws IOException {
        evalFile("helloWorld.logify");
    }

    @Test
    public void stringConcat() throws IOException {
        evalFile("stringConcat.logify");
    }

    @Test
    public void forLoopSum() throws IOException {
        evalFile("forLoopSum.logify");
    }

    @Test
    public void greetingFunction() throws IOException {
        evalFile("greetingFunction.logify");
    }

    @Test
    public void factorial() throws IOException {
        evalFile("factorial.logify");
    }

    @Test
    public void ifElse() throws  IOException {
        evalFile("ifElse.logify");
    }

    @Test
    public void IfString() throws IOException{
        evalFile("IfString.logify");
    }

    @Test
    public void whileLoop() throws IOException {
        evalFile("whileLoop.logify");
    }

    @Test
    public void breakLoop() throws IOException {
        evalFile("breakLoop.logify");
    }

    @Test
    public void arrayLogic() throws IOException {
        evalFile("arrayLogic.logify");
    }

    @Test
    public void aggData() throws IOException {
        evalFile("aggData.logify");
    }

    @Test
    public void eachLoop() throws IOException {
        evalFile("eachLoop.logify");
    }

    void evalFile(String fileName) throws IOException {
        Lexer lexer = new LogifyLexer(CharStreams.fromStream(
                Objects.requireNonNull(Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName))));
        TokenStream tokens = new BufferedTokenStream(lexer);
        LogifyParser parser = new LogifyParser(tokens);
        parser.program().expr.evaluate(new Runtime());
    }

}
