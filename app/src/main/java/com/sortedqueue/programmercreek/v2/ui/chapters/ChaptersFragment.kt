package com.sortedqueue.programmercreek.v2.ui.chapters

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.sortedqueue.programmercreek.R
import com.sortedqueue.programmercreek.util.AnimationUtils
import com.sortedqueue.programmercreek.v2.base.*
import com.sortedqueue.programmercreek.v2.data.model.Chapter
import com.sortedqueue.programmercreek.v2.data.model.Streak
import com.sortedqueue.programmercreek.v2.ui.HomeActivity
import com.sortedqueue.programmercreek.v2.ui.module.ModuleActivity
import kotlinx.android.synthetic.main.fragment_chapters_layout.*
import kotlinx.android.synthetic.main.view_practice_now.*
import java.util.*

class ChaptersFragment : BaseFragment(), BaseAdapterClickListener<Chapter> {

    private val colors : Array<Int> = arrayOf(
            R.color.md_amber_800,
            R.color.md_cyan_500,
            R.color.md_purple_500,
            R.color.md_brown_700,
            R.color.md_blue_grey_700,
            R.color.md_light_green_900,
            R.color.md_cyan_900,
            R.color.md_deep_orange_900
    )

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle? ): View?
            = inflater.inflate(R.layout.fragment_chapters_layout, container, false)

    private val chaptersMap = LinkedHashMap<String, ArrayList<Chapter>>()
    @SuppressLint("InflateParams")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        chaptersLayout.removeAllViews()

        val chapterList = Chapter.getAllChapters()
        val chapterTitleList = ArrayList<String>()

        for( chapter in chapterList ) {
            if( !chaptersMap.containsKey(chapter.chapterTitle) ){
                chapterTitleList.add(chapter.chapterTitle)
                chaptersMap[chapter.chapterTitle] = ArrayList()
            }
            chaptersMap[chapter.chapterTitle]?.add(chapter)
        }

        val layoutInflater = LayoutInflater.from(context)
        var position = 0
        for( (key, chaptersList) in chaptersMap ) {
            val chapterView = layoutInflater.inflate(R.layout.item_chapter, null)
            val ivChapterLocked = chapterView.findViewById<View>(R.id.ivChapterLocked)
            val cvChapter = chapterView.findViewById<View>(R.id.cvChapter)
            val tvHeader = chapterView.findViewById<TextView>(R.id.tvHeader)
            tvHeader.text = key
            val rvModules = chapterView.findViewById<RecyclerView>(R.id.rvModules)
            //rvModules.layoutManager = LinearLayoutManager(context)//StaggeredGridLayoutManager( 2, StaggeredGridLayoutManager.VERTICAL )
            rvModules.adapter = SubModulesAdapter( chaptersList, -1, this )
            chaptersLayout.addView(chapterView)

            if( position == 0 ) {
                rvModules.show()
                tvHeader.setCompoundDrawablesWithIntrinsicBounds(
                        null,
                        null,
                        ContextCompat.getDrawable(tvHeader.context,
                                    R.drawable.ic_down_arrow),
                        null)
                ivChapterLocked.hide()
            }
            else {
                rvModules.hide()
                ivChapterLocked.show()
            }

            cvChapter.setOnClickListener {
                if( !ivChapterLocked.isVisible() ) {
                    rvModules.toggleVisibility()
                    tvHeader.setCompoundDrawablesWithIntrinsicBounds(
                            null,
                            null,
                            ContextCompat.getDrawable(tvHeader.context,
                                    if( rvModules.isVisible() )
                                        R.drawable.ic_down_arrow
                                    else
                                        R.drawable.ic_right_arrow ),
                            null)
                }
            }
            ivChapterLocked.setOnClickListener {
                AnimationUtils.exitRevealGone(ivChapterLocked)
            }
            position++
        }

        tvRevise?.setOnClickListener {
            (activity as HomeActivity).practiceNow()
        }

        rvStreak?.apply {
            adapter = StreakAdapter().apply {
                add(Streak(""))
            }
        }

    }

    override fun onItemClick(position: Int, item: Chapter) {
        context?.let {
            ModuleActivity.loadChapter( it, item, position, chaptersMap[item.chapterTitle]  )
        }
    }
}