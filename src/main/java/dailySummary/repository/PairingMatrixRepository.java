package dailySummary.repository;


import dailySummary.contract.Pair;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface PairingMatrixRepository extends MongoRepository<Pair, String> {
    List<Pair> getByPair1AndPair2(String pair1, String pair2);
}
