package org.example;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Class for managing path
 *
 *
 */
public class Path {
    public static String buildPath(Map<String, String> parentMap, String destination) {
        List<String> path = new ArrayList<>();
        String current = destination;

        while (current != null) {
            path.add(current);
            current = parentMap.get(current);
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