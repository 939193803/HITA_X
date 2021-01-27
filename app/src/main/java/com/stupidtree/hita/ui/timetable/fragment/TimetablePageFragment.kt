package com.stupidtree.hita.ui.timetable.fragment

import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.TextView
import com.stupidtree.hita.R
import com.stupidtree.hita.data.model.timetable.EventItem
import com.stupidtree.hita.data.model.timetable.TimeInDay
import com.stupidtree.hita.databinding.FragmentTimetablePageBinding
import com.stupidtree.hita.ui.base.BaseFragment
import com.stupidtree.hita.ui.timetable.views.TimeTableBlockView.TimeTablePreferenceRoot
import com.stupidtree.hita.ui.timetable.views.TimeTableViewGroup
import com.stupidtree.hita.utils.TimeUtils
import java.util.*

class TimetablePageFragment : BaseFragment<TimetablePageViewModel, FragmentTimetablePageBinding>() {
    private val topDateTexts = arrayOfNulls<TextView>(8) //顶部日期文本



    override fun onStart() {
        super.onStart()
        arguments?.getLong("date")?.let {
            viewModel.setStartDate(it)
        }
    }


    fun setWeek(date: Long) {
        arguments?.getLong("date")?.let {
            val old = it
            if (date < old || date > old + 1000 * 60 * 60 * 24 * 7) {
                val b = Bundle()
                b.putLong("date", date)
                arguments = b
                if(isAdded){
                    viewModel.setStartDate(date)
                }
            }
        }
    }


    override fun initViews(view: View) {
        initDateTextViews()
        binding?.timetableView?.init(object : TimeTablePreferenceRoot {
            override val isColorEnabled: Boolean = false
            override val cardTitleColor: String = "white"
            override val subTitleColor: String = "white"
            override val iconColor: String = "white"

            override fun willBoldText(): Boolean {
                return true
            }

            override fun drawBGLine(): Boolean {
                return true
            }

            override fun cardIconEnabled(): Boolean {
                return true
            }

            override val cardOpacity: Int
                get() = 75
            override val cardHeight: Int
                get() = 180
            override val startTime: TimeInDay
                get() = TimeInDay(8, 0)
            override val todayBGColor: Int
                get() = Color.parseColor("#22000000")
            override val titleGravity: Int
                get() = Gravity.CENTER
            override val colorPrimary: Int
                get() = getColorPrimary()
            override val colorAccent: Int
                get() = getColorPrimary()
            override val titleAlpha: Int
                get() = 100
            override val subtitleAlpha: Int
                get() = 60

            override fun animEnabled(): Boolean {
                return false
            }

            override val cardBackground: String
                get() = "primary"
            override val tTPreference: SharedPreferences
                get() = tTPreference

            override fun drawNowLine(): Boolean {
                return true
            }

        })
        binding?.timetableView?.setOnCardClickListener(object : TimeTableViewGroup.OnCardClickListener {
            override fun onEventClick(v: View, eventItem: EventItem) {
                //EventsUtils.showEventItem(requireContext(), eventItem)
            }

            override fun onDuplicateEventClick(v: View, eventItems: List<EventItem>) {
                // EventsUtils.showEventItem(requireContext(), eventItems)
            }
        })
        binding?.timetableView?.setOnCardLongClickListener(object : TimeTableViewGroup.OnCardLongClickListener {
            override fun onEventLongClick(v: View, ei: EventItem): Boolean {
//                val pm = PopupMenu(requireContext(), v)
//                pm.inflate(R.menu.menu_opr_timetable)
//                pm.setOnMenuItemClickListener { item ->
//                    if (item.itemId == R.id.opr_delete) {
//                        val ad = AlertDialog.Builder(requireContext()).setNegativeButton(R.string.button_cancel, null)
//                                .setPositiveButton(R.string.button_confirm) { dialog, which ->
//                                    val ef: ExplosionField = ExplosionField.attach2Window(requireActivity())
//                                    ef.explode(v)
//                                    deleteEventsTask(this@FragmentTimeTablePage, ei).execute()
//                                }.create()
//                        ad.setTitle(R.string.dialog_title_sure_delete)
//                        if (ei.eventType === COURSE) {
//                            ad.setMessage("删除课程后,可以通过导入课表或同步云端数据恢复初始课表")
//                        }
//                        ad.show()
//                    }
//                    true
//                }
//                pm.show()
                return true
            }

            override fun onDuplicateEventClick(v: View, eventItems: List<EventItem>): Boolean {
//                val pm = PopupMenu(requireContext(), v)
//                pm.inflate(R.menu.menu_opr_timetable)
//                pm.setOnMenuItemClickListener { item ->
//                    if (item.itemId == R.id.opr_delete) {
//                        val ad = AlertDialog.Builder(requireContext()).setNegativeButton(R.string.button_cancel, null)
//                                .setPositiveButton(R.string.button_confirm) { dialog, which ->
//                                    val ef: ExplosionField = ExplosionField.attach2Window(requireActivity())
//                                    ef.explode(v)
//                                    deleteEventsTask(this@FragmentTimeTablePage, eventItems).execute()
//                                }.create()
//                        ad.setTitle(R.string.dialog_title_sure_delete)
//                        if (eventItems.size > 0 && eventItems[0].getEventType() === COURSE) {
//                            ad.setMessage("删除课程后,可以通过导入课表或同步云端数据恢复初始课表")
//                        }
//                        ad.show()
//                    }
//                    true
//                }
//                pm.show()
                return true
            }
        })
        viewModel.eventsOfThisWeek.observe(this) {
            arguments?.getLong("date")?.let { date ->
                binding?.timetableView?.notifyRefresh(date, it)
            }
        }
        viewModel.startDateLiveDate.observe(this){date->
            val startDate = Calendar.getInstance()
            startDate.timeInMillis = date
            Log.e("start_date",TimeUtils.printDate(date))
            binding?.timetableView?.setStartDate(date)
            /*显示上方日期*/
            topDateTexts[0]?.text = requireContext().resources.getStringArray(R.array.months)[startDate[Calendar.MONTH]]
            val temp = Calendar.getInstance()
            for (k in 1..7) {
                temp.time = startDate.time
                temp.add(Calendar.DATE, k - 1)
                topDateTexts[k]!!.text = temp[Calendar.DAY_OF_MONTH].toString()
            }
        }
    }

    private fun initDateTextViews() {
        topDateTexts[0] = binding?.ttTvMonth
        topDateTexts[1] = binding?.ttTvDay1
        topDateTexts[2] = binding?.ttTvDay2
        topDateTexts[3] = binding?.ttTvDay3
        topDateTexts[4] = binding?.ttTvDay4
        topDateTexts[5] = binding?.ttTvDay5
        topDateTexts[6] = binding?.ttTvDay6
        topDateTexts[7] = binding?.ttTvDay7
    }

    override fun getViewModelClass(): Class<TimetablePageViewModel> {
        return TimetablePageViewModel::class.java
    }

    override fun initViewBinding(): FragmentTimetablePageBinding {
        return FragmentTimetablePageBinding.inflate(layoutInflater)
    }

    companion object {
        fun newInstance(init_date:Long): TimetablePageFragment {
            val b = Bundle()
            b.putLong("date", init_date)
            val f = TimetablePageFragment()
            f.arguments = b
            return f
        }
    }
}