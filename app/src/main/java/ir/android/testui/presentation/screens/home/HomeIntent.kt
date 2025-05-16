package ir.android.testui.presentation.screens.home

import ir.android.testui.presentation.base.BaseIntent

sealed class HomeIntent : BaseIntent() {
    object LoadAll : HomeIntent()
    object LoadCategories : HomeIntent()
    object LoadDiscountItems : HomeIntent()
    object LoadRecommendedItems : HomeIntent()
    data class OnBannerClick(val id: Int) : HomeIntent()
}