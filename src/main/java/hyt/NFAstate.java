package hyt;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import lombok.Data;

@Data
public class NFAstate {
    private Map<Character, Collection<NFAstate>> onChar = new HashMap<>();
    private Collection<NFAstate> onEpsilon = new ArrayList<>();
    private boolean isFinal;


    public NFAstate(boolean isFinal){
        this.isFinal = isFinal;
    }

    public void addCharEdge(Character character, NFAstate next) {
        if (Objects.nonNull(onChar.get(character))) {
            onChar.get(character).add(next);
        } else {
            onChar.put(character, Arrays.asList(next));
        }
    }

    public void addEpsilonEdge(NFAstate next) {
        onEpsilon.add(next);
    }


    public boolean matches(String s) {
        return matches(s, new ArrayList<>());
    }

    private boolean matches(String s, Collection<NFAstate> history) {

        if (history.contains(this)) {
            return false;
        }

        history.add(this);

        if (s.length() == 0) {
            if (isFinal) {
                return true;
            } else{
                for (NFAstate state : onEpsilon) {
                    if (state.isFinal) {
                        return true;
                    }
                }
                return false;
            }
        } else {
            Character ch = s.charAt(0);
            if (Objects.nonNull(onChar.get(ch))){
                for (NFAstate next : onChar.get(ch)){
                    if (next.matches(s.substring(1))) {
                        return true;
                    }
                }
            }

            for (NFAstate next : onEpsilon) {
                if (next.matches(s, history)) {
                    return true;
                }
            }
            return false;
        }
    }
}