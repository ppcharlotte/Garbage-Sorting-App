package dk.itu.fluffypal

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.activityViewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.text.SimpleDateFormat
import java.util.*

class AddWeightFragment : BottomSheetDialogFragment() {

    private val petListViewModel: PetListViewModel by activityViewModels()
    private val weightViewModel: WeightViewModel by activityViewModels()

    private var selectedPetId: Int? = null
    private var weightDate: String = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_add_weight, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val petRadioGroup = view.findViewById<RadioGroup>(R.id.petRadioGroupR)
        val weightInput = view.findViewById<EditText>(R.id.input_weight)
        val dateInput = view.findViewById<EditText>(R.id.weight_date)
        val saveButton = view.findViewById<Button>(R.id.saveWeightButton)
        val petList = petListViewModel.petList.value

        dateInput.setText(weightDate)
        dateInput.setOnClickListener {
            val calendar = Calendar.getInstance()
            val datePicker = DatePickerDialog(
                requireContext(),
                { _, year, month, dayOfMonth ->
                    weightDate = String.format("%04d-%02d-%02d", year, month + 1, dayOfMonth)
                    dateInput.setText(weightDate)
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )
            datePicker.show()
        }

        if (petList.isNullOrEmpty()) {
            Toast.makeText(requireContext(), "Please add pet!", Toast.LENGTH_LONG).show()
            saveButton.isEnabled = false
            return
        }

        petListViewModel.petList.value?.forEach { pet ->
            val radioButton = RadioButton(requireContext()).apply {
                text = pet.petName
                id = View.generateViewId()
                setOnClickListener {
                    selectedPetId = petListViewModel.getPetIdByName(pet.petName)
                }
            }
            petRadioGroup.addView(radioButton)
        }

        saveButton.setOnClickListener {
            val weight = weightInput.text.toString()
            if (weight.isNotBlank() && selectedPetId != null) {
                val entry = PetWeight(
                    id = 0,
                    petId = selectedPetId!!,
                    weight = weight,
                    date = weightDate
                )
                weightViewModel.addWeight(entry)
                dismiss()
            } else {
                Toast.makeText(requireContext(), "Please complete all fields", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
