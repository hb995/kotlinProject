package rit.edu.gjs8472.finalproject.data.onboarding

import rit.edu.gjs8472.finalproject.R

class OnBoardingItems(
    val image: Int,
    val title: Int,
    val desc: Int
) {
    companion object{
        fun getData(): List<OnBoardingItems>{
            return listOf(
                OnBoardingItems(R.drawable.img1, R.string.onBoardingTitle1, R.string.onBoardingText1),
                OnBoardingItems(R.drawable.img2, R.string.onBoardingTitle2, R.string.onBoardingText2),
                OnBoardingItems(R.drawable.img3, R.string.onBoardingTitle3, R.string.onBoardingText3)
            )
        }
    }
}