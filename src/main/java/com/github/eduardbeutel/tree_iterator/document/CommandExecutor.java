package com.github.eduardbeutel.tree_iterator.document;

import java.util.function.Consumer;
import java.util.function.Predicate;

public class CommandExecutor<Node>
{

    /*
        args[0] = Node
        args[1] = Node Id as String
        args[2] = Node Path as String
   */
    void execute(Command<Node> command, Object... args)
    {
        if (args == null || args.length == 0) return;
        if (evaluatePredicate(command, args))
        {
            executeOperation(command, args);
        }
    }

    private boolean evaluatePredicate(Command<Node> command, Object... args)
    {
        Node node = (Node) args[0];
        String id = (String) args[1];
        String path = (String) args[2];

        switch (command.getPredicateType())
        {
            default:
                return false;
            case NODE:
                return ((Predicate<Node>)command.getPredicate()).test(node);
            case ID:
                return equals((String)command.getPredicate(), id);
            case PATH:
                return equals((String)command.getPredicate(), path);
        }
    }

    private void executeOperation(Command<Node> command, Object... args)
    {
        Node node = (Node) args[0];
        switch (command.getOperationsType())
        {
            default:
                break;
            case NODE_CONSUMER:
                ((Consumer<Node>)command.getOperation()).accept(node);
                break;
        }
    }

    private boolean equals(String left, String right)
    {
        if(left == null && right != null) return false;
        if(left != null && right == null) return false;
        if(left == null && right == null) return true;
        return left.equals(right);
    }

}
