import android.app.DatePickerDialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.mobdeve.s11.mco11.animocalendar.OnDialogCloseListener
import com.mobdeve.s11.mco11.animocalendar.R
import java.util.*

class AddNewTask : DialogFragment() {

    companion object {
        const val TAG = "AddNewTask"

        fun newInstance(): AddNewTask {
            return AddNewTask()
        }
    }

    private lateinit var setDueDate: TextView
    private lateinit var mTaskEdit: EditText
    private lateinit var mSaveBtn: Button
    private lateinit var firestore: FirebaseFirestore
    private lateinit var context: Context
    private var dueDate: String = ""
    private var id = ""
    private var dueDateUpdate = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.add_new_task, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setDueDate = view.findViewById(R.id.setDueDateTv)
        mTaskEdit = view.findViewById(R.id.taskEt)
        mSaveBtn = view.findViewById(R.id.saveBtn)

        firestore = FirebaseFirestore.getInstance()

        var isUpdate = false

        val bundle = arguments
        if (bundle != null) {
            isUpdate = true
            val task = bundle.getString("task")
            id = bundle.getString("id")!!
            dueDateUpdate = bundle.getString("due")!!

            mTaskEdit.setText(task)
            setDueDate.text = dueDateUpdate

            if (task!!.length > 0) {
                mSaveBtn.isEnabled = false
                mSaveBtn.setBackgroundColor(Color.GRAY)
            }
        }

        mTaskEdit.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}

            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                if (charSequence.toString().isEmpty()) {
                    mSaveBtn.isEnabled = false
                    mSaveBtn.setBackgroundColor(Color.GRAY)
                } else {
                    mSaveBtn.isEnabled = true
                    mSaveBtn.setBackgroundColor(resources.getColor(R.color.light_accent))
                }
            }

            override fun afterTextChanged(editable: Editable) {}
        })

        setDueDate.setOnClickListener {
            val calendar = Calendar.getInstance()

            val MONTH = calendar.get(Calendar.MONTH)
            val YEAR = calendar.get(Calendar.YEAR)
            val DAY = calendar.get(Calendar.DATE)

            val datePickerDialog = DatePickerDialog(context, { datePicker: DatePicker, i: Int, i1: Int, i2: Int ->
                var i = i
                i = i + 1
                setDueDate.text = "$i2/$i1/$i"
                dueDate = "$i2/$i1/$i"
            }, YEAR, MONTH, DAY)

            datePickerDialog.show()
        }

        val finalIsUpdate = isUpdate
        mSaveBtn.setOnClickListener {
            val task = mTaskEdit.text.toString()

            if (finalIsUpdate) {
                firestore.collection("task").document(id).update("task", task, "due", dueDate)
                Toast.makeText(context, "Task Updated", Toast.LENGTH_SHORT).show()
            } else {
                if (task.isEmpty()) {
                    Toast.makeText(context, "Please add a task", Toast.LENGTH_SHORT).show()
                } else {
                    val taskMap = hashMapOf<String, Any>()
                    taskMap["task"] = task
                    taskMap["due"] = dueDate
                    taskMap["status"] = 0
                    taskMap["time"] = FieldValue.serverTimestamp()

                    firestore.collection("task").add(taskMap)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                Toast.makeText(context, "Task Saved", Toast.LENGTH_SHORT).show()
                            } else {
                                Toast.makeText(context, task.exception?.message, Toast.LENGTH_SHORT).show()
                            }
                        }.addOnFailureListener { e ->
                            Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
                        }
                }
            }
            dismiss()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.context = context
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        val activity = getActivity()
        if (activity is OnDialogCloseListener) {
            (activity as OnDialogCloseListener).onDialogClose(dialog)
        }
    }
}
