package com.wang.crm.Pojo.DTO;

import com.wang.crm.Pojo.Entity.*;
import lombok.Data;
import lombok.ToString;

@ToString
@Data
public class ClueDTO {
    Integer id;
    private Clue clue;
    private User owner;
    private Activity activity;
    private DicValue appellation;
    private DicValue needLoan;
    private DicValue intentionState;
    private Product intentionProduct;
    private DicValue source;
    private DicValue state;

}
