package hnau.common.projector.utils

import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import hnau.common.model.EditingString
import hnau.common.kotlin.mapper.Mapper


private val editingStringTextFieldValueMapper = Mapper<EditingString, TextFieldValue>(
    direct = { editingString ->
        TextFieldValue(
            text = editingString.text,
            selection = TextRange(
                start = editingString.selection.first,
                end = editingString.selection.last,
            ),
        )
    },
    reverse = { textFieldValue ->
        EditingString(
            text = textFieldValue.text,
            selection = IntRange(
                start = textFieldValue.selection.min,
                endInclusive = textFieldValue.selection.max,
            ),
        )
    },
)

val EditingString.Companion.textFieldValueMapper: Mapper<EditingString, TextFieldValue>
    get() = editingStringTextFieldValueMapper

/*@Composable
fun MutableStateFlow<EditingString>.collectAsTextFieldValueMutableState(): MutableState<TextFieldValue> =
    collectAsMutableState().mapRemembered(EditingString.textFieldValueMapper)*/
