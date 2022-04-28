package com.example.hibernatesearchexample.product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.TermVector;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Indexed
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Field(termVector = TermVector.YES)
    private String name;

    @Field(termVector = TermVector.YES)
    private String description;

    @Field
    private int productionYear;

    public ProductDTO toDTO() {
        return new ProductDTO(this.id, this.name, this.description, this.productionYear);
    }
}
