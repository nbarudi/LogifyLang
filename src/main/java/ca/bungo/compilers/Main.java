package ca.bungo.compilers;

import ca.bungo.compilers.logic.Runtime;
import org.antlr.v4.runtime.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class Main {

    public static Logger logger = LoggerFactory.getLogger("Compilers Logic");

    public static void main(String[] args) {
        if(args.length == 0){
            logger.error("Invalid Arguments! Usage: majorproject <File Name>");
            return;
        }

        try {
            CharStream stream = CharStreams.fromFileName(args[0]);
            logger.debug("Reading file: {}", args[0]);
            Lexer lexer = new LogifyLexer(stream);
            TokenStream tokens = new BufferedTokenStream(lexer);
            LogifyParser logify = new LogifyParser(tokens);

            logify.program().expr.evaluate(new Runtime());

        } catch(IOException e){
            logger.error("Failed to read from file! Message: {}", e.getMessage());
        }
    }
}