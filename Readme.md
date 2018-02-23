# Design Patterns

## Tree Iterator

This pattern allows declarative (predicate,consumer) pairs to be applied to each tree node.
It is useful for the editing of tree objects like XML, JSON etc.
The tree is iterated only once.
Object ids can be matched by predicates by implementing path arguments that support regular expressions.
In some cases implementing parent aware predicates and consumers might be necessary.

    TreeIterator.of(tree)
        .when(predicate(node,path)).then(consumer(node,path)) // executes consumer 
        .when(predicate(node,path)).remove() // removes node 
        .when(predicate(node,path)).replace(supplier(node,path)) // replaces the node 
        .when(predicate(node,path)).skip() // skips the whole subtree starting with the node the predicate matches
        .when(predicate(node,path)).ignore() // skips an element but the iteration continues with its children
        .execute()
        
Designed by Eduard Beutel and Grebiel Ifill.
A generic implementation can be found at [https://github.com/ifillbrito/trees](https://github.com/ifillbrito/trees).
