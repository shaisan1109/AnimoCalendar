import com.google.firebase.firestore.PropertyName
import com.mobdeve.s11.mco11.animocalendar.TaskId

open class ToDoModel : TaskId() {

    @get:PropertyName("task")
    @set:PropertyName("task")
    var taskName: String = ""

    @get:PropertyName("due")
    @set:PropertyName("due")
    var dueDate: String = ""

    @get:PropertyName("status")
    @set:PropertyName("status")
    var status: Int = 0

    @get:PropertyName("host")
    @set:PropertyName("host")
    var host: String = ""

    @get:PropertyName("description")
    @set:PropertyName("description")
    var taskDescription: String = ""

    @get:PropertyName("priority")
    @set:PropertyName("priority")
    var priority: Priority = Priority.Low

    @get:PropertyName("category")
    @set:PropertyName("category")
    var category: Category = Category.Personal

    enum class Priority(val displayName: String) {
        Low("Low"),
        Medium("Medium"),
        High("High"),
        Urgent("Urgent")
    }

    enum class Category(val displayName: String) {
        Personal("Personal"),
        Academic("Academic"),
        Holiday("Holiday")
    }
}
