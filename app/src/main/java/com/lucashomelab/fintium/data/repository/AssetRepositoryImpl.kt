package com.lucashomelab.fintium.data.repository

import com.lucashomelab.fintium.data.local.dao.AssetDao
import com.lucashomelab.fintium.data.local.entity.AssetEntity
import com.lucashomelab.fintium.data.remote.api.FinanceApi
import com.lucashomelab.fintium.domain.model.Asset
import com.lucashomelab.fintium.domain.model.AssetType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AssetRepositoryImpl @Inject constructor(
    private val assetDao: AssetDao,
    private val financeApi: FinanceApi
) : AssetRepository {

    override fun getAllAssets(): Flow<List<Asset>> {
        return assetDao.getAllAssets().map { entities ->
            entities.map { it.toAsset() }
        }
    }

    override fun getAssetsByType(type: AssetType): Flow<List<Asset>> {
        return assetDao.getAssetsByType(type.name).map { entities ->
            entities.map { it.toAsset() }
        }
    }

    override suspend fun getAssetById(id: String): Asset? {
        return assetDao.getAssetById(id)?.toAsset()
    }

    override suspend fun addAsset(asset: Asset) {
        assetDao.insertAsset(AssetEntity.fromAsset(asset))
    }

    override suspend fun deleteAsset(asset: Asset) {
        assetDao.deleteAsset(AssetEntity.fromAsset(asset))
    }

    override suspend fun updateAssetPrices() {
        val assets = assetDao.getAllAssets()
        assets.collect { assetEntities ->
            assetEntities.forEach { asset ->
                try {
                    when (asset.type) {
                        AssetType.STOCK -> {
                            println("Updating stock: ${asset.symbol}")
                            val response = financeApi.getAssetPrice(symbol = asset.symbol)
                            println("Response received: $response")

                            response.quote?.let { quote ->
                                val price = quote.price.toDoubleOrNull()
                                println("Parsed price: $price")

                                price?.let {
                                    val updatedAsset = asset.copy(
                                        currentPrice = it,
                                        lastUpdated = System.currentTimeMillis()
                                    )
                                    println("Updating asset with: $updatedAsset")
                                    assetDao.insertAsset(updatedAsset)
                                }
                            }
                        }

                        AssetType.CRYPTOCURRENCY -> {
                            val response = financeApi.getCryptoPrice(symbol = asset.symbol)
                            val price = response.exchangeRate.exchangeRate.toDoubleOrNull()

                            price?.let {
                                assetDao.insertAsset(
                                    asset.copy(
                                        currentPrice = it,
                                        lastUpdated = System.currentTimeMillis()
                                    )
                                )
                            }
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }
}
