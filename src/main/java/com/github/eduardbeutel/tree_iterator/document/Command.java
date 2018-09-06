package com.github.eduardbeutel.tree_iterator.document;

import java.util.function.Consumer;
import java.util.function.Predicate;

public class Command<Node>
{

    protected enum PredicateType
    {
        NODE, // Predicate<Node>
        ID, // String
        PATH, // String
    }
    private PredicateType predicateType;
    private Object predicate;


    protected enum OperationType
    {
        NODE_CONSUMER // Consumer<Node>
    }
    private OperationType operationsType;
    private Object operation;

    //

    public PredicateType getPredicateType()
    {
        return predicateType;
    }

    public Object getPredicate()
    {
        return predicate;
    }

    public OperationType getOperationsType()
    {
        return operationsType;
    }

    public Object getOperation()
    {
        return operation;
    }

    public void setPredicateType(PredicateType predicateType)
    {
        this.predicateType = predicateType;
    }

    public void setPredicate(Object predicate)
    {
        this.predicate = predicate;
    }

    public void setOperationsType(OperationType operationsType)
    {
        this.operationsType = operationsType;
    }

    public void setOperation(Object operation)
    {
        this.operation = operation;
    }

}
