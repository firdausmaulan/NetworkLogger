package com.network.logger.detail

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.network.logger.database.AppDatabase
import com.network.logger.databinding.ActivityNetworkLoggerDetailBinding
import com.network.logger.util.Constant
import kotlinx.coroutines.launch


class NetworkLoggerDetailActivity : AppCompatActivity(), NetworkLoggerDetailView {

    private var binding: ActivityNetworkLoggerDetailBinding? = null
    private var presenter: NetworkLoggerDetailPresenter? = null
    private var mData = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNetworkLoggerDetailBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        val uid = intent.getIntExtra(Constant.UID, 0)

        setAction()

        presenter = NetworkLoggerDetailPresenter(this, AppDatabase.getAppDatabase())
        lifecycleScope.launch { presenter?.getData(uid) }
    }

    private fun setAction() {
        binding?.ivBack?.setOnClickListener { finish() }

        binding?.ivShare?.setOnClickListener {
            binding?.ivShare?.visibility = View.GONE
            binding?.progressBarShare?.visibility = View.VISIBLE
            val sendIntent = Intent()
            sendIntent.setAction(Intent.ACTION_SEND)
            sendIntent.putExtra(Intent.EXTRA_TEXT, mData)
            sendIntent.setType("text/plain")
            val shareIntent = Intent.createChooser(sendIntent, null)
            startActivity(shareIntent)
        }
    }

    override fun showData(data: String?) {
        mData = data.toString()
        binding?.progressBar?.visibility = View.GONE
        binding?.tvData?.text = mData
    }

    override fun onResume() {
        super.onResume()
        binding?.ivShare?.visibility = View.VISIBLE
        binding?.progressBarShare?.visibility = View.GONE
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter?.unBind()
    }
}
