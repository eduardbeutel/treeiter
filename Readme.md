# Patterns

A collection of design, architecture and process patterns.

A. Design Patterns:
- A.1 Tree Iterator

B. Architecture Patterns:
- B.1 CRUD REST API

C. Process Patterns:

## A. Design Patterns

### A.1 Tree Iterator

A pattern that allows declarative (predicate,operation) pairs to be applied to each tree node.
It is useful for the editing of tree objects like XML, JSON etc.
The tree is iterated only once.
Object ids can be matched by predicates by implementing path arguments that support regular expressions.
In some cases implementing parent aware predicates and consumers might be necessary.
    
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
    .collect(reference) // changes the reference to point to the node
    .collect(collection) // adds the node to the provided collection
    .collect(map,function(node,path)) // adds the node to the provided map by creating a key using the provided function
    .collect(map,function(node,path),function(node,path)) // adds the value created by the second function to the provided map by creating a key using the first function 
    
Examples:

Editing a tree to remove some nodes and replace others:

    TreeIterator.of(tree)
        .when(predicate(node,path)).skip() // skip sub-trees which should remain untouched
        .when(predicate(node,path)).remove() // remove the matching node
        .when(predicate(node,path)).replace(supplier(node,path)) // replace the matching node
        .execute()
        
Gather all nodes in a map:

    TreeIterator.of(tree)
        .always().collect(map,function(node,path),function(node,path))
        .execute()

       
Designed by Eduard Beutel and Grebiel Ifill.
A generic implementation can be found at [https://github.com/ifillbrito/trees](https://github.com/ifillbrito/trees).

## B Architecture Patterns

### B.1 CRUD REST API

    Create:
        POST /entity
    Retrieve
        byId: GET /entity/id
        bySimpleQuery: GET /entity?filter1=value1&filter2=value2&sortByAsc=filter1&page=1&pageSize=10&view=summary
        byComplexQuery: POST /entity/query
    Update:
        PUT /entity/id
    Delete
        byId: DELETE /entity/id
        
SimpleQuery supports filtering by several values using AND logic, views / projections, pagination and sorting.
ComplexQuery doesn't have any limitations, it can be freely designed because it is provided in the body of the request.
