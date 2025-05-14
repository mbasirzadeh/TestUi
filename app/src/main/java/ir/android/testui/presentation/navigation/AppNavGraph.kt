package ir.android.testui.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import ir.android.testui.presentation.navigation.graph.Graphs
import ir.android.testui.presentation.screens.home.HomeScreen
import ir.android.testui.presentation.screens.main.MainScreen
import ir.android.testui.presentation.screens.messages.MessagesScreen
import ir.android.testui.presentation.screens.orders.OrdersScreen
import ir.android.testui.presentation.screens.profile.ProfileScreen
import ir.android.testui.presentation.screens.wallet.WalletScreen

@Composable
fun AppNavGraph() {
    val navController = rememberNavController()
    //main graph : start destination here is where app start from
    NavHost(navController = navController, startDestination = Graphs.MainGraph.route) {
        //nested graph
        navigation(route = Graphs.MainGraph.route, startDestination = Screens.Main.route) {
            composable(route = Screens.Main.route) { MainScreen() }
            mainGraph()
        }
        // اما خارج از Main هم می‌تونی مستقیم بری به Profile
//        composable("profile_global") {
//            ProfileScreen(navController)
//        }
    }
}

fun NavGraphBuilder.mainGraph() {
    composable(route = Screens.Home.route) { HomeScreen() }
    composable(route = Screens.Orders.route) { OrdersScreen() }
    composable(route = Screens.Messages.route) { MessagesScreen() }
    composable(route = Screens.Wallet.route) { WalletScreen()}
    composable(route = Screens.Profile.route) { ProfileScreen() }
}