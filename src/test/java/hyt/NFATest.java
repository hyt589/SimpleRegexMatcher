package hyt;

import org.junit.Test;

import static org.junit.Assert.*;

public class NFATest {

    @Test
    public void orStarTest() {
        NFA nfa = NFA.star(NFA.or('b', 'c'));
        assertTrue("(b|c)* doesn't match bccbcbc", nfa.matches("bccbcbc"));
    }

    @Test
    public void orTest() {
        NFA nfa = NFA.or('a', 'b');
        assertTrue("(a|b)", nfa.matches("b"));
    }

    @Test
    public void starTest(){
        NFA nfa = NFA.star(NFA.c('a'));
        assertTrue("a", nfa.matches("aaaaaa"));
    }

    @Test
    public void stringTest(){
        NFA nfa = NFA.fromString("abc");
        assertTrue("abc", nfa.matches("abc"));
    }

    @Test
    public void charTest(){
        NFA nfa = NFA.c('a');
        assertTrue("char a", nfa.matches("a"));
        assertFalse("char a - does not match", nfa.matches("b"));
    }

    @Test
    public void epsilonTest() {
        NFA nfa = NFA.e();
        assertTrue("empty string", nfa.matches(""));
        assertFalse("non empty string", nfa.matches("s"));
    }

}