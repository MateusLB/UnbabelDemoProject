package com.mateus.batista.feature_post.detail


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.mateus.batista.base_feature.BaseFragment
import com.mateus.batista.base_feature.util.observeEvent
import com.mateus.batista.feature_post.R
import com.mateus.batista.feature_post.model.DetailPost
import kotlinx.android.synthetic.main.fragment_detail_post.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailPostFragment : BaseFragment() {

    private val args: DetailPostFragmentArgs by navArgs()
    private val viewModel: DetailPostViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        subscribeViewModel()
        viewModel.getPost(args.postId)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail_post, container, false)
    }

    private fun subscribeViewModel() {
        viewModel.getPostStatus().observeEvent(this,
            loading = { progressBar.visibility = View.VISIBLE },
            success = {
                progressBar.visibility = View.GONE
                setupView(it)
            },
            error = {
                progressBar.visibility = View.GONE
                handleErrors(it) { viewModel.getPost(args.postId) }
            })
    }

    private fun setupView(post: DetailPost) {
        author.text = post.author
        description.text = post.description
        numberComments.text = post.numberComments
    }
}
