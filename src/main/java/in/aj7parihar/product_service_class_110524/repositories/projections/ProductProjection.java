package in.aj7parihar.product_service_class_110524.repositories.projections;

import in.aj7parihar.product_service_class_110524.models.Category;

import java.math.BigDecimal;
import java.util.Date;


public interface ProductProjection {

    Long getId();
    String getTitle();

    Date getCreatedAt();
    Date getUpdatedAt();
    Boolean getIsDeleted();

    BigDecimal getPrice();
    String getDescription();
    String getImageUrl();

    Category getCategory();

}
