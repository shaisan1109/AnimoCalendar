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
    var priority: Priority = Priority.LOW // Default priority is LOW

    @get:PropertyName("category")
    @set:PropertyName("category")
    var category: Category = Category.PERSONAL // Default category is PERSONAL

    enum class Priority {
        LOW,
        MEDIUM,
        HIGH,
        URGENT
    }

    enum class Category {
        PERSONAL,
        ACADEMIC,
        HOLIDAY
    }
}
