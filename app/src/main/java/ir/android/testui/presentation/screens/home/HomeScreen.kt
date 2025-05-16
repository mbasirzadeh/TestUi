package ir.android.testui.presentation.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import ir.android.testui.R
import ir.android.testui.presentation.model.Banner
import ir.android.testui.presentation.model.FoodCategory
import ir.android.testui.presentation.model.FoodItem
import ir.android.testui.presentation.navigation.AppNavGraph
import ir.android.testui.presentation.theme.TestUiTheme
import ir.android.testui.presentation.theme.darkGray
import ir.android.testui.presentation.theme.lightGray
import ir.android.testui.presentation.theme.primaryGreen

@Composable
fun HomeScreen(viewModel: HomeVM = hiltViewModel()) {

    val banner by viewModel.bannerState.collectAsState()
    val categories by viewModel.categoriesState.collectAsState()
    val discountItems by viewModel.discountState.collectAsState()
    val recommendedItems by viewModel.recommendedState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.handleIntent(HomeIntent.LoadAll)
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp),
        verticalArrangement = Arrangement.spacedBy(18.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            HomeHeader()
        }

        item {
            SpecialOffersSection(banner = banner)
        }

        item {
            CategoriesSection(categories = categories)
        }

        item {
            DiscountSectionWrapper(discountItems = discountItems)
        }

        item {
            RecommendedSectionHeader()
        }

        items(recommendedItems) { item ->
            RecommendedFoodItem(foodItem = item)
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
private fun HomeHeader() {
    Column {
        Spacer(modifier = Modifier.height(8.dp))
        DeliveryHeader()
        Spacer(modifier = Modifier.height(8.dp))
        SearchBar()
    }
}

@Composable
private fun SpecialOffersSection(banner: Banner?, viewModel: HomeVM = hiltViewModel()) {
    banner?.let {
        Column {
            SectionHeader(
                title = "Special Offers",
                showSeeAll = true,
                onSeeAllClick = { /* Handle see all click */ }
            )
            SpecialOfferBanner(banner) { viewModel.handleIntent(HomeIntent.OnBannerClick(banner.id)) }
        }
    }

}

@Composable
private fun DiscountSectionWrapper(discountItems: List<FoodItem>) {
    Column {
        SectionHeader(
            title = "Discount Guaranteed! 👌",
            showSeeAll = true,
            onSeeAllClick = { /* Handle see all click */ }
        )
        DiscountSection(discountItems = discountItems)
    }
}

@Composable
private fun RecommendedSectionHeader() {
    Column {
        SectionHeader(
            title = "Recommended For You 😍",
            showSeeAll = true,
            onSeeAllClick = { /* Handle see all click */ }
        )
        FilterChips()
        Spacer(modifier = Modifier.height(15.dp))
    }
}


@Composable
fun DeliveryHeader(viewModel: HomeVM = hiltViewModel()) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.profile_pic),
            contentDescription = "Profile",
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 8.dp)
        ) {
            Text(
                text = "Deliver to",
                fontSize = 12.sp,
                color = Color.Gray
            )
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Times Square",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Icon(
                    imageVector = Icons.Default.KeyboardArrowDown,
                    contentDescription = "Location dropdown",
                    tint = primaryGreen,
                    modifier = Modifier.padding(start = 4.dp)
                )
            }
        }

        Box(
            modifier = Modifier
                .size(40.dp)
                .border(
                    width = 1.dp,
                    color = Color.LightGray,
                    shape = CircleShape
                )
                .clip(CircleShape)
                .clickable { viewModel.handleIntent(HomeIntent.OnNotificationsClick) },
            contentAlignment = Alignment.Center
        ) {
            BadgedBox(
                modifier = Modifier,
                badge = {
                    Badge(
                        modifier = Modifier.offset(x = (-8).dp, y = (1).dp)
                    )
                }
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_notification),
                    contentDescription = "Notifications",
                    modifier = Modifier.size(24.dp),
                    tint = Color.Black
                )
            }
        }

        Spacer(modifier = Modifier.width(12.dp))

        Box(
            modifier = Modifier
                .size(40.dp)
                .border(
                    width = 1.dp,
                    color = Color.LightGray,
                    shape = CircleShape
                )
                .clip(CircleShape)
                .clickable { viewModel.handleIntent(HomeIntent.OnCartClick) },
            contentAlignment = Alignment.Center
        ) {
            BadgedBox(
                modifier = Modifier,
                badge = {
                    Badge(
                        modifier = Modifier.offset(x = (-8).dp, y = (1).dp)
                    )
                }
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_shopping),
                    contentDescription = "Cart",
                    modifier = Modifier.size(24.dp)
                )
            }
        }

    }
}

