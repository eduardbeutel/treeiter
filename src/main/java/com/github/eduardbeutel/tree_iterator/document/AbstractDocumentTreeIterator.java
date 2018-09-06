package com.github.eduardbeutel.tree_iterator.document;

import com.github.eduardbeutel.tree_iterator.document.Command.OperationType;
import com.github.eduardbeutel.tree_iterator.document.Command.PredicateType;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.regex.Pattern;

public abstract class AbstractDocumentTreeIterator<Document, Node>
{

    public final Predicate<Node> ALWAYS_PREDICATE = node -> true;

    private Predicates<Node> predicates = new Predicates<>(this);
    private Operations<Node> operations = new Operations<>(this);
    private Document document;
    private List<Command<Node>> commands = new ArrayList<>();
    private Command<Node> currentCommand;
    private CommandExecutor<Node> executor = new CommandExecutor<>();

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
            return iterator.addPredicate(PredicateType.NODE, predicate).getOperations();
        }

        public Operations<Node> whenNot(Predicate<Node> predicate)
        {
            return iterator.addPredicate(PredicateType.NODE, predicate.negate()).getOperations();
        }

        public Operations<Node> always()
        {
            return iterator.addPredicate(PredicateType.NODE, iterator.ALWAYS_PREDICATE).getOperations();
        }

        public Operations<Node> whenId(String id)
        {
            return iterator.addPredicate(PredicateType.ID, id).getOperations();
        }

        public Operations<Node> whenPath(String path)
        {
            return iterator.addPredicate(PredicateType.PATH, path).getOperations();
        }

        public Operations<Node> whenIdMatches(String pattern)
        {
            return iterator.addPredicate(PredicateType.ID_PATTERN, Pattern.compile(pattern)).getOperations();
        }

        public Operations<Node> whenPathMatches(String pattern)
        {
            return iterator.addPredicate(PredicateType.PATH_PATTERN, Pattern.compile(pattern)).getOperations();
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
            iterator.addOperation(OperationType.NODE_CONSUMER, consumer);
            return iterator.getPredicates();
        }

    }

    protected abstract void iterate(Object object);

    protected AbstractDocumentTreeIterator(Document document)
    {
        this.document = document;
    }

    protected AbstractDocumentTreeIterator<Document, Node> addPredicate(PredicateType type, Object predicate)
    {
        Command<Node> command = new Command<>();
        command.setPredicateType(type);
        command.setPredicate(predicate);
        this.commands.add(command);
        this.currentCommand = command;
        return this;
    }

    protected AbstractDocumentTreeIterator<Document, Node> addOperation(OperationType type, Object operation)
    {
        Command<Node> command = this.currentCommand;
        command.setOperationsType(type);
        command.setOperation(operation);
        return this;
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

    protected List<Command<Node>> getCommands()
    {
        return commands;
    }

    protected CommandExecutor<Node> getExecutor()
    {
        return executor;
    }

    protected void executeCommands(Node node, String id, String path)
    {
        for (Command<Node> command : getCommands())
        {
            getExecutor().execute(command, node, id, path);
        }
    }
}
