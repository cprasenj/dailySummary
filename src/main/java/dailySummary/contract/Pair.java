package dailySummary.contract;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pair {
    private String pair1;
    private String pair2;
    private Integer days = 0;
    private LocalDateTime date = LocalDateTime.now();
}
