package ir.android.testui.presentation.screens.home

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.android.testui.R
import ir.android.testui.presentation.base.BaseIntent
import ir.android.testui.presentation.base.BaseVM
import ir.android.testui.presentation.model.Banner
import ir.android.testui.presentation.model.FoodCategory
import ir.android.testui.presentation.model.FoodItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeVM @Inject constructor(
    //use cases , repository ...
) : BaseVM() {

    private val _categoriesState = MutableStateFlow<List<FoodCategory>>(emptyList())
    val categoriesState = _categoriesState.asStateFlow()

    private val _discountState = MutableStateFlow<List<FoodItem>>(emptyList())
    val discountState = _discountState.asStateFlow()

    private val _recommendedState = MutableStateFlow<List<FoodItem>>(emptyList())
    val recommendedState = _recommendedState.asStateFlow()

    private val _bannerState = MutableStateFlow<Banner?>(null)
    val bannerState = _bannerState.asStateFlow()

    override fun handleIntent(intent: BaseIntent) {
        viewModelScope.launch(Dispatchers.IO) {
            when (intent as HomeIntent) {
                is HomeIntent.LoadAll -> {
                    loadBanner()
                    loadCategories()
                    loadRecommendedItems()
                    loadDiscountItems()
                }
                is HomeIntent.LoadCategories -> loadCategories()
                is HomeIntent.LoadDiscountItems -> loadDiscountItems()
                is HomeIntent.LoadRecommendedItems -> loadRecommendedItems()
                is HomeIntent.OnBannerClick -> {}
            }
        }
    }

    private fun loadCategories() {
        val list = listOf(
            FoodCategory("Hamburger", R.drawable.ic_hamburger),
            FoodCategory("Pizza", R.drawable.ic_pizza),
            FoodCategory("Noodles", R.drawable.ic_noodles),
            FoodCategory("Meat", R.drawable.ic_meat),
            FoodCategory("Vegetables", R.drawable.ic_vegetables),
            FoodCategory("Dessert", R.drawable.ic_dessert),
            FoodCategory("Drink", R.drawable.ic_drink),
            FoodCategory("More", R.drawable.ic_more)
        )
        _categoriesState.value = list
    }

    private fun loadDiscountItems() {
        val list = listOf(
            FoodItem(
                "Mixed Salad Bowl",
                R.drawable.img_salad_bowl,
                "1.5 km",
                4.8f,
                "1.2k",
                6.00f,
                2.00f
            ),
            FoodItem(
                "Vegetarian Menu",
                R.drawable.img_vegetarian,
                "1.7 km",
                4.7f,
                "900",
                5.50f,
                2.00f
            )
        )
        _discountState.value = list
    }

    private fun loadRecommendedItems() {
        val list = listOf(
            FoodItem(
                "Vegetarian Noodles",
                R.drawable.img_veggie_noodles,
                "800 m",
                4.9f,
                "2.3k",
                0f,
                2.00f
            ),
            FoodItem(
                "Pizza Hut - Lumintu",
                R.drawable.img_pizza,
                "1.2 km",
                4.5f,
                "19k",
                0f,
                1.50f,
                isFavorite = true
            ),
            FoodItem(
                "Mozarella Cheese Burger",
                R.drawable.img_cheeseburger,
                "1.6 km",
                4.6f,
                "1.5k",
                0f,
                2.50f
            ),
            FoodItem(
                "Fruit Salad - Kumpa",
                R.drawable.img_fruit_salad,
                "1.4 km",
                4.7f,
                "1.7k",
                0f,
                2.00f
            )
        )
        _recommendedState.value = list
    }

    private fun loadBanner() {
        _bannerState.value = Banner(1, R.drawable.img_burger)
    }

}