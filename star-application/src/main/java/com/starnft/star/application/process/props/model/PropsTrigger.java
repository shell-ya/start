package com.starnft.star.application.process.props.model;

import com.starnft.star.domain.prop.model.vo.PropsVO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PropsTrigger implements Serializable {

    private Long userId;

    private PropsVO prop;

}
