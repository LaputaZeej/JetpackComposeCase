package com.yunext.twins.compose.base

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelLazy
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.MutableCreationExtras
import com.yunext.twins.compose.ui.debug.D
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * 探索ViewModel共享
 */
internal interface Repository {
    companion object : CreationExtras.Key<Repository>
}

private class RepositoryImpl : Repository
private class RepositoryImpl2 : Repository

internal class ViewModelTemplate2() : ViewModel()
internal class ViewModelTemplate(
    private val repository: Repository = RepositoryImpl(),
    private val id: String = "default-id",
) : ViewModel() {

    var count: MutableStateFlow<Int> = MutableStateFlow(1)
        private set

    fun update(num: Int) {
        viewModelScope.launch {
            try {
                count.update {
                    num
                }
            } catch (e: Exception) {
                e.printStackTrace()
                count.update {
                    -1
                }
            } finally {
            }
        }
    }

    init {
        D.i("_VM_ repository:${repository.hashCode()} ,id:${id} ")
    }

    companion object {
        @JvmStatic
        fun providerFactory(repository: Repository, id: String): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return ViewModelTemplate(repository, id) as T
                }
            }

        @JvmStatic
        fun providerCreationExtras(id: String): CreationExtras {
            return MutableCreationExtras().apply {
                this[Repository] = RepositoryImpl2()
                this[CreationExtrasKey] = id
            }
        }

        private object CreationExtrasKey : CreationExtras.Key<String>
    }
}

//internal interface AppSingleContainer {
//    val repository: Repository
//
//    private class AppContainerImpl() : AppContainer {
//        override val repository: Repository
//            get() = RepositoryImpl()
//
//    }
//
//    companion object {
//        internal val container: AppContainer = AppContainerImpl()
//    }
//}

internal class ActivityTemplate : FragmentActivity() {

    private val vm1: ViewModelTemplate by viewModels<ViewModelTemplate>(
        factoryProducer = {
            ViewModelTemplate.providerFactory(RepositoryImpl(), "vm1-1")
        },
        extrasProducer = {
            ViewModelTemplate.providerCreationExtras("vm1-2")
        })

    private lateinit var vm2: ViewModelTemplate

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vm2 = ViewModelProvider(
            this.viewModelStore,
            factory = ViewModelTemplate.providerFactory(RepositoryImpl(), "vm2-1"),
            defaultCreationExtras = ViewModelTemplate.providerCreationExtras("vm2-2")
        )[ViewModelTemplate::class.java]
        // vm1 = vm2 优先vm2
        D.i("------------- 1")
        D.i("_VM_ vm1:${vm1.hashCode()} ")
        D.i("_VM_ vm2:${vm2.hashCode()} ")
        vm1.update(111)
        D.i("------------- 2")
        lifecycleScope.launch {
            delay(2000)
            D.i("_VM_ ActivityTemplate 跳转2")
            startActivity(Intent(this@ActivityTemplate, ActivityTemplate2::class.java))
        }
    }
}

internal class ActivityTemplate2 : FragmentActivity() {
    private val vm by globalViewModelContainer.viewModels<ViewModelTemplate>(
        factoryProducer = {
            ViewModelTemplate.providerFactory(
                RepositoryImpl(),
                "ActivityTemplate2-1"
            )
        },

        extrasProducer = {
            ViewModelTemplate.providerCreationExtras("ActivityTemplate2-2")
        }
    )

    private val vm2 by globalViewModelContainer2.viewModels<ViewModelTemplate2>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vm.count.flowWithLifecycle(lifecycle).onEach {
            D.i("_VM_ ActivityTemplate2::count :${vm.count.value}")
        }.launchIn(lifecycleScope)
        vm.update(222)
        lifecycleScope.launch {
            delay(2000)
            D.i("_VM_ ActivityTemplate2 跳转3")
            startActivity(Intent(this@ActivityTemplate2, ActivityTemplate3::class.java))
        }

