package com.example.weather


import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.BoundedMatcher
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.rule.GrantPermissionRule
import androidx.test.runner.AndroidJUnit4
import com.example.weather.Fragment.My_weather_Fragment
import com.example.weather.Viewmodel.My_weather_model
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.text.SimpleDateFormat
import java.util.*

@LargeTest
@RunWith(AndroidJUnit4::class)
class Weatherlist {
lateinit var model:My_weather_model
    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)

    @Before
    fun setUp() {
        IdlingRegistry.getInstance().register(MyIdlingResource.getIdlingResource())
    }

    @Test
    fun mainActivityTest() {
        checkdata()

        onView(ViewMatchers.withId(R.id.city_name)).check(ViewAssertions.matches(isDisplayed()))
        onView(ViewMatchers.withId(R.id.weather_list)).check(ViewAssertions.matches(isDisplayed()))

        onView(ViewMatchers.withId(R.id.city_name)).check(matches(checkdatecity()))
        onView(ViewMatchers.withId(R.id.weather_list)).check(matches(checkdatetemp()))






    }
    fun checkdata(){
       val fragment= getVisibleFragment() as My_weather_Fragment?
        if (fragment!=null){
            model= fragment.model
        }
    }



    fun checkdatetemp(): BoundedMatcher<View?, RecyclerView> {
        return object : BoundedMatcher<View?, RecyclerView>(RecyclerView::class.java) {
            override fun describeTo(description: Description) {
                description.appendText("has drawable")
            }

            override fun matchesSafely(list: RecyclerView): Boolean {
       if (list.adapter!!.itemCount==model.city!!.weather.size){
           return true
       }
return false
            }
        }
    }
    fun checkdatecities(): BoundedMatcher<View?, RecyclerView> {
        return object : BoundedMatcher<View?, RecyclerView>(RecyclerView::class.java) {
            override fun describeTo(description: Description) {
                description.appendText("has drawable")
            }

            override fun matchesSafely(list: RecyclerView): Boolean {
       if (list.adapter!!.itemCount==model.CitiesList.size){
           return true
       }
return false
            }
        }
    }

    fun checkdatecity(): BoundedMatcher<View?, TextView> {
        return object : BoundedMatcher<View?, TextView>(TextView::class.java) {
            override fun describeTo(description: Description) {
                description.appendText("has drawable")
            }

            override fun matchesSafely(textView: TextView): Boolean {
     if (model.city!!.cityname!!.trim().isNotEmpty()&&model.city!!.cityname!!.equals(textView.text.toString())){

return true
     }
        return false

            }
        }
    }


    private fun childAtPosition(
        parentMatcher: Matcher<View>, position: Int
    ): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }

    fun getVisibleFragment(): Fragment? {
        val fragments: List<Fragment> = mActivityTestRule.activity.supportFragmentManager.fragments
        val visibleFragments: ArrayList<Fragment> = ArrayList()
        if (fragments != null) {
            for (fragment in fragments) {
                if (fragment != null && fragment.isVisible) visibleFragments.add(fragment)
            }
        }
        return visibleFragments[0]
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(MyIdlingResource.getIdlingResource())
    }


}
