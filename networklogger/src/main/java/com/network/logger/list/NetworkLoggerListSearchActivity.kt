package com.network.logger.list

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.network.logger.R
import com.network.logger.database.AppDatabase
import com.network.logger.database.NetworkLoggerModel
import com.network.logger.databinding.ActivityNetworkLoggerSearchBinding
import com.network.logger.detail.NetworkLoggerDetailActivity
import com.network.logger.util.Constant
import com.network.logger.util.EditTextDelay
import kotlinx.coroutines.launch

class NetworkLoggerListSearchActivity : AppCompatActivity(), NetworkLoggerView {

    private var binding: ActivityNetworkLoggerSearchBinding? = null
    private var presenter: NetworkLoggerPresenter? = null
    private var editTextDelay: EditTextDelay? = null
    private var networkLoggerAdapter: NetworkLoggerAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNetworkLoggerSearchBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setView()
        setAction()

        presenter = NetworkLoggerPresenter(this, AppDatabase.getAppDatabase())
    }

    private fun setView() {
        editTextDelay = EditTextDelay(this)
        val rvVolleyLogger = findViewById<RecyclerView>(R.id.rvVolleyLogger)
        networkLoggerAdapter = NetworkLoggerAdapter(this)
        rvVolleyLogger.adapter = networkLoggerAdapter
    }

    private fun setAction() {
        binding?.ivBack?.setOnClickListener { finish() }

        binding?.etSearch?.addTextChangedListener(editTextDelay?.setListener(object : EditTextDelay.Listener{
            override fun onTextChanged(data: String?) {
                networkLoggerAdapter?.clear()
                if (!data.isNullOrEmpty()) {
                    lifecycleScope.launch { presenter?.getSearchData(data) }
                }
            }
        }))

        networkLoggerAdapter?.setClickListener { _: View?, model: NetworkLoggerModel? ->
            val intent = Intent(this, NetworkLoggerDetailActivity::class.java)
            intent.putExtra(Constant.UID, model?.uid)
            startActivity(intent)
        }
    }

    override fun showData(list: List<NetworkLoggerModel?>?) {
        networkLoggerAdapter?.addList(list)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter?.unBind()
    }

    override fun showLoading() {
    }

    override fun hideLoading() {
    }
}
