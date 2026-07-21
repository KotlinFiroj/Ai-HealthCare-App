package com.mediai.enterprise.feature.appointment.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mediai.enterprise.feature.appointment.domain.model.TimeSlot
import java.time.format.DateTimeFormatter

@Composable
fun SlotSelectionGrid(
    slots: List<TimeSlot>,
    selectedSlot: TimeSlot?,
    onSlotSelected: (TimeSlot) -> Unit,
    modifier: Modifier = Modifier
) {
    val formatter = DateTimeFormatter.ofPattern("HH:mm")

    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = modifier,
        contentPadding = PaddingValues(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(slots) { slot ->
            val isSelected = slot == selectedSlot
            FilterChip(
                selected = isSelected,
                onClick = { onSlotSelected(slot) },
                label = {
                    Text(
                        text = slot.startTime.format(formatter),
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )
                },
                enabled = slot.isAvailable,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}
