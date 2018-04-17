# Patterns

## Tree Iterator

This pattern allows declarative (predicate,operation) pairs to be applied to each tree node.
It is useful for the editing of tree objects like XML, JSON etc.
The tree is considered mutable and iterated only once.
Object ids can be matched by predicates by implementing path arguments that support regular expressions.
In some cases implementing parent aware predicates and consumers might be necessary.
    
An example containing all types of predicates and operations:

    TreeIterator.of(tree)
        .always().then(consumer(node,path)) // executes consumer 
        .when(predicate(node,path)).then(consumer(node,path)) // executes consumer 
        .when(predicate(node,path)).remove() // removes node 
        .when(predicate(node,path)).replace(supplier(node,path)) // replaces the node 
        .when(predicate(node,path)).skip() // skips the whole subtree starting with the node the predicate matches
        .when(predicate(node,path)).ignore() // skips a node but the iteration continues with its children
        .when(predicate(node,path)).collect(collection) // adds the node to the provided collection
        .execute()

Pattern:

    TreeIterator.of(tree)
        .predicate().operation()
        ...
        .execute()
        
Predicates:

    .when(predicate(node,path))
    .always()
    
Operations:

    .then(consumer(node,path)) // executes consumer 
    .remove() // removes node 
    .replace(supplier(node,path)) // replaces the node
    .skip() // skips the whole subtree starting with the node the predicate matches
    .ignore() // skips a node but the iteration continues with its children
    .collect(collection) // adds the node to the provided collection
       
Designed by Eduard Beutel and Grebiel Ifill.
A generic implementation can be found at [https://github.com/ifillbrito/trees](https://github.com/ifillbrito/trees).
