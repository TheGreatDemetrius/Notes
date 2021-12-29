package ru.simple.notes.components.presentation.list.components

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import ru.simple.notes.R
import ru.simple.notes.components.presentation.util.Dimensions.MEDIUM
import ru.simple.notes.components.presentation.util.Dimensions.SMALL
import ru.simple.notes.components.domain.util.OrderProperties
import ru.simple.notes.components.domain.util.OrderType

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
            RadioButton(
                text = stringResource(id = R.string.ascending),
                selected = orderProperties.orderType is OrderType.Ascending,
                onSelect = { onOrderChange(orderProperties.copy(OrderType.Ascending)) }
            )
            Spacer(modifier = Modifier.width(SMALL))
            RadioButton(
                text = stringResource(id = R.string.descending),
                selected = orderProperties.orderType is OrderType.Descending,
                onSelect = { onOrderChange(orderProperties.copy(OrderType.Descending)) }
            )
        }
        Spacer(modifier = Modifier.height(MEDIUM))
        Row(modifier = Modifier.fillMaxWidth()) {
            RadioButton(
                text = stringResource(id = R.string.title),
                selected = orderProperties is OrderProperties.Title,
                onSelect = { onOrderChange(OrderProperties.Title(orderProperties.orderType)) }
            )
            Spacer(modifier = Modifier.width(SMALL))
            RadioButton(
                text = stringResource(id = R.string.date),
                selected = orderProperties is OrderProperties.Date,
                onSelect = { onOrderChange(OrderProperties.Date(orderProperties.orderType)) }
            )
            Spacer(modifier = Modifier.width(SMALL))
            RadioButton(
                text = stringResource(id = R.string.color),
                selected = orderProperties is OrderProperties.Color,
                onSelect = { onOrderChange(OrderProperties.Color(orderProperties.orderType)) }
            )
        }
    }
}