        D.e("_VM_ ActivityTemplate2 vm:${vm.hashCode()}")
        D.e("_VM_ ActivityTemplate2 vm2:${vm2.hashCode()}")
    }
}

internal class ActivityTemplate3 : FragmentActivity() {
    private val vm by globalViewModelContainer.viewModels<ViewModelTemplate>(
        factoryProducer = {
            ViewModelTemplate.providerFactory(
                RepositoryImpl(),
                "ActivityTemplate3-1"
            )
        },

        extrasProducer = {
            ViewModelTemplate.providerCreationExtras("ActivityTemplate3-2")
        }
    )

    private val vm2 by globalViewModelContainer2.viewModels<ViewModelTemplate2>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vm.count.flowWithLifecycle(lifecycle).onEach {
            D.i("_VM_ ActivityTemplate3::count :${vm.count.value}")
        }.launchIn(lifecycleScope)

        lifecycleScope.launch {
            delay(2000)
            vm.update(333)
            finish()
        }
        D.e("_VM_ ActivityTemplate2 vm:${vm.hashCode()}")
        D.e("_VM_ ActivityTemplate2 vm2:${vm2.hashCode()}")

    }
}

private val globalViewModelContainer = GlobalViewModelContainer()
private val globalViewModelContainer2 = GlobalViewModelContainer()

class GlobalViewModelContainer {
    internal val viewModelStore: ViewModelStore = ViewModelStore()

    internal inline fun <reified T : ViewModel> viewModel(
        factory: ViewModelProvider.Factory,
        extras: CreationExtras,
    ): T {
        return ViewModelProvider(viewModelStore, factory, extras)[T::class.java]
    }

    fun clear(){
        viewModelStore.clear()
    }

    internal val defaultCreationExtras: CreationExtras = MutableCreationExtras().apply {
        this[GlobalViewModelContainer] =
            GlobalViewModelContainer::class.java.canonicalName
                ?: throw IllegalStateException("canonicalName is null")
        this[DemoKey] = 123
    }

    internal val defaultFactory = object : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return ViewModelProvider(
                viewModelStore,
                ViewModelProvider.AndroidViewModelFactory()
            )[modelClass]
        }

        override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {

            val globalViewModelContainerClzName =
                extras[GlobalViewModelContainer]
                    ?: throw IllegalStateException("GlobalViewModelContainerClzName is null")
            val demoKey = extras[DemoKey]
                ?: throw IllegalStateException("DemoKey is null")
            // use globalViewModelContainerClzName demoKey

            return ViewModelProvider(
                viewModelStore,
                ViewModelProvider.AndroidViewModelFactory(),
                extras
            )[modelClass]
        }
    }

    companion object : CreationExtras.Key<String> {
        private object DemoKey : CreationExtras.Key<Any>

    }
}

internal inline fun <reified VM : ViewModel> GlobalViewModelContainer.viewModels(
    noinline extrasProducer: (() -> CreationExtras)? = null,
    noinline factoryProducer: (() -> ViewModelProvider.Factory)? = null,
): Lazy<VM> {
    return ViewModelLazy(
        VM::class,
        { viewModelStore },
        { factoryProducer?.invoke() ?: this.defaultFactory },
        { extrasProducer?.invoke() ?: this.defaultCreationExtras }
    )
}

// 直接用单例的好？

internal object ViewModelTemplate3 : ViewModel() {
    private val repository: Repository = RepositoryImpl()
    private const val id: String = "default-id"
    var count: MutableStateFlow<Int> = MutableStateFlow(1)
        private set

    fun update(num: Int) {
        viewModelScope.launch {
            try {
                count.update {
                    num
                }
            } catch (e: Exception) {
                e.printStackTrace()
                count.update {
                    -1
                }
            } finally {
            }
        }
    }

    override fun onCleared() {
        super.onCleared()

    }

    init {
        D.i("_VM_ repository:${repository.hashCode()} ,id:${id} ")
    }
}