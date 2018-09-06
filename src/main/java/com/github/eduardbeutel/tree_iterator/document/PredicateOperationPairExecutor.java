package com.github.eduardbeutel.tree_iterator.document;

import java.util.function.Consumer;
import java.util.function.Predicate;

public class PredicateOperationPairExecutor<Node>
{

    void execute(PredicateOperationPair<Node> pair, Object... args)
    {
        if (args == null || args.length == 0) return;
        Node node = (Node) args[0];

        if (evaluatePredicate(pair, node))
        {
            executeOperation(pair, node);
        }
    }

    private boolean evaluatePredicate(PredicateOperationPair<Node> pair, Node node)
    {
        switch (pair.getPredicateType())
        {
            default:
                return false;
            case WHEN_NODE:
                return ((Predicate<Node>)pair.getPredicate()).test(node);
        }
    }

    private void executeOperation(PredicateOperationPair<Node> pair, Node node)
    {
        switch (pair.getOperationsType())
        {
            default: break;
            case NODE_CONSUMER:
                ((Consumer<Node>)pair.getOperation()).accept(node);
                break;
        }
    }

}
