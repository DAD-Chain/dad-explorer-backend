package com.github.dadchain.internal.dsp;

import com.github.dadchain.internal.dsp.model.*;
import com.github.dadchain.model.common.ResponseBean;
import feign.Headers;
import feign.RequestLine;
import org.springframework.web.bind.annotation.RequestBody;

@Headers("Accept: application/json")
public interface DSPClient {
    @RequestLine("POST /internal/award/user_award_lists")
    @Headers("Content-Type: application/json")
    ResponseBean<AwardDetailDto> getAwardList(@RequestBody GetAwardsListRequest request);

    @RequestLine("POST /internal/award/get")
    @Headers("Content-Type: application/json")
    ResponseBean<GetAwardResponse> getAward(@RequestBody GetAwardRequest request);

    @RequestLine("POST /internal/award/get/task")
    @Headers("Content-Type: application/json")
    ResponseBean<UserAwardRecord> getTask(@RequestBody GetAwardTaskRequest request);


    @RequestLine("POST /internal/ad/wall/get")
    @Headers("Content-Type: application/json")
    ResponseBean<AdWallDetailDto> getAdWall(@RequestBody GetADWallRequest request);

}
