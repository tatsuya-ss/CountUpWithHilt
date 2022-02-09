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
        }
    }

}

interface CountUp {
    fun countUp(): Int
}

class CountUpImpl @Inject constructor(): CountUp {
    private var count = 0

    override fun countUp(): Int {
        count ++
        return count
    }
}

@InstallIn(SingletonComponent::class)
@Module
object CountUpModule {
    @Provides fun provideCountUp(): CountUp {
        return CountUpImpl()
    }
}

@HiltAndroidApp
class CountUpApplication: Application()