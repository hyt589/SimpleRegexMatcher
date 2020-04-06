package hyt;

public class NFA {

    private NFAstate entry;
    private NFAstate exit;

    public NFA(NFAstate entry, NFAstate exit) {
        this.entry = entry;
        this.exit = exit;
    }

    public boolean matches(String s) {
        return entry.matches(s);
    }

    public static NFA c(Character c) {
        NFAstate entry = new NFAstate(false);
        NFAstate exit = new NFAstate(true);
        entry.addCharEdge(c, exit);
        return new NFA(entry, exit);
    }

    public static NFA e() {
        NFAstate entry = new NFAstate(false);
        NFAstate exit = new NFAstate(true);
        entry.addEpsilonEdge(exit);
        return new NFA(entry, exit);
    }

    public static NFA star(NFA nfa) {
        nfa.entry.addEpsilonEdge(nfa.exit);
        nfa.exit.addEpsilonEdge(nfa.entry);
        return nfa;
    }

    public static NFA s(NFA first, NFA second) {
        first.exit.setFinal(false);
        assert !first.exit.isFinal() : "first exit is final, can't chain";
        assert second.exit.isFinal() : "second exit must be final";
        first.exit.addEpsilonEdge(second.entry);
        second.exit.setFinal(true);
        return new NFA(first.entry, second.exit);
    }


    public static NFA or(NFA first, NFA second) {
        first.exit.setFinal(false);
        second.exit.setFinal(false);
        assert !first.exit.isFinal() && !second.exit.isFinal() : "one exit is final, can't chain";
        NFAstate entry = new NFAstate(false);
        NFAstate exit = new NFAstate(true);
        entry.addEpsilonEdge(first.entry);
        entry.addEpsilonEdge(second.entry);
        first.exit.addEpsilonEdge(exit);
        second.exit.addEpsilonEdge(exit);
        return new NFA(entry, exit);
    }

    public static NFA re(Object o) {
        if (o instanceof NFA) {
            return (NFA) o;
        } else if (o instanceof Character) {
            return c((Character) o);
        } else if (o instanceof String) {
            return fromString((String) o);
        } else {
            throw new RuntimeException("not a regex");
        }
    }

    public static NFA or(Object... rexps) {
        NFA exp = re(rexps[0]);
        for (int i = 1; i < rexps.length; i++) {
            exp = or(exp, re(rexps[i]));
        }
        return exp;
    }

    public static  NFA s(Object... rexps) {
        NFA exp = e();
        for (int i = 0; i < rexps.length; i++) {
            exp = s(exp, re(rexps[i]));
        }
        return exp;
    }

    public static NFA fromString(String str) {
        if (str.length() == 0)
            return e();
        else
            return s(re(str.charAt(0)), fromString(str.substring(1)));
    }

}