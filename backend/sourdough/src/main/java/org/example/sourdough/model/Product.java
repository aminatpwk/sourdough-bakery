package org.example.sourdough.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name="products")
public class Product {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Product name is required")
    @Size(min = 3, max = 100, message = "Product name must be between 3 and 100 characters")
    private String name;

    @Size(max = 1000, message = "Description cannot exceed 1000 characters")
    private String description;

    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.01", message = "Price must be greater than 0")
    private BigDecimal price;

    @NotNull(message = "Category is required")
    private Long category_id;

    private String image_url;

    @Min(value = 0, message = "Preparation time cannot be negative")
    private int prep_time_hours;

    private boolean is_available;
    private boolean is_featured;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy'T'HH:mm:ss")
    @Temporal(TemporalType.TIMESTAMP)
    private Date created_at;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy'T'HH:mm:ss")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updated_at;

    public Product() {}

    public Product(String name, String description, BigDecimal price, Long category_id) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.category_id = category_id;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Long getCategory_id() {
        return category_id;
    }
    public void setCategory_id(Long category_id) {
        this.category_id = category_id;
    }

    public String getImage_url() {
        return image_url;
    }
    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public int getPrep_time_hours() {
        return prep_time_hours;
    }
    public void setPrep_time_hours(int prep_time_hours) {
        this.prep_time_hours = prep_time_hours;
    }

    public boolean isIs_available() {
        return is_available;
    }
    public void setIs_available(boolean is_available) {
        this.is_available = is_available;
    }

    public boolean isIs_featured() {
        return is_featured;
    }
    public void setIs_featured(boolean is_featured) {
        this.is_featured = is_featured;
    }

    public Date getCreated_at() {
        return created_at;
    }
    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public Date getUpdated_at() {
        return updated_at;
    }
    public void setUpdated_at(Date updated_at) {
        this.updated_at = updated_at;
    }
}
