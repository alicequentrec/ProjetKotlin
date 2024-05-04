import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.testform.Voyage
import com.google.firebase.database.*

class DisplayInformation : AppCompatActivity() {
    private lateinit var database: FirebaseDatabase
    private lateinit var voyagesRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Créer un conteneur LinearLayout vertical pour l'affichage des données
        val layout = LinearLayout(this)
        layout.orientation = LinearLayout.VERTICAL
        layout.setBackgroundColor(Color.WHITE) // Couleur de fond blanche
        layout.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT
        )

        // Initialiser Firebase
        database = FirebaseDatabase.getInstance()
        voyagesRef = database.getReference("voyages")

        // Récupération des données depuis la base de données Firebase
        voyagesRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (snapshot in dataSnapshot.children) {
                    val voyage = snapshot.getValue(Voyage::class.java)
                    voyage?.let { displayVoyage(layout, it) }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Gérer les erreurs de lecture depuis la base de données
            }
        })

        // Définir le layout comme contenu de l'activité
        setContentView(layout)
    }

    private fun displayVoyage(layout: LinearLayout, voyage: Voyage) {
        // Créer des TextView pour afficher les données du voyage
        val textViewPays = TextView(this)
        textViewPays.text = "Pays: ${voyage.pays}"
        layout.addView(textViewPays)

        val textViewVille = TextView(this)
        textViewVille.text = "Ville: ${voyage.ville}"
        layout.addView(textViewVille)

        val textViewMonuments = TextView(this)
        textViewMonuments.text = "Monuments notables: ${voyage.monuments}"
        layout.addView(textViewMonuments)

        val textViewAvis = TextView(this)
        textViewAvis.text = "Avis: ${voyage.avis}"
        layout.addView(textViewAvis)

        // Créer un ImageView pour afficher l'image du voyage
        voyage.imageUri?.let { imageUri ->
            val imageView = ImageView(this)
            imageView.setImageURI(Uri.parse(imageUri))
            layout.addView(imageView)
        }
    }
}
