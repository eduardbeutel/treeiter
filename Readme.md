# Tree Iterator

A pattern for declarative tree iterators that allow (predicate,operation) pairs to be applied to each tree node.
It is useful for the editing of tree objects like XML or JSON and extracting information like all nodes that match a condition.
The tree must be iterated only once. This pattern is not language specific.

### Language

The language used in this document is based on the  [Java Functional Interfaces](https://docs.oracle.com/javase/8/docs/api/java/util/function/package-summary.html)

- predicate() is a function that returns true or false
- operation() is a function that can modify its arguments
- consumer() is a function without return value that can modify it's arguments
- supplier() is a function that returns a new object and doesn't modify it's arguments
- collection can be a list, set or map
    
### Structure

    TreeIterator.of(tree)
        .predicate().operation()
        .predicate().operation().operation() // operations can be chained
        .execute()
       
### Operations

    .then(consumer()) // executes consumer 
    .remove() // removes node 
    .replace(supplier()) // replaces the node
    .skip() // skips the whole subtree starting with the node the condition matches
    .ignore() // skips a node but the iteration continues with its children
    .stop() // stop the iteration
    .collect(reference) // sets reference to point to the node
    .collect(collection) // adds the node to the provided collection

### Predicates

    .when()
    .whenNot()
    .always()
    .otherwise() // will match when all previous predicates don't
    
Specific predicates can be also implemented to improve readibility. Some examples:
   
 - exact match: whenId(), whenPath()
 - pattern match: whenIdMatches(), whenPathMatches() 
 - occurence index: whenFirst(), whenLast(), whenNth()
 - node type: whenLeaf(), whenHasChildren()
 - level: whenOnLevel()
     
### Arguments

In the simplest form the predicates and operations will have the tree node as their argument.
Depending on the need other information about the node (which is not provided by the node itself) might be needed.
A list of possible arguments:

	node, id, path, level, parent, ancestors, descendants

If more then three arguments are needed they can be encapsulated in a **step** (as in iteration step) object.
The step will then be used as the argument of the predicates and operations.

Several argument combinations can be implemented to improve readiblity.
For example: 

	.when(node)
	.when(node,path)
	.when(node,id,path)

### Shortcuts:

	TreeIterator.of(tree).direct().shortcut()
	
The pattern allows the definition of shortcuts within the scope of direct().
Shortcuts are executed immediately.

### Design

#### Goals

**Simple structure**: follow a simple structure and allow flexibility through combinations.
**Completeness**: provide all possibly necessary predicates and operations to maximize the usefulness of the iterator.
**Don't overlap** with existing concepts. 

#### Consequences

Functionality to iterate and transform collections is omitted because it would overlap with existing concepts like Java Streams or C# LINQ.

### Credits
       
Designed by Eduard Beutel and Grebiel Ifill.
Another form of this pattern can be found at [https://github.com/ifillbrito/trees](https://github.com/ifillbrito/trees).




