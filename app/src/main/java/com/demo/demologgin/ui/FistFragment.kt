package com.demo.demologgin.ui

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import com.airbnb.mvrx.*
import com.demo.demologgin.R
import com.demo.demologgin.databinding.FragmentFistBinding
import com.demo.demologgin.maricks.ViewModel
import com.globits.mita.data.network.SessionManager



class FistFragment : BaseMvRxFragment() {
    val viewModel :ViewModel by activityViewModel()

    lateinit var binding : FragmentFistBinding
    lateinit var viewFragment: View


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFistBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewFragment = view
        binding.login.setOnClickListener {
            login()
        }

    }

    private fun login() {

        val account = binding.account.text.toString().trim()
        val password = binding.password.text.toString().trim()

        if (!TextUtils.isEmpty(account) || !TextUtils.isEmpty(password))
        {
            viewModel.hanled(HomeAction.LOGIN(account,password))
        }

    }


    override fun invalidate() {

        withState(viewModel)
        {
            when(it.asyncLogin)
            {
                is Success ->{
//                    it.asyncLogin.invoke()?.let { token->
//                        val sessionManager = context?.let { it1 -> SessionManager(it1.applicationContext) }
//                        token.accessToken?.let { it1 -> sessionManager!! .saveAuthToken(it1) }
//                        token.refreshToken?.let { it1 -> sessionManager!!.saveAuthTokenRefresh(it1) }
//                    }
                    Toast.makeText(requireContext(),"Danh Nhap Thanh Cong", Toast.LENGTH_LONG).show()

                    Navigation.findNavController(viewFragment).navigate(R.id.action_fistFragment_to_secondFragment)

                }

                is Fail ->
                {
                    Toast.makeText(requireContext(),"Dang Nhap That Bai", Toast.LENGTH_LONG).show()
                }

                else->{}
            }
        }

    }




}