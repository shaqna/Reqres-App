package com.maou.reqresapp.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.maou.reqresapp.data.mapper.toListDomainModel
import com.maou.reqresapp.data.source.remote.service.ApiService
import com.maou.reqresapp.domain.model.ReqresUser

class UsersPagingSource(private val apiService: ApiService): PagingSource<Int, ReqresUser>() {
    override fun getRefreshKey(state: PagingState<Int, ReqresUser>): Int? {
        return state.anchorPosition?.let {anchorPosistion ->
            val anchorPage = state.closestPageToPosition(anchorPosistion)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ReqresUser> {
        return try {
            val position = params.key ?: INITIAL_PAGE_INDEX
            val responseData = apiService.getUsers(page = position)
            val data = responseData.data.toListDomainModel()

            LoadResult.Page(
                data = data,
                prevKey = if (position == INITIAL_PAGE_INDEX) null else position-1,
                nextKey = if(data.isEmpty()) null else position + 1
            )
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }

    companion object {
        const val INITIAL_PAGE_INDEX = 1
    }
}