package com.elbek.space_stick.common.core

import android.app.Activity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.elbek.space_stick.common.core.commands.LiveEvent
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

interface FragmentBindings {
    val self: Fragment

    fun <T> LiveData<T>.observe(observer: (item: T?) -> Unit) =
        observe(getSuitableLifecycleOwner(), Observer(observer))

    fun LiveEvent.observe(block: () -> Unit) =
        this.observe(getSuitableLifecycleOwner(), Observer { block() })

    fun BottomSheetDialogFragment.show() =
        this.show(self.childFragmentManager, this::class.java.name)

    fun Fragment.showAllowingStateLoss(fm: FragmentManager) =
        fm.beginTransaction()
            .add(this, this::class.java.name)
            .addToBackStack(tag)
            .commitAllowingStateLoss()

    private fun getSuitableLifecycleOwner() =
        if (self.view != null) self.viewLifecycleOwner else self
}

val Fragment.parent: Any?
    get() = parentFragment ?: activity

inline fun <reified T> Fragment.castParent(): T? = parent as? T

inline fun <reified T> Fragment.findParentOfType(): T? {
    var parent = parent
    while (parent != null) {
        when (parent) {
            is T -> return parent
            is Activity -> return null
            is Fragment -> parent = parent.parent
        }
    }
    return null
}
