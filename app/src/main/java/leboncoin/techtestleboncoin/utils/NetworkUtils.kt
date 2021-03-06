package leboncoin.techtestleboncoin.utils

import android.content.Context
import android.net.ConnectivityManager

class NetworkUtils {

    companion object {

        /**
         * this function checks if user has any internet access and returns a Boolean
         */
        fun isConnectedToInternet(context:Context): Boolean {
            val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

            val activeNetwork = cm.activeNetworkInfo

            return activeNetwork != null && activeNetwork.isConnected
        }

    }

}