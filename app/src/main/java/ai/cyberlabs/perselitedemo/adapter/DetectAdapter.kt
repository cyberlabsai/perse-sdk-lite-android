package ai.cyberlabs.perselitedemo.adapter

import ai.cyberlabs.perselitedemo.R
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class DetectAdapter: RecyclerView.Adapter<DetectAdapter.ViewHolder>() {

    var data: List<Pair<String, String>> = emptyList()

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        fun bind(pair: Pair<String, String>) {
            itemView.findViewById<TextView>(android.R.id.text1).text = pair.first
            itemView.findViewById<TextView>(android.R.id.text2).text = pair.second
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(
                R.layout.list_item,
                parent,
                false
            )
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(this.data[position])

    override fun getItemCount(): Int = this.data.size

    fun setData(detectResponse: PerseAPIResponse.Face.Detect) {
        val list: MutableList<Pair<String, String>> = ArrayList()
        list.add(Pair("Detected Faces", detectResponse.totalFaces.toString()))

        var i = 0
        detectResponse.faces.forEach { face ->
            i++
            list.add(Pair("Face ".plus(i.toString()), ""))
            list.add(Pair("Liveness score", face.livenessScore.toString()))
            list.add(Pair("Bounding box", face.boundingBox.toString()))

            list.add(Pair("Face Metrics ".plus(i.toString()), ""))
            list.add(Pair("Overexposure", face.faceMetrics.overexposure.toString()))
            list.add(Pair("Underexposure", face.faceMetrics.underexposure.toString()))
            list.add(Pair("Sharpness", face.faceMetrics.sharpness.toString()))
        }

        list.add(Pair("Image Metrics", ""))
        list.add(Pair("Overexposure", detectResponse.imageMetrics.overexposure.toString()))
        list.add(Pair("Underexposure", detectResponse.imageMetrics.underexposure.toString()))
        list.add(Pair("Sharpness", detectResponse.imageMetrics.sharpness.toString()))
        this.data = list
        notifyDataSetChanged()
    }
}