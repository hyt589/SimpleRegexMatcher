package hyt;

public class Parser {

    private String input;

    public Parser(String input) {
        this.input = input;
    }

    public NFA parse(){
        return regex();
    }

    private Character peek() {
        return input.charAt(0);
    }

    private void eat(Character c) {
        if (peek() == c) {
            this.input = this.input.substring(1);
        } else {
            throw new RuntimeException("Expected " + c + "; got " + peek());
        }
    }

    private Character next() {
        Character c = peek();
        eat(c);
        return c;
    }

    private boolean more() {
        return input.length() > 0;
    }

    private NFA regex() {
        NFA term = term();
        if (more() && peek() == '|') {
            eat('|');
            NFA regex = regex();
            return NFA.or(term, regex);
        }else{
            return term;
        }
    }

    private NFA term(){
        NFA factor = NFA.e();
        while (more() && peek() != ')' && peek() != '|') {
            NFA nextFactor = factor();
            factor = NFA.s(factor, nextFactor);
        }
        return factor;
    }

    private NFA factor(){
        NFA base = base();
        while (more() && peek() == '*') {
            eat('*');
            base = NFA.star(base);
        }
        return base;
    }

    private NFA base(){
        switch (peek()) {
            case '(':
                eat('(');
                NFA r = regex();
                eat(')');
                return r;

            case '\\':
                eat('\\');
                Character esc = next();
                return NFA.c(esc);
        
            default:
                return NFA.c(next());
        }
    }
}