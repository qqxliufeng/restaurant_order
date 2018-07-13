package com.android.ql.restaurant.ui.activity

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import com.android.ql.restaurant.R
import com.android.ql.restaurant.ui.fragment.bottom.BottomMineFragment
import com.android.ql.restaurant.ui.fragment.bottom.BottomTicketFragment
import com.android.ql.restaurant.utils.BottomNavigationViewHelper
import kotlinx.android.synthetic.main.activity_main_layout.*

class MainActivity : BaseActivity() {


    override fun getLayoutId() = R.layout.activity_main_layout

    override fun initView() {
        mBvMainView.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.mMenuBottomTicket->{
                    mVpMainContainer.currentItem = 0
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.mMenuBottomMine->{
                    mVpMainContainer.currentItem = 2
                    return@setOnNavigationItemSelectedListener true
                }
                else->{
                    return@setOnNavigationItemSelectedListener false
                }
            }
        }
        BottomNavigationViewHelper.disableShiftMode(mBvMainView)
        mVpMainContainer.adapter = MainBottomViewPagerAdapter(supportFragmentManager)
    }


    class MainBottomViewPagerAdapter(fragmentManager: FragmentManager) :FragmentStatePagerAdapter(fragmentManager){

        override fun getItem(position: Int): Fragment {
            return if (position == 0){
                BottomTicketFragment()
            }else{
                BottomMineFragment()
            }
        }
        override fun getCount() = 2
    }

}
