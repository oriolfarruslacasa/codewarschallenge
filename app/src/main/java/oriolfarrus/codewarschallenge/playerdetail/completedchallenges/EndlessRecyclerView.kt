package oriolfarrus.codewarschallenge.playerdetail.completedchallenges

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.util.AttributeSet

class EndlessRecyclerView : RecyclerView {

    companion object {
        const val DEFAULT_PERCENT_THRESHOLD_SCROLL = 85
    }

    var visibleThreshold = DEFAULT_PERCENT_THRESHOLD_SCROLL
    var endlessRecyclerViewListener: EndlessRecyclerViewListener? = null

    private var isLoading: Boolean = false
    private var scrollListener: RecyclerView.OnScrollListener? = null
    private var isScrollingDown = false
    private var currentScrollX: Int = 0
    private var currentScrollY: Int = 0

    private val firstVisibleItem: Int
        get() = if (layoutManager.javaClass == LinearLayoutManager::class.java ||
                layoutManager.javaClass.superclass == LinearLayoutManager::class.java) {
            (layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
        } else {
            (layoutManager as StaggeredGridLayoutManager).findFirstVisibleItemPositions(null)[0]
        }

    constructor(context: Context) : super(context)


    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)


    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle)

    init {
        scrollListener = object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                //Only check for load more if the scroll direction is up
                if (!isScrollingDown && !isLoading &&
                        layoutManager.itemCount - childCount <= firstVisibleItem + visibleThreshold) {
                    // End has been reached
                    endlessRecyclerViewListener?.let {
                        it.onEndReached()
                        isLoading = true
                    }
                }
            }

            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                currentScrollX += dx
                currentScrollY += dy
                isScrollingDown = dy <= 0
            }
        }
        addOnScrollListener(scrollListener)
    }

    interface EndlessRecyclerViewListener {
        fun onEndReached()
    }

    /**
     * Informs the view that loading has finished (call this after you've fetched all the data)
     */
    fun finishedLoading() {
        isLoading = false
    }

}
