package com.example.monthlywriting

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.monthlywriting.databinding.ActivityMainBinding
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setDisplay()

    }

    private fun setDisplay() {

        setSupportActionBar(binding.contentMain.toolbar)

        binding.contentMain.appbar.outlineProvider = null

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragment_container_view) as NavHostFragment
        val navController = navHostFragment.navController

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_daily_writing,
                R.id.nav_monthly_writing
            ), binding.drawerLayout
        )

        binding.navigationView.setupWithNavController(navController)
        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.fragment_container_view)
        return navController.navigateUp(appBarConfiguration) || super.onNavigateUp()
    }

    fun setDailyWritingTitle(){
        val title = binding.contentMain.toolbarTitle
        when(SimpleDateFormat("MM").format(Date(System.currentTimeMillis()))){
            "01" -> {title.text = resources.getString(R.string.January_short)}
            "02" -> {title.text = resources.getString(R.string.February_short)}
            "03" -> {title.text = resources.getString(R.string.March_short)}
            "04" -> {title.text = resources.getString(R.string.April_short)}
            "05" -> {title.text = resources.getString(R.string.May_short)}
            "06" -> {title.text = resources.getString(R.string.June_short)}
            "07" -> {title.text = resources.getString(R.string.July_short)}
            "08" -> {title.text = resources.getString(R.string.August_short)}
            "09" -> {title.text = resources.getString(R.string.September_short)}
            "10" -> {title.text = resources.getString(R.string.October_short)}
            "11" -> {title.text = resources.getString(R.string.November_short)}
            "12" -> {title.text = resources.getString(R.string.December_short)}

        }
    }

    fun setMonthlyWritingTitle(){
        val title = binding.contentMain.toolbarTitle
        title.text = "Monthly Writing"
    }
}