package com.news.backendproject.dto.news;

import com.news.backendproject.dto.NewsGeneralDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@SuperBuilder
public class AdminNewsDto extends NewsGeneralDTO {
}
