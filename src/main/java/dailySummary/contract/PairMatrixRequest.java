package dailySummary.contract;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PairMatrixRequest {
    private String teamEmail;
    private List<Pair> pairs;
}
