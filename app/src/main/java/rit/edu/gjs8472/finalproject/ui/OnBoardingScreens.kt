package rit.edu.gjs8472.finalproject.ui

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowLeft
import androidx.compose.material.icons.outlined.KeyboardArrowRight
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import rit.edu.gjs8472.finalproject.data.onboarding.OnBoardingItems

@ExperimentalPagerApi
@Composable
fun OnBoarding(navController: NavController) {
    val items = OnBoardingItems.getData()
    val scope = rememberCoroutineScope()
    val pageState = rememberPagerState()

    Column(modifier = Modifier.fillMaxSize()) {
        TopSection(
            onBackClick = { navigateBack(scope, pageState) },
            onSkipClick = { skipToEnd(scope, pageState, items.size) }
        )

        HorizontalPager(
            count = items.size,
            state = pageState,
            modifier = Modifier.fillMaxHeight(0.9f).fillMaxWidth()
        ) { page ->
            OnBoardingItem(item = items[page])
        }

        BottomSection(
            size = items.size,
            index = pageState.currentPage,
            onButtonClick = { navigateForward(scope, pageState, items.size) },
            onFinish = { navController.navigate("home") }
        )
    }
}

@Composable
fun TopSection(onBackClick: () -> Unit, onSkipClick: () -> Unit) {
    Box(
        modifier = Modifier.fillMaxWidth().padding(12.dp)
    ) {
        IconButton(onClick = onBackClick, modifier = Modifier.align(Alignment.CenterStart)) {
            Icon(imageVector = Icons.Outlined.KeyboardArrowLeft, contentDescription = null)
        }

        TextButton(
            onClick = onSkipClick,
            modifier = Modifier.align(Alignment.CenterEnd),
            contentPadding = PaddingValues(0.dp)
        ) {
            Text("Skip", color = MaterialTheme.colorScheme.onBackground)
        }
    }
}

@Composable
fun BottomSection(size: Int, index: Int, onButtonClick: () -> Unit, onFinish: () -> Unit) {
    Box(
        modifier = Modifier.fillMaxWidth().padding(12.dp)
    ) {
        Indicators(size, index)

        FloatingActionButton(
            onClick = {
                if (index < size - 1) {
                    onButtonClick()
                } else {
                    onFinish()
                }
            },
            containerColor = Color.Black,
            modifier = Modifier.align(Alignment.CenterEnd).clip(RoundedCornerShape(15.dp))
        ) {
            Icon(Icons.Outlined.KeyboardArrowRight, tint = Color.White, contentDescription = "Next")
        }
    }
}

@Composable
fun BoxScope.Indicators(size: Int, currentIndex: Int) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier.align(Alignment.CenterStart)
    ) {
        (0 until size).forEach { index ->
            Indicator(isSelected = index == currentIndex)
        }
    }
}

@Composable
fun Indicator(isSelected: Boolean) {
    val width = animateDpAsState(
        targetValue = if (isSelected) 25.dp else 10.dp,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy)
    )

    Box(
        modifier = Modifier
            .height(10.dp)
            .width(width.value)
            .clip(CircleShape)
            .background(
                color = if (isSelected) MaterialTheme.colorScheme.primary else Color(0xFFF8E2E7)
            )
    )
}

@Composable
fun OnBoardingItem(item: OnBoardingItems) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        OnBoardingItemImage(item.image)
        OnBoardingItemText(item.title, MaterialTheme.typography.headlineMedium, FontWeight.Bold)
        OnBoardingItemText(item.desc, MaterialTheme.typography.bodyLarge, FontWeight.Light)
    }
}

@Composable
private fun OnBoardingItemImage(imageId: Int) {
    Image(
        painter = painterResource(id = imageId),
        contentDescription = "Onboarding Image",
        modifier = Modifier
            .padding(start = 50.dp, end = 50.dp)
            .fillMaxWidth()
    )
    Spacer(modifier = Modifier.height(25.dp))
}

@Composable
private fun OnBoardingItemText(text: Int, typography: TextStyle, fontWeight: FontWeight) {
    Text(
        text = stringResource(id = text),
        style = typography,
        color = MaterialTheme.colorScheme.onBackground,
        fontWeight = fontWeight,
        textAlign = TextAlign.Center,
        letterSpacing = 1.sp,
        modifier = Modifier.padding(10.dp)
    )
    Spacer(modifier = Modifier.height(8.dp))
}


@OptIn(ExperimentalPagerApi::class)
private fun navigateBack(scope: CoroutineScope, pageState: PagerState) {
    if (pageState.currentPage > 0) {
        scope.launch {
            pageState.scrollToPage(pageState.currentPage - 1)
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
private fun skipToEnd(scope: CoroutineScope, pageState: PagerState, itemCount: Int) {
    scope.launch {
        pageState.scrollToPage(itemCount - 1)
    }
}

@OptIn(ExperimentalPagerApi::class)
private fun navigateForward(scope: CoroutineScope, pageState: PagerState, itemCount: Int) {
    if (pageState.currentPage + 1 < itemCount) {
        scope.launch {
            pageState.scrollToPage(pageState.currentPage + 1)
        }
    }
}