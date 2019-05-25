package com.impinity.verdict
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView

/**
 * A Fragment that display the result of randomizing
 * the list of user inputted options
 */
class ResultFragment : Fragment() {

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_result, container, false)
        val resultView = root.findViewById(R.id.result_view) as TextView
        val mainMenuButton = root.findViewById(R.id.main_menu_button) as Button
        mainMenuButton.setOnClickListener {
            val homeFragment = HomeFragment()
            val manager = activity!!.supportFragmentManager
            val trans = manager.beginTransaction()
            trans.replace(R.id.fragment_container, homeFragment, "HomeFragment").commit()
        }
        resultView.text = arguments?.getString("verdict")
        return root
    }

}
