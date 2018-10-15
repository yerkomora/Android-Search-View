package cl.infomatico.android.examples.searchview

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.SearchView
import android.view.Menu
import android.view.MenuItem

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    // AppCompatActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        handleIntent(intent)
    }

    override fun onNewIntent(intent: Intent?) {
        setIntent(intent)
        handleIntent(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)

        // SearchItem

        val searchItem = menu.findItem(R.id.action_search)

        searchItem.setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
            override fun onMenuItemActionCollapse(item: MenuItem?): Boolean {
                text = null
                goMain()
                return true
            }

            override fun onMenuItemActionExpand(item: MenuItem?): Boolean {
                return true
            }
        })

        // SearchView

        searchView = searchItem.actionView as SearchView

        // SearchManager

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))

        return true
    }

    // MainActivity

    private lateinit var searchView: SearchView

    private var text: String? = null

    private fun goMain() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    private fun handleIntent(intent: Intent?) {
        if (intent != null && Intent.ACTION_SEARCH == intent.action) {
            text = intent.getStringExtra(SearchManager.QUERY)

            if (searchView.query.isEmpty())
                searchView.setQuery(text, false)

            searchView.clearFocus()
        }

        itemsLoad()
    }

    private fun itemsLoad() {
        val resultMsg = "Result text: $text"
        tvResult.text = resultMsg
    }
}