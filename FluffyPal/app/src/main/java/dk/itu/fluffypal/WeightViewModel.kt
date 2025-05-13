package dk.itu.fluffypal

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dk.itu.fluffypal.database.PetDBHelper


class WeightViewModel(application: Application) : AndroidViewModel(application) {

    private val db = PetDBHelper.getInstance(application.applicationContext)

    private val sWeights = MutableLiveData<List<PetWeight>>()
    val weights: LiveData<List<PetWeight>> = sWeights

    fun loadWeights(petId: Int) {
        val list = db.getWeightsForPet(petId)
        sWeights.value = list

        list.maxByOrNull { it.date }?.let {
            db.updatePetWeight(petId, it.weight)
        }
    }

    fun addWeight(weight: PetWeight) {
        db.insertWeight(weight)
        loadWeights(weight.petId)
    }

    fun getWeightsForPet(petId: Int): LiveData<List<PetWeight>>{
        val data = MutableLiveData<List<PetWeight>>()
        val list = db.getWeightsForPet(petId)
        val latest = list.maxByOrNull { it.date }
        if(latest != null){
            db.updatePetWeight(petId, latest.weight)
        }
        data.value = list
        return data
    }

}