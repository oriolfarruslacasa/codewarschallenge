package oriolfarrus.codewarschallenge.main

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_player.view.*
import oriolfarrus.codewarschallenge.R
import oriolfarrus.codewarschallenge.core.model.Player
import javax.inject.Inject

/**
 * Created by oriolfarrus on 24/03/2018.
 */
class MainFragmentAdapter @Inject constructor() : RecyclerView.Adapter<MainFragmentAdapter.ViewHolder>() {

    var listener: PlayerClickListener? = null

    var list: MutableList<Player> = mutableListOf()

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

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val playerToShow = list.getOrNull(position)

        playerToShow?.let { player ->
            holder.itemView.playerName.text = player.username

            holder.itemView?.setOnClickListener {
                listener?.onPlayerClick(player)
            }
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}