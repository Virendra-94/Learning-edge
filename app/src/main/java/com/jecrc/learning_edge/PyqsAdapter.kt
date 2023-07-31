import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.jecrc.learning_edge.R

//class PyqsAdapter(
//    private var pyqsData: Map<String, Map<String, Map<String, List<String>>>>,
//    private val itemClickListener: OnItemClickListener
//) : RecyclerView.Adapter<PyqsAdapter.PyqsViewHolder>() {

class PyqsAdapter(
    private var pyqsData: List<Pair<String, String>>, // Pair to hold both subject and pyqs file name
    private val itemClickListener: OnItemClickListener
) : RecyclerView.Adapter<PyqsAdapter.PyqsViewHolder>() {

    // Add these variables to hold the selected pyqs file and subject
    var selectedPyqsFile: String? = null
    var selectedSubject: String? = null

    fun updateData(newPyqsData: List<Pair<String, String>>) {
        pyqsData = newPyqsData
        notifyDataSetChanged()
    }

    inner class PyqsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val pyqsNameButton: Button = itemView.findViewById(R.id.btnSubject)
        val downloadIcon: ImageView = itemView.findViewById(R.id.ivDownloadIcon)
    }

    interface OnItemClickListener {
        fun onItemClick(pyqsFile: String)
    }

    private var onItemClickListener: OnItemClickListener? = null

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.onItemClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PyqsViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.subject_pyqs_item, parent, false)
        return PyqsViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: PyqsViewHolder, position: Int) {
        // Get the subject and pyqs file name for the current position from the pyqsData list
        val (subject, pyqsFileName) = pyqsData[position]

        // Set the pyqs name to the button
        holder.pyqsNameButton.text = pyqsFileName

        // Set a click listener for the pyqs name button
        holder.pyqsNameButton.setOnClickListener {
            selectedPyqsFile = pyqsFileName
            selectedSubject = subject
            onItemClickListener?.onItemClick(pyqsFileName)
        }
    }

    override fun getItemCount(): Int {
        // Return the total number of subjects and pyqs items
        return pyqsData.size
    }
}
