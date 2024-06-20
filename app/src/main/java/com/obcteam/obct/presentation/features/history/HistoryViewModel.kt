package com.obcteam.obct.presentation.features.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import com.google.firebase.auth.FirebaseAuth
import com.obcteam.obct.data.local.models.PredictionHistoryWithReccomendation
import com.obcteam.obct.domain.repository.OBCTRepository
import com.patrykandpatrick.vico.core.cartesian.data.CartesianChartModelProducer
import com.patrykandpatrick.vico.core.cartesian.data.CartesianValueFormatter
import com.patrykandpatrick.vico.core.cartesian.data.lineSeries
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    obctRepository: OBCTRepository,
    firebaseAuth: FirebaseAuth,
    pager: Pager<Int, PredictionHistoryWithReccomendation>
) : ViewModel() {

    val predictionItems = pager.flow

    val currentUser = firebaseAuth.currentUser

    private val _xy = MutableStateFlow(Pair(listOf<String>(), listOf<Int>()))
    val xy = _xy.asStateFlow()

    val cartesianChartModelProducer = CartesianChartModelProducer.build()

    val cartesianFormatter = xy
        .map {
            CartesianValueFormatter { x, chartValues, _ ->
                it.first[x.toInt()]
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = CartesianValueFormatter { x, chartValues, _ ->
                ""
            }
        )


    fun addData(xy: Pair<List<String>, List<Int>>) {
        _xy.value = xy
    }

    init {
        viewModelScope.launch {
            xy.collectLatest {
                if (it.first.isNotEmpty()) {
                    cartesianChartModelProducer.tryRunTransaction {
                        val x = it.first.mapIndexed() { index, _ -> index }
                        lineSeries {
                            series(x = x, y = it.second)
                        }
                    }
                }

            }
        }
    }
}




