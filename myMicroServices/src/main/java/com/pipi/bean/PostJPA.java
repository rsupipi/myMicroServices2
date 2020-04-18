package com.pipi.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * This call contain jpa configurations
 */

@Data   //  lombok
@AllArgsConstructor //  lombok
@NoArgsConstructor  //  lombok
@Entity     //  data jpa
public class PostJPA {

    /* make this a primary key**/
    @Id     //  data jpa
    @GeneratedValue
    private int id;

    private String description;

    /**
     * in hibernate it'll automatically fetch, so post will fetch user and user will fetch the post, this will happens
     * recurcively. by using FetchType.LAZY, unless you call you call post.getdetails(), it'll not fetch
     */
    @ManyToOne(fetch = FetchType.LAZY)  // data jpa
    @JsonIgnore /*this field will ignored*/
    private UserJPA user;

    /** here we skip user,
     * because post will print the user and user will print the user, hence it'll end-up out of memory.
     * */
    @Override
    public String toString() {
        return "PostJPA{" +
                "id=" + id +
                ", description='" + description + '\'' +
                '}';
    }
}
