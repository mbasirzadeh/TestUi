package ir.android.testui.presentation.base

import androidx.lifecycle.ViewModel


abstract class BaseVM : ViewModel() {
    //base loadings , events ...
    abstract fun handleIntent(intent : BaseIntent)
}