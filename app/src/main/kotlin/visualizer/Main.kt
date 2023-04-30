package visualizer

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import bstrees.BinarySearchTree
import bstrees.repos.JsonRepository
import bstrees.repos.TreeRepository
import bstrees.repos.strategies.AVLStrategy
import bstrees.repos.strategies.RBStrategy
import bstrees.repos.strategies.SimpleStrategy
import org.koin.core.context.startKoin
import org.koin.core.qualifier.named
import org.koin.dsl.module
import visualizer.commonui.defaultBackgroundColor
import visualizer.editor.EditorScreen
import visualizer.editor.EditorViewModel
import visualizer.menu.Menu
import java.awt.Dimension


private sealed class Screen {
    object Menu : Screen()

    data class Editor(
        val treeInfo: TreeInfo,
        val tree: BinarySearchTree<NodeData, *>
    ) : Screen()
}

fun main() {
    // setup DI
    val koinModule = module {
        factory<TreeRepository<*>>(named("simpleRepo")) {
            JsonRepository(SimpleStrategy(NodeData::serialize, NodeData::deserialize), "jj")
        }

        factory<TreeRepository<*>>(named("avlRepo")) {
            JsonRepository(AVLStrategy(NodeData::serialize, NodeData::deserialize), "jj")
        }

        factory<TreeRepository<*>>(named("rbRepo")) {
            JsonRepository(RBStrategy(NodeData::serialize, NodeData::deserialize), "jj")
        }
    }
    startKoin { modules(koinModule) }

    // start compose
    application {
        Window(
            onCloseRequest = ::exitApplication,
            title = "graph",
            state = rememberWindowState(
                position = WindowPosition(alignment = Alignment.Center),
                size = DpSize(1100.dp, 700.dp)
            ),
        ) {
            LocalDensity.current.run {
                window.minimumSize = Dimension(550.dp.roundToPx(), 350.dp.roundToPx())
            }

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = defaultBackgroundColor)
                    .padding(30.dp)
            ) {
                MaterialTheme(
                    colorScheme = MaterialTheme.colorScheme.copy(
                        surface = Color.White,
                    )
                ) {
                    var screenState by remember { mutableStateOf<Screen>(Screen.Menu) }
                    when (val screen = screenState) {
                        Screen.Menu -> Menu(
                            onEditTree = { info, tree ->
                                screenState = Screen.Editor(info, tree)
                            }
                        )

                        is Screen.Editor -> EditorScreen(
                            viewModel = EditorViewModel(
                                tree = screen.tree
                            ),
                            treeInfo = screen.treeInfo,
                            onGoHome = { screenState = Screen.Menu }
                        )
                    }
                }
            }
        }
    }
}