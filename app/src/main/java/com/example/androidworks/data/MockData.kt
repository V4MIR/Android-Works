package com.example.androidworks.data

import com.example.androidworks.data.model.Artwork
import com.example.androidworks.data.model.ArtworkDetails

object MockData {

    val artworks: List<Artwork> = listOf(
        Artwork(
            objectId = 437133,
            title = "Wheat Field with Cypresses",
            artistDisplayName = "Vincent van Gogh",
            primaryImageSmall = "https://images.metmuseum.org/CRDImages/ep/web-large/DT1567.jpg",
            objectDate = "1889",
            department = "European Paintings"
        ),
        Artwork(
            objectId = 45434,
            title = "The Unicorn Tapestries",
            artistDisplayName = "Unknown",
            primaryImageSmall = "https://images.metmuseum.org/CRDImages/cl/web-large/DP118567.jpg",
            objectDate = "1495–1505",
            department = "The Cloisters"
        ),
        Artwork(
            objectId = 547802,
            title = "Self-Portrait",
            artistDisplayName = "Vincent van Gogh",
            primaryImageSmall = "https://images.metmuseum.org/CRDImages/ep/web-large/DT1502_cropped2.jpg",
            objectDate = "1887",
            department = "European Paintings"
        ),
        Artwork(
            objectId = 436535,
            title = "Irises",
            artistDisplayName = "Vincent van Gogh",
            primaryImageSmall = "https://images.metmuseum.org/CRDImages/ep/web-large/DP346341.jpg",
            objectDate = "1890",
            department = "European Paintings"
        ),
        Artwork(
            objectId = 436155,
            title = "Young Woman with a Water Pitcher",
            artistDisplayName = "Johannes Vermeer",
            primaryImageSmall = "https://images.metmuseum.org/CRDImages/ep/web-large/DP347482.jpg",
            objectDate = "ca. 1662",
            department = "European Paintings"
        ),
        Artwork(
            objectId = 459055,
            title = "The Death of Socrates",
            artistDisplayName = "Jacques Louis David",
            primaryImageSmall = "https://images.metmuseum.org/CRDImages/ep/web-large/DP-13139-001.jpg",
            objectDate = "1787",
            department = "European Paintings"
        ),
        Artwork(
            objectId = 11417,
            title = "Marble statue of a kouros (youth)",
            artistDisplayName = "Unknown",
            primaryImageSmall = "https://images.metmuseum.org/CRDImages/gr/web-large/DP-23489-001.jpg",
            objectDate = "ca. 590–580 BCE",
            department = "Greek and Roman Art"
        ),
        Artwork(
            objectId = 236629,
            title = "Birds of America",
            artistDisplayName = "John James Audubon",
            primaryImageSmall = "https://images.metmuseum.org/CRDImages/dp/web-large/DP245865.jpg",
            objectDate = "1827–38",
            department = "Drawings and Prints"
        )
    )

    fun getDetailsById(objectId: Int): ArtworkDetails? {
        val base = artworks.firstOrNull { it.objectId == objectId } ?: return null
        return ArtworkDetails(
            objectId = base.objectId,
            title = base.title,
            artistDisplayName = base.artistDisplayName,
            artistDisplayBio = when (base.artistDisplayName) {
                "Vincent van Gogh" -> "Dutch, Zundert 1853–1890 Auvers-sur-Oise"
                "Johannes Vermeer" -> "Dutch, Delft 1632–1675 Delft"
                "Jacques Louis David" -> "French, Paris 1748–1825 Brussels"
                "John James Audubon" -> "American, Les Cayes, Haiti 1785–1851 New York"
                "Unknown" -> "Unknown artist"
                else -> "Unknown"
            },
            objectDate = base.objectDate,
            medium = when (base.objectId) {
                437133 -> "Oil on canvas"
                45434 -> "Wool, silk, silver, and gilt wefts"
                547802 -> "Oil on canvas"
                436535 -> "Oil on canvas"
                436155 -> "Oil on canvas"
                459055 -> "Oil on canvas"
                11417 -> "Marble, Naxian"
                236629 -> "Hand-colored engraving"
                else -> "—"
            },
            dimensions = when (base.objectId) {
                437133 -> "28 3/4 x 36 3/4 in. (73 x 93.4 cm)"
                547802 -> "17 1/2 x 14 in. (44.5 x 35.5 cm)"
                else -> "—"
            },
            classification = when (base.department) {
                "European Paintings" -> "Paintings"
                "The Cloisters" -> "Textiles-Tapestries"
                "Greek and Roman Art" -> "Stone Sculpture"
                "Drawings and Prints" -> "Prints"
                else -> "—"
            },
            department = base.department,
            culture = "—",
            period = "—",
            dynasty = "—",
            country = "—",
            city = "—",
            creditLine = "The Metropolitan Museum of Art, New York",
            primaryImage = base.primaryImageSmall,
            primaryImageSmall = base.primaryImageSmall,
            isPublicDomain = true,
            isHighlight = base.objectId in listOf(437133, 436535, 436155, 459055, 11417),
            galleryNumber = when (base.objectId) {
                437133 -> "822"
                547802 -> "825"
                436535 -> "822"
                436155 -> "636"
                459055 -> "614"
                11417 -> "154"
                else -> "—"
            },
            objectURL = "https://www.metmuseum.org/art/collection/search/${base.objectId}",
            tags = listOf("Abstraction", "Landscape", "Nature")
        )
    }
}