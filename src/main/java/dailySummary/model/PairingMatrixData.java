package dailySummary.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PairingMatrixData {
    private String pair1;
    private String pair2;
    private Integer days;
    private LocalDateTime date;
    private String teamEmail;
}
