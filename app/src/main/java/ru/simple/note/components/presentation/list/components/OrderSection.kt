package ru.simple.note.components.presentation.list.components

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import ru.simple.note.R
import ru.simple.note.components.domain.util.Dimensions.MEDIUM
import ru.simple.note.components.domain.util.Dimensions.SMALL
import ru.simple.note.components.domain.util.OrderProperties
import ru.simple.note.components.domain.util.OrderType

@Composable
fun OrderSection(
    modifier: Modifier = Modifier,
    orderProperties: OrderProperties = OrderProperties.Date(OrderType.Descending),
    onOrderChange: (OrderProperties) -> Unit
) {
    Column(
        modifier = modifier
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            DefaultRadioButton(
                text = stringResource(id = R.string.title),
                selected = orderProperties is OrderProperties.Title,
                onSelect = { onOrderChange(OrderProperties.Title(orderProperties.orderType)) }
            )
            Spacer(modifier = Modifier.width(SMALL))
            DefaultRadioButton(
                text = stringResource(id = R.string.date),
                selected = orderProperties is OrderProperties.Date,
                onSelect = { onOrderChange(OrderProperties.Date(orderProperties.orderType)) }
            )
            Spacer(modifier = Modifier.width(SMALL))
            DefaultRadioButton(
                text = stringResource(id = R.string.color),
                selected = orderProperties is OrderProperties.Color,
                onSelect = { onOrderChange(OrderProperties.Color(orderProperties.orderType)) }
            )
        }
        Spacer(modifier = Modifier.height(MEDIUM))
        Row(modifier = Modifier.fillMaxWidth()) {
            DefaultRadioButton(
                text = stringResource(id = R.string.ascending),
                selected = orderProperties.orderType is OrderType.Ascending,
                onSelect = { onOrderChange(orderProperties.copy(OrderType.Ascending)) }
            )
            Spacer(modifier = Modifier.width(SMALL))
            DefaultRadioButton(
                text = stringResource(id = R.string.descending),
                selected = orderProperties.orderType is OrderType.Descending,
                onSelect = { onOrderChange(orderProperties.copy(OrderType.Descending)) }
            )
        }
    }
}