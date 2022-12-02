package com.example.android.trackmysleepquality.sleeptracker

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.android.trackmysleepquality.R
import com.example.android.trackmysleepquality.convertDurationToFormatted
import com.example.android.trackmysleepquality.convertNumericQualityToString
import com.example.android.trackmysleepquality.database.SleepNight
import com.example.android.trackmysleepquality.databinding.ListItemSleepNightBinding

// 最初是 RecyclerView.Adapter<SleepNightAdapter.ViewHolder>
class SleepNightAdapter : ListAdapter<SleepNight,SleepNightAdapter.ViewHolder>(SleepNightDiffCallback()) {

/*  //由 ListAdapter 实现
       var data = listOf<SleepNight>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }
*/

/*
   override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TextItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.text_item_view, parent,false) as TextView
        return TextItemViewHolder(view)
    }

  override fun onBindViewHolder(holder: TextItemViewHolder, position: Int) {
        val item = data[position]
        holder.textView.text = item.sleepQuality.toString()// 仅显示 睡眠质量数值
    }
*/

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position) //由 ListAdapter.getItem 实现  data[position]
        holder.bind(item)
    }

/*
    //由 ListAdapter 实现
    override fun getItemCount(): Int {
        return data.size
    }
*/

    class ViewHolder private constructor(val binding: ListItemSleepNightBinding) : RecyclerView.ViewHolder(binding.root) {
        //inline property 取代变量声明
        companion object{
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                // 使用databinding
                //val view = layoutInflater.inflate(R.layout.list_item_sleep_night, parent, false)
                val binding =
                    ListItemSleepNightBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding) // 使用 binding 取代 view
            }
        }

        fun bind(
            item: SleepNight
        ) {
            binding.sleep = item
            // 此调用是一种优化，用于要求数据绑定功能立即执行任何待处理的绑定
            // 当您在 RecyclerView 中使用绑定适配器时，最好调用 executePendingBindings()，因为它可以略微加快调整视图大小的过程
            binding.executePendingBindings()
/*     // 可以使用 Databinding 和 BindingAdapter 执行
            val res = itemView.context.resources
            binding.sleepLength //取代  viewHolder.findViewById(R.id.sleep_length)
                .text = convertDurationToFormatted(
                item.startTimeMilli, item.endTimeMilli, res
            )

            binding.qualityString.text = convertNumericQualityToString(item.sleepQuality, res)
            binding.qualityImage.setImageResource(
                when (item.sleepQuality) {
                    0 -> R.drawable.ic_sleep_0
                    1 -> R.drawable.ic_sleep_1
                    2 -> R.drawable.ic_sleep_2
                    3 -> R.drawable.ic_sleep_3
                    4 -> R.drawable.ic_sleep_4
                    5 -> R.drawable.ic_sleep_5
                    else -> R.drawable.ic_sleep_active
                }
            )
 */
        }
    }
}

class SleepNightDiffCallback : DiffUtil.ItemCallback<SleepNight>(){
    override fun areItemsTheSame(oldItem: SleepNight, newItem: SleepNight): Boolean {
        return oldItem.nightId == newItem.nightId // nightId 相同 则 认为 是相同的item
    }

    override fun areContentsTheSame(oldItem: SleepNight, newItem: SleepNight): Boolean {
        return oldItem == newItem // 是否包含相同的数据：SleepNight 是一个数据类，此相等性检查将检查所有字段(Data 类自动定义的)
    }
}

