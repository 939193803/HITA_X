package com.stupidtree.hita.ui.event

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.stupidtree.hita.data.model.timetable.EventItem
import com.stupidtree.hita.data.repository.SubjectRepository

class EventItemViewModel(application: Application) : AndroidViewModel(application) {
    /**
     * 仓库区
     */
    private val subjectRepository = SubjectRepository.getInstance(application)

    /**
     * 数据区
     */
    val eventItemLiveData: MutableLiveData<EventItem> = MutableLiveData()

    val progressLiveData: LiveData<Pair<Int, Int>> = Transformations.switchMap(eventItemLiveData) {
        return@switchMap subjectRepository.getProgressOfSubject(it.subjectId, it.from.time)
    }

}