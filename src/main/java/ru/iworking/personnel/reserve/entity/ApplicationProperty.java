package ru.iworking.personnel.reserve.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "APPLICATION_PROPERTIES")
public class ApplicationProperty {
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "APPLICATION_PROPERTIES_SEQ_GEN")
    @SequenceGenerator(name = "APPLICATION_PROPERTIES_SEQ_GEN", sequenceName = "APPLICATION_PROPERTIES_SEQ", initialValue = 1000, allocationSize = 1)
    @Column(name = "ID")
    protected Long id;
    @Column(name = "NAME")
    protected String name;
    @Column(name = "VALUE")
    protected String value;
}
