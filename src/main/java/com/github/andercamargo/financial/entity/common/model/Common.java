package com.github.andercamargo.financial.entity.common.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@ToString
@NoArgsConstructor
public abstract class Common <ID extends Serializable> implements Serializable {
    @JsonIgnore
    private ID id;
}
