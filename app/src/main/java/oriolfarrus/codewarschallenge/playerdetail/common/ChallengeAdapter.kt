package oriolfarrus.codewarschallenge.playerdetail.common

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_challenge.view.*
import oriolfarrus.codewarschallenge.R
import oriolfarrus.codewarschallenge.core.model.ChallengeBase
import javax.inject.Inject

/**
 * Created by oriolfarrus on 25/03/2018.
 */
class ChallengeAdapter @Inject constructor() : RecyclerView.Adapter<ChallengeAdapter.ViewHolder>() {

    companion object {
        const val TYPE_LOADING = 0
        const val TYPE_ITEM = 1
    }

    val data: MutableList<ChallengeBase> = mutableListOf()

    var listener: ChallengeClickListener? = null

    var isLoading = false
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        when (viewType) {
            TYPE_LOADING -> ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_challenge_loading, parent, false))
            else -> ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_challenge, parent, false))
        }

    override fun getItemCount() = data.size + (if (isLoading) 1 else 0)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (getItemViewType(position) == TYPE_ITEM) {
            val challenge = data.getOrNull(position)
            challenge?.let {
                holder.itemView.challengeTitle?.text = it.name

                holder.itemView.setOnClickListener {
                    listener?.onChallengeClicked(challenge)
                }
            }
        }
    }

    fun addData(data: List<ChallengeBase>): Int {
        this.data.addAll(data)
        isLoading = false
        return this.data.size
    }

    override fun getItemViewType(position: Int) =
        if (data.size == 0 || (isLoading && position == data.size)) {
            TYPE_LOADING
        } else {
            TYPE_ITEM
        }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}