package de.westnordost.streetcomplete.quests.ferry

import de.westnordost.streetcomplete.R
import de.westnordost.streetcomplete.data.osm.osmquests.OsmFilterQuestType
import de.westnordost.streetcomplete.data.osm.edits.update_tags.StringMapChangesBuilder
import de.westnordost.streetcomplete.data.user.achievements.QuestTypeAchievement.PEDESTRIAN
import de.westnordost.streetcomplete.data.user.achievements.QuestTypeAchievement.RARE
import de.westnordost.streetcomplete.ktx.toYesNo
import de.westnordost.streetcomplete.quests.YesNoQuestAnswerFragment

class AddFerryAccessPedestrian : OsmFilterQuestType<Boolean>() {

    override val elementFilter = "ways, relations with route = ferry and !foot"
    override val commitMessage = "Specify ferry access for pedestrians"
    override val wikiLink = "Tag:route=ferry"
    override val icon = R.drawable.ic_quest_ferry_pedestrian
    override val hasMarkersAtEnds = true

    override val questTypeAchievements = listOf(RARE, PEDESTRIAN)

    override fun getTitle(tags: Map<String, String>): Int =
        if (tags.containsKey("name"))
            R.string.quest_ferry_pedestrian_name_title
        else
            R.string.quest_ferry_pedestrian_title

    override fun createForm() = YesNoQuestAnswerFragment()

    override fun applyAnswerTo(answer: Boolean, changes: StringMapChangesBuilder) {
        changes.add("foot", answer.toYesNo())
    }
}
