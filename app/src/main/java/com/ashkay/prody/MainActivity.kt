package com.ashkay.prody

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.ashkay.prody.ui.main.AppListFragment
import com.ashkay.prody.ui.main.CalendarFragment
import com.ashkay.prody.ui.main.HomeFragment
import com.ashkay.prody.utils.pageTransformers.ZoomOutPageTransformer

class MainActivity : FragmentActivity() {

    private lateinit var viewPager: ViewPager2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        viewPager = findViewById(R.id.viewPager)
        val pagerAdapter = ScreenSlidePagerAdapter(this)
        viewPager.adapter = pagerAdapter
        viewPager.setPageTransformer(ZoomOutPageTransformer())
        viewPager.currentItem = 1
    }

    private inner class ScreenSlidePagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
        override fun getItemCount(): Int = NUM_PAGES

        override fun createFragment(position: Int): Fragment {
            return when (position) {
                0 -> AppListFragment()
                1 -> HomeFragment()
                2 -> CalendarFragment()
                else -> HomeFragment()
            }
        }
    }

    companion object {
        private const val NUM_PAGES = 3
    }
}