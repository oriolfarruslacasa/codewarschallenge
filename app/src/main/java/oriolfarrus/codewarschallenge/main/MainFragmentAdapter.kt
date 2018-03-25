package oriolfarrus.codewarschallenge.main

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_player.view.*
import oriolfarrus.codewarschallenge.R
import oriolfarrus.codewarschallenge.core.gone
import oriolfarrus.codewarschallenge.core.model.Player
import oriolfarrus.codewarschallenge.core.visible
import javax.inject.Inject

/**
 * Created by oriolfarrus on 24/03/2018.
 */
class MainFragmentAdapter @Inject constructor() : RecyclerView.Adapter<MainFragmentAdapter.ViewHolder>() {

    companion object {
        const val SORT_DATE = 0
        const val SORT_RANK = 1
    }

    var listener: PlayerClickListener? = null

    private var list: MutableList<Player> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_player, parent, false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun addPlayer(player: Player) {
        list.remove(player)
        list.add(0, player)
        notifyDataSetChanged()
    }

    fun addList(list: List<Player>) {
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    fun setSort(sort: Int) {
        when (sort) {
            SORT_DATE -> list.sortByDescending { it.insertTime }
            SORT_RANK -> list.sortBy { it.leaderboardPosition }
        }

        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val playerToShow = list.getOrNull(position)

        playerToShow?.let { player ->
            if (player.name.isNotEmpty()) {
                holder.itemView.playerName.visible()
                holder.itemView.playerName.text = player.name
            } else {
                holder.itemView.playerName.gone()
            }
            holder.itemView.playerUserName.text = player.username
            holder.itemView.playerRank.text = holder.itemView.context.getString(R.string.main_fragment_rank_text, player.leaderboardPosition)

            holder.itemView?.setOnClickListener {
                listener?.onPlayerClick(player)
            }
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}