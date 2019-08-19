
public class Parser {

    PseudoTokeniser _tokenizer;
    
    public Parser(PseudoTokeniser tokeniser) {
        _tokenizer = tokeniser;
    }

    /**
     *  <exp> ::= <integer literal> | (<exp>*<exp>) | (<exp>/<exp>) | !(<exp>)
     *
     *
     *  <exp>  ::= <term> | (<term>*<term>) | (<term>/<term>) | !(<term>)
     *  <term> ::= <integer literal> | (<exp>)
     *
     */


    public Exp parseExp() {
        // TODO: Implement this method
        // You can use any standard library if you want
        // You can add any method if it helps implementation

        Exp term1 = null;
        Exp term2 = null;

        // Factorial.
        if (_tokenizer.current().type == TokenType.EXCLAMATION) {
            _tokenizer.next();  // The next "(".
            while (_tokenizer.hasNext() && _tokenizer.current().type != TokenType.RIGHTBRACKET) {
                _tokenizer.next();
                term1 = parseExp();
            }
            _tokenizer.next();
            return new FacExp(term1);

        }
        // Multiplication / Division.
        else if (_tokenizer.current().type == TokenType.LEFTBRACKET) {
            while (_tokenizer.hasNext() && _tokenizer.current().type != TokenType.RIGHTBRACKET) {
                _tokenizer.next();
                term1 = parseTerm();

                if (_tokenizer.hasNext() && _tokenizer.current().type == TokenType.MULTIPLY) {
                    _tokenizer.next();
                    term2 = parseTerm();
                    term1 = new MultExp(term1, term2);
                }

                if (_tokenizer.hasNext() && _tokenizer.current().type == TokenType.DIVIDE) {
                    _tokenizer.next();
                    term2 = parseTerm();
                    term1 = new DivExp(term1, term2);
                }
            }
            _tokenizer.next();
            return term1;
        }
        // Integer literal
        else {
            return parseTerm();
        }
    }

    public Exp parseTerm() {
        Exp exp = null;

        if (_tokenizer.hasNext() && _tokenizer.current().type == TokenType.INT) {
            exp = new IntExp(_tokenizer.current().value);
            _tokenizer.next();
            return exp;
        } else if (_tokenizer.current().type == TokenType.LEFTBRACKET) {
            while (_tokenizer.hasNext() && _tokenizer.current().type != TokenType.RIGHTBRACKET) {
                _tokenizer.next();
                exp = parseExp();
            }
            _tokenizer.next();
            return exp;
        } else {
            return null;
        }
    }
}
