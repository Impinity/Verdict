package com.impinity.verdict


import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AlertDialog
import android.text.InputType
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.impinity.verdict.com.impinity.verdict.OptionViewModel

class HomeFragment : Fragment() {
    var trialAdapter: ArrayAdapter<String>? = null
    var trialList: List<String>? = null
    private lateinit var optionViewModel: OptionViewModel
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                                savedInstanceState: Bundle?): View? {
        // Inflated layout to be returned
        val rootView = inflater.inflate(R.layout.fragment_home, container, false)
        initViewModel()
        initTrialAdapter()

        val newTrialButton = rootView.findViewById<Button>(R.id.new_trial_button)
        newTrialButton?.setOnClickListener{
            buildTitleDialog()
        }

        val chooseTrialButton = rootView.findViewById<Button>(R.id.choose_trial_button)
        chooseTrialButton?.setOnClickListener{
            buildChoiceDialog()
        }

        return rootView
    }

    /**
     * Creates a dialog that prompts the user for a title for their trial
     * and then sends sends that result and switches to the TrialFragment
     */
    private fun buildTitleDialog() {
        val builder: AlertDialog.Builder? = activity?.let {
            AlertDialog.Builder(it)
        }
        val titleInput = EditText(context)
        titleInput.inputType = InputType.TYPE_TEXT_FLAG_CAP_WORDS
        val lp = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT
        )
        titleInput.layoutParams = lp
        builder?.setTitle("Enter Title ")
        builder?.setView(titleInput)
        builder?.setPositiveButton("Continue") { _, _ ->
            if (titleInput.text.toString().isEmpty()) {
                Toast.makeText(context, "No title selected", Toast.LENGTH_SHORT).show()
            }
            else {
                sendToTrialFragment(titleInput.text.toString(), true)
            }
        }
        builder?.show()
    }

    /**
     * Creates a dialog of trials for a user to choose from
     */
    private fun buildChoiceDialog() {
        val builder: AlertDialog.Builder? = activity?.let {
            AlertDialog.Builder(it)
        }
        builder?.setTitle("Choose Trial")
        builder?.setAdapter(trialAdapter,
            DialogInterface.OnClickListener { dialog, which ->
                sendToTrialFragment(trialAdapter?.getItem(which), true)
                Log.v("Choices", "Choice ${trialAdapter?.getItem(which)}")
            })
        builder?.show()
    }

    private fun sendToTrialFragment(trialTitle: String?, exists: Boolean) {
        val trialFragment = TrialFragment()
        val bundle = Bundle()
        bundle.putBoolean("exists", exists)
        bundle.putString("title", trialTitle)
        trialFragment.arguments = bundle
        val manager = activity!!.supportFragmentManager
        val trans = manager.beginTransaction()
        trans.replace(R.id.fragment_container, trialFragment, "TrialFragment").commit()
    }

    private fun initViewModel() {
        optionViewModel = ViewModelProviders.of(this).get(OptionViewModel::class.java)
        optionViewModel.allTrials.observe(this, Observer { trialList ->
            trialList?.let{trialAdapter!!.clear(); trialAdapter!!.addAll(it)
            trialAdapter?.notifyDataSetChanged()}
        })
    }

    private fun initTrialAdapter() {
        trialAdapter = ArrayAdapter(context, android.R.layout.simple_list_item_1)
    }
}
