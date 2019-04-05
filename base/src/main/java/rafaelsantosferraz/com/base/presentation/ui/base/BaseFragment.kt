package rafaelsantosferraz.com.base.presentation.ui.base

import android.util.Log
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import rafaelsantosferraz.com.base.R
import retrofit2.HttpException
import java.net.ConnectException
import java.net.HttpURLConnection
import java.net.SocketTimeoutException


open class BaseFragment : Fragment() {

    private val TAG = "BaseFragment"


    protected fun showToast(message: String) {
        context?.let {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }


    // region Handle Error ---------------------------------------------------------------------------------------------
    open fun handleError(throwable: Throwable) {
        when(throwable) {
            is ConnectException -> showErrorDialog(R.string.error_no_internet)
            is SocketTimeoutException -> showErrorDialog(R.string.error_bad_connection)
            is HttpException ->
                when(throwable.code()){
                    HttpURLConnection.HTTP_UNAUTHORIZED -> showErrorDialog(R.string.error_session_expired, false)
                    HttpURLConnection.HTTP_PRECON_FAILED -> showErrorDialog(R.string.error_precon_failed)
                    else -> showErrorDialog(R.string.error_unknown)
            }
            else -> showErrorDialog(R.string.error_unknown)
        }
        Log.e(TAG, "$throwable")
    }
    // endregion




    // region Dialog ---------------------------------------------------------------------------------------------------
    private fun showErrorDialog(
        @StringRes messageId: Int,
        isCancelable: Boolean = true,
        @StringRes textId: Int = R.string.ok,
        functionBlock : (() -> Unit)? = null){

        AlertDialog.Builder(context!!)
                .setMessage(messageId)
                .setCancelable(isCancelable)
                .setPositiveButton(textId){ _,_ ->
                    functionBlock?.invoke()
                }
                .show()
    }
    // end region
}
