package oriolfarrus.codewarschallenge.main

import android.app.Activity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_main.*
import oriolfarrus.codewarschallenge.CodewarsApplication
import oriolfarrus.codewarschallenge.R
import oriolfarrus.codewarschallenge.core.model.Player
import javax.inject.Inject

/**
 * Created by oriolfarrus on 24/03/2018.
 */
class MainFragment : Fragment(), MainContract.MainView, PlayerClickListener {

    companion object {
        fun newInstance() = MainFragment()
    }

    @Inject lateinit var presenter: MainPresenterImpl

    @Inject lateinit var adapter: MainFragmentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        CodewarsApplication.instance.daggerAppComponent.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val inflate = inflater.inflate(R.layout.fragment_main, container, false)
        presenter.attachView(this)
        return inflate
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setSearchBehaviours()
        initRecyclerView()
    }

    override fun renderPlayer(player: Player) {
        adapter.addPlayer(player)
        resetSearchState()
        hideProgressBar()
    }

    override fun renderPlayerList(list: List<Player>) {
        adapter.addList(list)
    }

    override fun renderPlayerError() {
        hideProgressBar()
        Toast.makeText(context, R.string.user_not_found, Toast.LENGTH_LONG).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.detach()
    }

    override fun onPlayerClick(player: Player) {
        //TODO open player detail
        Toast.makeText(context, "Open detail: " + player.username, Toast.LENGTH_SHORT).show()
    }

    private fun resetSearchState() {
        nameField.text.clear()

        hideKeyboard()
    }

    private fun initRecyclerView() {
        recyclerView.adapter = adapter
        adapter.listener = this
    }

    private fun setSearchBehaviours() {
        sendName.setOnClickListener {
            searchNameAction()
        }

        nameField.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                searchNameAction()
                hideKeyboard()
                true
            } else {
                false
            }
        }
    }

    private fun searchNameAction() {
        progressBar.visibility = View.VISIBLE
        val name = nameField.text.toString()

        if (name.isNotEmpty()) {
            presenter.search(name)
        }
    }

    private fun hideKeyboard() {
        val imm = activity?.getSystemService(Activity.INPUT_METHOD_SERVICE) as? InputMethodManager
        imm?.let {
            imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0)
        }
    }

    private fun hideProgressBar() {
        progressBar.visibility = View.GONE
    }
}