package com.fenixgs.filmedodia.presentation.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.fenixgs.filmedodia.R

sealed class BottomNavItem(
    val route: String,
    @StringRes val titleResId: Int,
    @DrawableRes val iconResId: Int
) {
    object Home : BottomNavItem("home", R.string.home, R.drawable.ic_home)
    object Profile : BottomNavItem("profile", R.string.profile, R.drawable.ic_profile)
    object Logout : BottomNavItem("logout", R.string.logout, R.drawable.ic_logout)
}
