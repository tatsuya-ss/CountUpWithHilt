package com.example.countupwithhilt

import android.app.Application
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.countupwithhilt.databinding.ActivityMainBinding
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject
import javax.inject.Singleton

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    @Inject lateinit var useCase: UseCase

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
            binding.countUpText.text = useCase.countUp().toString()
        }
    }

}

interface UseCase {
    fun countUp(): Int
}

class UseCaseImpl @Inject constructor(private val countUp: CountUp): UseCase {

    override fun countUp(): Int {
        return countUp.countUp()
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

// HiltModuleで提供
@InstallIn(SingletonComponent::class)
@Module
object CountUpModule {
    @Provides fun provideUseCase(countUp: CountUp): UseCase {
        return UseCaseImpl(countUp = countUp)
    }
    @Provides fun provideCountUp(): CountUp {
        return CountUpImpl()
    }
}

// Bindで提供のパターン。インターフェースを使用する実装に使える
// 今回で言うと、constructorにインターフェースを設定したUseCaseに使う
//@InstallIn(SingletonComponent::class)
//@Module
//abstract class UseCaseBind {
//    @Binds abstract fun bindUseCase(impl: UseCaseImpl): UseCase
//}

@HiltAndroidApp
class CountUpApplication: Application()