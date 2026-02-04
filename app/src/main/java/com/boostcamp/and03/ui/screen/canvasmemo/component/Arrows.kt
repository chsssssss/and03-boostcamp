package com.boostcamp.and03.ui.screen.canvasmemo.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.IntSize
import com.boostcamp.and03.ui.screen.canvasmemo.model.EdgeUiModel
import com.boostcamp.and03.ui.screen.canvasmemo.model.MemoNodeUiModel
import kotlin.collections.forEach

@Composable
fun Arrows(
    arrows: List<EdgeUiModel>,
    items: Map<String, MemoNodeUiModel>,
    nodeSizes: Map<String, IntSize>,
) {
    val textMeasurer = rememberTextMeasurer()

    Canvas(modifier = Modifier.fillMaxSize()) {
        arrows.forEach { edge ->

            val fromNode = items[edge.edge.fromId]
            val toNode = items[edge.edge.toId]
            val fromSize = nodeSizes[edge.edge.fromId] ?: IntSize.Zero
            val toSize = nodeSizes[edge.edge.toId] ?: IntSize.Zero

            if (fromNode != null && toNode != null) {

                val start = fromNode.node.offset + Offset(
                    fromSize.width.toFloat(),
                    fromSize.height / 2f
                )

                val end = toNode.node.offset + Offset(
                    0f,
                    toSize.height / 2f
                )

                val midX = (start.x + end.x) / 2

                val path = Path().apply {
                    moveTo(start.x, start.y)
                    lineTo(midX, start.y)
                    lineTo(midX, end.y)
                    lineTo(end.x, end.y)
                }

                drawPath(
                    path = path,
                    color = Color.Black,
                    style = Stroke(width = 4f)
                )

                val label = edge.edge.name

                if (label.isNotEmpty()) {
                    val textLayoutResult = textMeasurer.measure(label)
                    val textWidth = textLayoutResult.size.width
                    val textHeight = textLayoutResult.size.height

                    val textPos = Offset(
                        x = midX - textWidth / 2,
                        y = (start.y + end.y) / 2 - textHeight / 2
                    )

                    drawRect(
                        color = Color.White,
                        topLeft = textPos,
                        size = Size(textWidth.toFloat(), textHeight.toFloat())
                    )

                    // 텍스트 그리기
                    drawText(
                        textLayoutResult = textLayoutResult,
                        topLeft = textPos
                    )
                }
            }
        }
    }
}