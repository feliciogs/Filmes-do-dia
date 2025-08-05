import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.fenixgs.filmedodia.data.api.dto.MovieDTO

@Composable
fun MovieDialog(
    movie: MovieDTO,
    onDismiss: () -> Unit,
    onMarkAsWatched: () -> Unit
) {
    AlertDialog(
        onDismissRequest = { onDismiss() },
        confirmButton = {
            TextButton(onClick = onMarkAsWatched) {
                Text("Marcar como assistido")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Fechar")
            }
        },
        title = { Text(text = movie.title) },
        text = {
            Column {
                val imageUrl = "https://image.tmdb.org/t/p/w500${movie.poster_path}"
                Image(
                    painter = rememberImagePainter(imageUrl),
                    contentDescription = movie.title,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Nota: ${movie.vote_average}")
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = movie.overview)
            }
        }
    )
}