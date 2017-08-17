package dailySummary.repository;

import dailySummary.model.DailySummary;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface DailySummaryRepository extends MongoRepository<dailySummary.model.DailySummary, String> {
    List<DailySummary> findByIdentity(String identity);
    Long deleteByIdentity(String identity);
}
