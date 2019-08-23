package com.impinity.verdict
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import kotlinx.android.synthetic.main.fragment_result.*

/**
 * A Fragment that display the result of randomizing
 * the list of user inputted options
 */
class ResultFragment : Fragment() {
    private lateinit var resultView: TextView
    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_result, container, false)
        resultView = root.findViewById(R.id.result_view) as TextView
        val mainMenuButton = root.findViewById(R.id.main_menu_button) as Button
        val editListButton = root.findViewById(R.id.edit_list_button) as Button
        val retrialButton = root.findViewById(R.id.retrial_button) as Button
        mainMenuButton.setOnClickListener {
            sendToHomeFragment()
        }
        editListButton.setOnClickListener{
            sendToTrialFragment()
        }
        retrialButton.setOnClickListener{
            retrial()
        }
        resultView.text = arguments?.getString("verdict")
        return root
    }

    private fun sendToHomeFragment() {
        val homeFragment = HomeFragment()
        val manager = activity!!.supportFragmentManager
        manager.popBackStackImmediate()
        val trans = manager.beginTransaction()
        trans.replace(R.id.fragment_container, homeFragment, "HomeFragment").commit()
    }

    private fun sendToTrialFragment() {
        fragmentManager!!.popBackStackImmediate()
    }
    private fun retrial() {
        val manager = activity!!.supportFragmentManager
        val tFragment: TrialFragment = manager.findFragmentByTag("TrialFragment") as TrialFragment
        resultView.text = tFragment.getRandomOption()
    }

}
