package org.example;

import java.util.*;

/**
 * Class for managing path
 *
 *
 */
public class Path {
    private Map<String, String> pathMap = new HashMap<>();
    public Map<String,String > getPathMap (){
        return pathMap;
    }
    public String buildPath(String destination) {
        List<String> path = new ArrayList<>();
        String current = destination;

        while (current != null) {
            path.add(current);
            current = pathMap.get(current);
        }

        Collections.reverse(path);
        String output = "";
        for(int i=0; i<path.size()-1; i++) {
            output += path.get(i) + " -> ";
        }
        output += path.get(path.size()-1);
        return output;
    }


}