package ir.android.testui.presentation.model

data class FoodItem(
    val name: String,
    val imageId: Int,
    val distance: String,
    val rating: Float,
    val reviews: String,
    val originalPrice: Float,
    val deliveryPrice: Float = 0f,
    val isFavorite: Boolean = false
)