package com.asetec.data.repository.activate

import com.asetec.domain.model.dto.ActivateDTO
import com.asetec.domain.repository.activate.ActivateRepository
import io.github.jan.supabase.postgrest.Postgrest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ActivateRepositoryImpl @Inject constructor(
    private val postgrest: Postgrest
) : ActivateRepository {
    override suspend fun insert(activateDTO: ActivateDTO, onTime: (Long) -> Unit) {
        onTime(0L)

        postgrest.from("Activity").insert(activateDTO)
    }

    override suspend fun delete(googleId: String, date: String, onSuccess: (Boolean) -> Unit) {
        postgrest.from("Activity").delete {
            filter {
                eq("google_id", googleId)
                eq("today_format", date)
            }
        }

        onSuccess(true)
    }

    override suspend fun selectActivateByGoogleId(googleId: String): List<ActivateDTO> {
        return withContext(Dispatchers.IO) {
            postgrest.from("Activity").select {
                filter {
                    eq("google_id", googleId)
                }
            }.decodeList<ActivateDTO>()
        }
    }

    override suspend fun selectActivateByDate(googleId: String, date: String): List<ActivateDTO> {
        return withContext(Dispatchers.IO) {
            postgrest.from("Activity").select {
                filter {
                    eq("google_id", googleId)
                    eq("eq_date", date)
                }
            }.decodeList<ActivateDTO>()
        }
    }

    override suspend fun selectActivityFindById(
        id: Int
    ): List<ActivateDTO> {
        return withContext(Dispatchers.IO) {
            postgrest.from("Activity").select {
                filter {
                    eq("id", id)
                }
            }.decodeList<ActivateDTO>()
        }
    }
}