package lost.canvas.micronaut_test.repository;

import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.PageableRepository;
import lost.canvas.micronaut_test.entity.Company;

@Repository
public interface CompanyRepository extends PageableRepository<Company, Long> {
}