@Composable
fun SearchBar() {
    var searchText = remember { mutableStateOf("") }

    TextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clip(RoundedCornerShape(15.dp))
            .background(MaterialTheme.colorScheme.surface),
        value = searchText.value,
        onValueChange = { searchText.value = it },
        placeholder = { Text(modifier = Modifier, text = "What are you craving?") },
        leadingIcon = {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.ic_search),
                contentDescription = "Search",
                tint = darkGray
            )
        },
        singleLine = true,
        maxLines = 1,
        colors = TextFieldDefaults.colors(
            unfocusedIndicatorColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedPlaceholderColor = darkGray,
            focusedPlaceholderColor = darkGray,
            focusedLeadingIconColor = darkGray,
            unfocusedLeadingIconColor = darkGray
        )
    )
}

@Composable
fun SectionHeader(
    title: String,
    showSeeAll: Boolean = false,
    onSeeAllClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )

        if (showSeeAll) {
            Text(
                text = "See All",
                color = primaryGreen,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.clickable { onSeeAllClick() }
            )
        }
    }
}

@Composable
fun SpecialOfferBanner(
    bannerItem : Banner,
    onBannerClick: () -> Unit = {}
) {
    Image(
        painter = painterResource(bannerItem.image),
        contentDescription = "Special Offer",
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .fillMaxSize()
            .clickable {
                onBannerClick.invoke()
            }
    )
}

@Composable
fun CategoriesSection(categories: List<FoodCategory>) {
    LazyHorizontalGrid(
        rows = GridCells.Fixed(2),
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp),
        verticalArrangement = Arrangement.Center,
    ) {
        items(categories) { category ->
            CategoryItem(category)
        }
    }
}

@Composable
fun CategoryItem(category: FoodCategory) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(horizontal = 15.dp)
            .clickable {
                //click
            }
    ) {
        Box(
            modifier = Modifier
                .size(60.dp)
                .clip(CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = category.imageId),
                contentDescription = category.name,
                modifier = Modifier.size(50.dp)
            )
        }

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            modifier = Modifier.width(60.dp),
            text = category.name,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
fun DiscountSection(discountItems: List<FoodItem>) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        discountItems.forEach { item ->
            DiscountFoodItem(
                foodItem = item,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
fun DiscountFoodItem(
    foodItem: FoodItem,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(end = 8.dp)
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(6.dp)
                .clip(RoundedCornerShape(12.dp)),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 4.dp
            ),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            )
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp)
                    .padding(10.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(lightGray)
            ) {
                Image(
                    painter = painterResource(id = foodItem.imageId),
                    contentDescription = foodItem.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )

                Box(
                    modifier = Modifier
                        .padding(8.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .background(primaryGreen)
                        .padding(horizontal = 4.dp)
                        .align(Alignment.TopStart)
                ) {
                    Text(
                        text = "PROMO",
                        color = Color.White,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                modifier = Modifier.padding(horizontal = 10.dp),
                text = foodItem.name,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Row(
                modifier = Modifier.padding(horizontal = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = foodItem.distance,
                    fontSize = 12.sp,
                    color = Color.Gray
                )

                Spacer(modifier = Modifier.width(8.dp))

                VerticalDivider(
                    modifier = Modifier.height(9.dp),
                    thickness = 1.dp,
                    color = Color.Gray
                )

                Spacer(modifier = Modifier.width(8.dp))

                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = "Rating",
                    tint = Color(0xFFFFC107),
                    modifier = Modifier.size(12.dp)
                )

                Spacer(modifier = Modifier.width(2.dp))

                Text(
                    text = "${foodItem.rating} (${foodItem.reviews})",
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(top = 2.dp, bottom = 10.dp, start = 10.dp, end = 10.dp)
            ) {
                Text(
                    text = "$${String.format("%.2f", foodItem.originalPrice)}",
                    color = primaryGreen,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.width(8.dp))

                VerticalDivider(
                    modifier = Modifier.height(9.dp),
                    thickness = 1.dp,
                    color = Color.Gray
                )

                Spacer(modifier = Modifier.width(8.dp))


                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.delivery_vehicle),
                        contentDescription = "Discount",
                        tint = primaryGreen,
                        modifier = Modifier.size(14.dp)
                    )

                    Spacer(modifier = Modifier.width(3.dp))

                    Text(
                        text = "$${String.format("%.2f", foodItem.deliveryPrice)}",
                        color = primaryGreen,
                        fontSize = 12.sp
                    )
                }

                Spacer(modifier = Modifier.weight(1f))

                Icon(
                    imageVector = if (foodItem.isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                    contentDescription = "Favorite",
                    tint = if (foodItem.isFavorite) Color.Red else Color.Gray,
                    modifier = Modifier
                        .size(20.dp)
                        .clickable { /* Toggle favorite */ }
                )
            }
        }
    }
}

