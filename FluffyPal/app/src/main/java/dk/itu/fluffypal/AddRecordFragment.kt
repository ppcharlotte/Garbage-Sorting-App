package dk.itu.fluffypal

import android.app.DatePickerDialog
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.fragment.app.activityViewModels
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.io.File
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale


class AddRecordFragment : BottomSheetDialogFragment() {

    private val recordViewModel: RecordViewModel by activityViewModels()
    private val petListViewModel: PetListViewModel by activityViewModels()

    private var selectedPetId: Int? = null
    private var pictureUri: Uri? = null

    private lateinit var cameraImageUri: Uri

    private val cameraLauncher = registerForActivityResult(ActivityResultContracts.TakePicture()) { success ->
        if (success) {
            pictureUri = cameraImageUri
            view?.findViewById<ImageView>(R.id.recordImagePreview)?.setImageURI(cameraImageUri)

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_add_record, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val petRadioGroup = view.findViewById<RadioGroup>(R.id.petRadioGroup)
        val titleInput = view.findViewById<EditText>(R.id.recordTitleInput)
        val descInput = view.findViewById<EditText>(R.id.recordDescriptionInput)
        val imageButton = view.findViewById<Button>(R.id.selectImageButton)
        val saveButton = view.findViewById<Button>(R.id.saveRecordButton)
        val addDateButton = view.findViewById<Button>(R.id.recordDateButton)
        val petList = petListViewModel.petList.value
        val selectedDate: String = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())


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

        addDateButton.text = selectedDate
        addDateButton.setOnClickListener {
            val calendar = Calendar.getInstance()
            val datePicker = DatePickerDialog(
                requireContext(), { _, year, month, dayOfMonth ->
                    val formatted = String.format("%04d-%02d-%02d", year, month + 1, dayOfMonth)
                    addDateButton.text = formatted
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )
            datePicker.show()
        }

        imageButton.setOnClickListener {
            val context = requireContext()
            val imageFile = File.createTempFile("record", ".jpg", context.cacheDir)
            cameraImageUri = FileProvider.getUriForFile(
                context,
                context.packageName + ".fileprovider",
                imageFile
            )
            cameraLauncher.launch(cameraImageUri)
        }

        saveButton.setOnClickListener {
            val title = titleInput.text.toString()
            val description = descInput.text.toString()

            if (title.isNotBlank() && description.isNotBlank() && selectedPetId != null) {
                val record = PetRecord(
                    id = 0,
                    petId = selectedPetId!!,
                    title = title,
                    date = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date()),
                    description = description,
                    recordImageUri = pictureUri?.toString()
                )
                recordViewModel.addRecord(record)
                dismiss()
                val navView = requireActivity().findViewById<BottomNavigationView>(R.id.bottomNav)
                navView.selectedItemId = R.id.RecordFragment
            } else {
                Toast.makeText(requireContext(), "Please fill in all required fields", Toast.LENGTH_SHORT).show()
            }
        }
    }
}