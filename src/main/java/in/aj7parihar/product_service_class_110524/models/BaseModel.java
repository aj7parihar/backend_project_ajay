package in.aj7parihar.product_service_class_110524.models;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Getter
@Setter
@MappedSuperclass
public class BaseModel {
    // Here BaseModel class is an Abstract Class.
    // Abstract class is an idea or concept that does not exist physically.
    // Like Interface the Abstract class will also have behaviours which are not concrete.
    // And unlike Interface the Abstract class will also have attributes.

    // When we have attributes along with behaviours we go for abstract class.
    // Also, whenever we want to restrict object creation we go for abstract class.
    // 'Final' object will be created but class won't be extended further.
    //

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    /*
        The Product object’s 'id' property is annotated with @Id so that JPA recognizes it as the object’s ID.
        The 'id' property is also annotated with @GeneratedValue to indicate that the ID should be generated
        automatically.
     */
    private long id;

    @CreationTimestamp
    private Date createdAt;

    @UpdateTimestamp
    private Date updatedAt;

    private boolean isDeleted = false;
}
