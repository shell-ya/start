package com.starnft.star.application.process.user.res;

import com.starnft.star.domain.user.model.vo.PopupAgreementInfo;
import com.starnft.star.domain.user.model.vo.PopupInfo;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author WeiChunLAI
 * @date 2022/5/13 10:51
 */
@Data
@Accessors(chain = true)
public class PopupAgreementRes {

    private List<PopupAgreementInfo> popupAgreementInfoList;

    private PopupInfo popupInfo;
}
