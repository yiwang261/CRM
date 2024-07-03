package com.wang.crm.Pojo.Query;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

/**
 * @author yiwan
 */
@ToString
@Data
public class BaseQuery {
    private Integer current;
    private Integer size;
    private String filterSql;
}
