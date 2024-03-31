import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.mobdeve.s11.mco11.animocalendar.OnDialogCloseListener
import com.mobdeve.s11.mco11.animocalendar.R
import java.util.*

class AddNewTask : Fragment() {

    companion object {
        const val TAG = "AddNewTask"
        fun newInstance(): AddNewTask {
            return AddNewTask()
        }
    }


    private lateinit var setDueDate: EditText
    private lateinit var mTaskNameEdit: EditText
    private lateinit var mTaskDescEdit: EditText
    private lateinit var mSaveBtn: Button
    private lateinit var firestore: FirebaseFirestore
    private lateinit var context: Context
    private var dueDate: String = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_create_task, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setDueDate = view.findViewById(R.id.dateTv)
        mTaskNameEdit = view.findViewById(R.id.taskNameEt)
        mTaskDescEdit = view.findViewById(R.id.descriptionEt)
        mSaveBtn = view.findViewById(R.id.createBtn)

        firestore = FirebaseFirestore.getInstance()

        mTaskNameEdit.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}

            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                mSaveBtn.isEnabled = charSequence.isNotBlank()
                mSaveBtn.setBackgroundColor(
                    resources.getColor(
                        if (charSequence.isNotBlank()) R.color.light_accent else android.R.color.darker_gray
                    )
                )
            }

            override fun afterTextChanged(editable: Editable) {}
        })

        setDueDate.setOnClickListener {
            val calendar = Calendar.getInstance()
            val datePickerDialog = DatePickerDialog(
                requireContext(),
                { _: DatePicker, i: Int, i1: Int, i2: Int ->
                    var i = i
                    i = i + 1
                    setDueDate.setText("$i2/$i1/$i")
                    dueDate = "$i2/$i1/$i"
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )

            datePickerDialog.show()
        }

        mSaveBtn.setOnClickListener {
            val task = mTaskNameEdit.text.toString()
            val desc = mTaskDescEdit.text.toString()

            if (task.isEmpty()) {
                Toast.makeText(requireContext(), "Task name cannot be blank", Toast.LENGTH_SHORT).show()
            } else {
                val taskMap = hashMapOf<String, Any>()
                taskMap["task"] = task
                taskMap["due"] = dueDate
                taskMap["description"] = desc
                taskMap["status"] = 0
                taskMap["time"] = FieldValue.serverTimestamp()

                firestore.collection("task").add(taskMap)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(requireContext(), "Task Saved", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(requireContext(), task.exception?.message, Toast.LENGTH_SHORT).show()
                        }
                    }.addOnFailureListener { e ->
                        Toast.makeText(requireContext(), e.message, Toast.LENGTH_SHORT).show()
                    }
            }
        }
    }
}

