package com.example.interviewapiapp.view

import android.graphics.Color
import android.graphics.Typeface
import android.text.SpannableString
import android.text.Spanned
import android.text.style.AbsoluteSizeSpan
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.interviewapiapp.databinding.ListItemBinding
import com.example.interviewapiapp.model.JSONUsers

class MyAdapter(private val userItemClick: UserItemClick) : ListAdapter<JSONUsers, MyAdapter.JSONUserViewHolder>(JSONUserDiff()) {

    fun getCurrentUser(position: Int) : JSONUsers{
        return getItem(position)
    }

    class JSONUserViewHolder(val binding: ListItemBinding, private val userItemClick: UserItemClick) : RecyclerView.ViewHolder(binding.root){

        fun bindViews(data: JSONUsers){
            val name_text = "Name: ${data.name}"
            val email_text = "Email Address: ${data.email}"

            val boldSpan = StyleSpan(Typeface.BOLD)
            val normalSpan = StyleSpan(Typeface.NORMAL)

            val blueColor = ForegroundColorSpan(Color.parseColor("#0091EA"))

            val spannableName = SpannableString(name_text)
            val spannableEmail = SpannableString(email_text)

            val spannableStringList = mutableListOf<SpannableString>()
            spannableStringList.add(spannableName)
            spannableStringList.add(spannableEmail)

            for(item in spannableStringList) {
                val substring = item.split(" ")
                var firstWord = substring[0]

                if(firstWord == "Email")
                    firstWord = "Email Address:"

                applySpan(item, boldSpan, null, null, 0, firstWord.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                applySpan(item, normalSpan, null, null, firstWord.length, item.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                applySpan(item, null, AbsoluteSizeSpan(17, true), null, 0, firstWord.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                applySpan(item, null, AbsoluteSizeSpan(15, true), null, firstWord.length, item.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                applySpan(item, null, null, blueColor, 0, firstWord.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

            }

            binding.txtName.text = spannableName
            binding.txtEmailAddress.text = spannableEmail

        }

        private fun applySpan(spannableString: SpannableString, typefaceSpan: StyleSpan?, sizeSpan: AbsoluteSizeSpan?, colorSpan: ForegroundColorSpan?, start: Int, end: Int, flag: Int){
            if (typefaceSpan!= null)
                spannableString.setSpan(typefaceSpan, start, end, flag)
            else
                spannableString.setSpan(sizeSpan, start, end, flag)

            if (colorSpan != null)
                spannableString.setSpan(colorSpan, start, end, flag)
        }

        init {
            binding.root.setOnClickListener {
                userItemClick.onClick(adapterPosition)
            }
        }

    }

    class JSONUserDiff : DiffUtil.ItemCallback<JSONUsers>(){
        override fun areItemsTheSame(oldItem: JSONUsers, newItem: JSONUsers): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: JSONUsers, newItem: JSONUsers): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyAdapter.JSONUserViewHolder {
        val binding = ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyAdapter.JSONUserViewHolder(binding, userItemClick)
    }

    override fun onBindViewHolder(holder: MyAdapter.JSONUserViewHolder, position: Int) {
        holder.bindViews(getItem(position))
    }
}