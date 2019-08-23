package com.impinity.verdict
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.appcompat.widget.Toolbar
import android.text.InputType
import android.view.*
import android.widget.EditText
import android.widget.Toast
import android.widget.LinearLayout
import androidx.lifecycle.ViewModelProviders
import com.impinity.verdict.com.impinity.verdict.OptionViewModel
import com.impinity.verdict.com.impinity.verdict.db.Option

class TrialFragment : Fragment() {
    private lateinit var optionsRecyclerView: RecyclerView
    private lateinit var optionsAdapter: RecyclerView.Adapter<*>
    private lateinit var optionsManager: RecyclerView.LayoutManager
    private lateinit var optionsList: MutableList<String>
    private lateinit var addOptionFAB: View
    private lateinit var optionViewModel: OptionViewModel
    private var trialTitle: String? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_trial, container, false)
        trialTitle = arguments?.getString("title")
        optionsList = mutableListOf()
        if(arguments?.getBoolean("exists") == true) {
            initOptionsList(arguments?.getString("title"))
        }
        setTrialToolbar(rootView)
        optionsManager = LinearLayoutManager(rootView.context)
        optionsAdapter = AddOptionsAdapter(optionsList)
        optionsRecyclerView = rootView.findViewById<RecyclerView>(R.id.add_options_list).apply {
            layoutManager = optionsManager
            adapter = optionsAdapter
        }
        addOptionFAB = rootView.findViewById(R.id.add_fab)
        addOptionFAB.setOnClickListener{
            buildAddOptionDialog()
        }
        return rootView
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        when (item.itemId) {
            R.id.action_done -> {
                if (optionsList.size == 0) {
                    Toast.makeText(context, "No Options Added", Toast.LENGTH_SHORT).show()
                } else {
                    sendToResultFragment()
                }
                return true
            }
            R.id.action_save -> {
                if (optionsList.size == 0) {
                    Toast.makeText(context, "No Options Added", Toast.LENGTH_SHORT).show()
                } else {
                    saveOptionsList()
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }
    
    private fun setTrialToolbar(sourceView: View) {
        val toolbar = sourceView.findViewById(R.id.my_toolbar) as Toolbar
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        (activity as AppCompatActivity).supportActionBar?.title = trialTitle
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as AppCompatActivity).supportActionBar?.setDisplayShowHomeEnabled(true)
        setHasOptionsMenu(true)
    }

    private fun sendToResultFragment() {
        val resultFragment = ResultFragment()
        val bundle = Bundle()
        bundle.putString("verdict", getRandomOption())
        resultFragment.arguments = bundle
        val manager = activity!!.supportFragmentManager
        val trans = manager.beginTransaction()
        trans.replace(R.id.fragment_container, resultFragment, "ResultFragment")
        trans.addToBackStack(null)
        trans.commit()
    }

    private fun saveOptionsList() {
        val viewModel = OptionViewModel(activity!!.application)
        viewModel.deleteAllFromTrial(trialTitle)
        for (item in optionsList) {
            val tempOption = Option(item, trialTitle)
            viewModel.insert(tempOption)
        }
    }

    private fun initOptionsList(title: String?) {
        optionViewModel = ViewModelProviders.of(this).get(OptionViewModel::class.java)
        optionsList.addAll(optionViewModel.getTrialOptions(title))
    }

    public fun getRandomOption() : String {
        val randomIndex = (0..optionsList.size - 1).random()
        return optionsList[randomIndex]
    }
    /**
     * Creates a dialog that allows users to add items to the options list
     */
    private fun buildAddOptionDialog() {
        val builder: AlertDialog.Builder? = activity?.let {
            AlertDialog.Builder(it)
        }
        val optionInput = EditText(context)
        optionInput.inputType = InputType.TYPE_TEXT_FLAG_CAP_WORDS
        val lp = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT
        )
        optionInput.layoutParams = lp

        builder?.setTitle("Enter new option")
        builder?.setView(optionInput)
        builder?.setPositiveButton("Add") { _, _ ->
            if (optionInput.text.toString().isEmpty()) {
                Toast.makeText(context, "No Item Added", Toast.LENGTH_SHORT).show()
            }
            else {
                optionsList.add(optionInput.text.toString())
                optionsAdapter.notifyDataSetChanged()
                optionsRecyclerView.scrollToPosition(optionsAdapter.itemCount - 1)
            }
        }
        builder?.show()
    }
}
