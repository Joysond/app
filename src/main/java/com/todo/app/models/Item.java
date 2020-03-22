package com.todo.app.models;

import com.todo.app.others.State;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name="items")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    private String item;

    @Enumerated(EnumType.STRING)
    private State state;

    private Date createdDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Item)) return false;
        Item item1 = (Item) o;
        return Objects.equals(getId(), item1.getId()) &&
                Objects.equals(item, item1.item) &&
                Objects.equals(createdDate, item1.createdDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), item, createdDate);
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

}
