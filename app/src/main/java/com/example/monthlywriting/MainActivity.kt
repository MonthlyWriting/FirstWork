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
import com.example.monthlywriting.util.CurrentInfo
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
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

    fun setDailyWritingTitle() {
        binding.contentMain.collapsingToolbar.title = CurrentInfo.currentMonthName
    }

    fun setMonthlyWritingTitle() {

    }
}