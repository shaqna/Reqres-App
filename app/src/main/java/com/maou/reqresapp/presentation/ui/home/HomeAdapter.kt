package com.maou.reqresapp.presentation.ui.home

import android.app.Activity
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.paging.PagingData
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.maou.reqresapp.databinding.UsersItemBinding
import com.maou.reqresapp.domain.model.ReqresUser
import com.maou.reqresapp.utils.bindImage
import com.maou.reqresapp.utils.generateFullName

class HomeAdapter : PagingDataAdapter<ReqresUser,HomeAdapter.ViewHolder>(DiffCallBack) {


    var onItemClickListener: ((ReqresUser, ActivityOptionsCompat) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding =
            UsersItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let { user ->
            holder.bind(user)
        }
    }


    inner class ViewHolder(private val binding: UsersItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(user: ReqresUser) {
            with(binding) {
                tvUserName.text = generateFullName(user.firstName, user.lastName)
                bindImage(itemView.context, user.avatar, ivAvatar)

                itemView.setOnClickListener {

                    val optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(
                        itemView.context as Activity,
                        Pair(ivAvatar, "avatar"),
                        Pair(tvUserName, "username")

                    )
                    onItemClickListener?.invoke(user, optionsCompat)
                }
            }


        }
    }

    companion object {
        val DiffCallBack = object: DiffUtil.ItemCallback<ReqresUser>() {
            override fun areItemsTheSame(oldItem: ReqresUser, newItem: ReqresUser): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: ReqresUser, newItem: ReqresUser): Boolean =
                oldItem.id == newItem.id

        }
    }

}