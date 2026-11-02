package com.example.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.ZoomIn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.Product
import com.example.model.StoreInfo
import com.example.ui.theme.AmazonOrange
import com.example.ui.theme.EmeraldTrust
import com.example.ui.theme.GoldPrimary
import com.example.ui.theme.NavyDeep
import com.example.ui.theme.WhatsAppGreen
import com.example.util.IntentUtils

@Composable
fun ProductCard(
  product: Product,
  isFavorite: Boolean,
  onToggleFavorite: () -> Unit,
  onImageClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  val context = LocalContext.current

  Card(
    modifier = modifier
      .fillMaxWidth()
      .padding(horizontal = 16.dp, vertical = 8.dp)
      .testTag("product_card_${product.id}"),
    shape = RoundedCornerShape(18.dp),
    colors = CardDefaults.cardColors(
      containerColor = MaterialTheme.colorScheme.surface
    ),
    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
  ) {
    Column(modifier = Modifier.fillMaxWidth()) {
      // Clickable Image Banner with "Click for all info" hint
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .height(220.dp)
          .clip(RoundedCornerShape(topStart = 18.dp, topEnd = 18.dp))
          .clickable(
            onClick = onImageClick,
            onClickLabel = "Click image for full details"
          )
          .testTag("product_image_${product.id}")
      ) {
        Image(
          painter = painterResource(id = product.imageRes),
          contentDescription = "${product.title} - Click for all details",
          modifier = Modifier.matchParentSize(),
          contentScale = ContentScale.Crop
        )

        // Gradient overlay at top & bottom of image for readability
        Box(
          modifier = Modifier
            .matchParentSize()
            .background(
              Brush.verticalGradient(
                colors = listOf(
                  Color.Black.copy(alpha = 0.4f),
                  Color.Transparent,
                  Color.Black.copy(alpha = 0.65f)
                )
              )
            )
        )

        // Top Badges: Trust Badge + Wishlist Heart
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Surface(
            shape = RoundedCornerShape(20.dp),
            color = EmeraldTrust,
            shadowElevation = 2.dp
          ) {
            Row(
              modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
              verticalAlignment = Alignment.CenterVertically
            ) {
              Icon(
                imageVector = Icons.Default.Check,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(13.dp)
              )
              Spacer(modifier = Modifier.width(4.dp))
              Text(
                text = product.badge,
                style = MaterialTheme.typography.labelSmall,
                color = Color.White,
                fontWeight = FontWeight.Bold
              )
            }
          }

          IconButton(
            onClick = onToggleFavorite,
            modifier = Modifier
              .size(38.dp)
              .clip(CircleShape)
              .background(Color.Black.copy(alpha = 0.45f))
              .testTag("favorite_button_${product.id}")
          ) {
            Icon(
              imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
              contentDescription = if (isFavorite) "Remove from wishlist" else "Add to wishlist",
              tint = if (isFavorite) Color.Red else Color.White,
              modifier = Modifier.size(20.dp)
            )
          }
        }

        // Bottom Image Overlay: Explicit Tap Hint requested by user!
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .align(Alignment.BottomCenter)
            .background(NavyDeep.copy(alpha = 0.75f))
            .padding(horizontal = 12.dp, vertical = 7.dp),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
              imageVector = Icons.Default.ZoomIn,
              contentDescription = null,
              tint = GoldPrimary,
              modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
              text = "Tap image for full info & specs",
              style = MaterialTheme.typography.labelSmall,
              fontWeight = FontWeight.Bold,
              color = Color.White
            )
          }

          Text(
            text = "Code: ${product.authenticityCode}",
            style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
            color = GoldPrimary,
            fontWeight = FontWeight.SemiBold
          )
        }
      }

      // Card Content
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .padding(14.dp)
      ) {
        // Category and Rating
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Text(
            text = product.category.uppercase(),
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.ExtraBold,
            letterSpacing = 1.sp
          )

          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
              imageVector = Icons.Default.Star,
              contentDescription = null,
              tint = GoldPrimary,
              modifier = Modifier.size(15.dp)
            )
            Spacer(modifier = Modifier.width(3.dp))
            Text(
              text = "${product.rating} (${product.reviewCount})",
              style = MaterialTheme.typography.labelMedium,
              fontWeight = FontWeight.Bold,
              color = MaterialTheme.colorScheme.onSurface
            )
          }
        }

        // Title
        Text(
          text = product.title,
          style = MaterialTheme.typography.titleMedium,
          fontWeight = FontWeight.Bold,
          color = MaterialTheme.colorScheme.onSurface,
          modifier = Modifier.padding(top = 4.dp),
          maxLines = 1,
          overflow = TextOverflow.Ellipsis
        )

        // Subtitle
        Text(
          text = product.subtitle,
          style = MaterialTheme.typography.bodySmall,
          color = MaterialTheme.colorScheme.onSurfaceVariant,
          modifier = Modifier.padding(top = 2.dp),
          maxLines = 2,
          overflow = TextOverflow.Ellipsis
        )

        // Price & Stock row
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Row(verticalAlignment = Alignment.Bottom) {
            Text(
              text = "₹${product.price}",
              style = MaterialTheme.typography.titleLarge,
              fontWeight = FontWeight.ExtraBold,
              color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
              text = "₹${product.originalPrice}",
              style = MaterialTheme.typography.bodySmall,
              textDecoration = TextDecoration.LineThrough,
              color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.width(6.dp))
            Surface(
              shape = RoundedCornerShape(4.dp),
              color = EmeraldTrust.copy(alpha = 0.15f)
            ) {
              Text(
                text = "${product.discountPercent}% OFF",
                style = MaterialTheme.typography.labelSmall,
                color = EmeraldTrust,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
              )
            }
          }

          Text(
            text = if (product.inStock) "✓ In Stock (${product.stockLeft} left)" else "Out of Stock",
            style = MaterialTheme.typography.labelSmall,
            color = if (product.inStock) EmeraldTrust else Color.Red,
            fontWeight = FontWeight.Bold
          )
        }

        // Action Buttons Row
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .padding(top = 12.dp),
          horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
          // Open info dialog
          OutlinedButton(
            onClick = onImageClick,
            modifier = Modifier
              .weight(1f)
              .height(40.dp)
              .testTag("view_details_button_${product.id}"),
            shape = RoundedCornerShape(10.dp)
          ) {
            Icon(
              imageVector = Icons.Default.Info,
              contentDescription = null,
              modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
              text = "All Info",
              style = MaterialTheme.typography.labelMedium,
              fontWeight = FontWeight.Bold
            )
          }

          // Direct WhatsApp
          Button(
            onClick = {
              IntentUtils.openWhatsApp(
                context,
                StoreInfo.WHATSAPP_NUMBER,
                "Hello Ranjeet ji, I want to buy '${product.title}' (Price: ₹${product.price}) from RS Store!"
              )
            },
            modifier = Modifier
              .weight(1f)
              .height(40.dp)
              .testTag("whatsapp_buy_button_${product.id}"),
            colors = ButtonDefaults.buttonColors(
              containerColor = WhatsAppGreen,
              contentColor = Color.White
            ),
            shape = RoundedCornerShape(10.dp)
          ) {
            Icon(
              imageVector = Icons.Default.Send,
              contentDescription = null,
              modifier = Modifier.size(15.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
              text = "WhatsApp",
              style = MaterialTheme.typography.labelMedium,
              fontWeight = FontWeight.Bold
            )
          }

          // Amazon Store Button
          IconButton(
            onClick = { IntentUtils.openAmazonStore(context, product.amazonProductUrl) },
            modifier = Modifier
              .size(40.dp)
              .clip(RoundedCornerShape(10.dp))
              .background(AmazonOrange)
              .testTag("amazon_store_button_${product.id}")
          ) {
            Icon(
              imageVector = Icons.Default.ShoppingCart,
              contentDescription = "Amazon Store",
              tint = Color.White,
              modifier = Modifier.size(18.dp)
            )
          }
        }
      }
    }
  }
}
