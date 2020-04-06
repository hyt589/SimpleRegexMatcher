# Overview
This is a simple regex matcher implemented in Java. The program parses the input regex and translates it into a non-deterministic finite automaton (NFA), which is then used to validate the input string against the regex.

## Parser
The parser takes a string representation of a regex and parses it using recursive descent. The production rules used are very simple:  

```
   <regex> ::= <term> '|' <regex>
            |  <term>

   <term> ::= { <factor> }

   <factor> ::= <base> { '*' }
             
   <base> ::= <char>
           |  '\' <char>
           |  '(' <regex> ')'  
```

(Source: http://matt.might.net/articles/parsing-regex-with-recursive-descent/)

## Matcher
The matcher is essentially a NFA produced by the parser. The NFA would be used to validate if an input string matches to the regex.