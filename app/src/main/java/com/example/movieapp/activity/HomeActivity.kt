package com.example.movieapp.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.viewpager.widget.ViewPager
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import butterknife.ButterKnife
import com.example.movieapp.R
import com.example.movieapp.constant.constant.SEARCH_QUERY
import com.example.moviesapp.adapter.ViewPagerAdapter
import com.google.android.material.tabs.TabLayout
import com.miguelcatalan.materialsearchview.MaterialSearchView

class HomeActivity : AppCompatActivity(),OnPageChangeListener  {


    lateinit var viewpageradapter: ViewPagerAdapter //Declare PagerAdapter
    var tablayout: TabLayout? = null
    var viewPager: ViewPager? = null
    var toolbar: Toolbar? = null
    lateinit var searchBar:MaterialSearchView
    val BROADCAST_FOR_MOVIES_SEARCHBAR = "broadcast_for_movie_searchbar"
val BROADCAST_FOR_MOVIES_SEARCHBAR_CLOSER="broadcast_for_movie_searchbar_closer"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        ButterKnife.bind(this)
        tablayout = findViewById(R.id.tablayout)
       viewPager = findViewById(R.id.viewpager)
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        searchBar=findViewById(R.id.search_bar)

//https://www.w3adda.com/kotlin-android-tutorial/kotlin-tablayout-example
        viewpageradapter = ViewPagerAdapter(supportFragmentManager, 2)
        viewPager?.adapter = viewpageradapter  //Binding PagerAdapter with ViewPager
        tablayout?.setupWithViewPager(viewPager) //Binding ViewPager with TabLayout
        viewPager!!.addOnPageChangeListener(this)

        searchBar.setOnQueryTextListener(object : MaterialSearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onQueryTextChange(newText: String?): Boolean {

                val intent = Intent(BROADCAST_FOR_MOVIES_SEARCHBAR)
                    intent.putExtra(SEARCH_QUERY,newText)
                sendBroadcast(intent)
                return false
            }

        })

        //searchBar Closer
        searchBar.setOnSearchViewListener(object :MaterialSearchView.SearchViewListener{
            override fun onSearchViewClosed() {
                val intent = Intent(BROADCAST_FOR_MOVIES_SEARCHBAR_CLOSER)
                sendBroadcast(intent)
            }

            override fun onSearchViewShown() {

            }

        })

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_item, menu)
        val searchMenuItem = menu.findItem(R.id.action_search)
        searchBar.setMenuItem(searchMenuItem)
        return super.onCreateOptionsMenu(menu)

    }

    override fun onPageScrollStateChanged(state: Int) {

    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
    }

    override fun onPageSelected(position: Int) {
        val state:Int=0
       /* if(position==state){
            searchBar.showSearch()

        }else{
           searchBar.closeSearch()

        }*/
    }


}


