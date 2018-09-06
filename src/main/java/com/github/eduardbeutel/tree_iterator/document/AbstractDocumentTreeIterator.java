package com.github.eduardbeutel.tree_iterator.document;

import com.github.eduardbeutel.tree_iterator.document.PredicateOperationPair.OperationType;
import com.github.eduardbeutel.tree_iterator.document.PredicateOperationPair.PredicateType;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

public abstract class AbstractDocumentTreeIterator<Document, Node>
{

    private Predicates<Node> predicates = new Predicates<>(this);
    private Operations<Node> operations = new Operations<>(this);
    private Document document;
    private List<PredicateOperationPair<Node>> allPairs = new ArrayList<>();
    private PredicateOperationPair<Node> currentPair;
    private PredicateOperationPairExecutor<Node> executor = new PredicateOperationPairExecutor<>();

    public static class Predicates<Node>
    {

        private AbstractDocumentTreeIterator iterator;

        private Predicates(AbstractDocumentTreeIterator iterator)
        {
            this.iterator = iterator;
        }

        public void execute()
        {
            iterator.iterate(iterator.getDocument());
        }

        public Operations<Node> when(Predicate<Node> predicate)
        {
            iterator.addPredicate(PredicateType.WHEN_NODE,predicate);
            return iterator.getOperations();
        }

    }

    public static class Operations<Node>
    {

        private AbstractDocumentTreeIterator iterator;

        private Operations(AbstractDocumentTreeIterator iterator)
        {
            this.iterator = iterator;
        }

        public Predicates<Node> then(Consumer<Node> consumer)
        {
            iterator.addOperation(OperationType.NODE_CONSUMER,consumer);
            return iterator.getPredicates();
        }

    }

    protected abstract void iterate(Object object);

    protected AbstractDocumentTreeIterator(Document document)
    {
        this.document = document;
    }

    protected PredicateOperationPair<Node> addPredicate(PredicateType type, Object predicate)
    {
        PredicateOperationPair<Node> pair = new PredicateOperationPair<>();
        pair.setPredicateType(type);
        pair.setPredicate(predicate);
        this.allPairs.add(pair);
        this.currentPair = pair;
        return pair;
    }

    protected PredicateOperationPair<Node> addOperation(OperationType type, Object operation)
    {
        PredicateOperationPair<Node> pair = this.currentPair;
        pair.setOperationsType(type);
        pair.setOperation(operation);
        return pair;
    }

    protected Predicates<Node> getPredicates()
    {
        return predicates;
    }

    protected Operations<Node> getOperations()
    {
        return operations;
    }

    protected Document getDocument()
    {
        return document;
    }

    protected List<PredicateOperationPair<Node>> getAllPairs()
    {
        return allPairs;
    }

    protected PredicateOperationPairExecutor<Node> getExecutor()
    {
        return executor;
    }

}
