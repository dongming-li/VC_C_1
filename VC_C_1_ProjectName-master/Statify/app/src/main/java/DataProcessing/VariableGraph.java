package DataProcessing;

import com.google.common.graph.Graph;
import com.google.common.graph.GraphBuilder;
import com.google.common.graph.Graphs;
import com.google.common.graph.MutableGraph;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.Set;
import java.util.Stack;

/**
 * Static utility class used for building up a directed graph data structure for calculated data expressions.
 * Vertices are the variables, where edges are the expression dependencies.
 * For example, if a = 3, and b = a + 3, an edge will be drawn from b to a.
 * The class utilizes the Guava Google Core Libraries to assist with the graph data structure.
 */
public final class VariableGraph {

    /**
     * Builds up the graph data structure from a given ArrayLists of expression names and expressions
     * @param expressionNames Names of all expressions. Should map directly to expressions parameter
     * @param expressions Expressions to be graphed. Should map directly to expressionNames parameter
     * @return
     */
    public static boolean testIfValidGraph(ArrayList<String> expressionNames, ArrayList<String> expressions){
        MutableGraph<String> graph = GraphBuilder.directed().build();


        for(int i = 0; i < expressions.size(); i++){
            graph.addNode(expressionNames.get(i));
            Scanner in = new Scanner(expressions.get(i));

            while(in.hasNext()){
                String next = in.next();
                if(next.charAt(0) == '$'){
                    next = next.substring(1);
                    graph.putEdge(expressionNames.get(i), next);
                }
            }

            in.close();
        }

        for(int i = 0; i < expressions.size(); i++){
           System.out.println("WWWWWWWWWWW: " + expressionNames.get(i) + " " + graph.inDegree(expressionNames.get(i)));
        }

        return !detectCycles(graph);
    }

    /**
     * Returns true is cycle is present, false if otherwise
     * @param graph
     * @return true is cycle is detected
     */
    public static boolean detectCycles(MutableGraph<String> graph){
        return Graphs.hasCycle(graph);

    }

}
