package ir.android.testui.presentation.navigation.graph

sealed class Graphs(val route: String) {
    data object MainGraph : Graphs("MainGraph")
}