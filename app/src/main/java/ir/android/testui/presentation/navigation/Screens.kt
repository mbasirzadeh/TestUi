package ir.android.testui.presentation.navigation

import ir.android.testui.R

sealed class Screens(val name: String,val route: String,val icon: Int? = null) {
    data object Main : Screens("Main","Main")
    data object Home : Screens("Home","Home",R.drawable.home)
    data object Orders : Screens("Orders","Orders",R.drawable.receipt)
    data object Messages : Screens("Messages","Messages",R.drawable.message)
    data object Wallet : Screens("Wallet","Wallet",R.drawable.accountbalancewallet)
    data object Profile : Screens("Profile","Profile",R.drawable.person)
}