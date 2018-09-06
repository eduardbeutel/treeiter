package com.github.eduardbeutel.tree_iterator.document;

import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.regex.Pattern;

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

        boolean result = false;
        switch (command.getPredicateType())
        {
            case NODE:
                result = ((Predicate<Node>) command.getPredicate()).test(node);
                break;
            case ID:
                result = equals((String) command.getPredicate(), id);
                break;
            case PATH:
                result = equals((String) command.getPredicate(), path);
                break;
            case ID_PATTERN:
                result = matches((Pattern) command.getPredicate(), id);
                break;
            case PATH_PATTERN:
                result = matches((Pattern) command.getPredicate(), path);
                break;
            case ROOT:
                result = isRoot(id, path);
                break;
        }
        return result;
    }

    private void executeOperation(Command<Node> command, Object... args)
    {
        Node node = (Node) args[0];
        switch (command.getOperationsType())
        {
            case NODE_CONSUMER:
                ((Consumer<Node>) command.getOperation()).accept(node);
                break;
        }
    }

    private boolean isRoot(String id, String path)
    {
        return ("/" + id).equals(path);
    }

    private boolean matches(Pattern pattern, String path)
    {
        return pattern.matcher(path).matches();
    }

    private boolean equals(String left, String right)
    {
        if (left == null && right != null) return false;
        if (left != null && right == null) return false;
        if (left == null && right == null) return true;
        return left.equals(right);
    }

}
