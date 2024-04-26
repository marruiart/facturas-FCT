package com.marinaruiz.facturas_fct.core

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData

class CombinedMediatorLiveData<A, B>(
    liveData1: LiveData<A>,
    liveData2: LiveData<B>,
    removeSources: Boolean
) : MediatorLiveData<Pair<A, B>>() {

/*    init {
        value = null
        val mediator: MediatorLiveData<Pair<A, B>> = this
        mediator.addSource(liveData1) { objectA ->
            mediator.value = Pair(objectA, mediator.value?.second)
            if (removeSources) mediator.removeSource(liveData1)
        }
        mediator.addSource(liveData2) { objectB ->
            mediator.value = Pair(mediator.value?.first, objectB)
            if (removeSources) mediator.removeSource(liveData2)
        }
    }*/
}

/*
private CombinedMediatorLiveData<ResponseHiReadVO, List<IWallboxChargerInfoWrapper>> cargadoresActualizadosLivedata;

setCargadoresActualizadosLivedata(new CombinedMediatorLiveData(CoreData.getInstance().getDispositivosHogarInteligente(), ApplicationData.getInstance().getListaCargadoresRegistradosEnWallboxYBackendLiveData(), false));

viewModel.getCargadoresActualizadosLivedata().observe(this, new Observer<>() {
    @Override
    public void onChanged(Pair<ResponseHiReadVO, List<IWallboxChargerInfoWrapper>> listPair) {
        List<IWallboxChargerInfoWrapper> listaCargadoresRegistradosEnWallboxYBackend;
        ResponseHiReadVO dispositivosMiddle;

        if (!isDispositivosDomotica() && listPair != null && listPair.first != null && listPair.second != null) {
            listaCargadoresRegistradosEnWallboxYBackend = listPair.second;
            dispositivosMiddle = listPair.first;

            ....

        }
    }
})*/
