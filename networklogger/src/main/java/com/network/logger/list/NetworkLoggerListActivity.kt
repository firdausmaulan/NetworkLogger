package com.network.logger.list

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.network.logger.database.AppDatabase
import com.network.logger.database.NetworkLoggerModel
import com.network.logger.databinding.ActivityNetworkLoggerListBinding
import com.network.logger.detail.NetworkLoggerDetailActivity
import com.network.logger.util.Constant
import kotlinx.coroutines.launch

class NetworkLoggerListActivity : AppCompatActivity(), NetworkLoggerView {
    
    private var binding: ActivityNetworkLoggerListBinding? = null
    private var presenter: NetworkLoggerPresenter? = null
    private var networkLoggerAdapter: NetworkLoggerAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNetworkLoggerListBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setView()
        setAction()

        presenter = NetworkLoggerPresenter(this, AppDatabase.getAppDatabase())
        lifecycleScope.launch { presenter?.getListData() }
    }

    private fun setView() {
        networkLoggerAdapter = NetworkLoggerAdapter(this)
        binding?.rvVolleyLogger?.adapter = networkLoggerAdapter
    }

    private fun setAction() {
        binding?.refreshLayout?.setOnRefreshListener {
            networkLoggerAdapter?.clear()
            lifecycleScope.launch { presenter?.getListData() }
        }

        binding?.ivBack?.setOnClickListener { finish() }

        binding?.ivDelete?.setOnClickListener { showDeleteDialog() }

        binding?.ivSearch?.setOnClickListener {
            val intent =
                Intent(this, NetworkLoggerListSearchActivity::class.java)
            startActivity(intent)
        }

        networkLoggerAdapter?.setClickListener { _, model ->
            val intent = Intent(this@NetworkLoggerListActivity, NetworkLoggerDetailActivity::class.java)
            intent.putExtra(Constant.UID, model?.uid)
            startActivity(intent)
        }
    }

    private fun showDeleteDialog() {
        AlertDialog.Builder(this)
            .setTitle("Delete")
            .setMessage("Are you sure want to delete?")
            .setNegativeButton("Cancel") { dialogInterface: DialogInterface, _: Int ->
                // dismiss dialog
                dialogInterface.dismiss()
            }.setPositiveButton("Yes") { dialogInterface: DialogInterface, _: Int ->
                dialogInterface.dismiss()
                networkLoggerAdapter?.clear()
                lifecycleScope.launch { presenter?.deleteAllData() }
            }.create().show()
    }

    override fun showLoading() {
        binding?.refreshLayout?.isRefreshing = true
    }

    override fun hideLoading() {
        binding?.refreshLayout?.isRefreshing = false
    }

    override fun showData(list: List<NetworkLoggerModel?>?) {
        networkLoggerAdapter?.addList(list)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter?.unBind()
    }
}
