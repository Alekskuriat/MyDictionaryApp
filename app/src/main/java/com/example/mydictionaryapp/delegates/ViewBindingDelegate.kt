package com.example.mydictionaryapp.delegates

import android.os.Looper
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.viewbinding.ViewBinding
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

fun <V : ViewBinding> Fragment.viewBinding(bind: (view: View) -> V) =
    ViewBindingDelegate(bind)


class ViewBindingDelegate<V : ViewBinding>(
    private val bind: (view: View) -> V
) : ReadOnlyProperty<Fragment, V> {

    private var viewBinding: V? = null

    override fun getValue(thisRef: Fragment, property: KProperty<*>): V {
        return viewBinding ?: run {
            bind.invoke(thisRef.requireView()).also {
                viewBinding = it
            }
        }
    }
}