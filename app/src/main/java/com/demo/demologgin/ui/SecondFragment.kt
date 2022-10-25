package com.demo.demologgin.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.mvrx.*
import com.demo.demologgin.R
import com.demo.demologgin.adapter.AdapterSucess
import com.demo.demologgin.databinding.FragmentFistBinding
import com.demo.demologgin.databinding.FragmentSecondBinding
import com.demo.demologgin.maricks.ViewModel
import com.demo.demologgin.model.User


class SecondFragment : BaseMvRxFragment() {

    lateinit var binding :FragmentSecondBinding

    val viewModel :ViewModel by activityViewModel()
    lateinit var adapter : AdapterSucess


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSecondBinding.inflate(layoutInflater)
        return  binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        viewModel.hanled(HomeAction.LOGINSUCESS("",""))


        val linerlayout = LinearLayoutManager(activity as MainActivity)
        adapter = AdapterSucess()

        binding.recyc.layoutManager=linerlayout
        binding.recyc.adapter=adapter



    }

    override fun invalidate() {
       withState(viewModel)
       {
           when(it.asyncSucess)
           {
               is Success ->{
                   it.asyncSucess.invoke()?.let { responsive->
                       adapter.setListUser(responsive.data)
//
                   }
               }

               is Fail ->{
                   Toast.makeText(requireContext(),"Error", Toast.LENGTH_LONG).show()
               }

               else->{}
           }
       }
    }


}