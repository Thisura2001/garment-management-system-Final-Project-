package lk.ijse.Dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString

public class rowMaterialDto {
    String row_id;
    String name;
    String unitPrice;
    int qty;

}
