package com.example.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.ContactSupport
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.StoreRepository
import com.example.model.Product
import com.example.model.StoreInfo
import com.example.ui.components.DirectContactDialog
import com.example.ui.components.ProductCard
import com.example.ui.components.ProductDetailSheet
import com.example.ui.components.QuickActionBar
import com.example.ui.components.ReviewsSection
import com.example.ui.components.StoreLocationCard
import com.example.ui.components.TrustGuaranteesSection
import com.example.ui.components.TrustHeader
import com.example.ui.theme.EmeraldTrust
import com.example.ui.theme.GoldPrimary
import com.example.ui.theme.NavyDeep
import com.example.ui.theme.WhatsAppGreen
import com.example.util.IntentUtils

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {
  val context = LocalContext.current

  var selectedProduct by remember { mutableStateOf<Product?>(null) }
  var showContactSheet by remember { mutableStateOf(false) }
  var favorites by remember { mutableStateOf(setOf<String>()) }
  var selectedTab by remember { mutableIntStateOf(0) } // 0: Catalogue, 1: Trust & Reviews, 2: Visit Store
  var searchQuery by remember { mutableStateOf("") }
  var selectedCategory by remember { mutableStateOf("All") }
  var showFavoritesOnly by remember { mutableStateOf(false) }

  val filteredProducts by remember(searchQuery, selectedCategory, showFavoritesOnly, favorites) {
    derivedStateOf {
      StoreRepository.products.filter { product ->
        val matchesCategory = (selectedCategory == "All" || product.category == selectedCategory)
        val matchesSearch = searchQuery.isBlank() ||
            product.title.contains(searchQuery, ignoreCase = true) ||
            product.subtitle.contains(searchQuery, ignoreCase = true) ||
            product.description.contains(searchQuery, ignoreCase = true)
        val matchesFavorites = !showFavoritesOnly || favorites.contains(product.id)

        matchesCategory && matchesSearch && matchesFavorites
      }
    }
  }

  Scaffold(
    topBar = {
      CenterAlignedTopAppBar(
        title = {
          Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Text(
                text = StoreInfo.STORE_NAME,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.ExtraBold,
                color = Color.White
              )
              Spacer(modifier = Modifier.width(4.dp))
              Icon(
                imageVector = Icons.Default.Verified,
                contentDescription = "Verified Official Store",
                tint = EmeraldTrust,
                modifier = Modifier.size(17.dp)
              )
            }
            Text(
              text = "${StoreInfo.OWNER_NAME} • Ph: ${StoreInfo.PHONE_NUMBER}",
              style = MaterialTheme.typography.labelSmall.copy(fontSize = 11.sp),
              color = GoldPrimary,
              fontWeight = FontWeight.SemiBold
            )
          }
        },
        actions = {
          // Direct call button
          IconButton(
            onClick = { IntentUtils.dialPhoneNumber(context, StoreInfo.PHONE_NUMBER) },
            modifier = Modifier.testTag("topbar_call_button")
          ) {
            Icon(
              imageVector = Icons.Default.Call,
              contentDescription = "Call Store",
              tint = Color.White
            )
          }

          // Wishlist filter button
          IconButton(
            onClick = { showFavoritesOnly = !showFavoritesOnly },
            modifier = Modifier.testTag("topbar_wishlist_button")
          ) {
            BadgedBox(
              badge = {
                if (favorites.isNotEmpty()) {
                  Badge { Text("${favorites.size}") }
                }
              }
            ) {
              Icon(
                imageVector = if (showFavoritesOnly) Icons.Default.Favorite else Icons.Default.Favorite,
                contentDescription = "Wishlist",
                tint = if (showFavoritesOnly) Color.Red else Color.White.copy(alpha = 0.85f)
              )
            }
          }

          // Contact Owner info sheet
          IconButton(
            onClick = { showContactSheet = true },
            modifier = Modifier.testTag("topbar_contact_button")
          ) {
            Icon(
              imageVector = Icons.Default.ContactSupport,
              contentDescription = "Owner Contact Info",
              tint = GoldPrimary
            )
          }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
          containerColor = NavyDeep,
          titleContentColor = Color.White
        )
      )
    },
    bottomBar = {
      NavigationBar(
        containerColor = MaterialTheme.colorScheme.surface,
        tonalElevation = 6.dp
      ) {
        NavigationBarItem(
          selected = selectedTab == 0,
          onClick = {
            selectedTab = 0
            showFavoritesOnly = false
          },
          icon = { Icon(Icons.Default.ShoppingBag, contentDescription = "Products") },
          label = { Text("Catalogue") },
          modifier = Modifier.testTag("tab_catalogue")
        )

        NavigationBarItem(
          selected = selectedTab == 1,
          onClick = { selectedTab = 1 },
          icon = { Icon(Icons.Default.Star, contentDescription = "Reviews") },
          label = { Text("Trust & Reviews") },
          modifier = Modifier.testTag("tab_reviews")
        )

        NavigationBarItem(
          selected = selectedTab == 2,
          onClick = { selectedTab = 2 },
          icon = { Icon(Icons.Default.LocationOn, contentDescription = "Visit Shop") },
          label = { Text("Visit Store") },
          modifier = Modifier.testTag("tab_location")
        )
      }
    },
    floatingActionButton = {
      ExtendedFloatingActionButton(
        onClick = {
          IntentUtils.openWhatsApp(
            context,
            StoreInfo.WHATSAPP_NUMBER,
            "Hello Ranjeet ji, I want to talk about products in your RS Store!"
          )
        },
        icon = { Icon(Icons.Default.Send, contentDescription = null, tint = Color.White) },
        text = {
          Text(
            text = "Chat on WhatsApp",
            fontWeight = FontWeight.Bold,
            color = Color.White
          )
        },
        containerColor = WhatsAppGreen,
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier.testTag("fab_whatsapp")
      )
    }
  ) { paddingValues ->
    Box(
      modifier = Modifier
        .fillMaxSize()
        .padding(paddingValues)
        .background(MaterialTheme.colorScheme.background)
    ) {
      when (selectedTab) {
        0 -> {
          // Catalogue Tab
          LazyColumn(
            modifier = Modifier
              .fillMaxSize()
              .testTag("catalogue_list"),
            contentPadding = PaddingValues(bottom = 80.dp)
          ) {
            // 1. Trust Hero Header
            item {
              TrustHeader(
                onImageInfoClick = {
                  // Open first product for demo inspection
                  selectedProduct = StoreRepository.products.firstOrNull()
                }
              )
            }

            // 2. Quick Action Bar (Call, WhatsApp, Amazon, Instagram, Maps)
            item {
              QuickActionBar()
            }

            // 3. Search Bar
            item {
              Column(
                modifier = Modifier
                  .fillMaxWidth()
                  .padding(horizontal = 16.dp, vertical = 6.dp)
              ) {
                OutlinedTextField(
                  value = searchQuery,
                  onValueChange = { searchQuery = it },
                  modifier = Modifier
                    .fillMaxWidth()
                    .testTag("search_text_field"),
                  placeholder = { Text("Search products, audio, watches, bags...") },
                  leadingIcon = {
                    Icon(imageVector = Icons.Default.Search, contentDescription = null)
                  },
                  trailingIcon = {
                    if (searchQuery.isNotEmpty()) {
                      IconButton(onClick = { searchQuery = "" }) {
                        Icon(imageVector = Icons.Default.Clear, contentDescription = "Clear search")
                      }
                    }
                  },
                  singleLine = true,
                  shape = RoundedCornerShape(14.dp),
                  colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.surface,
                    unfocusedContainerColor = MaterialTheme.colorScheme.surface
                  )
                )

                // Category Chips
                Row(
                  modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
                    .horizontalScroll(rememberScrollState()),
                  horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                  StoreRepository.getCategories().forEach { cat ->
                    val isSelected = selectedCategory == cat
                    FilterChip(
                      selected = isSelected,
                      onClick = { selectedCategory = cat },
                      label = { Text(cat, fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal) },
                      colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = MaterialTheme.colorScheme.primary,
                        selectedLabelColor = Color.White
                      ),
                      modifier = Modifier.testTag("category_chip_$cat")
                    )
                  }
                }
              }
            }

            // 4. Products Header & Click-on-Image Reminder
            item {
              Row(
                modifier = Modifier
                  .fillMaxWidth()
                  .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
              ) {
                Column {
                  Text(
                    text = if (showFavoritesOnly) "Saved Wishlist (${filteredProducts.size})" else "Featured Stock (${filteredProducts.size})",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                  )
                  Text(
                    text = "Tap image on any card to view 100% details",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.SemiBold
                  )
                }
              }
            }

            // 5. Product Cards
            if (filteredProducts.isEmpty()) {
              item {
                Card(
                  modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                  shape = RoundedCornerShape(16.dp)
                ) {
                  Column(
                    modifier = Modifier
                      .fillMaxWidth()
                      .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                  ) {
                    Text(
                      text = "No matching products found",
                      style = MaterialTheme.typography.titleMedium,
                      fontWeight = FontWeight.Bold
                    )
                    Text(
                      text = "Try clearing your search query or switching categories.",
                      style = MaterialTheme.typography.bodySmall,
                      color = MaterialTheme.colorScheme.onSurfaceVariant,
                      modifier = Modifier.padding(top = 4.dp)
                    )
                  }
                }
              }
            } else {
              items(
                items = filteredProducts,
                key = { it.id }
              ) { product ->
                ProductCard(
                  product = product,
                  isFavorite = favorites.contains(product.id),
                  onToggleFavorite = {
                    favorites = if (favorites.contains(product.id)) {
                      favorites - product.id
                    } else {
                      favorites + product.id
                    }
                  },
                  onImageClick = {
                    // Open "all info if i click on image" modal!
                    selectedProduct = product
                  }
                )
              }
            }

            // 6. Customer Trust Section at bottom of catalogue
            item {
              Spacer(modifier = Modifier.height(16.dp))
              TrustGuaranteesSection()
            }

            // 7. Store Location summary
            item {
              StoreLocationCard()
            }
          }
        }

        1 -> {
          // Trust & Reviews Tab
          LazyColumn(
            modifier = Modifier
              .fillMaxSize()
              .testTag("reviews_tab_list"),
            contentPadding = PaddingValues(top = 12.dp, bottom = 80.dp)
          ) {
            item {
              TrustGuaranteesSection()
            }
            item {
              ReviewsSection()
            }
          }
        }

        2 -> {
          // Visit Store Tab
          LazyColumn(
            modifier = Modifier
              .fillMaxSize()
              .testTag("location_tab_list"),
            contentPadding = PaddingValues(top = 12.dp, bottom = 80.dp)
          ) {
            item {
              StoreLocationCard()
            }
            item {
              QuickActionBar()
            }
            item {
              TrustGuaranteesSection()
            }
          }
        }
      }

      // "All info if I click on image" Detail Modal Sheet
      selectedProduct?.let { product ->
        ProductDetailSheet(
          product = product,
          isFavorite = favorites.contains(product.id),
          onToggleFavorite = {
            favorites = if (favorites.contains(product.id)) {
              favorites - product.id
            } else {
              favorites + product.id
            }
          },
          onDismiss = { selectedProduct = null }
        )
      }

      // Direct Owner Contact Bottom Sheet
      if (showContactSheet) {
        DirectContactDialog(onDismiss = { showContactSheet = false })
      }
    }
  }
}
