# Tree Iterator

The Tree Iterator is a pattern for declarative iterators that allows commands to be applied to each tree node. A command consists in its simplest form of a condition and an operation. The pattern is useful for the manipulation of tree objects like XML or JSON and information gathering. The tree is iterated only once. This pattern is not language specific.

## Version 1

### Structure

    TreeIterator.topDown(tree)
        .condition().operation()
        .condition().and().condition().operation()
        ...
        .execute()

### Conditions

    .when()
    .whenNot()
    .always()

Specific conditions can be also implemented to improve readibility. Some examples:

 - exact match: whenId(), whenPath()
 - pattern match: whenIdMatches(), whenPathMatches()
 - node type: whenLeaf(), whenNotLeaf(), whenRoot()
 - occurrence index: whenFirst(), whenLast(), whenNth()
 - level: whenOnLevel()

### Operations

    .then(consumer()) // executes consumer 
    .remove() // removes node 
    .replace(supplier()) // replaces the node
    .collect(reference) // sets reference to point to the node
    .collect(collection) // adds the node to the provided collection
    .skip() // skips the whole subtree starting with the node the condition matches (only works in topDown() mode)
    .stop() // stop the iteration

The operation names are based on the [Java Functional Interfaces](https://docs.oracle.com/javase/8/docs/api/java/util/function/package-summary.html)
     
### Arguments

In the simplest form conditions and operations will have the tree node as their argument.
Depending on the need, further node information (which is not provided by the node itself) can be added.
A list of possible arguments:

	node, id, path, level, parent, ancestors, descendants

Arguments can be encapsulated in a **IterationStep** object.

### Traversal Direction

    .topDown()
    .bottomUp()

### Design Goals

**Simple structure**: follow a simple structure for the fluent language and allow flexibility through combinations.

**Completeness**: provide all possibly necessary conditions and operations to maximize the usefulness of the iterator.

### Implementation

Java 8 : [treeiter4j-core](https://github.com/eduardbeutel/treeiter4j-core)

### Credits

Designed by Eduard Beutel and Grebiel Ifill.
Another form of this pattern can be found at [https://github.com/ifillbrito/trees](https://github.com/ifillbrito/trees).
