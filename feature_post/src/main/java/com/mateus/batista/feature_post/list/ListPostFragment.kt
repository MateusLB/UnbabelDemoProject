package com.mateus.batista.feature_post.list


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mateus.batista.base_feature.BaseFragment
import com.mateus.batista.base_feature.listeners.OnItemClickListener
import com.mateus.batista.base_feature.util.observeEvent
import com.mateus.batista.feature_post.R
import com.mateus.batista.feature_post.model.ListPost
import kotlinx.android.synthetic.main.fragment_list_post.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class ListPostFragment : BaseFragment(), OnItemClickListener<ListPost> {

    private lateinit var adapter: PostAdapter
    private val viewModel: ListPostViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list_post, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        subscribeViewModel()
    }

    override fun onResume() {
        super.onResume()
        viewModel.getPosts()
    }

    private fun subscribeViewModel() {
        viewModel.getListPostStatus().observeEvent(this,
            loading = { progressBar.visibility = View.VISIBLE },
            success = {
                progressBar.visibility = View.GONE
                setNoData(it.isNotEmpty())
                adapter = PostAdapter(it, this)
                recycleView.adapter = adapter
            },
            error = {
                progressBar.visibility = View.GONE
                handleErrors(it) { viewModel.getPosts() }
            })
    }

    private fun setNoData(hasData: Boolean) {
        if (hasData) {
            noData.visibility = View.GONE
            recycleView.visibility = View.VISIBLE
        } else {
            noData.visibility = View.VISIBLE
            recycleView.visibility = View.GONE
        }
    }

    override fun onItemClick(item: ListPost, position: Int) {
        navController.navigate(
            ListPostFragmentDirections.actionListPostFragmentToDetailPostFragment(
                item.id
            )
        )
    }
}
