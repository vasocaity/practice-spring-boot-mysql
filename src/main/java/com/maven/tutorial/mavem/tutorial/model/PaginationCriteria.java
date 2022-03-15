package com.maven.tutorial.mavem.tutorial.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaginationCriteria {
  private int pageNo = 1;
  private int itemPerPage = 20;
  private String direction = "desc";
  private String sort = "created";

  public PageRequest genPageRequest() {
    return PageRequest.of(pageNo - 1, itemPerPage, setDirection());
  }

  public String getSort() {
    return sort;
  }

  public void setSort(String sort) {
    this.sort = sort;
  }

  private Sort setDirection() {
    if (direction.equals("asc")) {
      return Sort.by(Sort.Direction.ASC, sort);
    } else {
      return Sort.by(Sort.Direction.DESC, sort);
    }
  }
}
