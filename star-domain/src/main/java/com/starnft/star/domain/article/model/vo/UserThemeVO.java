package com.starnft.star.domain.article.model.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserThemeVO implements Serializable {
  private   Long   themeId;
  private   String themeName;
  private   String themeImages;
  private   Integer nums;
}
