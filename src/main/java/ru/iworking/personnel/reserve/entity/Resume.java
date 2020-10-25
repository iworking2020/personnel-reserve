package ru.iworking.personnel.reserve.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.joda.time.LocalDateTime;
import ru.iworking.personnel.reserve.converter.LocalDateTimeDeserializer;
import ru.iworking.personnel.reserve.converter.LocalDateTimeSerializer;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Builder
@Table(name = "RESUME")
public class Resume implements Cloneable {

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RESUME_SEQ_GEN")
    @SequenceGenerator(name = "RESUME_SEQ_GEN", sequenceName = "RESUME_SEQ", initialValue = 1000, allocationSize = 1)
    @Column(name = "ID")
    @EqualsAndHashCode.Exclude
    private Long id;

    @Column(name = "USER_ID")
    private Long userId;

    @JsonSerialize(converter = LocalDateTimeSerializer.class)
    @JsonDeserialize(converter = LocalDateTimeDeserializer.class)
    @Column(name = "DATE_CREATE")
    private LocalDateTime dateCreate = LocalDateTime.now();

    @OneToOne(fetch = FetchType.EAGER, orphanRemoval = true, cascade = CascadeType.ALL)
    @JoinColumn(name="PROFILE_ID")
    private Profile profile;

    @Column(name = "PROFESSION")
    private String profession;

    @OneToOne(fetch = FetchType.EAGER, orphanRemoval = true, cascade = CascadeType.ALL)
    @JoinColumn(name="NUMBER_PHONE_ID")
    private NumberPhone numberPhone;

    @Column(name = "EMAIL")
    private String email;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "PROF_FIELD_ID")
    private ProfField profField;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "WORK_TYPE_ID")
    private WorkType workType;

    @OneToOne(fetch = FetchType.EAGER, orphanRemoval = true, cascade = CascadeType.ALL)
    @JoinColumn(name="WAGE_ID")
    private Wage wage;

    @OneToOne(fetch = FetchType.EAGER, orphanRemoval = true, cascade = CascadeType.ALL)
    @JoinColumn(name="ADDRESS_ID")
    private Address address;

    @OneToOne(fetch = FetchType.EAGER, orphanRemoval = true, cascade = CascadeType.ALL)
    @JoinColumn(name="IMAGE_CONTAINER_ID")
    private ImageContainer photo;

    @JoinTable(name = "RESUME_LEARNING_HISTORY")
    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LearningHistory> learningHistoryList = new LinkedList<>();

    @JoinTable(name = "RESUME_EXPERIENCE_HISTORY")
    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("dateStart DESC")
    private List<ExperienceHistory> experienceHistoryList = new LinkedList<>();

    @JsonIgnore
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "resume")
    private List<Click> clicks = new LinkedList<>();

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
