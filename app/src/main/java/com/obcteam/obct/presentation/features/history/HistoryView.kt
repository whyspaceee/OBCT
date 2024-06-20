package com.obcteam.obct.presentation.features.history

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeightIn
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.arnyminerz.markdowntext.MarkdownText
import com.google.firebase.auth.FirebaseUser
import com.obcteam.obct.R
import com.obcteam.obct.data.local.models.PredictionHistoryWithReccomendation
import com.obcteam.obct.domain.utils.OBCTFormatters
import com.obcteam.obct.presentation.features.auth.login.shimmerBrush
import com.patrykandpatrick.vico.compose.cartesian.CartesianChartHost
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberBottomAxis
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberStartAxis
import com.patrykandpatrick.vico.compose.cartesian.layer.rememberLineCartesianLayer
import com.patrykandpatrick.vico.compose.cartesian.layer.rememberLineSpec
import com.patrykandpatrick.vico.compose.cartesian.rememberCartesianChart
import com.patrykandpatrick.vico.compose.cartesian.rememberVicoZoomState
import com.patrykandpatrick.vico.compose.common.shader.color
import com.patrykandpatrick.vico.core.cartesian.data.CartesianChartModelProducer
import com.patrykandpatrick.vico.core.cartesian.data.CartesianValueFormatter
import com.patrykandpatrick.vico.core.common.component.LineComponent
import com.patrykandpatrick.vico.core.common.component.TextComponent
import com.patrykandpatrick.vico.core.common.shader.DynamicShader


@Composable
fun HistoryView(modifier: Modifier = Modifier, vm: HistoryViewModel) {
    val historyItems = vm.predictionItems.collectAsLazyPagingItems()
    val cartesianFormatter = vm.cartesianFormatter.collectAsState()
    val currentUser = vm.currentUser


    HistoryView(
        cartesianChartModelProducer = vm.cartesianChartModelProducer,
        items = historyItems,
        addData = vm::addData,
        currentUser = currentUser,
        cartesianValueFormatter = cartesianFormatter.value
    )
}

@Composable
fun HistoryView(
    modifier: Modifier = Modifier,
    cartesianChartModelProducer: CartesianChartModelProducer,
    items: LazyPagingItems<PredictionHistoryWithReccomendation>,
    addData: (Pair<List<String>, List<Int>>) -> Unit,
    currentUser: FirebaseUser?,
    cartesianValueFormatter: CartesianValueFormatter
) {
    if (items.loadState.refresh is LoadState.Loading) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .background(shimmerBrush())
        )
    } else {
        val context = LocalContext.current
        LaunchedEffect(key1 = items) {
            if (items.itemCount > 0) addData(Pair(first = items.itemSnapshotList.map {
                OBCTFormatters.formatDMMMFromISO(
                    it?.predictionHistory?.createdAt.toString(),
                    context
                ).reversed()
            }, second = items.itemSnapshotList.map {
                it?.predictionHistory?.weight ?: 0
            }.reversed()))

        }
        Column(
            modifier = modifier.then(
                Modifier
                    .fillMaxSize()
                    .padding(24.dp)

            )
        ) {
            Row {
                Column {
                    Text(
                        text = "Hello,",
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        text = "${currentUser?.displayName}",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.SemiBold
                        )
                    )


                }
                Box(
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth()
                ) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(currentUser?.photoUrl)
                            .crossfade(true).build(),
                        contentDescription = stringResource(R.string.profile_picture),
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .clip(CircleShape)
                            .size(40.dp)
                            .align(Alignment.CenterEnd)
                    )
                }
            }
            Spacer(modifier = Modifier.height(24.dp))
            CartesianChartHost(

                modifier = Modifier.fillMaxWidth(), zoomState = rememberVicoZoomState(
                    zoomEnabled = true,
                ), chart = rememberCartesianChart(
                    rememberLineCartesianLayer(
                        lines = listOf(
                            rememberLineSpec(
                                shader = DynamicShader.color(MaterialTheme.colorScheme.primary)
                            )
                        )
                    ),
                    startAxis = rememberStartAxis(
                        tick = LineComponent(color = MaterialTheme.colorScheme.onSurface.toArgb()),
                        axis = LineComponent(color = MaterialTheme.colorScheme.onSurface.toArgb()),
                        label = TextComponent.build {
                            color = MaterialTheme.colorScheme.onSurface.toArgb()
                        },
                        guideline = null
                    ),
                    bottomAxis = rememberBottomAxis(
                        tick = LineComponent(color = MaterialTheme.colorScheme.onSurface.toArgb()),
                        axis = LineComponent(color = MaterialTheme.colorScheme.onSurface.toArgb()),
                        label = TextComponent.build {
                            color = MaterialTheme.colorScheme.onSurface.toArgb()
                        },
                        guideline = null, valueFormatter = cartesianValueFormatter
                    )

                ), modelProducer = cartesianChartModelProducer
            )

            Spacer(modifier = Modifier.height(16.dp))
            OutlinedCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .requiredHeightIn(max = 160.dp)
            ) {
                if (items.itemCount > 0) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                            .verticalScroll(rememberScrollState())
                    ) {
                        MarkdownText(markdown = items[0]?.recommendation?.map { it.recommendation }
                            ?.joinToString(", ") ?: "",
                            style = MaterialTheme.typography.bodyLarge)
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                "History", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(16.dp))
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                items(items.itemCount) {
                    val item = items[it]
                    println(item)
                    if (item != null) {
                        ElevatedCard(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.elevatedCardColors()
                        ) {
                            Box(modifier = Modifier.padding(16.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Column {
                                        Text(
                                            OBCTFormatters.formatRelativeDateFromISO(
                                                item.predictionHistory.createdAt,
                                                LocalContext.current
                                            )
                                        )
                                        Text(
                                            OBCTFormatters.formatPredictionToString(item.predictionHistory.prediction.prediction),
                                            style = MaterialTheme.typography.titleMedium.copy(
                                                fontWeight = FontWeight.Bold,
                                                color = OBCTFormatters.formatPredictionToColor(item.predictionHistory.prediction.prediction)
                                            )
                                        )

                                    }
                                    Text(buildAnnotatedString {
                                        withStyle(
                                            style = MaterialTheme.typography.headlineLarge.copy(
                                                fontWeight = FontWeight.Bold,
                                            ).toSpanStyle()
                                        ) {
                                            append(item.predictionHistory.weight.toString())
                                        }
                                        append(" kg")
                                    })
                                }

                            }
                        }
                    }
                }
            }
        }

    }
}