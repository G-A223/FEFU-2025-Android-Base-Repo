package co.feip.fefu2025

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.core.content.ContextCompat


class InternetConnectionReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val connectivity =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val netInfo = connectivity.activeNetwork
        val netCap = connectivity.getNetworkCapabilities(netInfo)

        if (netCap != null) {
            if (netCap.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                Log.d("InternetConnectionReceiver", "Internet")
            } else if (netCap.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                Log.d("InternetConnectionReceiver", "Internet")
            } else if (netCap.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                Log.d("InternetConnectionReceiver", "Internet")
            } else {
                Log.d("InternetConnectionReceiver", "No internet")
            }
        } else {
            Log.d("InternetConnectionReceiver", "No internet")
        }
    }
}


class MainActivity : ComponentActivity() {
    private var countClicks = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        ContextCompat.registerReceiver(
            this,
            InternetConnectionReceiver(),
            IntentFilter("android.net.conn.CONNECTIVITY_CHANGE" ),
            ContextCompat.RECEIVER_EXPORTED
        )

        setContentView(R.layout.activity_main)
        val counter: TextView = findViewById(R.id.counter)
        counter.text = "$countClicks"
        counter.setOnClickListener {
            counter.text = "${++countClicks}"
        }
    }

    public override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("countClicks", countClicks)

    }
    public override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        countClicks = savedInstanceState.getInt("countClicks")
        findViewById<TextView>(R.id.counter).text = "$countClicks"
    }
}
