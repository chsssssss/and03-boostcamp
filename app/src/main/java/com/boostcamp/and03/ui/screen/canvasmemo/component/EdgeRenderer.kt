package com.boostcamp.and03.ui.screen.canvasmemo.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.IntSize
import com.boostcamp.and03.ui.screen.canvasmemo.model.EdgeUiModel
import com.boostcamp.and03.ui.screen.canvasmemo.model.MemoNodeUiModel
import kotlin.math.abs

@Composable
fun EdgeRenderer(
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

            if (fromNode == null || toNode == null) return@forEach

            val hasReverse = arrows.any {
                it.edge.fromId == edge.edge.toId &&
                        it.edge.toId == edge.edge.fromId
            }

            val pathOffset =
                if (hasReverse) {
                    if (edge.edge.fromId > edge.edge.toId) 100f else -100f
                } else 0f

            // 중심점 계산
            val fromCenter = fromNode.node.offset + Offset(
                fromSize.width / 2f,
                fromSize.height / 2f
            )

            val toCenter = toNode.node.offset + Offset(
                toSize.width / 2f,
                toSize.height / 2f
            )

            val dx = toCenter.x - fromCenter.x
            val dy = toCenter.y - fromCenter.y

            val isHorizontalDominant = abs(dx) > abs(dy)

            // 연결점 비율
            val ratio =
                if (hasReverse) {
                    if (edge.edge.fromId < edge.edge.toId) 1f / 3f else 2f / 3f
                } else 0.5f

            val start = calculateConnectionPoint(
                node = fromNode.node.offset,
                size = fromSize,
                dx = dx,
                dy = dy,
                ratio = ratio,
                isStart = true
            )

            val end = calculateConnectionPoint(
                node = toNode.node.offset,
                size = toSize,
                dx = dx,
                dy = dy,
                ratio = ratio,
                isStart = false
            )

            // 1) Edge Path
            val path = drawEdgePath(
                start = start,
                end = end,
                pathOffset = pathOffset,
                isHorizontalDominant = isHorizontalDominant,
                drawScope = this
            )

            // 2) Arrow Head
            drawArrowHead(
                path = path,
                end = end,
                start = start,
                pathOffset = pathOffset,
                isHorizontalDominant = isHorizontalDominant,
                drawScope = this
            )

            // 3) Label
            drawEdgeLabel(
                label = edge.edge.name,
                start = start,
                end = end,
                pathOffset = pathOffset,
                isHorizontalDominant = isHorizontalDominant,
                textMeasurer = textMeasurer,
                drawScope = this
            )
        }
    }
}

private fun drawEdgePath(
    start: Offset,
    end: Offset,
    pathOffset: Float,
    isHorizontalDominant: Boolean,
    drawScope: DrawScope
): Path {

    val path = Path().apply {
        moveTo(start.x, start.y)

        if (isHorizontalDominant) {
            val midX = (start.x + end.x) / 2
            lineTo(midX + pathOffset, start.y)
            lineTo(midX + pathOffset, end.y)
        } else {
            val midY = (start.y + end.y) / 2
            lineTo(start.x, midY + pathOffset)
            lineTo(end.x, midY + pathOffset)
        }

        lineTo(end.x, end.y)
    }

    drawScope.drawPath(
        path = path,
        color = Color.Black,
        style = Stroke(width = 4f)
    )

    return path
}

private fun drawArrowHead(
    path: Path,
    start: Offset,
    end: Offset,
    pathOffset: Float,
    isHorizontalDominant: Boolean,
    drawScope: DrawScope
) {
    val arrowHeadSize = 20f
    val arrowAngle = Math.PI / 6

    // 마지막 segment 방향 계산
    val lastSegmentStart =
        if (isHorizontalDominant) {
            val midX = (start.x + end.x) / 2
            Offset(midX + pathOffset, end.y)
        } else {
            val midY = (start.y + end.y) / 2
            Offset(end.x, midY + pathOffset)
        }

    val arrowDx = end.x - lastSegmentStart.x
    val arrowDy = end.y - lastSegmentStart.y

    val angle =
        kotlin.math.atan2(arrowDy.toDouble(), arrowDx.toDouble()).toFloat()

    val arrowPoint1 = Offset(
        (end.x - arrowHeadSize * kotlin.math.cos(angle - arrowAngle)).toFloat(),
        (end.y - arrowHeadSize * kotlin.math.sin(angle - arrowAngle)).toFloat()
    )

    val arrowPoint2 = Offset(
        (end.x - arrowHeadSize * kotlin.math.cos(angle + arrowAngle)).toFloat(),
        (end.y - arrowHeadSize * kotlin.math.sin(angle + arrowAngle)).toFloat()
    )

    val arrowPath = Path().apply {
        moveTo(end.x, end.y)
        lineTo(arrowPoint1.x, arrowPoint1.y)
        lineTo(arrowPoint2.x, arrowPoint2.y)
        close()
    }

    drawScope.drawPath(
        path = arrowPath,
        color = Color.Black
    )
}

private fun drawEdgeLabel(
    label: String,
    start: Offset,
    end: Offset,
    pathOffset: Float,
    isHorizontalDominant: Boolean,
    textMeasurer: TextMeasurer,
    drawScope: DrawScope
) {
    if (label.isEmpty()) return

    val textLayoutResult = textMeasurer.measure(label)
    val textWidth = textLayoutResult.size.width.toFloat()
    val textHeight = textLayoutResult.size.height.toFloat()

    val textPos =
        if (isHorizontalDominant) {
            val midX = (start.x + end.x) / 2
            Offset(
                x = midX + pathOffset - textWidth / 2,
                y = (start.y + end.y) / 2 - textHeight / 2
            )
        } else {
            val midY = (start.y + end.y) / 2
            Offset(
                x = (start.x + end.x) / 2 - textWidth / 2,
                y = midY + pathOffset - textHeight / 2
            )
        }

    // 배경 흰색 박스
    drawScope.drawRect(
        color = Color.White,
        topLeft = textPos,
        size = Size(textWidth, textHeight)
    )

    // 텍스트
    drawScope.drawText(
        textLayoutResult = textLayoutResult,
        topLeft = textPos
    )
}

private fun calculateConnectionPoint(
    node: Offset,
    size: IntSize,
    dx: Float,
    dy: Float,
    ratio: Float,
    isStart: Boolean
): Offset {

    val horizontal = kotlin.math.abs(dx) > kotlin.math.abs(dy)

    return if (horizontal) {
        if (dx > 0) {
            // 오른쪽 방향
            if (isStart) node + Offset(size.width.toFloat(), size.height * ratio)
            else node + Offset(0f, size.height * ratio)
        } else {
            // 왼쪽 방향
            if (isStart) node + Offset(0f, size.height * ratio)
            else node + Offset(size.width.toFloat(), size.height * ratio)
        }
    } else {
        if (dy > 0) {
            // 아래 방향
            if (isStart) node + Offset(size.width * ratio, size.height.toFloat())
            else node + Offset(size.width * ratio, 0f)
        } else {
            // 위 방향
            if (isStart) node + Offset(size.width * ratio, 0f)
            else node + Offset(size.width * ratio, size.height.toFloat())
        }
    }
}
