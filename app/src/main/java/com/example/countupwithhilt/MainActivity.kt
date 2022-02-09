package com.example.countupwithhilt

import android.app.Application
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.countupwithhilt.databinding.ActivityMainBinding
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    @Inject lateinit var countUp: CountUp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupBinding()
        setupButton()

    }

    private fun setupBinding() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun setupButton() {
        binding.countUpButton.setOnClickListener {
            binding.countUpText.text = countUp.countUp().toString()
            println(countUp.countUp().toString())
        }
    }

}

class CountUp @Inject constructor() {
    private var count = 0

    fun countUp(): Int {
        count += 1
        return count
    }
}

@InstallIn(SingletonComponent::class)
@Module
object CountUpModule {
    @Provides fun provideCountUp(): CountUp {
        return CountUp()
    }
}

@HiltAndroidApp
class CountUpApplication: Application()