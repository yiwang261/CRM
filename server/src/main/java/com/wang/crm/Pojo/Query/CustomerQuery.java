package com.wang.crm.Pojo.Query;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class CustomerQuery extends BaseQuery{
    List<Integer> ids;
}
