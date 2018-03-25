package oriolfarrus.codewarschallenge.core

import android.view.View

/**
 * Created by oriolfarrus on 25/03/2018.
 */
fun View.gone() {
    this.visibility = View.GONE
}

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.invisible() {
    this.visibility = View.INVISIBLE
}