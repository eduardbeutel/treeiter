# Tree Iterator

The Tree Iterator is a pattern for declarative iterators that allow (predicate,operation) pairs to be applied to each tree node.
It is useful for the editing of tree objects like XML or JSON and extracting information like all nodes that match a condition.
The tree must be iterated only once. This pattern is not language specific.

### Example

TODO
    
### Structure

    TreeIterator.of(tree)
        .predicate().operation()
        .predicate().operation()
	...
        .execute()
		
### Predicates

    .when()
    .whenNot()
    .always()

Specific predicates can be also implemented to improve readibility. Some examples:
   
 - exact match: whenId(), whenPath()
 - pattern match: whenIdMatches(), whenPathMatches() 
 - occurence index: whenFirst(), whenLast(), whenNth()
 - node type: whenLeaf(), whenHasChildren()
 - level: whenOnLevel()

### Operations

    .then(consumer()) // executes consumer 
    .remove() // removes node 
    .replace(supplier()) // replaces the node
    .skip() // skips the whole subtree starting with the node the condition matches
    .ignore() // skips a node but the iteration continues with its children
    .stop() // stop the iteration
    .collect(reference) // sets reference to point to the node
    .collect(collection) // adds the node to the provided collection
     
### Arguments

In the simplest form the predicates and operations will have the tree node as their argument.
Depending on the need, other information about the node (which is not provided by the node itself) might be needed.
A list of possible arguments:

	node, id, path, level, parent, ancestors, descendants

If more then three arguments are needed they can be encapsulated in a **step** (as in iteration step) object.
The step can then be used as the argument of the predicates and operations.

Several argument combinations can be implemented to improve readiblity.
For example: 

	.when(node)
	.when(node,path)
	.when(node,id,path)

### Design Goals

**Simple structure**: follow a simple structure and allow flexibility through combinations.
**Completeness**: provide all possibly necessary predicates and operations to maximize the usefulness of the iterator.
**Don't overlap** with existing concepts like Java Streams or C# LINQ.

### Terminology

The terminology used in this document is based on the [Java Functional Interfaces](https://docs.oracle.com/javase/8/docs/api/java/util/function/package-summary.html)

### Credits
       
Designed by Eduard Beutel and Grebiel Ifill.
Another form of this pattern can be found at [https://github.com/ifillbrito/trees](https://github.com/ifillbrito/trees).
