# BST Visualizer
Application that lets you create, visualize, and perform operations on 3 types of binary search trees:
[Simple](https://en.wikipedia.org/wiki/Binary_search_tree), [AVL](https://en.wikipedia.org/wiki/AVL_trees), [Red-black](https://en.wikipedia.org/wiki/Red–black_tree).

<img width="1000" src="https://user-images.githubusercontent.com/15161335/235806472-98112cf8-37ca-4bc0-ac17-59b9643ac923.gif">


## Features
- Support 3 types of BSTs
- Insert, remove and search for elements in trees
- Store BSTs in 3 different ways: in [PostgreSQL](https://www.postgresql.org) or [Neo4j](https://neo4j.com) db or locally in .json files
- Drag nodes of the trees
- Zoom and drag the screen for better viewing experience


## Built with
- [Kotlin](https://kotlinlang.org) based, [Coroutines](https://github.com/Kotlin/kotlinx.coroutines) for asynchronous execution
- [Compose Multiplatform](https://github.com/JetBrains/compose-multiplatform): modern UI framework for Kotlin
- [Koin](https://github.com/InsertKoinIO/koin): pragmatic lightweight dependency injection framework
- [bstrees](lib): library made specifically for the project that provides BSTs data structures and ways to save them in persistent storage. To learn more please read its own [README](lib/README.md)


## Getting started
You can download the latest version of the application on [Releases](https://github.com/spbu-coding-2022/trees-7/releases) page.


## Build
This project uses [`Gradle`](https://gradle.org) build system. Here are commands for some of its most important tasks:

| Command                                | Description                                                           |
|----------------------------------------|-----------------------------------------------------------------------|
| `./gradlew packageUberJarForCurrentOS` | Creates a single jar file, containing all dependencies for current OS |
| `./gradlew run`                        | Runs the application                                                  |
| `./gradlew assemble`                   | Builds without tests                                                  |
| `./gradlew test`                       | Runs the unit tests                                                   |
| `./gradlew tasks`                      | Displays all runnable tasks                                           |


## Screenshots
<img width="1000" src="https://user-images.githubusercontent.com/15161335/235806032-fa5c2ba9-c1f7-48c4-8316-33b1c5515278.png">
<img width="1000" src="https://user-images.githubusercontent.com/15161335/235806133-991a7e93-60fc-4b13-9674-c13d16712bab.png">


## License
Distributed under the MIT License. See [LICENSE](LICENSE) for more information.
