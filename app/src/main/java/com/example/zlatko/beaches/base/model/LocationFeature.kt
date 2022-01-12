package com.example.zlatko.beaches.base.model

import androidx.annotation.StringRes
import com.example.zlatko.beaches.R

// NOTE: the values for the ids correspond with the data names in the badestellen brandenburg kml
enum class LocationFeature(val id: String, @StringRes val text: Int) {
    WASTE_DISPOSAL("wasteDisposal", R.string.waste_disposal),
    GASTRONOMY("gastronomy", R.string.gastronomy),
    LIFEGUARD("lifeguard", R.string.lifeguard),
    PARKING_AREA("parkingArea", R.string.parking_area),
    LAVATORY("lavatory", R.string.lavatory),
    SUNBATHING_AREA("sunbathingArea", R.string.sunbathing_area),
    FISHING_ALLOWED("fishingAllowed", R.string.fishing_allowed),
    PLAYGROUND("playground", R.string.playground),
    BARBECUE_AREA("barbecueArea", R.string.barbecue_area),
    CAMPING_ALLOWED("campingAllowed", R.string.camping_allowed)
}