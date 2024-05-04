package com.example.testform
import android.os.Parcel
import android.os.Parcelable

//parcelable nous permet de récupérer les données dans display information


data class Voyage(
    val pays: String,
    val ville: String,
    val monuments: String,
    val avis: String,
    val imageUri: String? // Ajoutez la propriété pour stocker l'URI de l'image
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(pays)
        parcel.writeString(ville)
        parcel.writeString(monuments)
        parcel.writeString(avis)
        parcel.writeString(imageUri)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Voyage> {
        override fun createFromParcel(parcel: Parcel): Voyage {
            return Voyage(parcel)
        }

        override fun newArray(size: Int): Array<Voyage?> {
            return arrayOfNulls(size)
        }
    }
}