package oriolfarrus.codewarschallenge.main

import android.app.Activity
import android.arch.lifecycle.Lifecycle
import android.content.Context
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.test.espresso.idling.CountingIdlingResource
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_main.*
import oriolfarrus.codewarschallenge.CodewarsApplication
import oriolfarrus.codewarschallenge.R
import oriolfarrus.codewarschallenge.core.gone
import oriolfarrus.codewarschallenge.core.model.Player
import oriolfarrus.codewarschallenge.core.testing.IdlingResourceProvider
import oriolfarrus.codewarschallenge.core.visible
import oriolfarrus.codewarschallenge.playerdetail.PlayerDetailActivity
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
        getIdlingResource()?.increment()
        return inflate
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setSearchBehaviours()
        initRecyclerView()
        initSortingSpinner()
    }

    override fun renderPlayer(player: Player) {
        adapter.addPlayer(player)
        resetSearchState()
        hideProgressBar()
        getIdlingResource()?.decrement()
    }

    override fun renderPlayerList(list: List<Player>) {
        adapter.addList(list)
        getIdlingResource()?.decrement()
    }

    override fun renderPlayerError() {
        hideProgressBar()
        Snackbar.make(recyclerView, R.string.user_not_found, Snackbar.LENGTH_LONG).show()
        getIdlingResource()?.decrement()
    }

    override fun renderPlayerListError() {
        getIdlingResource()?.decrement()
    }

    override fun onPlayerClick(player: Player) {
        context?.startActivity(PlayerDetailActivity.getCallingIntent(context as Context, player))
    }

    override fun getViewLifeCycle() = lifecycle

    private fun resetSearchState() {
        nameField.text.clear()

        hideKeyboard()
    }

    private fun initSortingSpinner() {
        val spinnerAdapter = ArrayAdapter.createFromResource(activity, R.array.sorting_array, android.R.layout.simple_spinner_item)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        sortingSpinner.adapter = spinnerAdapter

        sortingSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                when (position) {
                    0 -> adapter.setSort(MainFragmentAdapter.SORT_DATE)
                    1 -> adapter.setSort(MainFragmentAdapter.SORT_RANK)
                }
            }
        }
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
        progressBar.visible()
        val name = nameField.text.toString()

        if (name.isNotEmpty()) {
            presenter.search(name)
            getIdlingResource()?.increment()
        }
    }

    private fun hideKeyboard() {
        val imm = activity?.getSystemService(Activity.INPUT_METHOD_SERVICE) as? InputMethodManager
        imm?.let {
            imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0)
        }
    }

    private fun hideProgressBar() {
        progressBar.gone()
    }

    private fun getIdlingResource(): CountingIdlingResource?{
        return (activity as? IdlingResourceProvider)?.getIdlingResource()
    }
}