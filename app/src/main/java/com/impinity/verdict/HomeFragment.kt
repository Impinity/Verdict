package com.impinity.verdict


import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AlertDialog
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast

class HomeFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                                savedInstanceState: Bundle?): View? {
        // Inflated layout to be returned
        val rootView = inflater.inflate(R.layout.fragment_home, container, false)
        val newTrialButton = rootView.findViewById<Button>(R.id.new_trial_button)
        newTrialButton?.setOnClickListener{
            buildTitleDialog()
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
                sendToTrialFragment(titleInput.text.toString())
            }
        }
        builder?.show()
    }
    private fun sendToTrialFragment(trialTitle: String) {
        val trialFragment = TrialFragment()
        val bundle = Bundle()
        bundle.putString("title", trialTitle)
        trialFragment.arguments = bundle
        val manager = activity!!.supportFragmentManager
        val trans = manager.beginTransaction()
        trans.replace(R.id.fragment_container, trialFragment, "TrialFragment").commit()
    }

}
