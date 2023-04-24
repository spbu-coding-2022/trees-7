
# bstrees - BSTs made easier

`bstrees` is a library that provides kotlin implementations of 3 binary search trees data structures: [Simple BST](https://en.wikipedia.org/wiki/Binary_search_tree), [AVL tree](https://en.wikipedia.org/wiki/AVL_trees), [Red-black tree](https://en.wikipedia.org/wiki/Red–black_tree). `bstrees` also provides implementation of `Repository` pattern which allows to store BSTs in either plain `.json` files, `SQL` or `neo4j` databases.


## Getting started
To build the library run
```bash
  ./gradlew build
```

## Using BSTs
Any `Comparable` data can be stored in trees.
```kotlin
import bstrees.AVLTree
import bstrees.RBTree
import bstrees.SimpleBST

val rbTree = RBTree<Int>() // instantiate empty red-black tree
val avlTree = AVLTree<Double>() // instantiate empty AVL tree
val simpleTree = SimpleBST<String>() // instantiate empty simple tree
```

Each tree supports 3 basic operations: `insert`, `search`, `delete`.
```kotlin
rbTree.insert(10)
rbTree.insert(20)

rbTree.search(10) // returns 10
rbTree.search(500) // returns null

rbTree.delete(20) // returns 20
rbTree.delete(-100) // returns null
```

Trees' nodes can be read-only accessed by `root` property.
```kotlin
avlTree.insert(10.0)
avlTree.insert(-10.0)
avlTree.insert(20.0)
avlTree.insert(30.0)

//            10.0
//    ┌─────────┴─────────┐
// -10.0                20.0
//                        └────┐
//                           30.0

avlTree.root?.data // 10.0
avlTree.root?.left?.data // -10.0
avlTree.root?.right?.data // 20.0
avlTree.root?.right?.right?.data // 30.0
avlTree.root?.right?.left?.data // null
```
## Storing BSTs
`bstrees` provides `JsonRepository`, `SqlRepository` and `Neo4jRepository` to save & load BSTs.

Each instance of repository is used to store exactly 1 tree type. To store different tree types several repositories can be instantiated.
Repository must be provided with `SerializationStrategy` which describes how to serialize & deserialize any particular type of tree.

`bstrees` is shipped with `AVLStrategy`, `RBStrategy` and `SimpleStrategy` to serialize & deserialize AVL trees, Red-black trees and Simple BSTs respectively. As these strategies don't know anything about the data type stored in trees' nodes, user must provide `serializeData` and `deserializeData` functions to them.
```kotlin
val avlIntRepo = JsonRepository(
    AVLStrategy(Int::toString, String::toInt),
    "saved_avl"
) // stores AVLTree<Int>

val rbDoubleRepo = JsonRepository(
    RBStrategy(Double::toString, String::toDouble),
    "saved_rb"
) // stores RBTree<Double>
```

### Saving & loading
Each repository acts like an associative array with trees' names as keys and trees as values.
```kotlin
val tree1 = AVLTree<Int>().apply {
    listOf(1, 2, 3, 4).forEach(::insert)
}
val tree2 = AVLTree<Int>().apply {
    listOf(-20, -30, 40, 50).forEach(::insert)
}

avlIntRepo["my tree"] = tree1
avlIntRepo["another tree"] = tree2
avlIntRepo.names // returns ["my tree", "another tree"]

avlIntRepo.remove("my tree") // returns true
avlIntRepo.names // returns ["another tree"]

val restored = avlIntRepo["another tree"] // 'restored' & 'tree2' are the same trees
```

### Storing different tree types in the same database (directory)
Different tree types can be stored in the same database (directory) by creating several repositories and passing them same databases (directory paths).
```kotlin
val db = Database.connect(
    "jdbc:postgresql://url", driver = "org.postgresql.Driver",
    user = "user", password = "pass"
)

val avlRepo = SqlRepository(AVLStrategy(Int::toString, String::toInt), db) // stores AVLTree<Int>
val rbRepo = SqlRepository(RBStrategy(Int::toString, String::toInt), db) // stores RBTree<Int>

// avlRepo & rbRepo use the same database
```

Please note that storing 1 tree type (RB, AVL, Simple) with different data type in the same database (directory) is not supported.
```kotlin
val conf = Configuration.Builder()
    .uri("bolt://localhost")
    .credentials("neo4j", "neo")
    .build()

val avlRepo1 = Neo4jRepository(AVLStrategy(Int::toString, String::toInt), conf)
val rbRepo = Neo4jRepository(RBStrategy(Double::toString, String::toDouble), conf)
// storing AVLTree<Int> and RBTree<Double> in the same database is ok

val avlRepo2 = Neo4jRepository(AVLStrategy<String>({ it }, { it }), conf)
// !!! storing AVLTree<Int> and AVLTree<String> in the same db is unsupported
```
## License
Distributed under the MIT License. See [LICENSE](LICENSE) for more information.
