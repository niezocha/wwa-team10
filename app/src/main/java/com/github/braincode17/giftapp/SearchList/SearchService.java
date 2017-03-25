package com.github.braincode17.giftapp.SearchList;


import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface SearchService {

    @GET("grids")
    Observable<List<SingleSearchResult>> searchGrid();

    @GET("filter")
    Observable<List<SingleSearchResult>> search(@Query("tag") String tag,
                                                @Query("price_to") String priceTo,
                                                @Query("sort") String sortBy);
}
