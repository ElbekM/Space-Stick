package com.elbek.space_stick.common.core

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.Fragment
import com.elbek.space_stick.R
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.SupervisorJob
import kotlin.coroutines.CoroutineContext

abstract class BaseBottomSheetDialogFragment<TViewModel : BaseViewModel>
    : BottomSheetDialogFragment(), CoroutineScope by MainScope(), FragmentBindings {

    protected abstract val viewModel: TViewModel

    private lateinit var bottomSheetBehavior: BottomSheetBehavior<FrameLayout>
    private lateinit var coordinatorLayout: CoordinatorLayout

    override val coroutineContext: CoroutineContext = Dispatchers.IO + SupervisorJob()
    override val self: Fragment
        get() = this

    override fun getTheme(): Int = R.style.AppBottomSheetDialogTheme

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireView().clipToOutline = true

        requireDialog().setOnShowListener {
            val dialog = requireDialog() as BottomSheetDialog
            val bottomSheet = dialog.findViewById<FrameLayout>(com.google.android.material.R.id.design_bottom_sheet)
            coordinatorLayout = bottomSheet?.parent as CoordinatorLayout
            bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
            bottomSheetBehavior.peekHeight = bottomSheet.height
            coordinatorLayout.parent.requestLayout()
        }
    }

    protected open fun bindViewModel() {
        viewModel.closeCommand.observe { dismissAllowingStateLoss() }
    }

    fun updateBottomSheetPickHeight(view: View) {
        ObjectAnimator.ofInt(
            bottomSheetBehavior, "peekHeight",
            bottomSheetBehavior.peekHeight, bottomSheetBehavior.peekHeight + view.height
        )
            .apply {
                duration = 250
                start()
            }
    }
}