@Composable
fun FilterChips() {
    val chips =
        listOf("✅ All", "\uD83C\uDF54 Hamburger", "\uD83C\uDF55 Pizza", "\uD83C\uDF7A Drink")
    val selectedChipIndex = remember { mutableStateOf(0) }

    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(chips.size) { index ->
            val isSelected = selectedChipIndex.value == index
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(20.dp))
                    .background(if (isSelected) primaryGreen else Color.White)
                    .border(
                        width = 1.dp,
                        color = if (isSelected) primaryGreen else Color.LightGray,
                        shape = RoundedCornerShape(20.dp)
                    )
                    .clickable { selectedChipIndex.value = index }
                    .padding(horizontal = 12.dp, vertical = 6.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = chips[index],
                    color = if (isSelected) Color.White else Color.Black,
                    fontSize = 14.sp
                )
            }
        }
    }
}

@Composable
fun RecommendedFoodItem(foodItem: FoodItem) {
    Card(
        modifier = Modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(12.dp)),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = foodItem.imageId),
                contentDescription = foodItem.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(12.dp))
            )

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 12.dp)
            ) {
                Text(
                    text = foodItem.name,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(4.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = foodItem.distance,
                        fontSize = 12.sp,
                        color = Color.Gray
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    VerticalDivider(
                        modifier = Modifier.height(9.dp),
                        thickness = 1.dp,
                        color = Color.Gray
                    )

                    Spacer(modifier = Modifier.width(8.dp))


                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = "Rating",
                        tint = Color(0xFFFFC107),
                        modifier = Modifier.size(14.dp)
                    )

                    Spacer(modifier = Modifier.width(2.dp))

                    Text(
                        text = "${foodItem.rating} (${foodItem.reviews})",
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.delivery_vehicle),
                        contentDescription = "Discount",
                        tint = primaryGreen,
                        modifier = Modifier.size(18.dp)
                    )

                    Spacer(modifier = Modifier.width(4.dp))

                    Text(
                        text = "$${String.format("%.2f", foodItem.deliveryPrice)}",
                        color = primaryGreen,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium
                    )

                }
            }
            Column(
                modifier = Modifier
            ) {
                Spacer(
                    modifier = Modifier
                        .height(60.dp)
                        .width(1.dp)
                )
                Icon(
                    imageVector = if (foodItem.isFavorite)
                        ImageVector.vectorResource(R.drawable.ic_favorite_filled)
                    else ImageVector.vectorResource(R.drawable.ic_favorite),
                    contentDescription = "Favorite",
                    tint = Color.Red,
                    modifier = Modifier
                        .size(24.dp)
                        .clickable { /* Handle click */ }
                )
            }
        }

    }

}


@Preview(showBackground = true)
@Composable
fun FoodDeliveryAppPreview() {
    TestUiTheme {
        AppNavGraph()
    }
}