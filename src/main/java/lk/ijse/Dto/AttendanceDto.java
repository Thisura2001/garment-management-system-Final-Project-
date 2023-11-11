package lk.ijse.Dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString

public class AttendanceDto {
    private String id;
    private String name;
    private String date;
    private String time;
    private String status;
}
