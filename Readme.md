# Patterns

## Tree Iterator

This pattern allows declarative (predicate,operation) pairs to be applied to each tree node.
It is useful for the editing of tree objects like XML, JSON etc.
The tree is considered mutable and iterated only once.
Object ids can be matched by predicates by implementing path arguments that support regular expressions.
In some cases implementing parent aware predicates and consumers might be necessary.
    
### Pattern:

    TreeIterator.of(tree)
        .predicate().operation()
        ...
        .execute()
        
### Predicates:

    .when(predicate(node,path))
    .always()
    
### Operations:

    .then(consumer(node,path)) // executes consumer 
    .remove() // removes node 
    .replace(supplier(node,path)) // replaces the node
    .skip() // skips the whole subtree starting with the node the predicate matches
    .ignore() // skips a node but the iteration continues with its children
    .collect(collection) // adds the node to the provided collection
    .collect(map,function(node,path)) // adds the node to the provided map by creating a key using the provided function
    .collect(map,function(node,path),function(node,path)) // adds the value created by the second function to the provided map by creating a key using the first function 
       
Designed by Eduard Beutel and Grebiel Ifill.
A generic implementation can be found at [https://github.com/ifillbrito/trees](https://github.com/ifillbrito/trees).
