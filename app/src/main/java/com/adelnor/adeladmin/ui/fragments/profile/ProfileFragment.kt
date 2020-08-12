package com.adelnor.adeladmin.ui.fragments.profile

import android.content.Intent
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.adelnor.adeladmin.R
import com.adelnor.adeladmin.ui.activities.LoginActivity

class ProfileFragment : Fragment() {

    private lateinit var viewModel: ProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ProfileViewModel()
        val root = inflater.inflate(R.layout.fragment_profile, container, false)
        val btLogout = root.findViewById<Button>(R.id.button_logout)
        val tvUser = root.findViewById<TextView>(R.id.text_user)
        val tvFullName = root.findViewById<TextView>(R.id.text_full_name)
        val tvCompany = root.findViewById<TextView>(R.id.text_company)
        val tvRFC = root.findViewById<TextView>(R.id.text_rfc)
        var sessionData = viewModel.getSessionData()

        tvUser.text = sessionData?.user
        tvFullName.text = sessionData?.fullName
        tvCompany.text = "Empresa: ${sessionData?.company?.companyName}"
        tvRFC.text = "RFC: ${sessionData?.company?.rfc}"

        btLogout.setOnClickListener { confirmLogout() }



        return root
    }

    private fun confirmLogout(){
        val builder = AlertDialog.Builder(requireContext())
            .setTitle("Atencion!")
            .setMessage(getString(R.string.hint_about_logout))
        builder.setNegativeButton(R.string.btn_accept) { dialog, which ->
            viewModel.deleteSession()
            activity?.finish()
            startActivity(Intent(activity, LoginActivity::class.java))
        }

        builder.setNeutralButton(R.string.btn_cancel) { dialog, which -> dialog.dismiss() }
        builder.show()
    }

}