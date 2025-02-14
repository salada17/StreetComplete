package de.westnordost.streetcomplete.quests.diet_type

import de.westnordost.streetcomplete.R
import de.westnordost.streetcomplete.data.meta.isKindOfShopExpression
import de.westnordost.streetcomplete.data.meta.updateWithCheckDate
import de.westnordost.streetcomplete.data.osm.edits.update_tags.StringMapChangesBuilder
import de.westnordost.streetcomplete.data.osm.mapdata.Element
import de.westnordost.streetcomplete.data.osm.mapdata.MapDataWithGeometry
import de.westnordost.streetcomplete.data.osm.mapdata.filter
import de.westnordost.streetcomplete.data.osm.osmquests.OsmFilterQuestType
import de.westnordost.streetcomplete.data.user.achievements.QuestTypeAchievement.CITIZEN

class AddHalal : OsmFilterQuestType<DietAvailabilityAnswer>() {

    override val elementFilter = """
        nodes, ways with
        (
          amenity ~ restaurant|cafe|fast_food|ice_cream and food != no
          or shop ~ butcher|supermarket|ice_cream
        )
        and name and (
          !diet:halal
          or diet:halal != only and diet:halal older today -4 years
        )
    """
    override val commitMessage = "Add Halal status"
    override val wikiLink = "Key:diet:halal"
    override val icon = R.drawable.ic_quest_halal
    override val isReplaceShopEnabled = true
    override val defaultDisabledMessage = R.string.default_disabled_msg_go_inside_regional_warning

    override val questTypeAchievements = listOf(CITIZEN)

    override fun getTitle(tags: Map<String, String>) = R.string.quest_dietType_halal_name_title

    override fun getHighlightedElements(element: Element, getMapData: () -> MapDataWithGeometry) =
        getMapData().filter("nodes, ways, relations with " + isKindOfShopExpression())

    override fun createForm() = AddDietTypeForm.create(R.string.quest_dietType_explanation_halal)

    override fun applyAnswerTo(answer: DietAvailabilityAnswer, changes: StringMapChangesBuilder) {
        when(answer) {
            is DietAvailability -> changes.updateWithCheckDate("diet:halal", answer.osmValue)
            NoFood -> changes.addOrModify("food", "no")
        }
    }
}
