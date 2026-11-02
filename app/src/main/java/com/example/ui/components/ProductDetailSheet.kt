package com.example.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Replay
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.Product
import com.example.model.StoreInfo
import com.example.ui.theme.AmazonOrange
import com.example.ui.theme.EmeraldTrust
import com.example.ui.theme.GoldPrimary
import com.example.ui.theme.GoogleMapsRed
import com.example.ui.theme.NavyDeep
import com.example.ui.theme.WhatsAppGreen
import com.example.util.IntentUtils

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailSheet(
  product: Product,
  isFavorite: Boolean,
  onToggleFavorite: () -> Unit,
  onDismiss: () -> Unit
) {
  val context = LocalContext.current
  val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

  var scale by remember { mutableFloatStateOf(1f) }
  var offsetX by remember { mutableFloatStateOf(0f) }
  var offsetY by remember { mutableFloatStateOf(0f) }

  ModalBottomSheet(
    onDismissRequest = onDismiss,
    sheetState = sheetState,
    containerColor = MaterialTheme.colorScheme.surface,
    dragHandle = null,
    modifier = Modifier.testTag("product_detail_sheet")
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .verticalScroll(rememberScrollState())
        .padding(bottom = 32.dp)
    ) {
      // Top Navigation Bar inside Sheet
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 16.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          IconButton(
            onClick = onDismiss,
            modifier = Modifier.testTag("detail_close_button")
          ) {
            Icon(imageVector = Icons.Default.Close, contentDescription = "Close")
          }
          Text(
            text = "Product & Trust Details",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
          )
        }

        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
          IconButton(
            onClick = { IntentUtils.shareProduct(context, product) },
            modifier = Modifier.testTag("detail_share_button")
          ) {
            Icon(imageVector = Icons.Default.Share, contentDescription = "Share")
          }
          IconButton(
            onClick = onToggleFavorite,
            modifier = Modifier.testTag("detail_favorite_button")
          ) {
            Icon(
              imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
              contentDescription = "Wishlist",
              tint = if (isFavorite) Color.Red else MaterialTheme.colorScheme.onSurface
            )
          }
        }
      }

      // Interactive Zoomable Image View
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .height(300.dp)
          .padding(horizontal = 16.dp)
          .clip(RoundedCornerShape(16.dp))
          .background(Color.Black.copy(alpha = 0.05f))
          .pointerInput(Unit) {
            detectTransformGestures { _, pan, zoom, _ ->
              scale = (scale * zoom).coerceIn(1f, 3.5f)
              if (scale > 1f) {
                offsetX += pan.x
                offsetY += pan.y
              } else {
                offsetX = 0f
                offsetY = 0f
              }
            }
          }
          .testTag("detail_zoomable_image")
      ) {
        Image(
          painter = painterResource(id = product.imageRes),
          contentDescription = product.title,
          modifier = Modifier
            .fillMaxSize()
            .graphicsLayer(
              scaleX = scale,
              scaleY = scale,
              translationX = offsetX,
              translationY = offsetY
            ),
          contentScale = ContentScale.Fit
        )

        // Reset zoom button if zoomed
        if (scale > 1.05f) {
          IconButton(
            onClick = {
              scale = 1f
              offsetX = 0f
              offsetY = 0f
            },
            modifier = Modifier
              .align(Alignment.BottomEnd)
              .padding(8.dp)
              .clip(CircleShape)
              .background(Color.Black.copy(alpha = 0.6f))
          ) {
            Icon(
              imageVector = Icons.Default.Replay,
              contentDescription = "Reset Zoom",
              tint = Color.White
            )
          }
        }

        // Pinch hint
        Surface(
          shape = RoundedCornerShape(20.dp),
          color = Color.Black.copy(alpha = 0.55f),
          modifier = Modifier
            .align(Alignment.BottomStart)
            .padding(10.dp)
        ) {
          Text(
            text = if (scale > 1f) "Zoomed: ${(scale * 100).toInt()}%" else "Pinch to Zoom Image",
            style = MaterialTheme.typography.labelSmall,
            color = Color.White,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
          )
        }
      }

      // Main Info Container
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .padding(16.dp)
      ) {
        // Authenticity Verified Certificate Card
        Card(
          shape = RoundedCornerShape(12.dp),
          colors = CardDefaults.cardColors(containerColor = EmeraldTrust.copy(alpha = 0.1f)),
          modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, EmeraldTrust.copy(alpha = 0.4f), RoundedCornerShape(12.dp))
        ) {
          Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
          ) {
            Icon(
              imageVector = Icons.Default.VerifiedUser,
              contentDescription = null,
              tint = EmeraldTrust,
              modifier = Modifier.size(28.dp)
            )
            Spacer(modifier = Modifier.width(10.dp))
            Column {
              Text(
                text = "100% Genuine Guaranteed",
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.Bold,
                color = EmeraldTrust
              )
              Text(
                text = "Sold & Certified by ${StoreInfo.OWNER_NAME} • SKU: ${product.authenticityCode}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
              )
            }
          }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Title & Rating
        Text(
          text = product.title,
          style = MaterialTheme.typography.headlineSmall,
          fontWeight = FontWeight.Bold,
          color = MaterialTheme.colorScheme.onSurface
        )

        Text(
          text = product.subtitle,
          style = MaterialTheme.typography.bodyMedium,
          color = MaterialTheme.colorScheme.onSurfaceVariant,
          modifier = Modifier.padding(top = 4.dp)
        )

        // Rating and reviews
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp),
          verticalAlignment = Alignment.CenterVertically
        ) {
          Icon(
            imageVector = Icons.Default.Star,
            contentDescription = null,
            tint = GoldPrimary,
            modifier = Modifier.size(18.dp)
          )
          Spacer(modifier = Modifier.width(4.dp))
          Text(
            text = "${product.rating} / 5.0",
            style = MaterialTheme.typography.labelLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
          )
          Spacer(modifier = Modifier.width(6.dp))
          Text(
            text = "• ${product.reviewCount} Verified Buyer Ratings",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
          )
        }

        // Pricing card
        Card(
          shape = RoundedCornerShape(12.dp),
          colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.4f)),
          modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp)
        ) {
          Row(
            modifier = Modifier
              .fillMaxWidth()
              .padding(14.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Column {
              Row(verticalAlignment = Alignment.Bottom) {
                Text(
                  text = "₹${product.price}",
                  style = MaterialTheme.typography.headlineMedium,
                  fontWeight = FontWeight.ExtraBold,
                  color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                  text = "MRP ₹${product.originalPrice}",
                  style = MaterialTheme.typography.bodyMedium,
                  textDecoration = TextDecoration.LineThrough,
                  color = MaterialTheme.colorScheme.onSurfaceVariant
                )
              }
              Text(
                text = "Inclusive of all taxes & free warranty",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
              )
            }

            Surface(
              shape = RoundedCornerShape(8.dp),
              color = EmeraldTrust
            ) {
              Text(
                text = "Save ₹${product.originalPrice - product.price} (${product.discountPercent}% OFF)",
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp)
              )
            }
          }
        }

        // Direct Purchase & Order Actions
        Text(
          text = "Quick Ordering & Inquiries",
          style = MaterialTheme.typography.titleSmall,
          fontWeight = FontWeight.Bold,
          modifier = Modifier.padding(top = 4.dp, bottom = 8.dp)
        )

        // 1. WhatsApp Button
        Button(
          onClick = {
            IntentUtils.openWhatsApp(
              context,
              StoreInfo.WHATSAPP_NUMBER,
              "Hello Ranjeet ji, I want to order '${product.title}' (₹${product.price}) with direct delivery!"
            )
          },
          modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .testTag("detail_order_whatsapp"),
          colors = ButtonDefaults.buttonColors(containerColor = WhatsAppGreen, contentColor = Color.White),
          shape = RoundedCornerShape(12.dp)
        ) {
          Icon(imageVector = Icons.Default.Send, contentDescription = null)
          Spacer(modifier = Modifier.width(8.dp))
          Text(
            text = "Order via WhatsApp (Best Price)",
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.Bold
          )
        }

        Spacer(modifier = Modifier.height(8.dp))

        // 2. Amazon Store Button
        Button(
          onClick = { IntentUtils.openAmazonStore(context, product.amazonProductUrl) },
          modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .testTag("detail_order_amazon"),
          colors = ButtonDefaults.buttonColors(containerColor = AmazonOrange, contentColor = Color.White),
          shape = RoundedCornerShape(12.dp)
        ) {
          Icon(imageVector = Icons.Default.ShoppingCart, contentDescription = null)
          Spacer(modifier = Modifier.width(8.dp))
          Text(
            text = "Buy on Verified Amazon Store",
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.Bold
          )
        }

        Spacer(modifier = Modifier.height(8.dp))

        // 3. Direct Call to Owner
        OutlinedButton(
          onClick = { IntentUtils.dialPhoneNumber(context, StoreInfo.PHONE_NUMBER) },
          modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .testTag("detail_call_owner"),
          shape = RoundedCornerShape(12.dp)
        ) {
          Icon(imageVector = Icons.Default.Call, contentDescription = null, tint = NavyDeep)
          Spacer(modifier = Modifier.width(8.dp))
          Text(
            text = "Call ${StoreInfo.OWNER_NAME} (${StoreInfo.PHONE_NUMBER})",
            style = MaterialTheme.typography.labelLarge,
            fontWeight = FontWeight.Bold
          )
        }

        // Trust Pillars Grid
        Spacer(modifier = Modifier.height(20.dp))
        Text(
          text = "Customer Trust Guarantees",
          style = MaterialTheme.typography.titleMedium,
          fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(10.dp))

        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
          TrustBadgeBox(
            title = "100% Original",
            subtitle = "Sealed Box",
            icon = Icons.Default.Verified,
            modifier = Modifier.weight(1f)
          )
          TrustBadgeBox(
            title = product.warrantyPeriod,
            subtitle = "Direct Support",
            icon = Icons.Default.Lock,
            modifier = Modifier.weight(1f)
          )
          TrustBadgeBox(
            title = product.returnPolicy,
            subtitle = "Easy Process",
            icon = Icons.Default.Replay,
            modifier = Modifier.weight(1f)
          )
        }

        // Description Section
        Spacer(modifier = Modifier.height(20.dp))
        Text(
          text = "About this Product",
          style = MaterialTheme.typography.titleMedium,
          fontWeight = FontWeight.Bold
        )
        Text(
          text = product.description,
          style = MaterialTheme.typography.bodyMedium,
          color = MaterialTheme.colorScheme.onSurface,
          lineHeight = 22.sp,
          modifier = Modifier.padding(top = 6.dp)
        )

        // Highlights
        Spacer(modifier = Modifier.height(16.dp))
        Text(
          text = "Key Highlights",
          style = MaterialTheme.typography.titleMedium,
          fontWeight = FontWeight.Bold
        )
        Column(
          modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp),
          verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
          product.highlights.forEach { highlight ->
            Row(verticalAlignment = Alignment.Top) {
              Icon(
                imageVector = Icons.Default.Check,
                contentDescription = null,
                tint = EmeraldTrust,
                modifier = Modifier
                  .size(18.dp)
                  .padding(top = 2.dp)
              )
              Spacer(modifier = Modifier.width(8.dp))
              Text(
                text = highlight,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface
              )
            }
          }
        }

        // Specifications Table
        Spacer(modifier = Modifier.height(20.dp))
        Text(
          text = "Technical Specifications",
          style = MaterialTheme.typography.titleMedium,
          fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))

        Card(
          shape = RoundedCornerShape(12.dp),
          colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f)),
          modifier = Modifier.fillMaxWidth()
        ) {
          Column(modifier = Modifier.fillMaxWidth()) {
            product.specs.entries.forEachIndexed { index, (key, value) ->
              Row(
                modifier = Modifier
                  .fillMaxWidth()
                  .background(
                    if (index % 2 == 0) Color.Transparent else MaterialTheme.colorScheme.surface.copy(alpha = 0.5f)
                  )
                  .padding(horizontal = 14.dp, vertical = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween
              ) {
                Text(
                  text = key,
                  style = MaterialTheme.typography.bodySmall,
                  fontWeight = FontWeight.SemiBold,
                  color = MaterialTheme.colorScheme.onSurfaceVariant,
                  modifier = Modifier.weight(0.45f)
                )
                Text(
                  text = value,
                  style = MaterialTheme.typography.bodySmall,
                  fontWeight = FontWeight.Bold,
                  color = MaterialTheme.colorScheme.onSurface,
                  modifier = Modifier.weight(0.55f)
                )
              }
              if (index < product.specs.size - 1) {
                HorizontalDivider(color = DividerDefaults.color.copy(alpha = 0.3f))
              }
            }
          }
        }

        // Package Contents Checklist
        Spacer(modifier = Modifier.height(20.dp))
        Text(
          text = "Package Contents (What's in the Box)",
          style = MaterialTheme.typography.titleMedium,
          fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))

        Card(
          shape = RoundedCornerShape(12.dp),
          colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
          elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
          modifier = Modifier.fillMaxWidth()
        ) {
          Column(
            modifier = Modifier.padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
          ) {
            product.packageIncludes.forEach { item ->
              Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                  imageVector = Icons.Default.Check,
                  contentDescription = null,
                  tint = EmeraldTrust,
                  modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                  text = item,
                  style = MaterialTheme.typography.bodySmall,
                  fontWeight = FontWeight.Medium
                )
              }
            }
          }
        }

        // Verified Customer Reviews for this item
        Spacer(modifier = Modifier.height(20.dp))
        Text(
          text = "Verified Customer Reviews (${product.verifiedReviews.size})",
          style = MaterialTheme.typography.titleMedium,
          fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))

        Column(
          modifier = Modifier.fillMaxWidth(),
          verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
          product.verifiedReviews.forEach { review ->
            Card(
              shape = RoundedCornerShape(12.dp),
              colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
              elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
              modifier = Modifier.fillMaxWidth()
            ) {
              Column(modifier = Modifier.padding(12.dp)) {
                Row(
                  modifier = Modifier.fillMaxWidth(),
                  horizontalArrangement = Arrangement.SpaceBetween,
                  verticalAlignment = Alignment.CenterVertically
                ) {
                  Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                      modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape)
                        .background(Color(review.avatarColorHex)),
                      contentAlignment = Alignment.Center
                    ) {
                      Text(
                        text = review.customerName.take(1),
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                      )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                      Text(
                        text = review.customerName,
                        style = MaterialTheme.typography.labelLarge,
                        fontWeight = FontWeight.Bold
                      )
                      Text(
                        text = review.location,
                        style = MaterialTheme.typography.bodySmall.copy(fontSize = 10.sp),
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                      )
                    }
                  }

                  Row(verticalAlignment = Alignment.CenterVertically) {
                    repeat(review.rating) {
                      Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = null,
                        tint = GoldPrimary,
                        modifier = Modifier.size(14.dp)
                      )
                    }
                  }
                }

                Text(
                  text = review.comment,
                  style = MaterialTheme.typography.bodySmall,
                  color = MaterialTheme.colorScheme.onSurface,
                  modifier = Modifier.padding(top = 8.dp)
                )

                Row(
                  modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 6.dp),
                  horizontalArrangement = Arrangement.SpaceBetween
                ) {
                  Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                      imageVector = Icons.Default.Check,
                      contentDescription = null,
                      tint = EmeraldTrust,
                      modifier = Modifier.size(12.dp)
                    )
                    Spacer(modifier = Modifier.width(3.dp))
                    Text(
                      text = "Verified Buyer",
                      style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
                      color = EmeraldTrust,
                      fontWeight = FontWeight.Bold
                    )
                  }
                  Text(
                    text = review.date,
                    style = MaterialTheme.typography.bodySmall.copy(fontSize = 10.sp),
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                  )
                }
              }
            }
          }
        }

        // Shop Address info card at bottom
        Spacer(modifier = Modifier.height(20.dp))
        Card(
          shape = RoundedCornerShape(12.dp),
          colors = CardDefaults.cardColors(containerColor = NavyDeep.copy(alpha = 0.05f)),
          modifier = Modifier.fillMaxWidth()
        ) {
          Row(
            modifier = Modifier
              .fillMaxWidth()
              .clickable { IntentUtils.openShopLocation(context, StoreInfo.SHOP_MAPS_URL) }
              .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
          ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Icon(
                imageVector = Icons.Default.LocationOn,
                contentDescription = null,
                tint = GoogleMapsRed,
                modifier = Modifier.size(24.dp)
              )
              Spacer(modifier = Modifier.width(10.dp))
              Column {
                Text(
                  text = "Prefer to visit our shop in person?",
                  style = MaterialTheme.typography.labelMedium,
                  fontWeight = FontWeight.Bold
                )
                Text(
                  text = "Tap to open exact Google Maps location",
                  style = MaterialTheme.typography.bodySmall,
                  color = MaterialTheme.colorScheme.primary
                )
              }
            }
          }
        }
      }
    }
  }
}

@Composable
private fun TrustBadgeBox(
  title: String,
  subtitle: String,
  icon: androidx.compose.ui.graphics.vector.ImageVector,
  modifier: Modifier = Modifier
) {
  Card(
    modifier = modifier,
    shape = RoundedCornerShape(10.dp),
    colors = CardDefaults.cardColors(
      containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.35f)
    )
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp),
      horizontalAlignment = Alignment.CenterHorizontally,
      verticalArrangement = Arrangement.Center
    ) {
      Icon(
        imageVector = icon,
        contentDescription = null,
        tint = MaterialTheme.colorScheme.primary,
        modifier = Modifier.size(20.dp)
      )
      Spacer(modifier = Modifier.height(4.dp))
      Text(
        text = title,
        style = MaterialTheme.typography.labelSmall,
        fontWeight = FontWeight.Bold,
        maxLines = 1
      )
      Text(
        text = subtitle,
        style = MaterialTheme.typography.bodySmall.copy(fontSize = 10.sp),
        color = MaterialTheme.colorScheme.onSurfaceVariant
      )
    }
  }
}
